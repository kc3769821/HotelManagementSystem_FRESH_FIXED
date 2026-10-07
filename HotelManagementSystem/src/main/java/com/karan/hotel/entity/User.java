package com.karan.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_users_email", columnNames = "email"))
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100) private String fullName;
    @Column(nullable = false, length = 150, unique = true) private String email;
    @JsonIgnore @Column(nullable = false) private String passwordHash;
    @Column(length = 20) private String phone;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Role role = Role.USER;
    @Column(nullable = false) private LocalDateTime createdAt = LocalDateTime.now();

    public enum Role { USER, ADMIN }
    public User() {}
    public Long getId(){return id;} public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public Role getRole(){return role;} public void setRole(Role v){role=v;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}
