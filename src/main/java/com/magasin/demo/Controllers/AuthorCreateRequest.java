package com.magasin.demo.Controllers;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorCreateRequest {

    private String firstname;
    private String username;
    private String password;
    private String lastname;
    private String tel;
    private Date datenai;
    private String email;

    // Constructors

    public AuthorCreateRequest() {
    }

    public AuthorCreateRequest(String firstname, String username, String password, String lastname, String tel,
            Date datenai, String email) {
        this.firstname = firstname;
        this.username = username;
        this.password = password;
        this.lastname = lastname;
        this.tel = tel;
        this.datenai = datenai;
        this.email = email;
    }

}
