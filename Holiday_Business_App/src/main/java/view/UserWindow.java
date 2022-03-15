package view;

import controller.PackageController;
import controller.UserController;
import javafx.scene.control.ComboBox;
import model.User;
import model.VacationPackage;
import utils.DestinationTableModel;
import utils.PackageTableModel;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Objects;

public class UserWindow extends JFrame implements ActionListener {

    private PackageController packageController;
    private UserController userController;
    private User user;

    private JPanel mainPanel;

    private JLabel title;

    private ButtonGroup radioGroup;
    private JRadioButton availablePackagesButton;
    private JRadioButton bookedPackagesButton;

    private JComboBox filteringOption;
    private JTextField filterInput;
    private JButton filterButton;

    private JLabel selectedPackage;
    private JButton bookPackageButton;

    private JScrollPane scrollPane;
    private JTable packageTable;

    private final String[] comboBoxValues = {
            "Default",
            "Price equal",
            "Price greater",
            "Price lower",
            "Destination",
            "Period"
    };

    public UserWindow (User user) {
        super("Customer Panel");

        this.setSize(1600,900);

        this.user = user;
        this.packageController = new PackageController();
        this.userController = new UserController();

        this.mainPanel = new JPanel(new FlowLayout());

        this.title = new JLabel("Welcome to Traveling agency");
        this.title.setFont(new Font("Verdana", Font.BOLD, 32));
        this.title.setBorder(BorderFactory.createEmptyBorder(50, 500, 50, 500));

        this.mainPanel.add(title);

        createItems();
        createTable();

        this.getContentPane().add(mainPanel);
        this.setVisible(true);
    }

    public void createItems() {
        availablePackagesButton = new JRadioButton("Available Packages");
        availablePackagesButton.setFont(new Font("Verdana", Font.BOLD, 24));
        availablePackagesButton.setActionCommand("fetch_available");
        availablePackagesButton.addActionListener(this);

        bookedPackagesButton = new JRadioButton("Booked Packages");
        bookedPackagesButton.setFont(new Font("Verdana", Font.BOLD, 24));
        bookedPackagesButton.setActionCommand("fetch_booked");
        bookedPackagesButton.addActionListener(this);
        bookedPackagesButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 850));

        radioGroup = new ButtonGroup();
        radioGroup.add(availablePackagesButton);
        radioGroup.add(bookedPackagesButton);

        mainPanel.add(availablePackagesButton);
        mainPanel.add(bookedPackagesButton);

        filteringOption = new JComboBox(comboBoxValues);
        filteringOption.setFont(new Font("Verdana", Font.BOLD, 20));
        mainPanel.add(filteringOption);

        filterInput = new JTextField(30);
        filterInput.setFont(new Font("Verdana", Font.BOLD, 20));
        mainPanel.add(filterInput);

        filterButton = new JButton("Filter");
        filterButton.setActionCommand("filter");
        filterButton.addActionListener(this);
        filterButton.setFont(new Font("Verdana", Font.BOLD, 20));
        mainPanel.add(filterButton);

        bookPackageButton = new JButton("Book");
        bookPackageButton.setActionCommand("book");
        bookPackageButton.addActionListener(this);
        bookPackageButton.setFont(new Font("Verdana", Font.BOLD, 20));
        mainPanel.add(bookPackageButton);

        selectedPackage = new JLabel("");
        selectedPackage.setFont(new Font("Verdana", Font.BOLD, 20));
        selectedPackage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 400));
        mainPanel.add(selectedPackage);
    }

    public void createTable() {
        scrollPane = new JScrollPane();

        packageTable = new JTable();
        packageTable.setFont(new Font("Verdana", Font.PLAIN, 14));

        packageTable.setModel(new PackageTableModel(packageController.fetchAvailable()));

        scrollPane.setViewportView(packageTable);

        mainPanel.add(scrollPane);
    }

    public void fetchAvailablePackages() {
        packageTable.setModel(new PackageTableModel(packageController.fetchAvailable()));
    }

    public void fetchBookedPackages() {
        packageTable.setModel(new PackageTableModel(userController.fetchBookedVacations(user)));
    }

    public Double fetchPrice() {
        try {
            return Double.parseDouble(filterInput.getText());
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "The provided price must be a number",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void filterPackages() {

        if (!availablePackagesButton.isSelected())
            return;

        switch ((String) Objects.requireNonNull(filteringOption.getSelectedItem())) {
            case "Default":
                fetchAvailablePackages();
                break;
            case "Price equal":
                Double price = fetchPrice();

                if (price != null) {
                    packageTable.setModel(new PackageTableModel(
                            packageController.filterByPrice(price, "equal")
                    ));
                }
                break;
            case "Price greater":
                price = fetchPrice();

                if (price != null) {
                    packageTable.setModel(new PackageTableModel(
                            packageController.filterByPrice(price, "greater")
                    ));
                }
                break;
            case "Price lower":
                price = fetchPrice();

                if (price != null) {
                    packageTable.setModel(new PackageTableModel(
                            packageController.filterByPrice(price, "lower")
                    ));
                }
                break;
            case "Destination":
                packageTable.setModel(new PackageTableModel(packageController.filterByDestination(
                        filterInput.getText()
                )));
                break;
            case "Period":
                Date start = null;
                Date end = null;

                String[] start_and_end = filterInput.getText().split(" ");

                if (start_and_end.length < 2) {
                    JOptionPane.showMessageDialog(this,
                            "The provided date interval is not valid (use space between and yyyy-mm-dd format)",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // YYYY-MM-DD

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

                packageTable.setModel(new PackageTableModel(
                        packageController.filterByPeriod(start, end)
                ));
                break;
            default:
                System.out.println("ERROR user window: filter option not valid " + (String) Objects.requireNonNull(filteringOption.getSelectedItem()));
                break;
        }
    }

    public void bookSelectedPackage() {
        int selectedRow = packageTable.getSelectedRow();

        if (selectedRow == -1 || bookPackageButton.isSelected()) {
            return;
        }

        VacationPackage vacationPackage =
                ((PackageTableModel) packageTable.getModel()).getPackages().get(selectedRow);
        List<VacationPackage> alreadyBooked = userController.fetchBookedVacations(user);

        VacationPackage filtered = alreadyBooked.stream()
                .filter(pack -> pack.getName().equals(vacationPackage.getName())).findAny().orElse(null);

        if(filtered == null) {
            userController.bookPackage(user, vacationPackage);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch(command) {
            case "fetch_available":
                fetchAvailablePackages();
                break;
            case "fetch_booked":
                fetchBookedPackages();
                break;
            case "filter":
                filterPackages();
                break;
            case "book":
                bookSelectedPackage();
                break;
        }
    }
}
