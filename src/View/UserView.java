package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Database.UserDatabase;
import Model.User;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.print.attribute.standard.JobPrioritySupported;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserView extends JFrame {

	private JPanel contentPane;
	private JTextField usernamet;
	private JTextField passwordt;
	private JTextField phonet;
	private JTextField textField_4;
	private JTextField aget;
	private JButton refresh;

	private String[] header = { "Username", "Password", "Phone", "Age" };
	private String body[][];

	private UserDatabase userDatabase = new UserDatabase();
	private JTable table;

	private ArrayList<User> users;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserView frame = new UserView(1);
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
	public UserView(long personId) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 1350, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(308, 115, 1006, 555);
		contentPane.add(scrollPane);

		table = new JTable();
		setTable();
		scrollPane.setViewportView(table);

		JLabel lblUsersmanagement = new JLabel("Users Management");
		lblUsersmanagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsersmanagement.setForeground(Color.RED);
		lblUsersmanagement.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 30));
		lblUsersmanagement.setBounds(184, 0, 1140, 50);
		contentPane.add(lblUsersmanagement);

		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (check()) {
						User user = new User(-1, usernamet.getText(), passwordt.getText(), phonet.getText(),
								aget.getText());
						userDatabase.addUser(user);
						JOptionPane.showMessageDialog(null, "ADDED SUCCESS...");
						refresh.doClick();

					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}

			}
		});
		add.setForeground(Color.LIGHT_GRAY);
		add.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
		add.setBorder(new EmptyBorder(2, 2, 2, 2));
		add.setBackground(Color.BLACK);
		add.setBounds(63, 380, 154, 50);
		contentPane.add(add);

		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int index = -1;
					index = table.getSelectedRow();
					if (index != -1) {
						User user = new User(users.get(index).getId(), usernamet.getText(), passwordt.getText(),
								phonet.getText(), aget.getText());
						userDatabase.updateUser(user);
						JOptionPane.showMessageDialog(null, "UPDATED SUCCESS..");
						refresh.doClick();
					} else
						JOptionPane.showMessageDialog(null, "Choose User From Table...");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		update.setForeground(Color.BLACK);
		update.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
		update.setBorder(new EmptyBorder(2, 2, 2, 2));
		update.setBackground(Color.CYAN);
		update.setBounds(63, 459, 154, 50);
		contentPane.add(update);

		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int index = -1;

					index = table.getSelectedRow();
					if (index != -1) {
						userDatabase.deleteUser(users.get(index).getId());
						JOptionPane.showMessageDialog(null, "DELETED SUCCESS...");
						refresh.doClick();
					} else
						JOptionPane.showMessageDialog(null, "Choose User From Table");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		delete.setForeground(Color.BLACK);
		delete.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
		delete.setBorder(new EmptyBorder(2, 2, 2, 2));
		delete.setBackground(Color.RED);
		delete.setBounds(63, 538, 154, 50);
		contentPane.add(delete);

		JLabel lblNewLabel_1 = new JLabel("UserName");
		lblNewLabel_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1.setBounds(0, 77, 186, 23);
		contentPane.add(lblNewLabel_1);

		usernamet = new JTextField();
		usernamet.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					passwordt.requestFocus();

			}
		});
		usernamet.setFont(new Font("Tahoma", Font.BOLD, 15));
		usernamet.setColumns(10);
		usernamet.setBounds(10, 115, 288, 26);
		contentPane.add(usernamet);

		JLabel lblNewLabel_1_1 = new JLabel("Password");
		lblNewLabel_1_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1_1.setBounds(0, 152, 186, 23);
		contentPane.add(lblNewLabel_1_1);

		passwordt = new JTextField();
		passwordt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					phonet.requestFocus();
			}
		});
		passwordt.setFont(new Font("Tahoma", Font.BOLD, 15));
		passwordt.setColumns(10);
		passwordt.setBounds(10, 195, 288, 26);
		contentPane.add(passwordt);

		JLabel lblNewLabel_1_2 = new JLabel("phone");
		lblNewLabel_1_2.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1_2.setBounds(0, 232, 186, 23);
		contentPane.add(lblNewLabel_1_2);

		phonet = new JTextField();
		phonet.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					aget.requestFocus();
			}
		});
		phonet.setFont(new Font("Tahoma", Font.BOLD, 15));
		phonet.setColumns(10);
		phonet.setBounds(10, 266, 288, 26);
		contentPane.add(phonet);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Tahoma", Font.BOLD, 20));
		textField_4.setColumns(10);
		textField_4.setBounds(942, 77, 306, 27);
		contentPane.add(textField_4);

		JButton search = new JButton("");
		search.setIcon(new ImageIcon(UserView.class.getResource("/View/Icons/search.png")));
		search.setBounds(1262, 81, 33, 23);
		contentPane.add(search);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(-10, 39, 1334, 3);
		contentPane.add(panel);

		refresh = new JButton("REFRESH");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new UserView(personId).setVisible(true);
			}
		});
		refresh.setForeground(Color.BLUE);
		refresh.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		refresh.setBorder(null);
		refresh.setBackground(Color.CYAN);
		refresh.setBounds(942, 47, 300, 23);
		contentPane.add(refresh);

		JLabel lblNewLabel_1_4 = new JLabel("Age");
		lblNewLabel_1_4.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel_1_4.setBounds(0, 303, 186, 23);
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
		aget.setBounds(10, 343, 288, 26);
		contentPane.add(aget);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Base(personId).setVisible(true);
			}
		});
		btnNewButton.setIcon(new ImageIcon(UserView.class.getResource("/View/Icons/Back.png")));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(0, 0, 74, 28);
		contentPane.add(btnNewButton);

		JButton details = new JButton("Details");
		details.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = -1;
				index = table.getSelectedRow();
				if (index != -1) {
					new UserDetailsView(users.get(index).getId()).setVisible(true);
				} else
					JOptionPane.showMessageDialog(null, "CHOOSE USER FROM TABLE...");
			}
		});
		details.setForeground(Color.BLUE);
		details.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 20));
		details.setBorder(new EmptyBorder(2, 2, 2, 2));
		details.setBackground(Color.CYAN);
		details.setBounds(10, 620, 288, 50);
		contentPane.add(details);

		usernamet.requestFocus();
	}

	private void setTable() {
		try {
			users = userDatabase.getAllUsers();
			int len = users.size();
			body = new String[len][4];
			for (int i = 0; i < len; i++) {
				body[i][0] = users.get(i).getUsername();
				body[i][1] = users.get(i).getPassword();
				body[i][2] = users.get(i).getPhone();
				body[i][3] = users.get(i).getAge();
			}

			table = new JTable(body, header);
			table.getTableHeader().setBackground(Color.cyan);
			table.setFont(new Font("Tahoma", Font.BOLD, 15));
			table.setRowHeight(40);
			table.setForeground(Color.LIGHT_GRAY);
			table.setBackground(Color.DARK_GRAY);
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int index = table.getSelectedRow();
					if (index != -1) {
						User user = users.get(index);
						usernamet.setText(user.getUsername());
						passwordt.setText(user.getPassword());
						aget.setText(user.getAge());
						phonet.setText(user.getPhone());
					}
				}
			});

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private boolean check() {
		if (usernamet.getText().isEmpty() || usernamet.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Enter Username.");
			return false;
		}
		if (passwordt.getText().isEmpty() || passwordt.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Enter Username.");
			return false;
		}
		return true;
	}
}
