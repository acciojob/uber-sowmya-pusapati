package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name="Admin")
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;
    String userName;
    String password;

    public Admin() {
    }

    public Admin(int adminId, String userName, String password) {
        this.adminId= adminId;
        this.userName = userName;
        this.password = password;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int id) {
        this.adminId = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}