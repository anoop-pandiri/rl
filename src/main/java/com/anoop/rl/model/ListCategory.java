package com.anoop.rl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.sql.Timestamp;
import lombok.Data;

@Data
@Entity
@Table(name = "LISTCATEGORIES")
public class ListCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="listcategory_id")
    private Long lcId;

    @NotNull
    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @Column(name = "date_added")
    private Timestamp dateAdded;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    @ManyToOne
    @JoinColumn(name = "user_id_ref")
    private UserEntity user;

    // Getters and setters
}
