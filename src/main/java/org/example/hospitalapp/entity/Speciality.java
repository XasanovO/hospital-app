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
public class Speciality extends BaseEntity {
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
