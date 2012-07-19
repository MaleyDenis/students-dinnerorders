package com.exadel.dinnerorders.entity;

/**
 * User: Dima Shulgin
 * Date: 17.07.12
 */
public class User {

    private Long id;
    private String ldapLogin;
    private String userName;
    private Role role;


    public User(Long id, String ldapLogin, String userName, Role role) {
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
