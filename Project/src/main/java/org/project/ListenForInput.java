package org.project;

import org.project.databases.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ListenForInput implements Runnable {
    public void run() {
        boolean ok = true;
        Scanner scanner = new Scanner(System.in);
        while (ok) {
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("Exiting...");
                ok = false;
            } else if (input.equals("help")) {
                System.out.println("Here are the available commands:" +
                        "\n\texit - exits the program" +
                        "\n\thelp - shows the available commands" +
                        "\n\tshow doctors - shows the available doctors" +
                        "\n\tshow patients - shows the available patients");
            } else if (input.equals("show doctors")) {
                System.out.println("Here are the available doctors:");
                try (Statement stmt = Database.getConnection().createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");
                    while (rs.next()) {
                        System.out.println("ID: " + rs.getString("id_doctor") + " NAME: " + rs.getString("fullName"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (input.equals("show patients")) {
                System.out.println("Here are the available patients:");
                try (Statement stmt = Database.getConnection().createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM patients");
                    while (rs.next()) {
                        System.out.println("ID: " + rs.getString("id_patient") + " NAME: " + rs.getString("fullName"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Invalid command! Type help for the available commands.");
            }
        }
        System.exit(0);
    }
}
