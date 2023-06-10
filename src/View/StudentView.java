package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

import Database.CourseDatabase;
import Database.StudentCourseDatabase;
import Database.StudentDatabase;
import Model.Course;
import Model.Student;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class StudentView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField namet;
	private JTextField phonet;
	private JTextField emailt;
	private JTextField addresst;
	private JScrollPane scrollPane;
	private JTextField searcht;
	private JButton search, add, update, delete;

	private StudentDatabase studentDatabase = new StudentDatabase();
	private ArrayList<Student> students = new ArrayList<>();
	private String[] header = { "Name", "Phone", "Email", "Address", "Age" };
	private String body[][];

	private JButton refresh;
	private JLabel lblNewLabel_1_4;
	private JTextField aget;
	private JButton btnNewButton;
	private JButton btnAssigncourses;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentView frame = new StudentView(0);
					frame.setVisible(true);
					frame.namet.requestFocus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StudentView(long personId) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1350, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(318, 115, 1006, 555);
		contentPane.add(scrollPane);
		try {
			students = studentDatabase.getAllStudents();
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}
		setTable();

		JLabel lblNewLabel = new JLabel("Students Management");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel.setBounds(194, 0, 1140, 50);
		contentPane.add(lblNewLabel);

		add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (check()) {
					try {
						Student student = new Student(0, namet.getText(), phonet.getText(), emailt.getText(),
								addresst.getText(), aget.getText());
						studentDatabase.addStudent(student);
						JOptionPane.showMessageDialog(null, "Student Added Success.");
						setTable();
						refresh();
						refresh.doClick();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			}
		});
		add.setForeground(Color.LIGHT_GRAY);
		add.setBorder(new EmptyBorder(2, 2, 2, 2));
		add.setBackground(Color.BLACK);
		add.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
		add.setBounds(74, 462, 154, 50);
		contentPane.add(add);

		update = new JButton("Update");
		update.setForeground(Color.BLACK);
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = -1;
				index = table.getSelectedRow();
				if (index != -1) {
					Student student = students.get(index);
					student.setName(namet.getText());
					student.setAge(aget.getText());
					student.setEmail(emailt.getText());
					student.setAddress(addresst.getText());
					student.setPhone(phonet.getText());
					try {
						studentDatabase.updateStudent(student);

						JOptionPane.showMessageDialog(null, "Update Success.");
						setTable();
						refresh();
						refresh.doClick();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}

				} else {
					JOptionPane.showMessageDialog(null, "Choose Any Student From Table ...");
				}
			}
		});
		update.setBorder(new EmptyBorder(2, 2, 2, 2));
		update.setBackground(Color.CYAN);
		update.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
		update.setBounds(74, 541, 154, 50);
		contentPane.add(update);
		if (personId == -1) {
			delete = new JButton("Delete");
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int index = -1;
					index = table.getSelectedRow();
					if (index != -1) {
						long studentId = students.get(index).getId();
						try {
							studentDatabase.deleteStudent(studentId);
							new StudentCourseDatabase().deleteAllStudentAssigns(studentId);
							JOptionPane.showMessageDialog(null, "Deleted Success.");
							refresh.doClick();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}

					} else {
						JOptionPane.showMessageDialog(null, "Choose Any Student From Table ...");
					}
				}

			});
			delete.setForeground(Color.BLACK);
			delete.setBorder(new EmptyBorder(2, 2, 2, 2));
			delete.setBackground(Color.RED);
			delete.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
			delete.setBounds(74, 620, 154, 50);
			contentPane.add(delete);
		}
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1.setBounds(10, 77, 186, 23);
		contentPane.add(lblNewLabel_1);

		namet = new JTextField();
		namet.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					phonet.requestFocus();
			}
		});
		namet.setFont(new Font("Tahoma", Font.BOLD, 15));
		namet.setBounds(20, 115, 288, 26);
		contentPane.add(namet);
		namet.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Phone");
		lblNewLabel_1_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1_1.setBounds(10, 152, 186, 23);
		contentPane.add(lblNewLabel_1_1);

		phonet = new JTextField();
		phonet.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					emailt.requestFocus();
			}
		});
		phonet.setFont(new Font("Tahoma", Font.BOLD, 15));
		phonet.setColumns(10);
		phonet.setBounds(20, 195, 288, 26);
		contentPane.add(phonet);

		JLabel lblNewLabel_1_2 = new JLabel("Email");
		lblNewLabel_1_2.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1_2.setBounds(10, 232, 186, 23);
		contentPane.add(lblNewLabel_1_2);

		emailt = new JTextField();
		emailt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					addresst.requestFocus();
			}
		});
		emailt.setFont(new Font("Tahoma", Font.BOLD, 15));
		emailt.setColumns(10);
		emailt.setBounds(20, 266, 288, 26);
		contentPane.add(emailt);

		JLabel lblNewLabel_1_3 = new JLabel("Address");
		lblNewLabel_1_3.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1_3.setBounds(10, 303, 186, 23);
		contentPane.add(lblNewLabel_1_3);

		addresst = new JTextField();
		addresst.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					aget.requestFocus();
			}
		});
		addresst.setFont(new Font("Tahoma", Font.BOLD, 15));
		addresst.setColumns(10);
		addresst.setBounds(20, 343, 288, 26);
		contentPane.add(addresst);

		searcht = new JTextField();
		searcht.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					search.doClick();
			}
		});
		searcht.setFont(new Font("Tahoma", Font.BOLD, 20));
		searcht.setBounds(952, 77, 306, 27);
		contentPane.add(searcht);
		searcht.setColumns(10);

		search = new JButton("");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String condition = searcht.getText();
				try {
					students = studentDatabase.search(condition);
					setTable();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}

			}
		});
		search.setIcon(new ImageIcon(CourseView.class.getResource("/View/Icons/search.png")));
		search.setBounds(1272, 81, 33, 23);
		contentPane.add(search);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 39, 1334, 3);
		contentPane.add(panel);

		refresh = new JButton("REFRESH");
		refresh.setBackground(Color.CYAN);
		refresh.setForeground(Color.BLUE);
		refresh.setBorder(null);
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new StudentView(personId).setVisible(true);
			}
		});
		refresh.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		refresh.setBounds(952, 47, 300, 23);
		contentPane.add(refresh);

		lblNewLabel_1_4 = new JLabel("Age");
		lblNewLabel_1_4.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1_4.setBounds(10, 385, 186, 23);
		contentPane.add(lblNewLabel_1_4);

		aget = new JTextField();
		aget.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					add.doClick();
			}
		});
		aget.setFont(new Font("Tahoma", Font.BOLD, 15));
		aget.setColumns(10);
		aget.setBounds(20, 425, 288, 26);
		contentPane.add(aget);

		btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Base(personId).setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setIcon(new ImageIcon(StudentView.class.getResource("/View/Icons/Back.png")));
		btnNewButton.setBounds(10, 0, 74, 28);
		contentPane.add(btnNewButton);

		btnAssigncourses = new JButton("Assign Courses");
		btnAssigncourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				try {
					new AssignView(personId).setVisible(true);
				} catch (BadLocationException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnAssigncourses.setForeground(Color.BLUE);
		btnAssigncourses.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 25));
		btnAssigncourses.setBorder(new EmptyBorder(2, 2, 2, 2));
		btnAssigncourses.setBackground(Color.CYAN);
		btnAssigncourses.setBounds(343, 54, 581, 50);
		contentPane.add(btnAssigncourses);

		lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(StudentView.class.getResource("/View/Icons/backforall2.png")));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(0, 0, 1334, 681);
		contentPane.add(lblNewLabel_2);

	}

	private void setTable() {
		try {

			int len = students.size();
			body = new String[len][5];
			for (int i = 0; i < len; i++) {
				body[i][0] = students.get(i).getName();
				body[i][1] = students.get(i).getPhone();
				body[i][2] = students.get(i).getEmail();
				body[i][3] = students.get(i).getAddress();
				body[i][4] = students.get(i).getAge();
			}

			table = new JTable(body, header);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int index = -1;
					index = table.getSelectedRow();
					if (index != -1) {
						namet.setText(students.get(index).getName());
						phonet.setText(students.get(index).getPhone());
						emailt.setText(students.get(index).getEmail());
						addresst.setText(students.get(index).getAddress());
						aget.setText(students.get(index).getAge());

					}
				}
			});
			table.setFont(new Font("Tahoma", Font.BOLD, 15));
			table.setForeground(Color.LIGHT_GRAY);
			table.setBackground(Color.DARK_GRAY);
			table.setBorder(null);
			setTableViews();
			scrollPane.setViewportView(table);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

	private void refresh() {
		namet.setText("");
		phonet.setText("");
		emailt.setText("");
		addresst.setText("");
		namet.requestFocus();
	}

	private boolean check() {
		if (namet.getText().isEmpty() || namet.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Student Name Must Not Be Empty!");
			return false;
		}
//		if (descriptiont.getText().isEmpty() || descriptiont.getText().isBlank()) {
//			JOptionPane.showMessageDialog(null, "Course Name Must Not Be Empty!");
//			return false;
//		}
//		if (emailt.getText().isEmpty() || emailt.getText().isBlank()) {
//			JOptionPane.showMessageDialog(null, "Price Must Not Be Empty!");
//			return false;
//		}
//		if (addresst.getText().isEmpty() || addresst.getText().isBlank()) {
//			JOptionPane.showMessageDialog(null, "Discount For Course Must Not Be Empty!");
//			return false;
//		}
		return true;
	}

	private void setTableViews() {
		table.getTableHeader().setBackground(Color.cyan);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
		table.setRowHeight(30);
	}
}
