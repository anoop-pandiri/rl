package com.anoop.rl.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SORTCRITERIA")
public class SortCriterionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private ListEntity list;

    @OneToMany(mappedBy = "sortCriterion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ItemPosition> itemPositions = new HashSet<>();

}
