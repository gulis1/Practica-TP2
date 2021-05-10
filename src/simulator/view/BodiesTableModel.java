package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
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

        return switch (column) {
            case 0 -> "Id";
            case 1 -> "Mass";
            case 2 -> "Position";
            case 3 -> "Velocity";
            case 4 -> "Force";
            default -> null;
        };

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        return switch (columnIndex) {
            case 0 -> _bodies.get(rowIndex).getId();
            case 1 -> _bodies.get(rowIndex).getMass();
            case 2 -> _bodies.get(rowIndex).getPos();
            case 3 -> _bodies.get(rowIndex).getVel();
            case 4 -> _bodies.get(rowIndex).getForce();
            default -> null;
        };
    }

    private void updateTable() {
        SwingUtilities.invokeLater(this::fireTableStructureChanged);


    }

    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
        _bodies = new ArrayList<>();
        rows = 0;
       updateTable();
    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {
        _bodies.add(b);
        rows++;
        updateTable();
    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {
        _bodies = bodies;
        updateTable();
    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
}