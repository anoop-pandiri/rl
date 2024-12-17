package com.anoop.rl.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TAGS")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date_added")
    private Timestamp addedTimestamp = new Timestamp(System.currentTimeMillis()); // Example output: 2024-06-15 15:45:30.123456789

    @Column(name = "date_modified")
    private Timestamp lastmodifiedTimestamp = new Timestamp(System.currentTimeMillis()); // Example output: 2024-06-15 15:45:30.123456789

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private ListEntity list;

    @ManyToMany(mappedBy = "tags")
    private Set<ItemEntity> items = new HashSet<>();
}
