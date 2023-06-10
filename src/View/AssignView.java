package View;

import Database.*;
import Model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AssignView extends JFrame {

    private JCheckBox checkBox;
    private JPanel contentPane;
    private JTextField studentSearcht;
    private JTextField courseSearcht;
    private JTextField moneyt;
    private JTextPane printt;
    private JButton refresh, done, studentSearch, courseSearch;
    private JLabel studentName, courseName;
    private StyledDocument doc;
    private SimpleAttributeSet small = new SimpleAttributeSet();
    private SimpleAttributeSet large = new SimpleAttributeSet();
    private JScrollPane scrollPane, scrollPane_1, scrollPane_2;
    private boolean discountFlag = false;

    private Student chosenStudent;
    private ArrayList<Course> chosenCourses = new ArrayList<>();
    private ArrayList<Level> levels = new ArrayList<>();

    private String[] studentHeader = {"Student Name"};
    private String[] courseHeader = {"Coure Name", "Course Price"};
    private String[] levelHeader = {"Level Name", "Level Price"};

    private String[][] studentBody;
    private String[][] courseBody;
    private String[][] levelBody;

    private JTable studentTable;
    private JTable courseTable;
    private JTable levelTable;

    private ArrayList<Student> students;
    private ArrayList<Course> courses;
    private ArrayList<StudentCourse> assigns = new ArrayList<>();
    private double bonus = 0;

    // For Printer.
    private double totalPayedMoney = 0;
    private double totalMoney = 0;
    private boolean all = false;
    private boolean flag = false;

    private StudentDatabase studentDatabase = new StudentDatabase();
    private CourseDatabase courseDatabase = new CourseDatabase();
    private StudentCourseDatabase studentCourseDatabase = new StudentCourseDatabase();
    private LevelDatabase levelDatabase = new LevelDatabase();
    private UserDetailsDatabase userDetailsDatabase = new UserDetailsDatabase();
    private JTextField monthst;

    // PER DISCOUNT:
    private double discountPre = 0;
    private double discount = 0;
    private JLabel lblNewLabel_2;
    private JTextField discountPret;
    private JLabel lblNewLabel_4;
    private JTextField discountt;

    private long personId;
    private JScrollPane scrollPane_3;

    public AssignView(long personId) throws BadLocationException {
        this.personId = personId;
        totalMoney = 0;
        // for text pane
        StyleConstants.setFontSize(small, 18);
        StyleConstants.setFontSize(large, 24);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 1350, 720);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(204, 204, 204));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 148, 273, 319);
        contentPane.add(scrollPane);

        studentTable = new JTable();
        try {
            students = studentDatabase.getAllStudents();
            setStudentTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        studentSearcht = new JTextField();
        studentSearcht.setFont(new Font("Tahoma", Font.BOLD, 15));
        studentSearcht.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    studentSearch.doClick();
            }
        });
        studentSearcht.setBounds(10, 121, 239, 20);
        contentPane.add(studentSearcht);
        studentSearcht.setColumns(10);

        studentSearch = new JButton("");
        studentSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String condition = studentSearcht.getText();
                    students = studentDatabase.search(condition);
                    setStudentTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        studentSearch.setIcon(new ImageIcon(AssignView.class.getResource("/View/Icons/search.png")));
        studentSearch.setBounds(259, 121, 24, 23);
        contentPane.add(studentSearch);

        courseSearcht = new JTextField();
        courseSearcht.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    courseSearch.doClick();
            }
        });
        courseSearcht.setFont(new Font("Tahoma", Font.BOLD, 15));
        courseSearcht.setColumns(10);
        courseSearcht.setBounds(317, 122, 239, 20);
        contentPane.add(courseSearcht);

        courseSearch = new JButton("");
        courseSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String condition = courseSearcht.getText();
                    courses = courseDatabase.search(condition);
                    setCourseTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        courseSearch.setIcon(new ImageIcon(AssignView.class.getResource("/View/Icons/search.png")));
        courseSearch.setBounds(566, 121, 24, 23);
        contentPane.add(courseSearch);

        scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(317, 149, 273, 318);
        contentPane.add(scrollPane_1);

        courseTable = new JTable();
        try {
            courses = courseDatabase.getAllCourse();
            setCourseTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        moneyt = new JTextField();
        moneyt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    done.doClick();

            }
        });
        moneyt.setText("0");
        moneyt.setFont(new Font("Tahoma", Font.BOLD, 30));
        moneyt.setBounds(171, 546, 162, 32);
        contentPane.add(moneyt);
        moneyt.setColumns(10);

        JLabel lblNewLabel = new JLabel("Money");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 25));
        lblNewLabel.setBounds(10, 546, 129, 32);
        contentPane.add(lblNewLabel);

        done = new JButton("Done");
        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int studentIndex = studentTable.getSelectedRow();
                    int courseIndex = courseTable.getSelectedRow();
                    UserDetails details = new UserDetails();
                    StudentCourse assign = null;
                    if (studentIndex != -1 && courseIndex != -1) {
                        Student student = students.get(studentIndex);
                        Course course = courses.get(courseIndex);
                        chosenStudent = student;
                        putStudentForPrinter();
                        if (levels.size() == 0) {// THERE ARE NO LEVEL.
                            // THERE ARE NO LEVEL : MONTHLY
                            if (checkBox.isSelected()) {
                                Level level = new Level();
                                level.setId(-1);
                                int months = Integer.parseInt(monthst.getText());
                                double totalMoney = (months * 1.0) * course.getPrice();
                                double payedMoney = Double.parseDouble(moneyt.getText());
                                assign = new StudentCourse(0, student, course, level, payedMoney, totalMoney, months);
                                putAssignedInPrinter(assign);
                                // UPDATE COURSE MONEY AFTER DISCOUNT.
                                assign.setCourseMoney(getCourseMoneyAfterDiscount(assign.getCourseMoney()));
                                // PUT COURSE MONEY AFTER DISCOUNT TO 400.
                                putCourseMoneyAfterDiscountInPrinter(assign);
                                // PUT BALANCE DUE IN PRINTER.
                                double diff = assign.getCourseMoney();
                                diff -= assign.getPayedMoney();
                                putDifferentMoneyInPrinter(diff);

                                // ADD TOTAL FOR PRINTER.
                                putTotalMoneyInPrinter(payedMoney);
                                // ADD LINE.
                                addLine();

                                assigns.add(assign);
//							saveAssign(assign);
                            }
                            // THERE ARE NO LEVEL: ALL COURSE
                            else {
                                Level level = new Level();
                                level.setId(-5);
                                double totalMoney = course.getPrice();
                                double payedMoney = Double.parseDouble(moneyt.getText());
                                assign = new StudentCourse(0, student, course, level, payedMoney, totalMoney, -5);
                                putAssignedInPrinter(assign);
                                // UPDATE COURSE MONEY AFTER DISCOUNT.
                                assign.setCourseMoney(getCourseMoneyAfterDiscount(assign.getCourseMoney()));
                                // PUT COURSE MONEY AFTER DISCOUNT TO 400.
                                putCourseMoneyAfterDiscountInPrinter(assign);
                                // PUT PAYED MONEY IN PRINTER.
                                putPayedMoneyInPrinter(assign);
                                // PUT BALANCE DUE IN PRINTER.
                                double diff = assign.getCourseMoney();
                                diff -= assign.getPayedMoney();
                                putDifferentMoneyInPrinter(diff);

                                // ADD TOTAL FOR PRINTER.
                                putTotalMoneyInPrinter(payedMoney);
                                // ADD LINE.
                                addLine();
//							saveAssign(assign);
                            }

                        } else {// THERE ARE LEVELS FOR COURSE ==> ASSIGN IN ALL OR SPECIFIC.
                            if (!all) {
                                int indexs[] = levelTable.getSelectedRows();
                                if (indexs.length > 0) {// HE CHOOSE LEVEL FROM THIS COURSE.
                                    for (int i = 0; i < indexs.length; i++) {
                                        double totalMoney = levels.get(indexs[i]).getPrice();
                                        double payedMoney = Double.parseDouble(moneyt.getText());
                                        assign = new StudentCourse(0, student, course, levels.get(indexs[i]), payedMoney,
                                                totalMoney, -5);
                                        putAssignedInPrinter(assign);
                                        // UPDATE COURSE MONEY AFTER DISCOUNT.
                                        assign.setCourseMoney(getCourseMoneyAfterDiscount(assign.getCourseMoney()));
                                        // PUT COURSE MONEY AFTER DISCOUNT TO 400.
                                        putCourseMoneyAfterDiscountInPrinter(assign);
                                        // PUT BALANCE DUE IN PRINTER .
                                        double diff = assign.getCourseMoney();
                                        diff -= assign.getPayedMoney();
                                        putDifferentMoneyInPrinter(diff);

                                        // ADD TOTAL FOR PRINTER.
                                        putTotalMoneyInPrinter(payedMoney);
                                        // ADD LINE.
                                        addLine();
                                        assigns.add(assign);
//									saveAssign(assign);
                                    }
                                } else
                                    JOptionPane.showMessageDialog(null, "Choose Levels");
                            } else if (all) {// ALL COURSE.
                                Level level = new Level();
                                level.setId(-5);
                                double totalMoney = course.getPrice();
                                double payedMoney = Double.parseDouble(moneyt.getText());
                                assign = new StudentCourse(0, student, course, level, payedMoney, totalMoney, -5);
                                putAssignedInPrinter(assign);
//							assign.setCourseMoney(getCourseMoneyAfterDiscount(assign.getCourseMoney()));
                                // UPDATE COURSE MONEY AFTER DISCOUNT.
                                double diff = assign.getCourseMoney();
                                diff -= assign.getPayedMoney();
                                double newCourseMoney = getCourseMoneyAfterDiscount(assign.getCourseMoney());
                                assign.setCourseMoney(newCourseMoney);

                                // PUT COURSE MONEY AFTER DISCOUNT TO 400.
                                System.out.println("After Disc " + assign.getCourseMoney());
                                putCourseMoneyAfterDiscountInPrinter(assign);
                                // PUT DIFFERNCE
                                putDifferentMoneyInPrinter(diff);

                                // ADD TOTAL FOR PRINTER.
                                putTotalMoneyInPrinter(payedMoney);
                                // ADD LINE.
                                addLine();
                                assigns.add(assign);
//							saveAssign(assign);
                            }
                        }
                        details = new UserDetails(-1, assign.getStudent().getName(), assign.getCourse().getName(),
                                assign.getMoney(), "SUBSCRIPTION", new Method().returnDate(), personId);
                        userDetailsDatabase.addUserDetails(details);
                    } else
                        JOptionPane.showMessageDialog(null, "Choose Student And Course FROM tables");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                checkBox.setSelected(false);
            }
        });
        done.setForeground(Color.BLUE);
        done.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
        done.setBorder(new EmptyBorder(2, 2, 2, 2));
        done.setBackground(Color.CYAN);
        done.setBounds(282, 620, 504, 50);
        contentPane.add(done);

        JLabel lblNewLabel_1 = new JLabel("Students");
        lblNewLabel_1.setForeground(Color.BLUE);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 40));
        lblNewLabel_1.setBounds(10, 65, 273, 45);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("courses");
        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1.setForeground(Color.BLUE);
        lblNewLabel_1_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 40));
        lblNewLabel_1_1.setBounds(317, 65, 273, 45);
        contentPane.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Student Course Management");
        lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_2.setForeground(Color.RED);
        lblNewLabel_1_2.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 40));
        lblNewLabel_1_2.setBounds(100, 9, 1224, 45);
        contentPane.add(lblNewLabel_1_2);

        scrollPane_3 = new JScrollPane();
        scrollPane_3.setBounds(820, 99, 485, 510);
        contentPane.add(scrollPane_3);

        printt = new JTextPane();
        scrollPane_3.setViewportView(printt);
        printt.setEditable(false);
        printt.setBackground(Color.WHITE);
        printt.setFont(new Font("Tahoma", Font.BOLD, 15));
        doc = printt.getStyledDocument();

        setTextForPrinter();

        JButton print = new JButton("Print");
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
//					putTotalMoneyInPrinter();
                    flag = true;
