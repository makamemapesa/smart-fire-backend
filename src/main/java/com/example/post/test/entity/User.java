//package com.example.post.test.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.List;
//
//
//@Entity
//@Table(name = "users")
//@Data
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String firstName;
//
//    @Column(nullable = false)
//    private String middleName;
//
//    @Column(nullable = false)
//    private String lastName;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Column(nullable = false)
//    private String phoneNumber;
//
//    @Column(nullable = false)
//    private String roles;
//
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    private Double latitude;
//    private Double longitude;
//
//    private String address;
//
//    @OneToMany(mappedBy = "reporter")
//    private List<Emergency> emergencies;
//}
package com.example.post.test.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = true)
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, unique = true)
    private String email;

    private Double latitude;
    private Double longitude;

    private String address;



    @OneToMany(mappedBy = "reporter")
    @JsonIgnore
    private List<Emergency> emergencies;

    @PrePersist
    public void setDefaults() {
        if (latitude == null) latitude = 0.0;
        if (longitude == null) longitude = 0.0;
    }
}
