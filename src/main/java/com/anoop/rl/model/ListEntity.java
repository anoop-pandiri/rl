package com.anoop.rl.model;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "LISTS", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "user_id_ref"})
})
public class ListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="name", nullable = false)
    private String name;

    @Column(name = "date_added")
    private Timestamp addedTimestamp = new Timestamp(System.currentTimeMillis()); // Example output: 2024-06-15 15:45:30.123456789

    @Column(name = "date_modified")
    private Timestamp lastmodifiedTimestamp = new Timestamp(System.currentTimeMillis()); // Example output: 2024-06-15 15:45:30.123456789

    @ManyToOne
    @JoinColumn(name = "user_id_ref", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "listcategory_id_ref")
    private ListCategoryEntity listCategory;

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemEntity> items = new HashSet<>();

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TagEntity> tags = new HashSet<>();
    
}