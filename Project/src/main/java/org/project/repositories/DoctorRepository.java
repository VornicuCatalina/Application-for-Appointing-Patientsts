package org.project.repositories;

import org.project.entities.Doctor;

public class DoctorRepository extends DataRepository<Doctor,Long> {
    public Class<Doctor> getEntityClass(){
        return Doctor.class;
    }

    public DoctorRepository(){}

    public void create(Doctor doctor){
        save(doctor);
    }
}
