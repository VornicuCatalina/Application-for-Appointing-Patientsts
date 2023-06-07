package org.project.repositories;

import jakarta.persistence.NoResultException;
import org.project.entities.Doctor;

public class DoctorRepository extends DataRepository<Doctor, Long> {
    public Class<Doctor> getEntityClass() {
        return Doctor.class;
    }

    public DoctorRepository() {
    }

    public void create(Doctor doctor) {
        save(doctor);
    }

    public Doctor findById(int id) {
        try {
            return getEntityManager().
                    createNamedQuery("Doctor.findById", Doctor.class)
                    .setParameter(1, id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
