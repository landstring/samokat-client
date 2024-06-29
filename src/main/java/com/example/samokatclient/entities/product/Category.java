package com.example.samokatclient.entities.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean hasChildren;

    @Column(name = "image", columnDefinition = "OID")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
}
