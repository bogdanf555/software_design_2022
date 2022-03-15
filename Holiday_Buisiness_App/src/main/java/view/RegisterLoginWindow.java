package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterLoginWindow extends JFrame implements ActionListener {

    private MainWindow mainWindow;

    private UserController userController;

    private JPanel mainPanel;

    private JLabel title;

    private JLabel usernameLabel;
    private JTextField usernameText;

    private JLabel passwordLabel;
    private JPasswordField passwordText;

    private JLabel firstNameLabel;
    private JTextField firstNameText;

    private JLabel lastNameLabel;
    private JTextField lastNameText;

    private JButton loginOrRegisterButton;

    public RegisterLoginWindow(String loginOrRegister, MainWindow mainWindow) {

        super("User " + loginOrRegister);

        this.setSize(650,600);


        this.userController = new UserController();
        this.mainWindow = mainWindow;
        this.mainPanel = new JPanel(new FlowLayout());

        this.title = new JLabel("User " + loginOrRegister);
        this.title.setFont(new Font("Verdana", Font.BOLD, 32));
        this.title.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));
        this.mainPanel.add(title);

        createLabelsAndFields(loginOrRegister);
        createButton(loginOrRegister);

        this.getContentPane().add(mainPanel);
        this.setVisible(true);
    }

    public void createLabelsAndFields(String loginOrRegister) {

        this.usernameLabel = new JLabel("Username:");
        this.usernameText = new JTextField(50);

        this.passwordLabel = new JLabel("Password:");
        this.passwordText = new JPasswordField(50);

        this.mainPanel.add(usernameLabel);
        this.mainPanel.add(usernameText);
        this.mainPanel.add(passwordLabel);
        this.mainPanel.add(passwordText);

        if (loginOrRegister.equals("register")) {
            this.firstNameLabel = new JLabel("First name:");
            this.firstNameText = new JTextField(50);

            this.lastNameLabel = new JLabel("Last name:");
            this.lastNameText = new JTextField(50);

            this.mainPanel.add(firstNameLabel);
            this.mainPanel.add(firstNameText);
            this.mainPanel.add(lastNameLabel);
            this.mainPanel.add(lastNameText);
        }
    }

    public void createButton(String loginOrRegister) {
        this.loginOrRegisterButton = new JButton(
                loginOrRegister.substring(0, 1).toUpperCase() + loginOrRegister.substring(1)
        );
        this.loginOrRegisterButton.setFont(new Font("Verdana", Font.BOLD, 16));
        this.loginOrRegisterButton.setActionCommand(loginOrRegister);
        this.loginOrRegisterButton.addActionListener(this);
        this.mainPanel.add(loginOrRegisterButton);
    }

    public void userLogin(boolean fail) {
        User user = userController.login(usernameText.getText(), passwordText.getText());

        if (user != null && !fail) {
            this.mainWindow.createAndOpenUserWindow(user);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this,
                    "The provided username or password is not valid",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {

            case "login":
                userLogin(false);
                break;
            case "register":
                boolean inserted = userController.insertUser(new User(
                        firstNameText.getText(),
                        lastNameText.getText(),
                        usernameText.getText(),
                        passwordText.getText()
                ));

                userLogin(!inserted);
                break;
            default:
                System.out.println("user window action performed problem");
                break;
        }
    }
}
