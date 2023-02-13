package com.blockent.contactapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.blockent.contactapp.model.Contact;
import com.blockent.contactapp.util.Util;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DB_NAME, null, Util.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 테이블 생성
        String CREATE_CONTACT_TABLE = "create table contact ( id integer primary key, name text, phone text )";

        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 기존의 테이블을 삭제하고, 새 테이블을 다시 만든다.

        String DROP_TABLE = "drop table contact";
//        sqLiteDatabase.execSQL(DROP_TABLE);
        sqLiteDatabase.execSQL(DROP_TABLE, new String[]{Util.DB_NAME});

        onCreate(sqLiteDatabase);

    }

    // 이제부터는 우리가 앱 동작시키는데 필요한
    // CRUD 관련된 SQL문이 들어간,
    // 메소드를 만들면 된다.

    // 1. 연락처 추가하는 메소드(함수)  C
    public void addContact(Contact contact){
        // 1. 데이터베이스를 가져온다.
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. 저장가능한 형식으로 만들어 준다.
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.name);
        values.put(Util.KEY_PHONE, contact.phone);
        // 3. insert 한다.
        db.insert(Util.TABLE_NAME, null, values);
        // 4. db 사용이 끝나면, 닫아준다.
        db.close();
    }

    // 2. 저장된 연락처를 모두 가져오는 메소드 R
    public ArrayList<Contact> getAllContacts(){

        // 1. 데이터베이스를 가져온다.
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. 쿼리문 만든다.
        String query = "select * from contact";

        // 3. 쿼리문을 실행하여, 커서로 받는다.
        Cursor cursor = db.rawQuery(query, null);

        // 3-1. 여러 데이터를 저장할 어레이리스트 만든다.
        ArrayList<Contact> contactArrayList = new ArrayList<>();

        // 4. 커서에서 데이터를 뽑아낸다.
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);

                Log.i("Contact_TABLE", id + ", "+name+", "+phone);

                // 이 데이터를, 화면에 표시하기 위해서는
                // 메모리에 전부 다 남아있어야 한다!!!

                Contact contact = new Contact(id, name, phone);
                contactArrayList.add(0, contact);

            } while (cursor.moveToNext());
        }

        // 5. db닫기
        db.close();

        // 6. DB에서 읽어온 연락처 정보를 리턴해야 한다.

        return contactArrayList;

    }


    // 3. 연락처 업데이트하는 메소드  U
    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "update contact " +
                        "set name = ? , phone = ? " +
                        "where id = ? ";

        db.execSQL(query, new String[] {contact.name, contact.phone, contact.id+""} );

        db.close();
    }

    // 4. 연락처 삭제하는 메소드 D
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "delete from contact " +
                        "where id = ?";
        String[] args = new String[]{ contact.id+"" };

        db.execSQL(query, args);
        db.close();
    }
}









