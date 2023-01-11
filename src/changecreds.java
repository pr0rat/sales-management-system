import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class changecreds {

	public JFrame frame;
	private JTextField username;
	private JPasswordField pass;
	private JPasswordField conpass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					changecreds window = new changecreds();
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
	public changecreds() {
		initialize();
	}
	login Login=new login();

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(270, 100, 794, 541);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon("C:\\files\\BISF\\project\\index123.jpg"));
		lblNewLabel_5.setBounds(513, 66, 62, 64);
		frame.getContentPane().add(lblNewLabel_5);
		
		conpass = new JPasswordField();
		conpass.setBounds(543, 226, 152, 20);
		frame.getContentPane().add(conpass);
		
		pass = new JPasswordField();
		pass.setBounds(543, 188, 152, 20);
		frame.getContentPane().add(pass);
		
		username = new JTextField();
		username.setBounds(543, 150, 152, 20);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("CLEAR");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username.setText("");
				pass.setText("");
				conpass.setText("");
			}
		});
		btnNewButton_2.setBounds(606, 278, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("BACK");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login Login=new login();
				Login.frame.setVisible(true);
				frame.dispose();
			}
		});
		btnNewButton_1.setBounds(467, 278, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton submit = new JButton("SUBMIT");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String addpass=pass.getText();
				String addconpass=conpass.getText();
				String prorat="prorat";
				Encoder encoder=Base64.getEncoder();
				String addedpass=encoder.encodeToString(addpass.getBytes());
				String addedconpass=encoder.encodeToString(addconpass.getBytes());
				String hardpass=encoder.encodeToString(prorat.getBytes());
				try {
					if(username.getText().isEmpty() || pass.getText().isEmpty() || conpass.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "fill the fields provided");
					}else {
					if(addedpass.matches(addedconpass)) {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
					String Query="UPDATE `usertable` SET `userID`='"+0000+"',`username`='"+username.getText()+"',`password`='"+addedpass+"',`role`='owner' WHERE `userID`='"+0000+"'";
					PreparedStatement pst=con.prepareStatement(Query);
					pst.execute();
					JOptionPane.showMessageDialog(null, "credentials updated successfully");
					Login.frame.setVisible(true);
					frame.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "password did not match");
						pass.setText("");
						conpass.setText("");
					}}
				}catch(Exception E) {
					E.printStackTrace();
				}
				
			}
		});
		submit.setFont(new Font("Tahoma", Font.BOLD, 15));
		submit.setBounds(530, 339, 109, 23);
		frame.getContentPane().add(submit);
		
		JLabel lblNewLabel_4 = new JLabel("confirm password");
		lblNewLabel_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4.setBounds(377, 227, 141, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("password");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(431, 189, 87, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("username");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(431, 151, 87, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("FILL IN THE FIELDS WITH THE NEW CREDENTIALS TO CONTINUE");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(100, 22, 595, 46);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 778, 502);
		lblNewLabel.setIcon(new ImageIcon("C:\\files\\BISF\\project\\images2.jpg"));
		frame.getContentPane().add(lblNewLabel);
	}
}
