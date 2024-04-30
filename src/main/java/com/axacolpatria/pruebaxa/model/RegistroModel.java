package com.axacolpatria.pruebaxa.model;

public class RegistroModel {
    private final String name;
    private final Integer age;
    private final String phoneNumber;
    private final String address;
   

    public RegistroModel(String name, Integer age, String phoneNumber, String address) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

}

