package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.List;

public class ControlPanel extends JPanel implements SimulatorObserver {
    // ...
    private Controller _ctrl;
    private boolean _stopped;
    private String deltaTime = "2500.0";

    private int steps;

    ControlPanel(Controller ctrl) {
        _ctrl = ctrl;
        _stopped = true;
        initGUI();
        _ctrl.addObserver(this);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));


    }

    private void initGUI() {
        JButton openButton = new JButton(new ImageIcon("resources/icons/open.png"));
        JButton physicsButton = new JButton(new ImageIcon("resources/icons/physics.png"));
        JButton runButton = new JButton(new ImageIcon("resources/icons/run.png"));
        JButton stopButton = new JButton(new ImageIcon("resources/icons/stop.png"));
        JButton exitButton = new JButton(new ImageIcon("resources/icons/exit.png"));

        JSpinner fidgetSpinner = new JSpinner(new SpinnerNumberModel(steps, 0, null, 1000));
        fidgetSpinner.setPreferredSize(new Dimension(100, 30));

        JTextField deltaVaina = new JTextField(deltaTime);
        deltaVaina.setPreferredSize(new Dimension(100 , 30));

        this.add(openButton);
        this.add(physicsButton);
        this.add(runButton);
        this.add(stopButton);


        this.add(new JLabel("Steps:"));

        this.add(fidgetSpinner);
        this.add(new JLabel("Delta-Time:"));
        this.add(deltaVaina);

        this.add(exitButton);




        //boton1.addActionListener(event -> System.out.println("hola"));

    }

    // other private/protected methods
// ...

    private class FidgetSpinnerModel implements SpinnerModel {

        @Override
        public Object getValue() {
            return null;
        }

        @Override
        public void setValue(Object value) {

        }

        @Override
        public Object getNextValue() {
            return null;
        }

        @Override
        public Object getPreviousValue() {
            return null;
        }

        @Override
        public void addChangeListener(ChangeListener l) {

        }

        @Override
        public void removeChangeListener(ChangeListener l) {

        }
    }

    private void run_sim(int n) {
        if (n > 0 && !_stopped) {

            try {
                _ctrl.run(1);
            }

            catch (Exception e) {
                // TODO show the error in a dialog box
                // TODO enable all buttons
                _stopped = true;
                return;
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    run_sim(n - 1);
                }
            });
        }
        else {
            _stopped = true;

            // TODO enable all buttons
        }
    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {

    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {

    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
// SimulatorObserver methods
// ...
}
