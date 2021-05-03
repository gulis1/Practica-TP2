package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

    int rows;
    int cols;

    private List<Body> _bodies;

    BodiesTableModel(Controller ctrl) {
        _bodies = new ArrayList<>();
        rows = 0;
        cols = 5;
        ctrl.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return _bodies.size();
    }

    @Override
    public int getColumnCount() {
        return cols;
    }

    @Override
    public String getColumnName(int column) {

        switch(column) {

            case 0: return "Id";
            case 1: return "Mass";
            case 2: return "Position";
            case 3: return "Velocity";
            case 4: return "Force";
            default: return null;
        }

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch(columnIndex) {

            case 0: return _bodies.get(rowIndex).getId();
            case 1: return _bodies.get(rowIndex).getMass();
            case 2: return _bodies.get(rowIndex).getPos();
            case 3: return _bodies.get(rowIndex).getVel();
            case 4: return _bodies.get(rowIndex).getForce();
            default: return null;
        }
    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _bodies = new ArrayList<>();
        rows = 0;
        fireTableRowsDeleted(0,0);
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        _bodies.add(b);
        rows++;
        fireTableRowsInserted(0, rows);
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        _bodies = bodies;
        fireTableDataChanged();
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