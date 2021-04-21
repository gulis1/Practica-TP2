//package simulator.view;
//
//import simulator.control.Controller;
//import simulator.model.Body;
//import simulator.model.SimulatorObserver;
//
//import javax.swing.table.AbstractTableModel;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BodiesTableModel extends AbstractTableModel
//        implements SimulatorObserver {
//    // ...
//    private List<Body> _bodies;
//    BodiesTableModel(Controller ctrl) {
//        _bodies = new ArrayList<>();
//        ctrl.addObserver(this);
//    }
//    @Override
//    public int getRowCount() {
//// TODO complete
//    }
//    @Override
//    public int getColumnCount() {
//// TODO complete
//    }
//    @Override
//    public String getColumnName(int column) {
//// TODO complete
//    }
//    @Override
//    public Object getValueAt(int rowIndex, int columnIndex) {
//// TODO complete
//    }
//// SimulatorObserver methods
//// ...
//}