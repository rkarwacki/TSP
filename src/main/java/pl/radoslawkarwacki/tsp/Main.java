package pl.radoslawkarwacki.tsp;

import pl.radoslawkarwacki.tsp.gui.Window;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                System.err.println("Error during UI startup: " + ex.getMessage());
            }
            new Window();
        });
    }
}
