package kino;

import kino.view.MainProgramView;
import kino.repository.LoginRepo;

public class Main {
    public static void main(String[] args) {
        // Hashing av alle passord
        LoginRepo repo = new LoginRepo();
        repo.hashAllPlaintextPins();

        // Starter hovedmeny
        MainProgramView mainView = new MainProgramView();
        mainView.start();
    }
}
