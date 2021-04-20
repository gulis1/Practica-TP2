package simulator.view;

import simulator.control.Controller;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    // ...
    Controller _ctrl;
    public MainWindow(Controller ctrl) {
        super("Physics Simulator");
        _ctrl = ctrl;
        initGUI();
    }

    private void initGUI() {

        this.setVisible(true);
        this.setSize(300, 300);
        this.setIconImages();



// TODO complete this method to build the GUI
// ..
    }
// other private/protected methods
// ...
}