//					addTotalForPrinter();
                    updateAllAssignsAfterDisountAndSaveIt();
                    printt.print();
                    refresh.doClick();
                } catch (PrinterException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                } // catch (BadLocationException e1) {
                // JOptionPane.showMessageDialog(null, e1.getMessage());
                // }
            }
        });
        print.setForeground(Color.LIGHT_GRAY);
        print.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
        print.setBorder(new EmptyBorder(2, 2, 2, 2));
        print.setBackground(Color.BLACK);
        print.setBounds(995, 620, 154, 50);
        contentPane.add(print);

        JButton btnNewButton = new JButton("");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StudentView(personId).setVisible(true);
            }
        });
        btnNewButton.setIcon(new ImageIcon(AssignView.class.getResource("/View/Icons/Back.png")));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnNewButton.setBounds(16, 11, 74, 28);
        contentPane.add(btnNewButton);

        refresh = new JButton("REFRESH");
        refresh.setForeground(Color.BLUE);
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new AssignView(personId).setVisible(true);
                } catch (BadLocationException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
        refresh.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
        refresh.setBorder(null);
        refresh.setBackground(Color.CYAN);
        refresh.setBounds(1005, 65, 300, 23);
        contentPane.add(refresh);

        studentName = new JLabel("");
        studentName.setForeground(Color.RED);
        studentName.setFont(new Font("Algerian", Font.BOLD, 15));
        studentName.setBounds(20, 489, 263, 32);
        contentPane.add(studentName);

        courseName = new JLabel("");
        courseName.setForeground(Color.RED);
        courseName.setFont(new Font("Algerian", Font.BOLD, 15));
        courseName.setBounds(317, 489, 273, 32);
        contentPane.add(courseName);

        scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(600, 151, 210, 316);
        contentPane.add(scrollPane_2);

        levelTable = new JTable();
        scrollPane_2.setViewportView(levelTable);

        JLabel lblNewLabel_1_1_1 = new JLabel("Levels");
        lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1_1.setForeground(Color.BLUE);
        lblNewLabel_1_1_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 40));
        lblNewLabel_1_1_1.setBounds(600, 65, 210, 45);
        contentPane.add(lblNewLabel_1_1_1);

        JButton allButton = new JButton("All");
        allButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (levels.size() > 0) {
                    int index = courseTable.getSelectedRow();
                    if (index != -1) {
                        levelTable.setRowSelectionInterval(0, levels.size() - 1);
                        all = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Choose Course To Select All Levels");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "There Are No Levels");
                }
            }
        });
        allButton.setForeground(Color.BLUE);
        allButton.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
        allButton.setBorder(new EmptyBorder(2, 2, 2, 2));
        allButton.setBackground(Color.CYAN);
        allButton.setBounds(636, 489, 154, 50);
        contentPane.add(allButton);

        JLabel monthsL = new JLabel("months");
        monthsL.setForeground(Color.BLACK);
        monthsL.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 25));
        monthsL.setBounds(10, 638, 129, 32);
        contentPane.add(monthsL);

        monthst = new JTextField();
        monthst.setText("1");
        monthst.setFont(new Font("Tahoma", Font.BOLD, 30));
        monthst.setColumns(10);
        monthst.setBounds(171, 632, 78, 32);
        contentPane.add(monthst);

        checkBox = new JCheckBox("Months");
        checkBox.setFont(new Font("Algerian", Font.BOLD, 20));
        checkBox.setBounds(10, 599, 250, 23);
        checkBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    monthst.setVisible(true);
                    monthsL.setVisible(true);

                } else {
                    monthst.setVisible(false);
                    monthsL.setVisible(false);
                }
            }
        });

        contentPane.add(checkBox);
        JLabel lblNewLabel_3 = new JLabel("Discount%");
        lblNewLabel_3.setForeground(Color.BLUE);
        lblNewLabel_3.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 25));
        lblNewLabel_3.setBounds(301, 577, 151, 32);
        contentPane.add(lblNewLabel_3);

        discountPret = new JTextField();
        discountPret.setFont(new Font("Tahoma", Font.BOLD, 30));
        discountPret.setColumns(10);
        discountPret.setBounds(462, 577, 74, 32);
        contentPane.add(discountPret);
        monthst.setVisible(false);
        monthsL.setVisible(false);

        lblNewLabel_4 = new JLabel("discount");
        lblNewLabel_4.setForeground(Color.BLUE);
        lblNewLabel_4.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 25));
        lblNewLabel_4.setBounds(551, 577, 151, 32);
        contentPane.add(lblNewLabel_4);

        discountt = new JTextField();
        discountt.setFont(new Font("Tahoma", Font.BOLD, 30));
        discountt.setColumns(10);
        discountt.setBounds(712, 577, 74, 32);
        contentPane.add(discountt);

        lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setIcon(new ImageIcon(AssignView.class.getResource("/View/Icons/backforall2.png")));
        lblNewLabel_2.setBounds(0, 0, 1334, 681);
        contentPane.add(lblNewLabel_2);

    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AssignView frame = new AssignView(1);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setStudentTable() throws SQLException {

        int len = students.size();
        studentBody = new String[len][1];
        for (int i = 0; i < len; i++)
            studentBody[i][0] = students.get(i).getName();
        studentTable = new JTable(studentBody, studentHeader);
        studentTable.setBackground(Color.LIGHT_GRAY);
        studentTable.setFont(new Font("Tahoma", Font.BOLD, 20));
        studentTable.getTableHeader().setBackground(Color.cyan);
        studentTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
        studentTable.setRowHeight(30);
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = -1;
                index = studentTable.getSelectedRow();
                if (index != -1) {
                    studentName.setText(students.get(index).getName());
                }
            }
        });
        scrollPane.setViewportView(studentTable);
    }

    private void setCourseTable() throws SQLException {

        int len = courses.size();
        courseBody = new String[len][2];
        for (int i = 0; i < len; i++) {
            courseBody[i][0] = courses.get(i).getName();
            courseBody[i][1] = String.valueOf(courses.get(i).getPrice());
        }
        courseTable = new JTable(courseBody, courseHeader);
        courseTable.setBackground(Color.LIGHT_GRAY);
        courseTable.setFont(new Font("Tahoma", Font.BOLD, 20));
        courseTable.getTableHeader().setBackground(Color.cyan);
        courseTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
        courseTable.setRowHeight(30);
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = -1;
                index = courseTable.getSelectedRow();
                if (index != -1) {
                    courseName.setText(courses.get(index).getName());
                    try {
                        levels = levelDatabase.getAllLevelsForCourse(courses.get(index).getId());
                        setLevelTable();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    setLevelTable();
                }
            }
        });
        scrollPane_1.setViewportView(courseTable);
    }

    private void setTextForPrinter() throws BadLocationException {
//		printt.setText("\n\n\t\t\t     DELTA COLLEGE       ");
//        doc.insertString(0, "\n\t &$DELTA COLLEGE$&", large);
//        doc.insertString(doc.getLength(), "\n\t&$Development Capacity Unit$&", small);
        doc.insertString(0, "\n\t\t  &$Reciet$&", large);
        doc.insertString(doc.getLength(), "\n\t\t      &$DCU$&", small);
        doc.insertString(doc.getLength(), "\n\t      &$ " + returnDate() + " $&", large);
//		doc.insertString(doc.getLength(), "\n\t\t\t      #*****************************#", small);
    }

