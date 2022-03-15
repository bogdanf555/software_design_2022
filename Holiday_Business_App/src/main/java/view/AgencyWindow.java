package view;

import controller.DestinationController;
import controller.PackageController;

import model.Destination;
import model.VacationPackage;
import utils.DestinationTableModel;
import utils.PackageTableModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;

public class AgencyWindow extends JFrame implements ActionListener {

    private DestinationController destinationController;
    private PackageController packageController;

    private JSplitPane mainPanel;
    private JPanel editPanel;
    private JPanel showPanel;
    private JScrollPane destinationPane;
    private JScrollPane packagePane;

    // destination editing
    private JLabel destinationTitle;

    private JLabel destinationName;
    private JLabel destinationCountry;
    private JLabel destinationDescription;
    private JLabel destinationTemperature;

    private JTextField destinationNameText;
    private JTextField destinationCountryText;
    private JTextField destinationDescriptionText;
    private JTextField destinationTemperatureText;

    private JButton addDestinationButton;
    private JButton deleteDestinationButton;

    // package editing

    private JLabel packageTitle;

    private JLabel packageName;
    private JLabel packageDestinationName;
    private JLabel packageBookings;
    private JLabel packagePrice;
    private JLabel packageExtraDetails;
    private JLabel interval;

    private JTextField packageNameText;
    private JTextField packageDestinationNameText;
    private JTextField packageBookingsText;
    private JTextField packagePriceText;
    private JTextField packageExtraDetailsText;
    private JTextField intervalText;

    private JButton addPackageButton;
    private JButton updatePackageButton;
    private JButton deletePackageButton;

    // package contents
    private JLabel packageTableLabel;
    private JTable packageTable;

    // destination contents
    private JLabel destinationTableLabel;
    private JTable destinationTable;

    public AgencyWindow() {

        super("Travels management");

        destinationController = new DestinationController();
        packageController = new PackageController();

        this.setSize(1600,900);

        createPanels();

        JLabel title = new JLabel("Welcome to Traveling agency management");
        title.setFont(new Font("Verdana", Font.BOLD, 20));
        //this.title.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.editPanel.add(title);

        setupDestination();
        setupPackage();
        setupTables();

        this.mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                true, editPanel, showPanel);
        this.mainPanel.setDividerLocation(getWidth() / 3);

