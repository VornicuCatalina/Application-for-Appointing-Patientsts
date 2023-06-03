package org.project.files;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MainJSONStructure {
    @JsonProperty("doctors")
    private List<DoctorsJSON> doctorsJSONList;
    @JsonProperty("patients")
    private List<PatientsJSON> patientsJSONS;
    public MainJSONStructure(){
        doctorsJSONList = new ArrayList<>();
        patientsJSONS = new ArrayList<>();
    }

    //GETTERS
    public List<PatientsJSON> getPatientsJSONS() {
        return patientsJSONS;
    }

    public List<DoctorsJSON> getDoctorsJSONList() {
        return doctorsJSONList;
    }

    //SETTERS

    public void setDoctorsJSONList(List<DoctorsJSON> doctorsJSONList) {
        this.doctorsJSONList = doctorsJSONList;
    }

    public void setPatientsJSONS(List<PatientsJSON> patientsJSONS) {
        this.patientsJSONS = patientsJSONS;
    }
}
