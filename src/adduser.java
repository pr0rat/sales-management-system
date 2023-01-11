import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.awt.event.ActionEvent;

public class adduser {

	public JFrame frame;
	private JTextField name;
	private JPasswordField passwd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adduser window = new adduser();
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
	public adduser() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 230, 140));
		frame.setBounds(270, 100, 618, 347);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADDING USERS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(210, 41, 231, 19);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("role");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(126, 91, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("username");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(126, 126, 97, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("password");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(126, 164, 97, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		name = new JTextField();
		name.setBounds(258, 125, 171, 20);
		frame.getContentPane().add(name);
		name.setColumns(10);
		
		JComboBox role = new JComboBox();
		role.setModel(new DefaultComboBoxModel(new String[] {"owner", "employee"}));
		role.setBounds(258, 89, 103, 22);
		frame.getContentPane().add(role);
		
		passwd = new JPasswordField();
		passwd.setBounds(258, 163, 171, 20);
		frame.getContentPane().add(passwd);
		
		JButton okay = new JButton("SUBMIT");
		okay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String addpass=passwd.getText();
				Encoder encoder=Base64.getEncoder();
				String addedpass=encoder.encodeToString(addpass.getBytes());
				try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
				Statement st=con.createStatement();
				String sql="Select * from usertable where username='"+name.getText()+"'and password='"+addedpass.toString()+"' and role='"+role.getSelectedItem()+"'";
				ResultSet rs=st.executeQuery(sql);
				
				if(rs.next()){
					JOptionPane.showMessageDialog(null,"switched successfully to '"+name.getText()+"'");
					main Main=new main();
					Main.frame.setVisible(true);
					frame.dispose();
					Main.loggeduser.setText(name.getText());
					rs.close();}
				}catch(Exception E) {
					E.printStackTrace();
				}
			}
			
		});
		okay.setBounds(258, 212, 89, 23);
		frame.getContentPane().add(okay);
	}
}
