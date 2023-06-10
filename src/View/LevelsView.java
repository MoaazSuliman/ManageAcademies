package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Database.CourseDatabase;
import Database.LevelDatabase;
import Model.Course;
import Model.Level;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LevelsView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField namet;
	private JTextField pricet;
	private JScrollPane scrollPane;
	private JButton add, update, delete;

	private LevelDatabase levelDatabase = new LevelDatabase();
	private CourseDatabase courseDatabase = new CourseDatabase();
	private ArrayList<Level> levels = new ArrayList<>();
	private long courseId;

	private String[] header = { "Name", "Price" };
	private String[][] body;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LevelsView frame = new LevelsView(0, -1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LevelsView(long courseId, long personId) {
		this.courseId = courseId;
		setUndecorated(true);
		setTitle("Levels");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 100, 844, 444);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLevelsManagement = new JLabel("Levels Management");
		lblLevelsManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevelsManagement.setForeground(Color.RED);
		lblLevelsManagement.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
		lblLevelsManagement.setBounds(10, 11, 828, 34);
		contentPane.add(lblLevelsManagement);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(255, 111, 563, 305);
		contentPane.add(scrollPane);
		table = new JTable();
		try {
			setTable();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1.setBounds(0, 111, 227, 23);
		contentPane.add(lblNewLabel_1);

		namet = new JTextField();
		namet.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					pricet.requestFocus();
			}
		});
		namet.setFont(new Font("Tahoma", Font.BOLD, 15));
		namet.setColumns(10);
		namet.setBounds(10, 149, 217, 26);
		contentPane.add(namet);

		JLabel lblNewLabel_1_1 = new JLabel("Price");
		lblNewLabel_1_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1_1.setBounds(0, 189, 227, 23);
		contentPane.add(lblNewLabel_1_1);

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
		pricet.setBounds(10, 227, 217, 26);
		contentPane.add(pricet);

		add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (check()) {
						Course course = courseDatabase.getCourseById(courseId);
						double price = Double.parseDouble(pricet.getText());
//						double pricePerMonth = Double.parseDouble(pricePerMontht.getText());
						levelDatabase.addLevel(new Level(0, namet.getText(), price, 0, course));
						JOptionPane.showMessageDialog(null, "Added New Level Success...");
						dispose();
						new LevelsView(courseId, personId).setVisible(true);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		add.setForeground(Color.WHITE);
		add.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
		add.setBorder(new EmptyBorder(2, 2, 2, 2));
		add.setBackground(Color.DARK_GRAY);
		add.setBounds(10, 358, 100, 23);
		contentPane.add(add);

		update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if (index != -1) {
					Level level = levels.get(index);
					level.setName(namet.getText());
					level.setPrice(Double.parseDouble(pricet.getText()));
					level.setPricePerMonth(0);
					try {
						levelDatabase.updateLevel(level);
						JOptionPane.showMessageDialog(null, "Updated Success...");
						dispose();
						new LevelsView(courseId, personId).setVisible(true);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Choose Row From Table To Update It.");
				}
			}
		});
		update.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
		update.setBorder(new EmptyBorder(2, 2, 2, 2));
		update.setBackground(Color.BLACK);
		update.setBounds(120, 358, 107, 23);
		contentPane.add(update);
		if (personId == -1) {
			delete = new JButton("Delete");
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int index = table.getSelectedRow();
					if (index != -1) {
						try {
							levelDatabase.deleteLevel(levels.get(index).getId());
							JOptionPane.showMessageDialog(null, "Deleted Success...");
							dispose();
							new LevelsView(courseId, personId).setVisible(true);
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					} else
						JOptionPane.showMessageDialog(null, "Choose Row From Table To Can Delete It.");
				}
			});
			delete.setForeground(Color.RED);
			delete.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
			delete.setBorder(new EmptyBorder(2, 2, 2, 2));
			delete.setBackground(Color.BLACK);
			delete.setBounds(14, 392, 196, 24);
			contentPane.add(delete);
		}
		JButton exit = new JButton("");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exit.setIcon(new ImageIcon(LevelsView.class.getResource("/View/Icons/Close.png")));
		exit.setBounds(767, 45, 51, 55);
		contentPane.add(exit);

	}

	private void setTable() throws SQLException {
		levels = levelDatabase.getAllLevelsForCourse(courseId);
		int len = levels.size();
		body = new String[len][2];
		for (int i = 0; i < len; i++) {
			body[i][0] = levels.get(i).getName();
			body[i][1] = String.valueOf(levels.get(i).getPrice());
//			body[i][2] = String.valueOf(levels.get(i).getPricePerMonth());
		}
		table = new JTable(body, header);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				if (index != -1) {
					namet.setText(levels.get(index).getName());
					pricet.setText(String.valueOf(levels.get(index).getPrice()));
//					pricePerMontht.setText(String.valueOf(levels.get(index).getPricePerMonth()));
				}

			}
		});
	}

	private boolean check() {
		if (namet.getText().isEmpty() || namet.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Enter Level Name");
			return false;
		}
		if (pricet.getText().isEmpty() || pricet.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Enter Level Price");
			return false;
		}
		return true;
	}
}
