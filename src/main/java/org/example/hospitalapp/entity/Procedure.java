package org.example.hospitalapp.entity;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.hospitalapp.entity.abs.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Procedure extends BaseEntity {
    private String title;
    private String description;
    private Integer price;
}
