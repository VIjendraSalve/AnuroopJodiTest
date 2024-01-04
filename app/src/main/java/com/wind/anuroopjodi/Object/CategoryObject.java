package com.wind.anuroopjodi.Object;


import java.io.Serializable;

public class CategoryObject implements Serializable {


    private static final long serialVersionUID = 0L;
    public String id;
    public String category_id;
    public String category;
    public String lang;
    public String short_desc;
    public String long_desc;
    public String category_image;
    public String category_icon;

    public CategoryObject() {
    }

    public CategoryObject(String id, String category_id, String category, String lang, String short_desc, String long_desc, String category_image, String category_icon) {
        this.id = id;
        this.category_id = category_id;
        this.category = category;
        this.lang = lang;
        this.short_desc = short_desc;
        this.long_desc = long_desc;
        this.category_image = category_image;
        this.category_icon = category_icon;
    }
}
