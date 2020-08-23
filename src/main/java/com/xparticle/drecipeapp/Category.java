package com.xparticle.drecipeapp;

public class Category {
    private String mImageUrl;
    private String mCategoryName;

    public Category(String mImageUrl, String mCategoryName) {
        this.mImageUrl = mImageUrl;
        this.mCategoryName = mCategoryName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }
}
