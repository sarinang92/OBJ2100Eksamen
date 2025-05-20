package kino;

import kino.view.MainProgramView;
import kino.repository.LoginRepo;

public class Main {
    public static void main(String[] args) {
        // ✅ One-time hashing of any plain-text PINs
        LoginRepo repo = new LoginRepo();
        repo.hashAllPlaintextPins();

        // ✅ Start main menu
        MainProgramView mainView = new MainProgramView();
        mainView.start();
    }
}
