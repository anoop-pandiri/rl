package com.anoop.rl.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ITEMPOSITIONS")
public class ItemPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer position;

    @ManyToOne
    @JoinColumn(name = "sort_criterion_id", nullable = false)
    private SortCriterionEntity sortCriterion;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity item;
}
