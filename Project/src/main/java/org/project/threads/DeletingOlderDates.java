package org.project.threads;

import org.project.functions.FunctionsDB;

public class DeletingOlderDates implements Runnable {
    FunctionsDB functionsDB = new FunctionsDB();
    private int millis = 60000;

    @Override
    public void run() {
        boolean ok = true;
        while (ok) {
            functionsDB.deleteOlderDates();
            try {
                Thread.sleep(millis * 30);
            } catch (InterruptedException e) {
                System.out.println("Thread for deleting older instances is exiting...");
                ok = false;
            }
        }
    }
}
