package com.gjjg.camera_app_java.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
    private static final String LIST_SEPARATOR = "__,__";

    public static String convertListToString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : stringList) {
            stringBuilder.append(str).append(LIST_SEPARATOR);
        }

        if(stringBuilder.length() > 0){
            // Remove last separator
            stringBuilder.setLength(stringBuilder.length() - LIST_SEPARATOR.length());
        }

        return stringBuilder.toString();
    }

    public static List<String> convertStringToList(String str) {
        if(str.length() > 0){
            return Arrays.asList(str.split(LIST_SEPARATOR));
        } else {
            return new ArrayList<String>(0);
        }
    }

    public static <T> ArrayList<T> listToArrayList(List<T> list) {
        return list != null ? new ArrayList<>(list) : null;
    }

    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
