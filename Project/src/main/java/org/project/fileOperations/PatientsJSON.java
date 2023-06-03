package org.project.fileOperations;

import java.util.List;

public class PatientsJSON {
    private String name;
    private List<Integer> preferences;

    //GETTERS

    public String getName() {
        return name;
    }

    public List<Integer> getPreferences() {
        return preferences;
    }

    //SETTERS

    public void setName(String name) {
        this.name = name;
    }

    public void setPreferences(List<Integer> preferences) {
        this.preferences = preferences;
    }
}
