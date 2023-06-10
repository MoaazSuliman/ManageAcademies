package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Color;

public class Base extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Base frame = new Base(-1);
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
	public Base(long personId) {
		setTitle("Base");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 50, 750, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1_1 = new JLabel("Manage Courses");
		lblNewLabel_1_1.setForeground(Color.RED);
		lblNewLabel_1_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 40));
		lblNewLabel_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new CourseView(personId).setVisible(true);
			}
		});
		lblNewLabel_1_1.setIcon(new ImageIcon(Base.class.getResource("/View/Icons/all questions.png")));
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setBounds(10, 236, 702, 74);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1 = new JLabel("Manage Students");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 40));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new StudentView(personId).setVisible(true);
			}
		});
		lblNewLabel_1.setIcon(new ImageIcon(Base.class.getResource("/View/Icons/index student.png")));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 112, 734, 74);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Manage Balance");
		lblNewLabel_1_1_1.setForeground(Color.RED);
		lblNewLabel_1_1_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 40));
		lblNewLabel_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				new StudentMoneyView(personId).setVisible(true);
			}
		});
		lblNewLabel_1_1_1.setIcon(new ImageIcon(Base.class.getResource("/View/Icons/lastMoney.png")));
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setBounds(10, 468, 714, 92);
		contentPane.add(lblNewLabel_1_1_1);
		JLabel lblNewLabel_2 = new JLabel("DELTA COLLEGE");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 40));
		lblNewLabel_2.setBounds(0, 0, 734, 101);
		contentPane.add(lblNewLabel_2);

		if (personId == -1) {
			JLabel lblNewLabel_1_1_1_1 = new JLabel("Manage Users");
			lblNewLabel_1_1_1_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					dispose();
					new UserView(personId).setVisible(true);
				}
			});
			lblNewLabel_1_1_1_1.setIcon(new ImageIcon(Base.class.getResource("/View/Icons/index admin.png")));
			lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1_1_1_1.setForeground(Color.RED);
			lblNewLabel_1_1_1_1.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 40));
			lblNewLabel_1_1_1_1.setBounds(0, 334, 671, 92);
			contentPane.add(lblNewLabel_1_1_1_1);
		}
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Base.class.getResource("/View/Icons/studentbackground.jpg")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 734, 622);
		contentPane.add(lblNewLabel);

	}
}
