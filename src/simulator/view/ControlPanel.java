package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ControlPanel extends JToolBar implements SimulatorObserver {
    // ...
    private Controller _ctrl;
    private boolean _stopped;
    private String deltaTime = "2500.0";


    private int steps;

    private JButton openButton;
    private JButton physicsButton;
    private JButton runButton;
    private JButton stopButton;
    private JButton exitButton;

    private JSpinner fidgetSpinner;
    private JTextField deltaVaina;

    private JFrame padre;

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
        physicsButton.addActionListener(event->{

           JDialog setforces= new JDialog(padre,"Force Laws Selection",true);
           //Font bold = new Font("Arial",Font.BOLD);
            JTable table= new JTable();
            JPanel panel = new JPanel();
            JButton cancel=new JButton("cancel");
            JButton ok=new JButton("OK");
            JComboBox comboBox = new JComboBox();



           setforces.setSize(800,500);
           setforces.setLayout(new BorderLayout());
           setforces.add(new JLabel("Select a force law  and provide values for the parameters in the Value column (default values are used for parameters with no value)"),BorderLayout.NORTH);
           setforces.add(table,BorderLayout.CENTER);

           panel.add(new JLabel("Force Law: "));
           panel.add(comboBox);
           panel.add(cancel);
           panel.add(ok);

           setforces.add(panel,BorderLayout.SOUTH);

               // _ctrl.setForceLaws();
            setforces.setVisible(true);

        });


        runButton = new JButton(new ImageIcon("resources/icons/run.png"));
        runButton.addActionListener(event -> {

            // Se desactivan todos los botones-
            runButton.setEnabled(false);
            openButton.setEnabled(false);
            physicsButton.setEnabled(false);
            exitButton.setEnabled(false);

            _ctrl.setDeltaTime(Double.parseDouble(deltaTime));
            run_sim(steps);

        });

        stopButton = new JButton(new ImageIcon("resources/icons/stop.png"));
        stopButton.addActionListener(event -> _stopped = true);


        exitButton = new JButton(new ImageIcon("resources/icons/exit.png"));
        exitButton.addActionListener(event -> {
            // Hemos hecho un poco el gamba aqui

            int dialogResult = JOptionPane.showConfirmDialog(null, "Quieres salir?","Salir", JOptionPane.YES_NO_OPTION);

            if (dialogResult == 0) {

                dialogResult = JOptionPane.showConfirmDialog(null, "Seguro?","Pain-peko", JOptionPane.YES_NO_OPTION);

                if (dialogResult == 0) {

                    dialogResult = JOptionPane.showConfirmDialog(null, "De verdad?",":(", JOptionPane.YES_NO_OPTION);

                    if (dialogResult == 0)
                        System.exit(0);
                }
            }

        });
    }

    private void initSpinnerAndTextField(){
        fidgetSpinner = new JSpinner(new SpinnerNumberModel(steps, 0, null, 1000));
        fidgetSpinner.setMaximumSize(new Dimension(600, 30));

        deltaVaina = new JTextField(deltaTime);
        deltaVaina.setMaximumSize(new Dimension(300 , 30));
    }

    private void initGUI() {

        initButtons();
        initSpinnerAndTextField();

        this.add(openButton);
        this.add(physicsButton);
        this.add(runButton);
        this.add(stopButton);

        this.add(new JLabel("Steps:"));

        this.add(fidgetSpinner);
        this.add(new JLabel("Delta-Time:"));
        this.add(deltaVaina);

        this.add(Box.createGlue());
        this.add(exitButton);

    }

    // other private/protected methods
// ...



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
