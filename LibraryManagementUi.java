import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryHome extends JFrame {
    public LibraryHome() {
        setTitle("Library Management System");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));
        
        JLabel titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JButton loginButton = new JButton("Login");
        
        add(titleLabel);
        add(loginButton);
        
        loginButton.addActionListener(e -> {
            new LibraryLogin();
            dispose();
        });
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new LibraryHome();
    }
}

class LibraryLogin extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton, cancelButton;
    private static final String USERS_FILE = "users.txt";
    private static Map<String, String> userRoles = new HashMap<>();

    public LibraryLogin() {
        setTitle("Library Management System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));
        
        JLabel titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel userLabel = new JLabel("User ID:");
        userField = new JTextField("adm");
        JLabel passLabel = new JLabel("Password:");
        passField = new JPasswordField("adm");
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");
        
        add(titleLabel);
        add(new JLabel(""));
        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(cancelButton);
        add(loginButton);
        
        loadUsers();
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                
                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    new MainMenu(username, userRoles.get(username));
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        cancelButton.addActionListener(e -> {
            new LibraryHome();
            dispose();
        });
        
        setVisible(true);
    }
    
    private void loadUsers() {
        List<String> users = DataStorage.loadFromFile(USERS_FILE);
        for (String user : users) {
            String[] parts = user.split(",");
            if (parts.length == 3) {
                userRoles.put(parts[0], parts[2]); // Store username and role
            }
        }
    }

    private boolean authenticate(String username, String password) {
        List<String> users = DataStorage.loadFromFile(USERS_FILE);
        for (String user : users) {
            String[] parts = user.split(",");
            if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                return true;
            }
        }
        return false;
    }
}

class MainMenu extends JFrame {
    public MainMenu(String username, String role) {
        setTitle("Library Management System - Main Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));
        
        JButton maintenanceButton = new JButton("Maintenance");
        JButton reportsButton = new JButton("Reports");
        JButton transactionsButton = new JButton("Transactions");
        JButton bookManagementButton = new JButton("Book Management");
        JButton logoutButton = new JButton("Log Out");
        
        buttonPanel.add(maintenanceButton);
        buttonPanel.add(reportsButton);
        buttonPanel.add(transactionsButton);
        buttonPanel.add(bookManagementButton);
        
        add(welcomeLabel);
        add(buttonPanel);
        add(logoutButton);
        
        bookManagementButton.addActionListener(e -> {
            new BookManagement();
        });
        
        logoutButton.addActionListener(e -> {
            new LibraryLogin();
            dispose();
        });
        
        setVisible(true);
    }
}

class BookManagement extends JFrame {
    public BookManagement() {
        setTitle("Book Management");
        setSize(500, 300);
        setLayout(new GridLayout(3, 2));
        
        JButton addBook = new JButton("Add Book");
        JButton updateBook = new JButton("Update Book");
        JButton issueBook = new JButton("Issue Book");
        JButton returnBook = new JButton("Return Book");
        JButton back = new JButton("Back");
        
        add(addBook);
        add(updateBook);
        add(issueBook);
        add(returnBook);
        add(back);
        
        back.addActionListener(e -> dispose());
        
        setVisible(true);
    }
}
