package pl.radoslawkarwacki.tsp;

import pl.radoslawkarwacki.tsp.gui.Window;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        if (args != null && args.length > 0 && "--headless".equals(args[0])) {
            int code = pl.radoslawkarwacki.tsp.cli.HeadlessRunner.run(java.util.Arrays.copyOfRange(args, 1, args.length));
            System.exit(code);
            return;
        }

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
