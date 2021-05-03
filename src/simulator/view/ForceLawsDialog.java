package simulator.view;

import org.json.JSONObject;
import simulator.control.Controller;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ForceLawsDialog extends JDialog {

    List<JSONObject> listaDeLeyes;
    Controller controller;

    public ForceLawsDialog (JFrame parent, Controller controller) {
        super();
        this.listaDeLeyes = controller.getForceLawsInfo();
        this.controller = controller;
        init(parent);

    }

    public void init(JFrame padre) {

        JDialog setforces = new JDialog(padre, "Force Laws Selection", true);
        setforces.setSize(500, 600);
        setforces.setLayout(new BoxLayout(setforces.getContentPane(), BoxLayout.Y_AXIS));

        setforces.add(new JLabel("Select a force law  and provide values for the parameters in the Value column (default values are used for parameters with no value)"));


        //Font bold = new Font("Arial",Font.BOLD);
        ModeloTablaLeyes modelo = new ModeloTablaLeyes();
        JTable table = new JTable(modelo);

        table.setMinimumSize(new Dimension(300, 250));
        table.setMaximumSize(new Dimension(300, 250));
        table.setShowGrid(false);
        table.setVisible(true);
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setVisible(true);

        setforces.add(scrollPane);

        List<JSONObject> listaDeLeyes = controller.getForceLawsInfo();
        // Panel de abajo
        JPanel panelAbajo = new JPanel();
        JButton cancel = new JButton("cancel");
        JButton ok = new JButton("OK");
        JTextArea info = new JTextArea();
        JComboBox<String> comboBox = new JComboBox<String>();

        for (JSONObject mierda : listaDeLeyes) {
            comboBox.addItem(mierda.getString("desc"));
        }

        comboBox.addActionListener(event3 -> modelo.setRows(listaDeLeyes.get(comboBox.getSelectedIndex())));


        ok.addActionListener(event2 -> {

            JSONObject leyTemplate = listaDeLeyes.get(comboBox.getSelectedIndex());
            Iterator<String> iterator = leyTemplate.getJSONObject("data").keys();
            int i = 0;

            JSONObject ley = new JSONObject();
            ley.put("type", leyTemplate.get("type"));
            ley.put("desc", leyTemplate.get("desc"));
            ley.put("data", new JSONObject());

            while (iterator.hasNext()) {
                String key = iterator.next();

                if (table.getValueAt(i, 1) != null)
                    ley.getJSONObject("data").put(key, table.getValueAt(i, 1));

                i++;
            }

            controller.setForceLaws(ley);
        });
        panelAbajo.add(new JLabel("Force Law: "));
        panelAbajo.add(comboBox);
        panelAbajo.add(cancel);
        panelAbajo.add(ok);


        setforces.add(panelAbajo);

        // _ctrl.setForceLaws();
        setforces.setVisible(true);
    }

    private class ModeloTablaLeyes extends AbstractTableModel {

        private int numRows;
        private int numCols;

        ArrayList<JSONObject> rows;

        public ModeloTablaLeyes() {
            this.numRows = 0;
            this.numCols = 3;
            this.rows = new ArrayList<JSONObject>();
        }

        // Añade filas srgun la ley que se le pasa
        public void setRows(JSONObject ley) {

            rows = new ArrayList<JSONObject>();

            Iterator<String> iterator = ley.getJSONObject("data").keys();
            numRows = 0;

            while(iterator.hasNext()) {
                JSONObject newRow = new JSONObject();
                String key = iterator.next();

                newRow.put("Key", key);
                newRow.put("Descripcion", ley.getJSONObject("data").getString(key));

                rows.add(newRow);

                numRows++;

            }

            fireTableRowsInserted(0, numRows - 1);
        }

        @Override
        public int getRowCount() {
            return numRows;
        }

        @Override
        public int getColumnCount() {
            return numCols;
        }

        @Override
        public String getValueAt(int rowIndex, int columnIndex) {

            switch (columnIndex) {

                case 0 :  return rows.get(rowIndex).getString("Key");
                case 1 :  return rows.get(rowIndex).has("Value") ? rows.get(rowIndex).get("Value").toString() : null;
                case 2 :  return rows.get(rowIndex).getString("Descripcion");
                default:  return null;
            }
        }

        @Override
        public String getColumnName(int column) {

            switch(column) {

                case 0: return "Key";
                case 1: return "Value";
                case 2: return "Descripcion";
                default: return null;
            }

        }


        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 1;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            rows.get(row).put("Value", value);
            fireTableCellUpdated(row, col);
        }


    }

}
