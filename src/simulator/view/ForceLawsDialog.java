package simulator.view;

import org.json.JSONArray;
import org.json.JSONException;
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
        super(parent, "Force Laws Selection", true);
        this.listaDeLeyes = controller.getForceLawsInfo();
        this.controller = controller;
        init(parent);

    }

    public void init(JFrame padre) {

        this.setSize(500, 600);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // Descripcion.
        this.add(new JLabel("Select a force law  and provide values for the parameters in the Value column (default values are used for parameters with no value)"));

        // Tabla de leyes
        ModeloTablaLeyes modelo = new ModeloTablaLeyes();
        JTable table = new JTable(modelo);
        modelo.setRows(listaDeLeyes.get(0));            // Por defecto se pone la primera ley de la lista.
        //Font bold = new Font("Arial",Font.BOLD);

        table.setMinimumSize(new Dimension(300, 250));
        table.setMaximumSize(new Dimension(300, 250));
        table.setShowGrid(false);
        table.setVisible(true);


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);
        this.add(scrollPane);


        // Panel de abajo
        JPanel panelAbajo = new JPanel();

        // ComboBox
        JComboBox<String> comboBox = new JComboBox<String>();

        for (JSONObject ley : listaDeLeyes) {
            comboBox.addItem(ley.getString("desc"));
        }

        comboBox.addActionListener(event -> modelo.setRows(listaDeLeyes.get(comboBox.getSelectedIndex())));

        JButton cancel = new JButton("cancel");
        cancel.addActionListener((event) -> this.dispose());

        JButton ok = new JButton("OK");
        ok.addActionListener((event)-> {

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
            this.dispose();
        });

        panelAbajo.add(new JLabel("Force Law: "));
        panelAbajo.add(comboBox);
        panelAbajo.add(cancel);
        panelAbajo.add(ok);


        this.add(panelAbajo);
        this.setVisible(true);

    }


    private final class ModeloTablaLeyes extends AbstractTableModel {

        private int numRows;
        private int numCols;

        private ArrayList<JSONObject> rows;

        public ModeloTablaLeyes() {
            this.numRows = 0;
            this.numCols = 3;
            this.rows = new ArrayList<JSONObject>();
        }

        // Añade filas segun la ley que se le pasa
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
        public Object getValueAt(int rowIndex, int columnIndex) {

            switch (columnIndex) {

                case 0 :  return rows.get(rowIndex).getString("Key");
                case 1 :  {

                    // SI no se ha introducido un valor se devuelve null
                    if (!rows.get(rowIndex).has("Value"))
                        return null;

                    else
                        return rows.get(rowIndex).get("Value");


                }
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

            try {
                rows.get(row).put("Value", Double.parseDouble(value.toString()));
            }

            catch (NumberFormatException e) {
                // Si el valor introducido en la tabla es un vector 2d se entra aqui
                try {
                    rows.get(row).put("Value", new JSONArray(value.toString()));
                }

                catch (JSONException e2){
                    JOptionPane.showMessageDialog(null,"The introduced value is incorrect.");
                }

            }

            fireTableCellUpdated(row, col);
        }


    }

}
