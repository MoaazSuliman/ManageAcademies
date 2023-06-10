package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Database.CourseDatabase;
import Database.StudentCourseDatabase;
import Model.Course;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;

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

public class CourseView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField namet;
	private JTextField descriptiont;
	private JTextField pricet;
	private JScrollPane scrollPane;
	private JTextField searcht;
	private JButton search, add, update, delete;

	private CourseDatabase courseDatabase = new CourseDatabase();
	private ArrayList<Course> courses = new ArrayList<>();
	private String[] header = { "Name", "Desciption", "Price" };
	private String body[][];
	private JButton refresh;
	private JButton back;
	private JButton btnLevels;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseView frame = new CourseView(-1);
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
	public CourseView(long personId) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 10, 919, 720);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(318, 115, 516, 555);
		contentPane.add(scrollPane);
		try {
			courses = courseDatabase.getAllCourse();
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}
		setTable();

		JLabel lblNewLabel = new JLabel("Courses Management");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel.setBounds(89, 0, 816, 50);
		contentPane.add(lblNewLabel);

		add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (check()) {
					try {
						Course course = new Course(0, namet.getText(), descriptiont.getText(),
								Double.parseDouble(pricet.getText()), Double.parseDouble("0"));
						courseDatabase.addCourse(course);
						JOptionPane.showMessageDialog(null, "Course Added Success.");
						setTable();
						refresh();
						refresh.doClick();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			}
		});
		add.setForeground(Color.WHITE);
		add.setBorder(new EmptyBorder(2, 2, 2, 2));
		add.setBackground(Color.DARK_GRAY);
		add.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
		add.setBounds(8, 476, 142, 50);
		contentPane.add(add);

		update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = -1;
				index = table.getSelectedRow();
				if (index != -1) {
					Course course = courses.get(index);
					course.setName(namet.getText());
					course.setDescription(descriptiont.getText());
					course.setPrice(Double.parseDouble(pricet.getText()));
//					course.setPricePerMonth(Double.parseDouble(pricePerMontht.getText()));
					try {
						courseDatabase.updateCourse(course);
						JOptionPane.showMessageDialog(null, "Update Success.");
//						setTable();
						refresh.doClick();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}

				} else {
					JOptionPane.showMessageDialog(null, "Choose Any Course From Table ...");
				}
			}
		});
		update.setBorder(new EmptyBorder(2, 2, 2, 2));
		update.setBackground(Color.BLACK);
		update.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
		update.setBounds(160, 476, 130, 50);
		contentPane.add(update);

		if (personId == -1) {
			delete = new JButton("Delete");
			delete.setIcon(new ImageIcon(CourseView.class.getResource("/View/Icons/delete.png")));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int index = -1;
					index = table.getSelectedRow();
					if (index != -1) {
						long courseId = courses.get(index).getId();
						try {
							courseDatabase.deleteCourseById(courseId);
							new StudentCourseDatabase().deleteAllCourseAssigns(courseId);
							JOptionPane.showMessageDialog(null, "Deleted Success.");
							refresh.doClick();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}

					} else {
						JOptionPane.showMessageDialog(null, "Choose Any Course From Table ...");
					}
				}
			});
			delete.setForeground(Color.BLUE);
			delete.setBorder(new EmptyBorder(2, 2, 2, 2));
			delete.setBackground(Color.RED);
			delete.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
			delete.setBounds(22, 537, 263, 50);
			contentPane.add(delete);
		}
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1.setBounds(8, 154, 95, 23);
		contentPane.add(lblNewLabel_1);

		namet = new JTextField();
		namet.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					descriptiont.requestFocus();
			}
		});
		namet.setFont(new Font("Tahoma", Font.BOLD, 15));
		namet.setBounds(18, 192, 288, 26);
		contentPane.add(namet);
		namet.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Description");
		lblNewLabel_1_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1_1.setBounds(8, 229, 186, 23);
		contentPane.add(lblNewLabel_1_1);

		descriptiont = new JTextField();
		descriptiont.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					pricet.requestFocus();
			}
		});
		descriptiont.setFont(new Font("Tahoma", Font.BOLD, 15));
		descriptiont.setColumns(10);
		descriptiont.setBounds(18, 272, 288, 26);
		contentPane.add(descriptiont);

		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1_2.setBounds(8, 309, 298, 23);
		contentPane.add(lblNewLabel_1_2);

		pricet = new JTextField();
		pricet.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					add.doClick();
			}
		});
		pricet.setFont(new Font("Tahoma", Font.BOLD, 15));
		pricet.setColumns(10);
		pricet.setBounds(18, 343, 288, 26);
		contentPane.add(pricet);

		searcht = new JTextField();
		searcht.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					search.doClick();
			}
		});
		searcht.setFont(new Font("Tahoma", Font.BOLD, 20));
		searcht.setBounds(318, 77, 306, 27);
		contentPane.add(searcht);
		searcht.setColumns(10);

		search = new JButton("");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String condition = searcht.getText();
				try {
					courses = courseDatabase.search(condition);
					setTable();
				} catch (SQLException e1) {

					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

			}
		});
		search.setIcon(new ImageIcon(CourseView.class.getResource("/View/Icons/search.png")));
		search.setBounds(638, 81, 33, 23);
		contentPane.add(search);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(8, 47, 1334, 3);
		contentPane.add(panel);

		refresh = new JButton("REFRESH");
		refresh.setBackground(Color.ORANGE);
		refresh.setBorder(null);
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new CourseView(personId).setVisible(true);
			}
		});
		refresh.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		refresh.setBounds(677, 81, 175, 23);
		contentPane.add(refresh);

		back = new JButton("");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Base(personId).setVisible(true);
			}
		});
		back.setIcon(new ImageIcon(CourseView.class.getResource("/View/Icons/Back.png")));
		back.setFont(new Font("Tahoma", Font.BOLD, 20));
		back.setBounds(5, 0, 74, 28);
		contentPane.add(back);

		btnLevels = new JButton("Levels");
		btnLevels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if (index != -1) {
					long courseId = courses.get(index).getId();
					new LevelsView(courseId, personId).setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Choose Course To Can Manage It's Levels...");
				}

			}
		});
		btnLevels.setForeground(Color.RED);
		btnLevels.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
		btnLevels.setBorder(new EmptyBorder(2, 2, 2, 2));
		btnLevels.setBackground(Color.BLACK);
		btnLevels.setBounds(22, 390, 263, 50);
		contentPane.add(btnLevels);

		lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(CourseView.class.getResource("/View/Icons/backforall2.png")));
		lblNewLabel_2.setBounds(0, 0, 905, 681);
		contentPane.add(lblNewLabel_2);

	}

	private void setTable() {
		try {

			int len = courses.size();
			body = new String[len][3];
			for (int i = 0; i < len; i++) {
				body[i][0] = courses.get(i).getName();
				body[i][1] = courses.get(i).getDescription();
				body[i][2] = String.valueOf(courses.get(i).getPrice());
//				body[i][3] = String.valueOf(courses.get(i).getPricePerMonth());
			}

			table = new JTable(body, header);
			table.getTableHeader().setBackground(Color.cyan);
			table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 15));
			table.setRowHeight(30);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int index = -1;
					index = table.getSelectedRow();
					if (index != -1) {
						namet.setText(courses.get(index).getName());
						descriptiont.setText(courses.get(index).getDescription());
						pricet.setText(String.valueOf(courses.get(index).getPrice()));
//						pricePerMontht.setText(String.valueOf(courses.get(index).getPricePerMonth()));

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
		descriptiont.setText("");
		pricet.setText("");
	}

	private boolean check() {
		if (namet.getText().isEmpty() || namet.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Course Name Must Not Be Empty!");
			return false;
		}
//		if (descriptiont.getText().isEmpty() || descriptiont.getText().isBlank()) {
//			JOptionPane.showMessageDialog(null, "Course Name Must Not Be Empty!");
//			return false;
////		}
//		if (pricet.getText().isEmpty() || pricet.getText().isBlank()) {
//			JOptionPane.showMessageDialog(null, "Price Must Not Be Empty!");
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
