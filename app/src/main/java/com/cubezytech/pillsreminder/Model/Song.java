package com.cubezytech.pillsreminder.Model;

public class Song {

    String id, name, uri;

    public Song(String id, String name, String uri) {
        this.id = id;
        this.name = name;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
