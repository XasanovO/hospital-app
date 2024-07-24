package org.example.hospitalapp.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class Doctor extends BaseEntity {
    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToOne
    private Speciality speciality;
    @ManyToOne
    private Room room;
}
