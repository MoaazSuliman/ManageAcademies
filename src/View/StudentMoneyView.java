package View;

import Database.CourseDatabase;
import Database.StudentCourseDatabase;
import Database.StudentDatabase;
import Database.UserDetailsDatabase;
import Model.*;
import Service.PrintRecietService;
import Service.PrintSheetService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentMoneyView extends JFrame {

    private JPanel contentPane;
    private JTextField moneyt;
    private JTable table;
    private JLabel studentName, courseName;
    private JScrollPane scrollPane;
    private JButton search, addMoney;

    private StudentDatabase studentDatabase = new StudentDatabase();
    private CourseDatabase courseDatabase = new CourseDatabase();
    private StudentCourseDatabase studentCourseDatabase = new StudentCourseDatabase();
    private UserDetailsDatabase userDetailsDatabase = new UserDetailsDatabase();

    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<StudentCourse> assigns = new ArrayList<>();

    private String header[] = {"Student Name", "Course Name", "Level", "Payed Money", "CourseMoney", "Balance",
            "Months", "Date"};
    private String[][] body;
    private JTextField searcht;
    private JLabel lblNewLabel;
    private JButton refund;

    private long personId;
    private JTextField courseSearcht;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JButton print;
    private JTextField sheetNamet;

    /**
     * Create the frame.
     */
    public StudentMoneyView(long personId) {
        this.personId = personId;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 1350, 720);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(253, 148, 1071, 466);
        contentPane.add(scrollPane);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        try {
            assigns = studentCourseDatabase.getAllAssigns();
            setTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        JLabel lblNewLabel_1_2 = new JLabel("Student Course Money");
        lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_2.setForeground(Color.RED);
        lblNewLabel_1_2.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 40));
        lblNewLabel_1_2.setBounds(94, 0, 1227, 45);
        contentPane.add(lblNewLabel_1_2);

        moneyt = new JTextField();
        moneyt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    addMoney.doClick();
            }
        });
        moneyt.setFont(new Font("Tahoma", Font.BOLD, 20));
        moneyt.setText("0");
        moneyt.setBounds(70, 198, 128, 28);
        contentPane.add(moneyt);
        moneyt.setColumns(10);

        addMoney = new JButton("Add Money");
        addMoney.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double money = Double.parseDouble(moneyt.getText());
                int index = table.getSelectedRow();
                if (index != -1) {
                    StudentCourse studentCourse = assigns.get(index);
                    double newMoney = money + studentCourse.getPayedMoney();
                    try {
                        studentCourseDatabase.updateMoney(studentCourse, newMoney);
                        JOptionPane.showMessageDialog(null, "Payed Success.");
                        // SAY THAT USER TAKE SOME MONEY FROM STUDENT.
                        UserDetails userDetails = new UserDetails(-1, studentCourse.getStudent().getName(),
                                studentCourse.getCourse().getName(), money, "ADDITION", new Method().returnDate(),
                                personId);
                        new UserDetailsDatabase().addUserDetails(userDetails);
                        // REFRESH.
                        dispose();
                        new StudentMoneyView(personId).setVisible(true);

                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "CHOOSE ROW FROM THIS TABLE");
                }

            }
        });
        addMoney.setBackground(Color.CYAN);
        addMoney.setForeground(Color.BLUE);
        addMoney.setFont(new Font("Algerian", Font.BOLD, 20));
        addMoney.setBounds(43, 250, 200, 45);
        contentPane.add(addMoney);

        studentName = new JLabel("No Student");
        studentName.setForeground(Color.BLUE);
        studentName.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
        studentName.setBounds(10, 63, 482, 23);
        contentPane.add(studentName);

        courseName = new JLabel("No Course");
        courseName.setForeground(Color.BLUE);
        courseName.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
        courseName.setBounds(10, 102, 482, 23);
        contentPane.add(courseName);

        if (personId == -1) {
            JButton delete = new JButton("Delete");
            delete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int index = table.getSelectedRow();
                    if (index >= 0) {
                        long id = assigns.get(index).getId();
                        try {
                            studentCourseDatabase.deleteStudentCourseById(id);
                            dispose();
                            new StudentMoneyView(personId).setVisible(true);
                        } catch (SQLException e1) {
                            JOptionPane.showMessageDialog(null, e1.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "CHOOSE ROW FROM THIS TABLE TO DELETE IT..");
                    }

                }
            });
            delete.setBackground(Color.RED);
            delete.setForeground(Color.BLUE);
            delete.setIcon(new ImageIcon(StudentMoneyView.class.getResource("/View/Icons/delete.png")));
            delete.setFont(new Font("Algerian", Font.BOLD, 20));
            delete.setBounds(43, 460, 198, 51);
            contentPane.add(delete);
        }
        JButton btnNewButton = new JButton("");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Base(personId).setVisible(true);
            }
        });
        btnNewButton.setIcon(new ImageIcon(StudentMoneyView.class.getResource("/View/Icons/Back.png")));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnNewButton.setBounds(10, 11, 74, 28);
        contentPane.add(btnNewButton);

        JButton refresh = new JButton("REFRESH");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StudentMoneyView(personId).setVisible(true);
            }
        });
        refresh.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
        refresh.setBorder(null);
        refresh.setBackground(Color.ORANGE);
        refresh.setBounds(1012, 22, 300, 23);
        contentPane.add(refresh);

        searcht = new JTextField();
        searcht.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    search.doClick();
            }
        });
        searcht.setFont(new Font("Tahoma", Font.BOLD, 20));
        searcht.setColumns(10);
        searcht.setBounds(608, 98, 306, 27);
        contentPane.add(searcht);

        search = new JButton("");
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String condition = searcht.getText();
                    assigns = new StudentCourse().getByStudentName(studentCourseDatabase.getAllAssigns(), condition);
                    setTable();
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
        search.setIcon(new ImageIcon(StudentMoneyView.class.getResource("/View/Icons/search.png")));
        search.setBounds(928, 102, 33, 23);
        contentPane.add(search);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBounds(10, 50, 1314, 2);
        contentPane.add(panel);

        refund = new JButton("course refund");
        refund.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = -1;
                    index = table.getSelectedRow();
                    if (index != -1) {
                        StudentCourse assign = assigns.get(index);
                        UserDetails details = new UserDetails(-1, assign.getStudent().getName(),
                                assign.getCourse().getName(), assign.getCourseMoney(), "REFUND",
                                new Method().returnDate(), personId);
                        // ADD THIS FOR USER DETAILS.
                        new UserDetailsDatabase().addUserDetails(details);
                        // REMOVE ASSIGN FROM STUDENT AND COURSE.
                        deleteAssign(index);
                        JOptionPane.showMessageDialog(null, "REFUND COURSE SUCCESS...");
                        refresh.doClick();
                    } else
                        JOptionPane.showMessageDialog(null, "CHOOSE ROWS FROM TABLE...");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        refund.setForeground(Color.BLUE);
        refund.setFont(new Font("Algerian", Font.BOLD, 20));
        refund.setBackground(Color.CYAN);
        refund.setBounds(43, 329, 200, 45);
        contentPane.add(refund);

        JButton courseSearch = new JButton("");
        courseSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String condition = courseSearcht.getText();
                    assigns = new StudentCourse().getByCourseName(studentCourseDatabase.getAllAssigns(), condition);
                    setTable();
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
        courseSearch.setIcon(new ImageIcon(StudentMoneyView.class.getResource("/View/Icons/search.png")));
        courseSearch.setBounds(1287, 102, 33, 23);
        contentPane.add(courseSearch);

        courseSearcht = new JTextField();
        courseSearcht.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    courseSearch.doClick();
            }
        });
        courseSearcht.setFont(new Font("Tahoma", Font.BOLD, 20));
        courseSearcht.setColumns(10);
        courseSearcht.setBounds(971, 102, 306, 27);
        contentPane.add(courseSearcht);

        lblNewLabel_1 = new JLabel("Student");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
        lblNewLabel_1.setBounds(608, 56, 281, 30);
        contentPane.add(lblNewLabel_1);

        lblNewLabel_2 = new JLabel("Course");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
        lblNewLabel_2.setBounds(971, 56, 281, 30);
        contentPane.add(lblNewLabel_2);

        print = new JButton("Print");
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = sheetNamet.getText();
                new PrintSheetService().printTableAsExcel(name, table);
                JOptionPane.showMessageDialog(null, "Printed Success...");
            }
        });
        print.setForeground(Color.BLUE);
        print.setFont(new Font("Algerian", Font.BOLD, 20));
        print.setBackground(Color.CYAN);
        print.setBounds(763, 642, 380, 28);
        contentPane.add(print);


        sheetNamet = new JTextField();
        sheetNamet.setBounds(408, 638, 336, 32);
        contentPane.add(sheetNamet);
        sheetNamet.setColumns(10);

        JButton printReciet = new JButton("Print Reciet");
        printReciet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = -1;
                index = table.getSelectedRow();
                if (index != -1) {
                    new PrintRecietService(assigns.get(index)).print();
                    JOptionPane.showMessageDialog(null, "PRINTED SUCCESS...");
                } else
                    JOptionPane.showMessageDialog(null, "CHOOSE ROW FROM THE TABLE...");
            }
        });
        printReciet.setForeground(Color.BLUE);
        printReciet.setFont(new Font("Algerian", Font.BOLD, 20));
        printReciet.setBackground(Color.CYAN);
        printReciet.setBounds(43, 397, 200, 45);
        contentPane.add(printReciet);

        lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon(StudentMoneyView.class.getResource("/View/Icons/backforall2.png")));
        lblNewLabel.setBounds(0, 0, 1334, 681);
        contentPane.add(lblNewLabel);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StudentMoneyView frame = new StudentMoneyView(-1);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setTable() throws SQLException {

        int len = assigns.size();
        body = new String[len][8];
        for (int i = 0; i < len; i++) {
            body[i][0] = assigns.get(i).getStudent().getName();
            body[i][1] = assigns.get(i).getCourse().getName();
            if (assigns.get(i).getLevel().getId() == -1)
                body[i][2] = "OFF";
            else if (assigns.get(i).getLevel().getId() == -5)
                body[i][2] = "ALL";
            else
                body[i][2] = assigns.get(i).getLevel().getName();
            body[i][3] = String.valueOf(assigns.get(i).getPayedMoney());
            body[i][4] = String.valueOf(assigns.get(i).getCourseMoney());
            double remainingBalance = (assigns.get(i).getCourseMoney()) - (assigns.get(i).getPayedMoney());
            body[i][5] = String.valueOf(remainingBalance);
            if (assigns.get(i).getMonths() > 0)
                body[i][6] = String.valueOf(assigns.get(i).getMonths());
            else if (assigns.get(i).getMonths() == -5)
                body[i][6] = "All Course";
            else if (assigns.get(i).getMonths() == -3)
                body[i][6] = "Level";
            else
                body[i][6] = "OFF";
            body[i][7] = assigns.get(i).getDate();

        }
        table = new JTable(body, header);
        table.setBackground(Color.LIGHT_GRAY);
        table.setFont(new Font("Tahoma", Font.BOLD, 15));
        table.setForeground(Color.BLACK);

        table.getTableHeader().setBackground(Color.cyan);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
        table.setRowHeight(30);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = -1;
                index = table.getSelectedRow();
                if (index != -1) {
                    studentName.setText(assigns.get(index).getStudent().getName());
                    courseName.setText(assigns.get(index).getCourse().getName());
                }
            }
        });
        scrollPane.setViewportView(table);
    }

    private void deleteAssign(int index) {
        long id = assigns.get(index).getId();
        try {
            studentCourseDatabase.deleteStudentCourseById(id);
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, e1.getMessage());
        }
    }
}
