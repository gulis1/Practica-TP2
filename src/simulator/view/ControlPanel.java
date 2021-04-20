//package simulator.view;
//
//import simulator.control.Controller;
//import simulator.model.SimulatorObserver;
//
//import javax.swing.*;
//
//public class ControlPanel extends JPanel implements SimulatorObserver {
//    // ...
//    private Controller _ctrl;
//    private boolean _stopped;
//
//    ControlPanel(Controller ctrl) {
//        _ctrl = ctrl;
//        _stopped = true;
//        initGUI();
//        _ctrl.addObserver(this);
//    }
//
//    private void initGUI() {
//// TODO build the tool bar by adding buttons, etc.
//    }
//
//    // other private/protected methods
//// ...
//
//
//
//    private void run_sim(int n) {
//        if (n > 0 && !_stopped) {
//
//            try {
//                _ctrl.run(1);
//            }
//
//            catch (Exception e) {
//                // TODO show the error in a dialog box
//                // TODO enable all buttons
//                _stopped = true;
//                return;
//            }
//
//            SwingUtilities.invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    run_sim(n - 1);
//                }
//            });
//        }
//        else {
//            _stopped = true;
//
//            // TODO enable all buttons
//        }
//    }
//// SimulatorObserver methods
//// ...
//}