//	private void addStudentAndCourseForPrinter(Student student, Course course, double money)
//			throws BadLocationException {
//		if (flag) {
//			doc.insertString(doc.getLength(), "\n\n Student Name : \n", large);
//			doc.insertString(doc.getLength(), "\t" + student.getName() + "\n", small);
//			doc.insertString(doc.getLength(), "-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-", small);
//			chosenStudent = student;
//			flag = false;
//		}
//		doc.insertString(doc.getLength(), "\n\t Course Name : ", large);
//		doc.insertString(doc.getLength(), course.getName(), small);
//		doc.insertString(doc.getLength(), "\n\t Money : ", large);
//		doc.insertString(doc.getLength(), String.valueOf(money) + "\n\n", small);
//		totalMoney += money;
//		chosenCourses.add(course);
////	assigns.add(new StudentCourse(0, chosenStudent, course, money, course.getTotal()));
//	}

    private String returnDate() {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/YYYY");
        return f.format(new Date());
    }

    private void addTotalForPrinter() {
        try {
            doc.insertString(doc.getLength(), "Total Money Before Discount : " + (double) totalMoney + "\n\n", small);
            doc.insertString(doc.getLength(),
                    "Total Money After Discount : " + (double) getTotalAfterDisount() + "\n\n", small);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    private void setLevelTable() {
        int len = levels.size();
        levelBody = new String[len][2];
        for (int i = 0; i < len; i++) {
            levelBody[i][0] = levels.get(i).getName();
            levelBody[i][1] = String.valueOf(levels.get(i).getPrice());
        }
        levelTable = new JTable(levelBody, levelHeader);
        scrollPane_2.setViewportView(levelTable);
    }

    private void saveAssign(StudentCourse assign) {
        try {
            studentCourseDatabase.addStudentCourse(assign);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void putStudentForPrinter() {
        try {
            if (chosenStudent != null && !flag) {
                doc.insertString(doc.getLength(), "\n\n Student Name : \n", large);
                doc.insertString(doc.getLength(), "\t" + chosenStudent.getName() + "\n", small);
                addLine();
                flag = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void putAssignedInPrinter(StudentCourse prints) {
        try {
            doc.insertString(doc.getLength(), "\n Course Name : ", large);
            doc.insertString(doc.getLength(), prints.getCourse().getName(), small);
            if (prints.getLevel().getId() >= 1) {
                doc.insertString(doc.getLength(), "\n Level Name : ", small);
                doc.insertString(doc.getLength(), prints.getLevel().getName(), small);
            } else {

                doc.insertString(doc.getLength(), "\n Level Name : ", small);
                doc.insertString(doc.getLength(), "All Course ", small);
            }
            if (prints.getMonths() >= 1) {
                doc.insertString(doc.getLength(), "\n Months : ", small);
                doc.insertString(doc.getLength(), String.valueOf(prints.getMonths()), small);

            }
            doc.insertString(doc.getLength(), "\n Course Money : ", small);
            doc.insertString(doc.getLength(), String.valueOf(prints.getCourseMoney()), small);
            if (!discountt.getText().isEmpty()) {
                doc.insertString(doc.getLength(), "\n Discount: ", small);
                doc.insertString(doc.getLength(), discountt.getText(), small);
                discountFlag = true;
//				doc.insertString(doc.getLength(), "\n\t Total After Discount : ", large);
//				doc.insertString(doc.getLength(), "" +  + "%", small);
            } else if (!discountPret.getText().isEmpty()) {
                doc.insertString(doc.getLength(), "\n Discount% : ", small);
                doc.insertString(doc.getLength(), discountPret.getText() + "%", small);
                discountFlag = true;
            }

            totalPayedMoney += prints.getPayedMoney();
            totalMoney += prints.getCourseMoney();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void putPayedMoneyInPrinter(StudentCourse prints) {
        try {
            doc.insertString(doc.getLength(), "\nPayed Money : ", small);
            doc.insertString(doc.getLength(), "" + prints.getPayedMoney(), small);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void putTotalMoneyInPrinter(double total) {
        try {

            doc.insertString(doc.getLength(), "\n Payed Money : ", small);
            doc.insertString(doc.getLength(), "" + total, small);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    private void updateAllAssignsAfterDisountAndSaveIt() {
//		getBouns();
        try {
            for (int i = 0; i < assigns.size(); i++) {
//				assigns.get(i).setPayedMoney(assigns.get(i).getPayedMoney() + bonus);
                saveAssign(assigns.get(i));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private double getBouns() {
        bonus = getDiscountMoney() / (assigns.size() * 1.0);
        return bonus;
    }

    private double getDiscountMoney() {
        return totalMoney - getTotalAfterDisount();
    }

    private double getCourseMoneyAfterDiscount(double courseMoney) {
        if (!discountPret.getText().isEmpty()) {
            discountPre = Double.parseDouble(discountPret.getText());
            courseMoney = totalMoney - (totalMoney * (discountPre / 100));
            return courseMoney;
        } else if (!discountt.getText().isEmpty()) {
            discount = Double.parseDouble(discountt.getText());
            System.out.println("Course Money After " + courseMoney);
            courseMoney -= discount;
            System.out.println("Course Money Before " + courseMoney);
            return courseMoney;
        }
        return courseMoney;
    }

    private double getTotalAfterDisount() {
        if (!discountPret.getText().isEmpty()) {
            discountPre = Double.parseDouble(discountPret.getText());
            totalMoney = totalMoney - (totalMoney * (discountPre / 100));
            return totalMoney;
        } else if (!discountt.getText().isEmpty()) {
            discount = Double.parseDouble(discountt.getText());
            totalMoney -= discount;
            return totalMoney;
        }
        return totalMoney;

    }

    private void putCourseMoneyAfterDiscountInPrinter(StudentCourse studentCourse) {
        try {
            doc.insertString(doc.getLength(), "\n Course After Discount : ", small);
            doc.insertString(doc.getLength(), "" + studentCourse.getCourseMoney(), small);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void addLine() {
        try {
            doc.insertString(doc.getLength(), "\n-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-", small);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void putDifferentMoneyInPrinter(double diff) {
        try {
            doc.insertString(doc.getLength(), "\n Remaining Balance : ", small);
            doc.insertString(doc.getLength(), "" + diff, small);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
