package com.exadel.dinnerorders.entity;

/**
 * User: Dima Shulgin
 * Date: 17.07.12
 */
public class User {

    private int id;
    private String ldapLogin;
    private String userName;
    private String role;


    public User(int id, String ldapLogin, String userName, String role) {
        this.id = id;
        this.userName = userName;
        this.ldapLogin = ldapLogin;
        this.role = role;
    }


    public User(User newUser) {
        this.id = newUser.getId();
        this.userName = newUser.getUserName();
        this.ldapLogin = newUser.getLdapLogin();
        this.role = newUser.role;
    }

    public User() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLdapLogin() {
        return ldapLogin;
    }

    public void setLdapLogin(String ldapLogin) {
        this.ldapLogin = ldapLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
