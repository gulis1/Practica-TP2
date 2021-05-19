package simulator.view;


import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ControlPanel extends JToolBar implements SimulatorObserver {
    // ...
    private Controller _ctrl;
    private boolean _stopped;

    private JButton openButton;
    private JButton physicsButton;
    private JButton runButton;
    private JButton stopButton;
    private JButton exitButton;

    private JSpinner Spinner;
    private JTextField deltaText;


    ControlPanel(Controller ctrl) {
        _ctrl = ctrl;
        _stopped = true;
        initGUI();
        _ctrl.addObserver(this);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    }

    //se inicializan los botones
    private void initButtons() {

        openButton = new JButton(new ImageIcon("resources/icons/open.png"));
        openButton.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Only json files", "json"));
            int valor = fileChooser.showOpenDialog(this);

            // Si se escoge un archivos
            if (valor == JFileChooser.APPROVE_OPTION) {
                _ctrl.reset();
                try {
                    _ctrl.loadBodies(new FileInputStream(fileChooser.getSelectedFile().getAbsoluteFile()));
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });


        physicsButton = new JButton(new ImageIcon("resources/icons/physics.png"));
        physicsButton.addActionListener(event-> {
            new ForceLawsDialog( (JFrame) SwingUtilities.getWindowAncestor(this), _ctrl);
        });

        runButton = new JButton(new ImageIcon("resources/icons/run.png"));

        runButton.addActionListener(event -> {

            // Se desactivan todos los botones-
            runButton.setEnabled(false);
            openButton.setEnabled(false);
            physicsButton.setEnabled(false);
            exitButton.setEnabled(false);
            _stopped=false;


            _ctrl.setDeltaTime(Double.parseDouble(deltaText.getText()));

            try {
                run_sim((Integer) Spinner.getValue());
            }

            catch (Exception e) {
                JOptionPane.showMessageDialog(null,e.getMessage());

            }

        });

        stopButton = new JButton(new ImageIcon("resources/icons/stop.png"));
        stopButton.addActionListener(event -> _stopped = true);


        exitButton = new JButton(new ImageIcon("resources/icons/exit.png"));
        exitButton.addActionListener(event -> {

            if(JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(this), "Quieres salir?","Salir", JOptionPane.YES_NO_OPTION) == 0)
                System.exit(0);
        });
    }

    private void initSpinnerAndTextField(){
        Spinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1000));
        Spinner.setMaximumSize(new Dimension(600, 30));


        deltaText = new JTextField("2500.0");
        deltaText.setMaximumSize(new Dimension(300 , 30));

    }

    private void initGUI() {

        initButtons();
        initSpinnerAndTextField();

        this.add(openButton);
        this.add(physicsButton);
        this.add(runButton);
        this.add(stopButton);

        this.add(new JLabel("Steps:"));

        this.add(Spinner);
        this.add(new JLabel("Delta-Time:"));
        this.add(deltaText);

        this.add(Box.createGlue());
        this.add(exitButton);
        
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

            runButton.setEnabled(true);
            openButton.setEnabled(true);
            physicsButton.setEnabled(true);
            exitButton.setEnabled(true);
        }
    }

    public  void setDt(double dt){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                deltaText.setText(""+dt);
            }
        });
    }


    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
        setDt(dt);
    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        setDt(dt);
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {

    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {

    }

    @Override
    public void onDeltaTimeChanged(double dt) {
        setDt(dt);
    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }





// SimulatorObserver methods
// ...
}
