package org.example.hospitalapp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.hospitalapp.entity.abs.BaseEntity;
import org.example.hospitalapp.entity.enums.AdmissionStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Admission extends BaseEntity {
    private LocalDateTime subscribeDate;
    private LocalDateTime arrivedDate;

    @Enumerated(EnumType.STRING)
    private AdmissionStatus status;

    @ManyToOne
    private Adminstrator adminstrator;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    private String description;
    @OneToMany
    private List<Procedure> procedures;

    public String getSubscribeDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm");
        return subscribeDate.format(formatter);
    }

}
