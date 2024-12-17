package com.anoop.rl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
@Entity
@Table(name = "LISTCATEGORIES")
public class ListCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="listcategory_id")
    private Long id;

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
    @JoinColumn(name = "user_id_ref", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "listCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ListEntity> lists = new HashSet<>();

}
