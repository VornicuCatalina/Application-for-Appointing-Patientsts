package org.project.repositories;

import org.project.entities.Patient;

public class PatientRepository extends DataRepository<Patient,Long> {
    public Class<Patient> getEntityClass(){
        return Patient.class;
    }

    public PatientRepository(){}

    public void create(Patient patient){
        save(patient);
    }
}
