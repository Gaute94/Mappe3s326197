package com.example.mappe3s326197;

import java.util.List;

public class Room {
    private int id;
    private String name;
    private String desc;
    private Building building;

    public Building getBuilding(){
        return building;
    }

    public void setBuilding(Building building){
        this.building = building;
    }



    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
