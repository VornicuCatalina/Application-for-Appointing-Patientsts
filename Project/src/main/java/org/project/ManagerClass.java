package org.project;

import org.project.threads.DeletingOlderDates;
import org.project.threads.ListenForInput;

public class ManagerClass {
    public void start() {
        //opening an important thread for deleting older dates
        DeletingOlderDates deletingOlderDates = new DeletingOlderDates();
        Thread deletingOlderDatesThread = new Thread(deletingOlderDates);
        deletingOlderDatesThread.start();

        //opening the following thread for console commands
        ListenForInput listenForInput = new ListenForInput();
        listenForInput.setDeletingStuff(deletingOlderDatesThread);
        Thread listenForInputThread = new Thread(listenForInput);
        listenForInputThread.start();
    }
}
