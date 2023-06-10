package View;

import Database.UserDatabase;
import Database.UserDetailsDatabase;
import Model.UserDetails;
import Service.PrintSheetService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDetailsView extends JFrame {

    private JPanel contentPane;
    private JTextField datet;
    private JTextField fromt, tot;
    private JTable table;
    private JButton search;
    private JLabel totalLabel;

    private String[] header = {"studentName", "CourseName", "Money", "Detail", "Date"};
    private String[][] body;

    private UserDetailsDatabase userDetailsDatabase = new UserDetailsDatabase();
    private ArrayList<UserDetails> userDetails = new ArrayList<>();
    private ArrayList<UserDetails> userDetailsFromDateToDate = new ArrayList<>();
    private long personId;


    private String name = "No Name";
    private JScrollPane scrollPane;

    /**
     * Create the frame.
     */
    public UserDetailsView(long personId) {
        this.personId = personId;
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(250, 5, 900, 720);
        contentPane = new JPanel();
        contentPane.setBackground(Color.GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        try {
            name = new UserDatabase().getUserById(personId).getUsername();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        JLabel lblNewLabel = new JLabel(name);
        lblNewLabel.setForeground(Color.RED);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
        lblNewLabel.setBounds(0, 0, 900, 42);
        contentPane.add(lblNewLabel);

        JLabel lblDate = new JLabel("date");
        lblDate.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
        lblDate.setBounds(21, 84, 82, 33);
        contentPane.add(lblDate);

        datet = new JTextField();
        datet.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    search.doClick();
            }
        });
        datet.setFont(new Font("Tahoma", Font.BOLD, 14));
        datet.setColumns(10);
        datet.setBounds(102, 88, 135, 20);
        contentPane.add(datet);

        search = new JButton("");
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    userDetails = userDetailsDatabase.getAllByUserAndDate(datet.getText(), personId);
                    setTable();
                    totalLabel.setText(String.valueOf(getTotal()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        search.setIcon(new ImageIcon(UserDetailsView.class.getResource("/View/Icons/search.png")));
        search.setBounds(247, 88, 30, 23);
        contentPane.add(search);

        JButton search2 = new JButton("");
        search2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    setTableAfterSearchFromAndTo();
                    totalLabel.setText(String.valueOf(getTotal()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        search2.setIcon(new ImageIcon(UserDetailsView.class.getResource("/View/Icons/search.png")));
        search2.setBounds(474, 91, 30, 23);
        contentPane.add(search2);

        tot = new JTextField();
        tot.setFont(new Font("Tahoma", Font.BOLD, 14));
        tot.setColumns(10);
        tot.setBounds(329, 91, 135, 20);
        contentPane.add(tot);

        fromt = new JTextField();
        fromt.setFont(new Font("Tahoma", Font.BOLD, 14));
        fromt.setColumns(10);
        fromt.setBounds(328, 53, 135, 20);
        contentPane.add(fromt);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(31, 137, 823, 402);
        contentPane.add(scrollPane);

        table = new JTable();
        try {
            userDetails = userDetailsDatabase.getUserDetailsForUserByUserId(personId);
            setTable();
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, e1.getMessage());
        }

        JButton exit = new JButton("");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        exit.setIcon(new ImageIcon(UserDetailsView.class.getResource("/View/Icons/Close.png")));
        exit.setBounds(762, 53, 89, 64);
        contentPane.add(exit);

        JButton print = new JButton("Print");
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PrintSheetService().printTableAsExcelWithTotal(name, table , getTotal());
                JOptionPane.showMessageDialog(null, "Printed Success...");
            }
        });
        print.setBackground(Color.CYAN);
        print.setForeground(Color.BLUE);
        print.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
        print.setBounds(210, 609, 473, 42);
        contentPane.add(print);

        totalLabel = new JLabel("Total : ");
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setForeground(Color.RED);
        totalLabel.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
        totalLabel.setBounds(10, 556, 890, 42);
        contentPane.add(totalLabel);

        JButton btnClearAllUser = new JButton("Clear all user details");
        btnClearAllUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int x = JOptionPane.showConfirmDialog(null,
                            "ARE YOU SURE YOU WANT TO CLEAR ALL DETAILS FOR THIS USER ");
                    if (x == 0) {
                        userDetailsDatabase.clearAllDataForUser(personId);
                        refresh();
                    }
                } catch (Exception ex) {
                }
            }
        });
        btnClearAllUser.setIcon(new ImageIcon(UserDetailsView.class.getResource("/View/Icons/delete.png")));
        btnClearAllUser.setForeground(Color.BLUE);
        btnClearAllUser.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
        btnClearAllUser.setBackground(Color.RED);
        btnClearAllUser.setBounds(210, 662, 473, 47);
        contentPane.add(btnClearAllUser);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserDetailsView(personId).setVisible(true);
            }
        });
        btnRefresh.setBackground(Color.CYAN);
        btnRefresh.setForeground(Color.BLUE);
        btnRefresh.setFont(new Font("Algerian", Font.BOLD, 25));
        btnRefresh.setBounds(551, 84, 201, 29);
        contentPane.add(btnRefresh);

        // TOTAL
        totalLabel.setText(String.valueOf(getTotal()));
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserDetailsView frame = new UserDetailsView(1);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setTable() {
        try {

            int len = userDetails.size();
            body = new String[len][5];
            for (int i = 0; i < len; i++) {
                body[i][0] = userDetails.get(i).getStudentName();
                body[i][1] = userDetails.get(i).getCourseName();
                body[i][2] = String.valueOf(userDetails.get(i).getMoney());
                body[i][3] = userDetails.get(i).getSub();
                body[i][4] = userDetails.get(i).getDate();
            }
            table = new JTable(body, header);
            scrollPane.setViewportView(table);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void refresh() {
        this.dispose();
        new UserDetailsView(personId).setVisible(true);
    }

    private void setTableAfterSearchFromAndTo() throws SQLException {
        userDetails = userDetailsDatabase.getUserDetailsForUserByUserId(personId);
        String from = fromt.getText(), to = tot.getText();
        int i = 0;
        int counter = 0;
        boolean flag = false;
        boolean flag2 = false;
        for (; i < userDetails.size(); i++) {
            if (userDetails.get(i).getDate().equals(from)) {
                flag = true;
            }

            if (!flag2 && userDetails.get(i).getDate().equals(to))
                flag2 = true;
            if (flag2 && !userDetails.get(i).getDate().equals(to))
                break;
            if (flag) {
                userDetailsFromDateToDate.add(userDetails.get(i));
                counter++;
            }
        }
        userDetails = userDetailsFromDateToDate;
        setTable();
    }

    private double getTotal() {
        double total = 0;
        for (int i = 0; i < userDetails.size(); i++) {
            if (userDetails.get(i).getSub().equals("REFUND"))
                total -= userDetails.get(i).getMoney();
            else
                total += userDetails.get(i).getMoney();
        }
        return total;
    }
}
