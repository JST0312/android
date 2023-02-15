package com.blockent.postingapp.api;

import com.blockent.postingapp.model.PostingList;
import com.blockent.postingapp.model.Res;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostingApi {

    // 포스팅 생성 API
    @Multipart
    @POST("/posting")
    Call<Res> addPosting(@Header("Authorization") String token,
                         @Part MultipartBody.Part photo,
                         @Part("content")RequestBody content);

    // 친구들의 포스팅 가져오는 API
    @GET("/posting")
    Call<PostingList> getPosting(@Header("Authorization") String token,
                                 @Query("offset") int offset,
                                 @Query("limit") int limit);

    // 좋아요 하는 API
    @POST("/posting/{postingId}/like")
    Call<Res> setLike(@Header("Authorization") String token,
                      @Path("postingId") int postingId);

    // 좋아요 취소하는 API
    @DELETE("/posting/{postingId}/like")
    Call<Res> deleteLike(@Header("Authorization") String token,
                         @Path("postingId") int postingId);

}

















