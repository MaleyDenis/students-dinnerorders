package com.exadel.dinnerorders.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

/**
 * User: Dima Shulgin
 * Date: 17.07.12
 */

@Entity
@Table(name = "user", catalog = "dinnerorders")
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

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LDAPLOGIN", nullable = false, unique = false)
    public String getLdapLogin() {
        return ldapLogin;
    }

    public void setLdapLogin(String ldapLogin) {
        this.ldapLogin = ldapLogin;
    }

    @Column(name = "USERNAME", nullable = false, unique = false)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false, unique = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
