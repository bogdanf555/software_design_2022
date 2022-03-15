package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {

    private RegisterLoginWindow registerLoginWindow;
    private AgencyWindow agencyWindow;
    private UserWindow userWindow;

    private User user;

    private JPanel mainPanel;

    private JLabel title;
    private JButton loginButton;
    private JButton registerButton;
    private JButton managementButton;

    public MainWindow() {

        super("Traveling Agency");

        this.agencyWindow = new AgencyWindow();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650,600);

        this.mainPanel = new JPanel(new FlowLayout());

        this.title = new JLabel("Welcome to Traveling agency");
        this.title.setFont(new Font("Verdana", Font.BOLD, 32));
        this.title.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.mainPanel.add(title);

        createAddButtons();


        this.getContentPane().add(mainPanel);
        this.setVisible(true);
    }

    public void createAddButtons() {
        this.loginButton = new JButton("Login");
        this.registerButton = new JButton("Register");
        this.managementButton = new JButton("Manage Packages");

        this.loginButton.setFont(new Font("Verdana", Font.BOLD, 16));
        this.registerButton.setFont(new Font("Verdana", Font.BOLD, 16));
        this.managementButton.setFont(new Font("Verdana", Font.BOLD, 16));

        this.loginButton.setActionCommand("login");
        this.registerButton.setActionCommand("register");
        this.managementButton.setActionCommand("management");

        this.loginButton.addActionListener(this);
        this.registerButton.addActionListener(this);
        this.managementButton.addActionListener(this);

        this.mainPanel.add(loginButton);
        this.mainPanel.add(registerButton);
        this.mainPanel.add(managementButton);
    }

    public void createAndOpenRegisterLoginWindow(String loginOrRegister) {

        registerLoginWindow = new RegisterLoginWindow(loginOrRegister, this);
        registerLoginWindow.setVisible(true);
    }

    public void createAndOpenUserWindow(User user) {
        this.userWindow = new UserWindow(user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "login":
                createAndOpenRegisterLoginWindow("login");
                break;
            case "register":
                createAndOpenRegisterLoginWindow("register");
                break;
            case "management":
                agencyWindow.updatePackageTable();
                agencyWindow.updateDestinationTable();
                agencyWindow.setVisible(true);
                break;
            default:
                break;
        }
    }
}
