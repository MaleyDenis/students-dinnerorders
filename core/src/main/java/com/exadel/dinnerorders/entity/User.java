package com.exadel.dinnerorders.entity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * User: Dima Shulgin
 * Date: 17.07.12
 */

@javax.persistence.Entity(name = "user")
public class User implements Entity {
    @Id
    private Long id;
    private String ldapLogin;

    @Export(column = "User",collection = false)
    private String userName;

    @Export(column = "Role",collection = false)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Topic>topics;

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
