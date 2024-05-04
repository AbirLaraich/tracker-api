package com.tracker.trackerapi.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupplierDto {

    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("adresse")
    private String adresse;
    @JsonProperty("name")
    private String  name ;
    @JsonProperty("siret_number")
    private Long siret_number ;

    public SupplierDto(){}
    public SupplierDto(String email, String password, String adresse, String name, Long siret_number) {
        this.email = email;
        this.password = password;
        this.adresse = adresse;
        this.name =  name ;
        this.siret_number= siret_number;
    }
    @JsonProperty("siret_number")
    public Long getSiret_number() {
        return siret_number;
    }
    @JsonProperty("siret_number")
    public void setSiret_number(Long siret_number) {
        this.siret_number = siret_number;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }
    @JsonProperty("adresse")
    public String getAdresse() {
        return adresse;
    }
    @JsonProperty("adresse")
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    @JsonProperty("name")

    public String getName() {
        return name;
    }
    @JsonProperty("name")

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SupplierDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
