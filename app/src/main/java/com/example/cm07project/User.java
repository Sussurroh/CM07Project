package com.example.cm07project;

public class User {

    public String firstname;
    public String  lastname;
    public String email;

    public User(){
    }
    public User(String firstname, String lastname, String email){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public String getName(){
        return this.firstname;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
