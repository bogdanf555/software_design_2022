package utils;

import model.Destination;
import model.VacationPackage;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PackageTableModel extends AbstractTableModel {

    private List<VacationPackage> packages;
    private final String[] columnNames = {"Name", "Destination", "Status", "Price", "Start", "End", "Extra details"};

    public PackageTableModel(List<VacationPackage> packages) {
        this.packages = packages;
    }

    @Override
    public String getColumnName(int columnIndex){
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount() {
        return this.packages.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VacationPackage vacationPackage = packages.get(rowIndex);

        switch(columnIndex) {
            case 0:
                return vacationPackage.getName();
            case 1:
                return vacationPackage.getDestination().getName();
            case 2:
                Integer bookingsLeft = vacationPackage.getMaximumBookings() - vacationPackage.getBookings();

                if (bookingsLeft.equals(vacationPackage.getMaximumBookings())) {
                    return "NOT_BOOKED";
                }

                if (bookingsLeft > 0) {
                    return "IN_PROGRESS";
                }

                if (bookingsLeft == 0) {
                    return "BOOKED";
                }

                return "ERROR";
            case 3:
                return vacationPackage.getPrice();
            case 4:
                return vacationPackage.getStart().toString();
            case 5:
                return vacationPackage.getEnd().toString();
            case 6:
                return vacationPackage.getExtraDetails();
        }

        return null;
    }

    public List<VacationPackage> getPackages() {
        return packages;
    }
}
