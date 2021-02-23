package com.example.acctask;

public class MusicGetSet {
    private String Name,url;

    public MusicGetSet(){

    }

    public MusicGetSet(String name, String url) {
        Name = name;
        this.url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
