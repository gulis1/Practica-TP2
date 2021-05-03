package simulator.view;

import simulator.control.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class BodiesTable extends JPanel {

    BodiesTableModel model;

    BodiesTable(Controller ctrl) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Bodies", TitledBorder.LEFT, TitledBorder.TOP));

        JTable table = new JTable();
        table.setModel(new BodiesTableModel(ctrl));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);

    }
}