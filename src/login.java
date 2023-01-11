import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.awt.event.ActionEvent;
import java.sql.*;

public class login {

	public JFrame frame;
	public JTextField username;
	private JPasswordField pass;
	public JComboBox role;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
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
	public login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(270, 100, 788, 513);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 230, 140));
		panel.setBounds(0, 0, 371, 474);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\files\\BISF\\project\\1.png"));
		lblNewLabel.setBounds(35, 11, 336, 319);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("LOGIN");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_1.setBounds(505, 92, 87, 22);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Role");
		lblNewLabel_2.setBounds(449, 141, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("username");
		lblNewLabel_3.setBounds(428, 187, 67, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("password");
		lblNewLabel_4.setBounds(428, 218, 67, 14);
		frame.getContentPane().add(lblNewLabel_4);
		
		role = new JComboBox();
		role.setModel(new DefaultComboBoxModel(new String[] {"owner", "employee"}));
		role.setToolTipText("");
		role.setBounds(505, 137, 119, 22);
		frame.getContentPane().add(role);
		
		username = new JTextField();
		username.setBounds(505, 184, 119, 20);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		pass = new JPasswordField();
		pass.setBounds(505, 215, 119, 20);
		frame.getContentPane().add(pass);
		
		JButton loginbtn = new JButton("LOGIN");
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String addpass=pass.getText();
				Encoder encoder=Base64.getEncoder();
				String addedpass=encoder.encodeToString(addpass.getBytes());
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
				Statement st=con.createStatement();
				String sql="Select * from usertable where username='"+username.getText()+"'and password='"+addedpass.toString()+"' and role='"+role.getSelectedItem()+"'";
				String query="Select password from usertable where password='"+addedpass.toString()+"'";
				ResultSet resultset=st.executeQuery(query);
				if(addpass.toString().matches("prorat") && resultset.next()) {
					JOptionPane.showMessageDialog(null, "change the credentials first to continue");
					changecreds chcreds=new changecreds();
					chcreds.frame.setVisible(true);
					frame.dispose();
					resultset.close();
				}else {
				ResultSet rs=st.executeQuery(sql);
				if(rs.next()){
					JOptionPane.showMessageDialog(null,"Login successfully");
					main Main=new main();
					Main.frame.setVisible(true);
					frame.dispose();
					Main.loggeduser.setText(username.getText());
					rs.close();}
				else {
					JOptionPane.showMessageDialog(null,"Incorrect credentials");
					username.setText("");
					pass.setText("");
					role.setSelectedItem("employee");
					con.close();}}				
				}catch(Exception E) {E.printStackTrace();}
			
					}
				}
		);
		loginbtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		loginbtn.setBounds(525, 274, 89, 23);
		frame.getContentPane().add(loginbtn);
		
		JButton clear = new JButton("CLEAR");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				role.setSelectedItem("employee");
				username.setText("");
				pass.setText("");
			}
		});
		clear.setBounds(398, 274, 89, 23);
		frame.getContentPane().add(clear);
		
		JButton close = new JButton("CLOSE");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		close.setBounds(655, 274, 89, 23);
		frame.getContentPane().add(close);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon("C:\\files\\BISF\\project\\index123.jpg"));
		lblNewLabel_5.setBounds(506, 32, 58, 68);
		frame.getContentPane().add(lblNewLabel_5);
		}
}
