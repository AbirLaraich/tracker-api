package com.tracker.trackerapi.api.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
@Table(name = "supplier_tb")
public class Supplier{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Long siretNumber;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Transient
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Column(nullable = false)
    private String adresse;

    public Supplier(Long siretNumber, String name, String email, String password, String adresse) {
        this.siretNumber = siretNumber;
        this.name = name;
        this.email = email;
        this.password = passwordEncoder.encode(password);
        this.adresse = adresse;
    }


    public Supplier() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getSiretNumber() {
        return siretNumber;
    }

    public void setSiretNumber(Long siretNumber) {
        this.siretNumber = siretNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", siretNumber=" + siretNumber +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordEncoder=" + passwordEncoder +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
