package com.example.daggerpractice;

public class Dog implements Pet{
    private String name = "";
    public String getName(){
        name = "dog";
        return name;
    }
}
