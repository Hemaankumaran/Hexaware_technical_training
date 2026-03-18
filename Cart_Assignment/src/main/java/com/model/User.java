package com.model;

import com.enums.UserMembership;
import org.springframework.stereotype.Component;

@Component
public class User {
    private int id;
    private String name;
    private UserMembership userMembership;

    public User() {
    }

    public User(int id, String name, UserMembership userMembership) {
        this.id = id;
        this.name = name;
        this.userMembership = userMembership;
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

    public UserMembership getUserMembership() {
        return userMembership;
    }

    public void setUserMembership(UserMembership userMembership) {
        this.userMembership = userMembership;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userMembership=" + userMembership +
                '}';
    }
}
