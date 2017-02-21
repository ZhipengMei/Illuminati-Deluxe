import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class IlluminatiDeluxe {

	private JFrame frame;
	private JTextField username;
	private JTextField password;

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
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Garamond", Font.PLAIN, 29));
		lblUsername.setBounds(87, 151, 115, 41);
		LoginPage.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Garamond", Font.PLAIN, 29));
		lblPassword.setBounds(87, 217, 115, 41);
		LoginPage.add(lblPassword);
		
		username = new JTextField();
		username.setFont(new Font("Tahoma", Font.PLAIN, 21));
		username.setBounds(238, 151, 256, 41);
		LoginPage.add(username);
		username.setColumns(10);
		
		password = new JTextField();
		password.setFont(new Font("Tahoma", Font.PLAIN, 21));
		password.setColumns(10);
		password.setBounds(238, 217, 256, 41);
		LoginPage.add(password);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uname = username.getText();
				String pass = password.getText();
				
				if(uname.equals("admin") && pass.equals("password")){
					LoginPage.setVisible(false);
					
				}
			}
		});
		btnLogin.setFont(new Font("Arial Black", Font.PLAIN, 24));
		btnLogin.setBounds(540, 151, 136, 93);
		LoginPage.add(btnLogin);
		
		JPanel Success = new JPanel();
		Success.setBounds(0, 0, 1184, 681);
		frame.getContentPane().add(Success);
		Success.setLayout(null);
		
		JLabel lblSuckItYolo = new JLabel("Suck it, YOLO");
		lblSuckItYolo.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblSuckItYolo.setBounds(698, 164, 170, 89);
		Success.add(lblSuckItYolo);
		
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage.setVisible(true);
				Success.setVisible(false);
			}
		});
		btnGoBack.setFont(new Font("Tahoma", Font.PLAIN, 22));
		btnGoBack.setBounds(721, 309, 170, 70);
		Success.add(btnGoBack);
	}
}
