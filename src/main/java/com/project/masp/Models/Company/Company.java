package com.project.masp.Models;

public class Company {
    private String name;
    private String companyEmail;
    private String description;

    public Company(){

    }

    public Company(String name, String companyEmail, String description) {
        this.name = name;
        this.companyEmail = companyEmail;
        this.description = description;
    }
}
