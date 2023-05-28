package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username",nullable = false,length = 255)
    private String username;

    @Column(nullable = false,length = 255)
    private String email;

    @Column(nullable = false,length = 255)
    private String password;

    @Column(nullable = false,length = 255)
    private boolean enabled;

    public void addRole (Role role) {
        this.roles.add(role);
    }

    public User(){
        roles = new HashSet<>();
    }

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "userroles", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
