package qkd;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
public class VisualDisplay extends JPanel {
    JTable table = null;
    VisualDisplayTableModel visualDisplayTableModel =null;
    
    public VisualDisplay(VisualDisplayTableModel visualDisplayTableModel ) {
        this.visualDisplayTableModel = visualDisplayTableModel;
        table = new JTable(visualDisplayTableModel );
        
        table.setPreferredScrollableViewportSize(new Dimension(700, 150));
        JScrollPane scrollPane = new JScrollPane(table);
        fixTable();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add( new InputPanel());
        add( scrollPane );
        
        
    }
    public void fixTable() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        for ( int c = 1; c < visualDisplayTableModel.getColumnCount(); c++) {
            table.getColumnModel().getColumn( c ).setCellRenderer( renderer );
        }
        table.getColumnModel().getColumn(0).setMinWidth(200);
    }
    class InputPanel extends JPanel implements ActionListener {
        JTextField stringToProcess = null;
        public InputPanel() {
            JLabel filterLabel = new JLabel("Enter your filters:");
            stringToProcess = new JTextField("");
            stringToProcess.setColumns(30);
            JButton process = new JButton("Send");
            process.addActionListener(this);
            add(filterLabel);
            add(stringToProcess);
            add(process);
        }
        public void actionPerformed(ActionEvent actionEvent) {
            System.out.println( actionEvent );
            Simulation.process(stringToProcess.getText(),0);
            fixTable();
            
            
        }
    }
}
