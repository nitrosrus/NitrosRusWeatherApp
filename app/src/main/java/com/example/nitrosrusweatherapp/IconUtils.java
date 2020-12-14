package com.example.nitrosrusweatherapp;

public class IconUtils {

    public int getResByIdCode(int description) {
        int defaultIcon = 2131624043;
        String textMaker = ("wi_owm_" + description);
        try {
            defaultIcon = R.string.class.getField(textMaker).getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultIcon;
    }
}
