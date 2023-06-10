package View;

import Database.UserDatabase;
import Model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginView extends JFrame {

    private JPanel contentPane;
    private JTextField usernamet;
    private JPasswordField passwordt;
    private JButton login;

    private UserDatabase userDatabase = new UserDatabase();

    /**
     * Create the frame.
     */
    public LoginView() {
        setIconImage(Toolkit.getDefaultToolkit()
                .getImage(LoginView.class.getResource("/View/Icons/WhatsApp Image 2023-05-09 at 5.27.55 PM.jpeg")));
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(350, 100, 657, 559);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        usernamet = new JTextField();
        usernamet.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    passwordt.requestFocus();
            }
        });
        usernamet.setToolTipText("");
        usernamet.setFont(new Font("Tahoma", Font.BOLD, 20));
        usernamet.setBounds(183, 108, 289, 26);
        contentPane.add(usernamet);
        usernamet.setColumns(10);

        JLabel lblNewLabel = new JLabel("Username");
        lblNewLabel.setForeground(Color.BLUE);
        lblNewLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        lblNewLabel.setBounds(10, 111, 153, 26);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Login");
        lblNewLabel_1.setForeground(Color.BLUE);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
        lblNewLabel_1.setBounds(0, 0, 641, 86);
        contentPane.add(lblNewLabel_1);

        JLabel passw = new JLabel("Password");
        passw.setForeground(Color.BLUE);
        passw.setFont(new Font("Algerian", Font.BOLD, 20));
        passw.setBounds(10, 251, 153, 26);
        contentPane.add(passw);

        passwordt = new JPasswordField();
        passwordt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    login.doClick();
            }
        });
        passwordt.setFont(new Font("Tahoma", Font.BOLD, 20));
        passwordt.setBounds(183, 255, 289, 26);
        contentPane.add(passwordt);

        login = new JButton("LOGIN");
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    if (check()) {
                        String username = usernamet.getText();
                        String password = passwordt.getText();
                        User user = userDatabase.getUserByUsernameAndPassword(username, password);
                        if (username.equals("Reciet")) {
                            if (password.equals("Reciet")) {
                                JOptionPane.showMessageDialog(null, "WELCOME ADMIN!.");
                                dispose();
                                new Base(-1).setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "ERROR IN PASSWORD", "ERROR MESSAGE",
                                        JOptionPane.WARNING_MESSAGE);
                            }
                        } else if (user != null) {
                            JOptionPane.showMessageDialog(null, "WELCOME USER!.");
                            dispose();
                            new Base(user.getId()).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "ERROR IN PASSWORD", "ERROR MESSAGE",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        login.setBorder(new LineBorder(new Color(0, 0, 0), 4));
        login.setForeground(Color.ORANGE);
        login.setBackground(new Color(249, 82, 11));
        login.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
        login.setBounds(212, 468, 200, 41);
        contentPane.add(login);

        JLabel lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setIcon(
                new ImageIcon(LoginView.class.getResource("/View/Icons/WhatsApp Image 2023-05-09 at 5.27.55 PM.jpeg")));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(0, 0, 641, 520);
        contentPane.add(lblNewLabel_2);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginView frame = new LoginView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean check() {
        if (usernamet.getText().isBlank() || usernamet.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "USERNAME IS REQUIRED...");
            return false;
        }
        if (passwordt.getText().isBlank() || passwordt.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "PASSWORD IS REQUIRED...");
            return false;
        }
        return true;
    }
}
