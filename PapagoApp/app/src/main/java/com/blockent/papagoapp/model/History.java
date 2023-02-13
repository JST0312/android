package com.blockent.papagoapp.model;

import java.io.Serializable;

public class History implements Serializable {

    public String text;
    public String result;
    public String target;

    public History(){

    }

    public History(String text, String result, String target) {
        this.text = text;
        this.result = result;
        this.target = target;
    }
}
