package qkd;

import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class VisualDisplayTableModel extends AbstractTableModel {
    
    private int numberOfRows  = 8;
    private int numberOfColumns  = 12;
    
    private Vector vector = null;
    
    private String stateValues[][] = null;
    
    String labels[] = {
        "filters input",
        "corresponding filter SET",
        "bits Alice intends (sends)",
        "filters chosen by Bob",
        "corresponding filter SET",
        "filters chosen by optional Eve",
        "corresponding filter SET",
        "Again! This time reset by Eve",
        "Which K of N filters match? Y, N,",
        "What are the K of N bits for the matches?",
        "What are the K=J of N indexes for the matches?",
        "What are the M equal subset bits ? Filled with -3."
                
    };
    private String columnNames[] = {"Step","1","2"};
    
    
    public String getColumnName( int column ) {
        System.out.println( column + " = " + columnNames[column] );
        return ( columnNames[column]);
    }
    public void setNumberFilters(int numberOfFilters ) {
        vector =  new Vector();
        numberOfColumns = numberOfFilters + 1;
        columnNames = new String[numberOfColumns];
        columnNames[0] = "Step";
        for ( int c = 1; c < numberOfColumns; c++) {
            columnNames[c] = Integer.toString( c );
        }
        stateValues = new String[numberOfRows][numberOfFilters];
        fireTableStructureChanged();
    }
    public void registerRow(String key, Object o) {
        vector.add( o);
    }
    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex	the row whose value is to be queried
     * @param columnIndex 	the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if ( columnIndex == 0 ) {
            return ( labels[rowIndex]);
        } else {
            try {
                Object o = vector.get( rowIndex );
                if ( o instanceof char[] ) {
                    char c = ((char [] ) o)[columnIndex-1];
                    return (String.valueOf( c ));
                } else if ( o instanceof int[] ) {
                    int i = ((int [] ) o)[columnIndex-1];
                    return ( String.valueOf( i ));
                }
                return ("e");
            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                return ("?");
            }
        }
    }
    //public void setValueAt(Object value, int row, int col) {
    
    
    //data[row][col] = value;
    // fireTableCellUpdated(row, col);
    //}
    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    public int getRowCount() {
        return ( labels.length );
    }
    
    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    public int getColumnCount() {
        return ( columnNames.length );
    }
 /*
  public Class getColumnClass(int c) { return getValueAt(0, c).getClass(); }
  
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        } else {
            return true;
        }
  
    }
  **/
}