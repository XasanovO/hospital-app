package org.example.hospitalapp.dto;


public record DoctorReq(
        String firstName,
        String lastName,
        String phone,
        Integer specialtyId,
        Integer roomId
) {
}
