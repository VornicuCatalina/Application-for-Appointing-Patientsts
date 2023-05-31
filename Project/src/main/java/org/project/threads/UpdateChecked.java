package org.project.threads;

import org.project.functions.FunctionsDB;

import java.util.Date;

public class UpdateChecked implements Runnable {
    FunctionsDB functionsDB = new FunctionsDB();
    private int millis = 60000;

    @Override
    public void run() {
        boolean ok = true;
        while (ok) {
            Date date1 = new Date();
            Date date2 = new Date();
            if (checkingCaseDate(date1)) {
                date2.setMinutes(30);
            } else {
                date2.setMinutes(0);
                date2.setHours((date1.getHours() + 1) % 24);
            }

            long waitingThreadTime = date2.getTime();
            long initialTime = date1.getTime();

            functionsDB.updateChecked();
            try {
                System.out.println("Thread for updating checked instances will be sleeping for "
                        + (waitingThreadTime - initialTime) / millis +
                        " minutes");
                Thread.sleep(waitingThreadTime - initialTime);
            } catch (InterruptedException e) {
                System.out.println("Thread for updating checked instances is exiting...");
                ok = false;
            }
        }
    }

    private boolean checkingCaseDate(Date date1) {
        if (date1.getMinutes() < 30) {
            return true;
        }
        return false;
    }
}
