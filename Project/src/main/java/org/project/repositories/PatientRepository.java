package org.project.repositories;

import org.project.entities.Doctor;
import org.project.entities.Patient;

public class PatientRepository extends DataRepository<Patient, Long> {
    public Class<Patient> getEntityClass() {
        return Patient.class;
    }

    public PatientRepository() {
    }

    public void create(Patient patient) {
        save(patient);
    }

    public Patient findById(int id) {
        return getEntityManager().
                createNamedQuery("Patient.findById", Patient.class)
                .setParameter(1, id)
                .getSingleResult();
    }
}
