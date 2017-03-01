import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class IlluminatiDeluxe {

	private JFrame frame;
	private JTextField usernameField;
	private JTextField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IlluminatiDeluxe window = new IlluminatiDeluxe();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IlluminatiDeluxe() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1200, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel LoginPage = new JPanel();
		LoginPage.setBounds(0, 0, 1184, 681);
		frame.getContentPane().add(LoginPage);
		LoginPage.setLayout(null);
		
		JPanel SecondPage = new JPanel();
		SecondPage.setBounds(0, 0, 1184, 681);
		frame.getContentPane().add(SecondPage);
		SecondPage.setLayout(null);
		SecondPage.setVisible(false);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Lucida Fax", Font.BOLD, 32));
		lblUsername.setBounds(81, 159, 169, 48);
		LoginPage.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Lucida Fax", Font.BOLD, 32));
		lblPassword.setBounds(81, 259, 169, 48);
		LoginPage.add(lblPassword);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 28));
		usernameField.setBounds(260, 159, 244, 48);
		LoginPage.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 28));
		passwordField.setColumns(10);
		passwordField.setBounds(260, 260, 244, 48);
		LoginPage.add(passwordField);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uname = usernameField.getText();
				String pass = passwordField.getText();
				
				if(uname.equals("admin") && pass.equals("password")){
					LoginPage.setVisible(false);
					SecondPage.setVisible(true);
				}
			}
		});
		btnNewButton.setFont(new Font("Lucida Fax", Font.ITALIC, 24));
		btnNewButton.setBounds(574, 259, 203, 48);
		LoginPage.add(btnNewButton);
		
		JLabel LoginPageBackground = new JLabel("");
		LoginPageBackground.setIcon(new ImageIcon("C:\\Users\\YOLO\\Desktop\\CSULB Spring2017\\IlluminatiDeluxe\\Sample Project\\graphics\\illuminatiBackground.jpg"));
		LoginPageBackground.setBounds(-300, 0, 1484, 681);
		LoginPage.add(LoginPageBackground);
		
		JButton btnNewButton_1 = new JButton("Illuminati Confirmed");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage.setVisible(true);
				SecondPage.setVisible(false);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1.setBounds(77, 180, 251, 80);
		SecondPage.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\YOLO\\Desktop\\CSULB Spring2017\\IlluminatiDeluxe\\Sample Project\\graphics\\108_Blinker.gif"));
		lblNewLabel.setBounds(199, 23, 1221, 712);
		SecondPage.add(lblNewLabel);
		
		
	}
}
