package utils;

import model.Destination;
import sun.security.krb5.internal.crypto.Des;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DestinationTableModel extends AbstractTableModel {

    private List<Destination> destinations;
    private final String[] columnNames = {"Name", "Country", "Temperature", "Description"};

    public DestinationTableModel(List<Destination> destinations) {
        this.destinations = destinations;
    }

    @Override
    public String getColumnName(int columnIndex){
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return destinations.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Destination destination = destinations.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return destination.getName();
            case 1:
                return destination.getCountry();
            case 2:
                return destination.getCelsiusTemperature();
            case 3:
                return destination.getDescription();
        }

        return null;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }
}
