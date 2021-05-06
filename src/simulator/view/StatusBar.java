package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class StatusBar extends JPanel implements SimulatorObserver {

    private JLabel _currTime; // for current time
    private JLabel _currLaws; // for gravity laws
    private JLabel _numOfBodies; // for number of bodies


    StatusBar(Controller ctrl) {
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
        this.setLayout( new FlowLayout( FlowLayout.LEFT ));
        this.setBorder( BorderFactory.createBevelBorder( 1 ));
        // TODO complete the code to build the tool bar


        _currTime = new JLabel("0");
        _currLaws = new JLabel("");
        _numOfBodies = new JLabel("0");


        this.add(new JLabel("Time:"));
        this.add(_currTime);
        JSeparator js1= new JSeparator(JSeparator.VERTICAL);
        js1.setPreferredSize(new Dimension(10,20));
        js1.setBackground(Color.DARK_GRAY);

        this.add(js1);
        this.add(Box.createRigidArea(new Dimension(180, 0)));
        this.add(new JLabel("Bodies:"));

        this.add(_numOfBodies);
        JSeparator js2= new JSeparator(JSeparator.VERTICAL);
        js2.setPreferredSize(new Dimension(10,20));
        js2.setBackground(Color.DARK_GRAY);
        this.add(js2);
        this.add(Box.createRigidArea(new Dimension(180, 0)));
        this.add(new JLabel("Laws:"));
        this.add(_currLaws);

        this.setVisible(true);


    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        SwingUtilities.invokeLater(() ->_currLaws.setText(fLawsDesc));

    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {

        SwingUtilities.invokeLater(() -> {
            _currTime.setText(Double.toString(time));
            _numOfBodies.setText(Integer.toString(bodies.size()));
        });


    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        SwingUtilities.invokeLater(() ->_numOfBodies.setText(Integer.toString(bodies.size())));

    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        SwingUtilities.invokeLater(() -> _currTime.setText(Double.toString(time)));


    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {
        SwingUtilities.invokeLater(() ->_currLaws.setText(fLawsDesc));

    }
}


