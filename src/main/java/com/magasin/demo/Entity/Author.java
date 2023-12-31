package com.magasin.demo.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author {
    public Author(String string, String string2, String string3, String string4, Date date) {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    private String firstname;
    private String lastname;
    private String email;
    private String tel;
    private Date datenai;

    @ElementCollection
    @CollectionTable(name = "author_avatars", joinColumns = @JoinColumn(name = "author_id"))
    @Column(name = "avatar_url")
    private List<String> avatarUrls = new ArrayList<>();

    // Getters and setters
    // Constructors
}
