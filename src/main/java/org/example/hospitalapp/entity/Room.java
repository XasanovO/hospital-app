package org.example.hospitalapp.entity;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.example.hospitalapp.entity.abs.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Room extends BaseEntity {
    private Integer number;
    private String name;

    @Override
    public String toString() {
        return name + " " + number;
    }
}