        this.getContentPane().add(mainPanel);
        this.setVisible(false);
    }

    public void createPanels() {
        this.editPanel = new JPanel(new FlowLayout());
        this.showPanel = new JPanel(new FlowLayout());

        this.editPanel.setBackground(Color.lightGray);
        this.showPanel.setBackground(Color.lightGray);
    }

    public void setupDestination() {
        createDestination();

        setupDestinationButtons();
        setupDestinationFieldSetting();

        addDestinationFieldsToPanel();
    }

    public void setupPackage() {

        createPackage();

        setupPackageButtons();
        setupPackageFieldSetting();

        addPackageFieldsToPanel();
    }

    public void setupTables() {

        destinationPane = new JScrollPane();
        packagePane = new JScrollPane();

        destinationTableLabel = new JLabel("Destinations:");
        packageTableLabel = new JLabel("Packages:");

        destinationTableLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        destinationTableLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 500));

        packageTableLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        packageTableLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 500));

        destinationTable = new JTable();
        destinationTable.setFont(new Font("Verdana", Font.PLAIN, 14));
        destinationTable.setModel(new DestinationTableModel(destinationController.fetchAll()));

        destinationPane.setViewportView(destinationTable);

        packageTable = new JTable();
        packageTable.setFont(new Font("Verdana", Font.PLAIN, 14));
        packageTable.setModel(new PackageTableModel(packageController.fetchAll()));

        packagePane.setViewportView(packageTable);

        showPanel.add(destinationTableLabel);
        showPanel.add(destinationPane);

        showPanel.add(packageTableLabel);
        showPanel.add(packagePane);
    }

    public void createPackage() {
        packageTitle = new JLabel("Package editing section");

        packageName = new JLabel("Package Name:");
        packageDestinationName = new JLabel("Destination Name:");
        packageBookings = new JLabel("Max Bookings:");
        packagePrice = new JLabel("Price:");
        packageExtraDetails = new JLabel("Extra details");
        interval = new JLabel("Date interval");

        packageNameText = new JTextField(30);
        packageDestinationNameText = new JTextField(30);
        packageBookingsText = new JTextField(30);
        packagePriceText = new JTextField(30);
        packageExtraDetailsText = new JTextField(30);
        intervalText = new JTextField(32);

        addPackageButton = new JButton("Add");
        updatePackageButton = new JButton("Update");
        deletePackageButton = new JButton("Delete");
    }

    public void setupPackageButtons() {
        addPackageButton.setActionCommand("add_package");
        updatePackageButton.setActionCommand("update_package");
        deletePackageButton.setActionCommand("delete_package");

        addPackageButton.addActionListener(this);
        updatePackageButton.addActionListener(this);
        deletePackageButton .addActionListener(this);
    }

    public void setupPackageFieldSetting() {
        this.packageTitle.setFont(new Font("Verdana", Font.BOLD, 20));
        this.packageTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 250));

        packageName.setFont(new Font("Verdana", Font.PLAIN, 16));
        packageDestinationName.setFont(new Font("Verdana", Font.PLAIN, 16));
        packageBookings.setFont(new Font("Verdana", Font.PLAIN, 16));
        packagePrice.setFont(new Font("Verdana", Font.PLAIN, 16));
        packageExtraDetails.setFont(new Font("Verdana", Font.PLAIN, 16));
        interval.setFont(new Font("Verdana", Font.PLAIN, 16));

        packageNameText.setFont(new Font("Verdana", Font.PLAIN, 16));
        packageDestinationNameText.setFont(new Font("Verdana", Font.PLAIN, 16));
        packageBookingsText.setFont(new Font("Verdana", Font.PLAIN, 16));
        packagePriceText.setFont(new Font("Verdana", Font.PLAIN, 16));
        packageExtraDetailsText.setFont(new Font("Verdana", Font.PLAIN, 16));
        intervalText.setFont(new Font("Verdana", Font.PLAIN, 16));

        packagePrice.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
        /*interval.setBorder(BorderFactory.createEmptyBorder(5, 70, 5, 5));*/
    }

    public void addPackageFieldsToPanel() {
        this.editPanel.add(packageTitle);

        this.editPanel.add(packageName);
        this.editPanel.add(packageNameText);

        this.editPanel.add(packageDestinationName);
        this.editPanel.add(packageDestinationNameText);

        this.editPanel.add(packageBookings);
        this.editPanel.add(packageBookingsText);

        this.editPanel.add(packagePrice);
        this.editPanel.add(packagePriceText);

        this.editPanel.add(packageExtraDetails);
        this.editPanel.add(packageExtraDetailsText);

        this.editPanel.add(interval);
        this.editPanel.add(intervalText);


        this.editPanel.add(addPackageButton);
        this.editPanel.add(updatePackageButton);
        this.editPanel.add(deletePackageButton);
    }

    public void createDestination() {

        this.destinationTitle = new JLabel("Destination edit section:");

        this.destinationName = new JLabel("Destination Name");
        this.destinationCountry = new JLabel("Destination Country");
        this.destinationDescription = new JLabel("Destination Description");
        this.destinationTemperature = new JLabel("Destination Temperature");

        this.destinationDescriptionText = new JTextField(30);
        this.destinationNameText = new JTextField(30);
        this.destinationTemperatureText = new JTextField(32);
        this.destinationCountryText = new JTextField(30);

        this.addDestinationButton = new JButton("Add");
        this.deleteDestinationButton = new JButton("Delete");
    }

    public void addDestinationFieldsToPanel() {
        this.editPanel.add(this.destinationTitle);

        this.editPanel.add(this.destinationName);
        this.editPanel.add(this.destinationNameText);
        this.editPanel.add(this.destinationCountry);
        this.editPanel.add(this.destinationCountryText);

        this.editPanel.add(this.destinationDescription);
        this.editPanel.add(this.destinationDescriptionText);
        this.editPanel.add(this.destinationTemperature);
        this.editPanel.add(this.destinationTemperatureText);

        this.editPanel.add(this.addDestinationButton);
        this.editPanel.add(this.deleteDestinationButton);
    }

    public void setupDestinationButtons() {

        this.addDestinationButton.setActionCommand("add_destination");
        this.deleteDestinationButton.setActionCommand("delete_destination");

        this.addDestinationButton.addActionListener(this);
        this.deleteDestinationButton.addActionListener(this);
    }

    public void setupDestinationFieldSetting() {
        this.destinationTitle.setFont(new Font("Verdana", Font.BOLD, 20));
        this.destinationTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 250));

        this.destinationName.setFont(new Font("Verdana", Font.PLAIN, 16));
        this.destinationNameText.setFont(new Font("Verdana", Font.PLAIN, 16));

        this.destinationCountry.setFont(new Font("Verdana", Font.PLAIN, 16));
        this.destinationCountryText.setFont(new Font("Verdana", Font.PLAIN, 16));

        this.destinationTemperature.setFont(new Font("Verdana", Font.PLAIN, 16));
        this.destinationTemperatureText.setFont(new Font("Verdana", Font.PLAIN, 16));

        this.destinationDescription.setFont(new Font("Verdana", Font.PLAIN, 16));
        this.destinationDescriptionText.setFont(new Font("Verdana", Font.PLAIN, 16));
    }

    public void updateDestinationTable() {
        destinationTable.setModel(
                new DestinationTableModel(destinationController.fetchAll())
        );
    }

    public void updatePackageTable() {
        packageTable.setModel(
                new PackageTableModel(packageController.fetchAll())
        );
    }

    public void addDestination() {

        double temperature;
        try {
            temperature = Double.parseDouble(this.destinationTemperatureText.getText());
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "The provided temperature is invalid",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Destination destination = new Destination(this.destinationNameText.getText(),
                this.destinationDescriptionText.getText(),
                this.destinationCountryText.getText(),
                temperature);

        this.destinationController.insertDestination(destination);
        updateDestinationTable();
    }

    public void deleteDestination() {

        String destinationTitle = destinationNameText.getText();
        List<Destination> destinationList = ((DestinationTableModel) destinationTable.getModel()).getDestinations();

        Destination destination = destinationList.stream().filter(dest -> dest.getName().equals(destinationTitle)).findAny().orElse(null);

        if (destination == null) {
            JOptionPane.showMessageDialog(this,
                    "This destination does not exist",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<VacationPackage> packages = packageController.fetchAll();

        packages = packages.stream()
                .filter(pack -> pack.getDestination().getName().equals(destination.getName()))
                .collect(Collectors.toList());
        packageController.clearLinksWithUsers(packages);
        this.destinationController.deleteDestination(destination.getId());

        updateDestinationTable();
        updatePackageTable();
    }

    public void addPackage() {

        String name = packageNameText.getText();
        String destinationName = packageDestinationNameText.getText();

        List<Destination> destinationList = ((DestinationTableModel) destinationTable.getModel()).getDestinations();
        Destination destination = destinationList.stream().filter(dest -> dest.getName().equals(destinationName)).findAny().orElse(null);

        if (destination == null) {
            JOptionPane.showMessageDialog(this,
                    "This destination does not exist",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookings;
        double price;

        try {
            bookings = Integer.parseInt(packageBookingsText.getText());
            price = Double.parseDouble(packagePriceText.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "The provided price or bookings number are not invalid",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] start_and_end = intervalText.getText().split(" ");

        // YYYY-MM-DD
        Date start, end;
        try {
            start = Date.valueOf(start_and_end[0]);
            end = Date.valueOf(start_and_end[1]);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "The provided date interval is not valid (use space between and yyyy-mm-dd format)",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        VacationPackage vacationPackage = new VacationPackage(name, price, start, end,
                packageExtraDetailsText.getText(), bookings, destination);

        this.packageController.insertPackage(vacationPackage);
        updatePackageTable();
    }

    public void deletePackage() {
        String packageTitle = packageNameText.getText();
        List<VacationPackage> packages = ((PackageTableModel) packageTable.getModel()).getPackages();

        VacationPackage vacationPackage = packages.stream().filter(pack -> pack.getName().equals(packageTitle)).findAny().orElse(null);

        if (vacationPackage == null) {
            JOptionPane.showMessageDialog(this,
                    "This package does not exist",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<VacationPackage> packageWrapped = new ArrayList<>();
        packageWrapped.add(vacationPackage);
        packageController.clearLinksWithUsers(packageWrapped);
        this.packageController.deletePackage(vacationPackage.getId());
        updatePackageTable();
    }

    public boolean updateBookings(VacationPackage vacationPackage) {
        String maxBookings = packageBookingsText.getText();
        if (!maxBookings.isEmpty()) {
            int bookings;
            try {
                bookings = Integer.parseInt(maxBookings);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "The provided bookings number is not invalid",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (bookings > 0 && bookings >= vacationPackage.getBookings()) {
                vacationPackage.setBookings(bookings);
            } else {
                JOptionPane.showMessageDialog(this,
                        "The provided bookings number is lower that the already booked vacations: "
                                + vacationPackage.getBookings(),
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    public boolean updatePrice(VacationPackage vacationPackage) {
        String priceString = packagePriceText.getText();
        if (!priceString.isEmpty()) {
            double price;
            try {
                price = Double.parseDouble(priceString);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "The provided price is not a valid number",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (price > 0) {
                vacationPackage.setPrice(price);
            } else {
                JOptionPane.showMessageDialog(this,
                        "The provided price must be greater that zero",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public boolean updateDateInterval(VacationPackage vacationPackage) {
        String[] start_and_end = intervalText.getText().split(" ");

        if (start_and_end.length < 2) {
            JOptionPane.showMessageDialog(this,
                    "The provided date interval is not valid (use space between and yyyy-mm-dd format)",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // YYYY-MM-DD
        Date start, end;
        try {
            start = Date.valueOf(start_and_end[0]);
            end = Date.valueOf(start_and_end[1]);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "The provided date interval is not valid (use space between and yyyy-mm-dd format)",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        vacationPackage.setStart(start);
        vacationPackage.setEnd(end);

        return true;
    }

    public void updatePackage() {
        String packageTitle = packageNameText.getText();
        List<VacationPackage> packages = ((PackageTableModel) packageTable.getModel()).getPackages();

        VacationPackage vacationPackage = packages.stream().filter(pack -> pack.getName().equals(packageTitle)).findAny().orElse(null);

        if (vacationPackage == null) {
            JOptionPane.showMessageDialog(this,
                    "This package does not exist",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!updateBookings(vacationPackage)) {
            return;
        }
        if (!updatePrice(vacationPackage))
            return;

        if (!packageExtraDetailsText.getText().isEmpty()) {
            vacationPackage.setExtraDetails(packageExtraDetailsText.getText());
        }

        if (!intervalText.getText().isEmpty()) {
            if(!updateDateInterval(vacationPackage)) {
                return;
            }
        }

        if (!packageDestinationNameText.getText().isEmpty()) {
            List<Destination> destinationList = ((DestinationTableModel) destinationTable.getModel()).getDestinations();
            Destination destination = destinationList.stream()
                    .filter(dest -> dest.getName().equals(packageDestinationNameText.getText())).findAny().orElse(null);

            if (destination == null) {
                JOptionPane.showMessageDialog(this,
                        "This destination does not exist",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            vacationPackage.setDestination(destination);
        }

        this.packageController.updatePackage(vacationPackage);
        updatePackageTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "add_destination":
                addDestination();
                break;
            case "delete_destination":
                deleteDestination(); // TODO: solve bug - DELETES ALSO THE USERS
                break;
            case "add_package":
                addPackage();
                break;
            case "delete_package":
                deletePackage();
                break;
            case "update_package":
                updatePackage();
                break;
            default:
                break;
        }
    }
}
