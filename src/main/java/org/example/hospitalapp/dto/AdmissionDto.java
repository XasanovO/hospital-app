package org.example.hospitalapp.dto;

import org.example.hospitalapp.entity.enums.AdmissionStatus;


public record AdmissionDto(
        String doctor,
        String speciality,
        String subscribeDate,
        Integer id,
        Integer sumOfProcedures,
        AdmissionStatus status
) {

}
