package org.project;

import org.project.threads.DeletingOlderDates;
import org.project.threads.ListenForInput;
import org.project.threads.UpdateChecked;

public class ManagerClass {
    public void start() {
        //opening an important thread for deleting older dates
        DeletingOlderDates deletingOlderDates = new DeletingOlderDates();
        Thread deletingOlderDatesThread = new Thread(deletingOlderDates);
        deletingOlderDatesThread.start();

        //opening an important thread that updates the checked variable
        UpdateChecked updateChecked = new UpdateChecked();
        Thread updateCheckedThread = new Thread(updateChecked);
        updateCheckedThread.start();

        //opening the following thread for console commands
        ListenForInput listenForInput = new ListenForInput();
        listenForInput.setThreads(deletingOlderDatesThread, updateCheckedThread);
        Thread listenForInputThread = new Thread(listenForInput);
        listenForInputThread.start();
    }
}
