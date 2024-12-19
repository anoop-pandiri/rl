package com.anoop.rl.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ITEM_TAGS")
@Data
public class ItemTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id_ref", nullable = false)
    private ItemEntity item;

    @ManyToOne
    @JoinColumn(name = "tag_id_ref", nullable = false)
    private TagEntity tag;

    @Column(name = "date_added")
    private Timestamp dateAdded = new Timestamp(System.currentTimeMillis());
}

