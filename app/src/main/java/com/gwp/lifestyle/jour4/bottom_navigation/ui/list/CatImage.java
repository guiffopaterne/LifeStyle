package com.gwp.lifestyle.jour4.bottom_navigation.ui.list;

public class CatImage {
    private int id;
    private String name;
    private String src;

    public CatImage(int id, String name, String src) {
        this.id = id;
        this.name = name;
        this.src = src;
    }

    public CatImage(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
