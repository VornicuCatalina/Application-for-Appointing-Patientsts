package org.project.fileOperations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ReadingFiles {
    private String positionFolder = "C:\\Users\\User\\Documents\\Project-Java\\Project\\src\\main\\java\\org\\project\\files\\";

    //private String positionFolder = "";
    public void createFile(String filename) {
        //creating file position
        String file = positionFolder + filename;

        //checking if it exists
        File fileJson = new File(file);
        if (fileJson.exists()) {
            System.out.println("The file already exists!!!");
            return;
        }

        try {
            if (fileJson.createNewFile()) {
                System.out.println("File was created!");
            } else {
                System.out.println("Some error occurred...");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readJSON(String filename) {
        //creating file position
        String file = positionFolder + filename;

        //checking if the file exists
        File fileJson = new File(file);
        if (!fileJson.exists()) {
            System.out.println("Create the file first, by writing 'create <name.extension>'");
            return;
        }

        //checking if it is empty
        if (fileJson.length() == 0) {
            System.out.println("IT IS AN EMPTY FILE!");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(file);
        try {
            MainJSONStructure mainJSONStructure = objectMapper.readValue(fileJson, MainJSONStructure.class);
            mainJSONStructure.savingPeopleInDB();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readCSV() {

    }
}
