import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ListSelectionModel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import com.toedter.calendar.JDateChooser;
import java.awt.Label;
import java.awt.Button;

public class main {

	public JFrame frame;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JTable makesaletbl;
	private JTextField search;
	private JTextField salessearch;
	private JTable salestbl;
	private JScrollPane scrollPane;
	private JTextField find;
	private JTextField artno;
	private JTextField prodname;
	private JTextField qty;
	private JTextField bp;
	private JTextField sp;
	private JTable stocktbl;
	private JTextField discount;
	private JScrollPane scrollPane_2;
	public JLabel loggeduser;
	private JLabel c_date;
	private JTextField adduname;
	private JPasswordField addpass;
	private JPasswordField confpass;
	private JTable userstbl;
	private JTextField userID;
	JTabbedPane tabbedPane;
	private JLabel total;
	private JLabel salestotal;
	private JTable stattbl;
	private Label statsales;
	private Label statstock;
	private JTable reporttbl;
	private JLabel profit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		main window = new main();
		window.frame.setVisible(true);
		
	}

	/**
	 * Create the application.
	 */
	public main() {
		initialize();
		fetchsalestbl();
		fetchstocktbl();
		setcurrentdate();
		viewuserstbl();
		disabletabs();}
		//sumcalstat();}
	private void disabletabs() {
		login lg=new login();
		String condi="employee";
		String tabcondition=lg.role.getSelectedItem().toString();
		while(tabcondition.matches(condi)){
				tabbedPane.setEnabledAt(4, false);
				tabbedPane.setEnabledAt(5, false);}
		
	}
	public void sumcalstat() {
		int sum=0;
		for(int i=0;i<=makesaletbl.getRowCount();i++) {
			sum=sum + Integer.parseInt(salestbl.getValueAt(i, 3).toString());
			statstock.setText("Kshs. "+Integer.toString(sum)+".00");}
	}

	private void setcurrentdate() {
		   Date date = new Date();
		   SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		   c_date.setText(sf.format(date));
		// TODO Auto-generated method stub
		
	}
	private void fetchmakesaletbl() {
		DefaultTableModel model=(DefaultTableModel)makesaletbl.getModel();
		try {
			String sql="Select * from makesaletable";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
			PreparedStatement pst=con.prepareStatement(sql);
			ResultSet rs=pst.executeQuery(sql);
			model.setRowCount(0);
			while(rs.next()) {
				model.addRow(new String[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
			}			
		}catch(Exception E) {
			E.printStackTrace();
		}
		for(int i=0;i<=makesaletbl.getRowCount();i++) {
		int qty=Integer.parseInt(model.getValueAt(i, 2).toString());
		int price=Integer.parseInt(model.getValueAt(i, 3).toString());
		int total=qty*price;
		model.setValueAt(total, i, 4);
		}
	}
	private void getsum() {
		int sum=0;
		int disc=0;
		for(int i=0;i<=makesaletbl.getRowCount();i++) {
			//disc=Integer.parseInt(discount.getText().toString());
			sum=sum + Integer.parseInt(makesaletbl.getValueAt(i, 4).toString());
			total.setText("Kshs. "+Integer.toString(sum)+".00");
			//sum=sum-((disc/100)*sum);
			//total.setText("Kshs. "+Integer.toString(sum)+".00");
			}
		}
	private void profitcalc() {
		DefaultTableModel model=(DefaultTableModel) stocktbl.getModel();
		DefaultTableModel model1=(DefaultTableModel) salestbl.getModel();
		
		for(int i=0;i<stocktbl.getRowCount();i++) {
		int bp=Integer.parseInt(stocktbl.getValueAt(i, 3).toString());
		String artno=stocktbl.getValueAt(i, 0).toString();
		
		for(int x=0;x<stocktbl.getRowCount();x++) {
		String name=stocktbl.getValueAt(i, 3).toString();
		int qty=Integer.parseInt(salestbl.getValueAt(x, 2).toString());
		int sp=Integer.parseInt(salestbl.getValueAt(x, 3).toString());
		
		while(artno==name) {
			int profit1=bp*qty;
			int profit2=sp-profit1;
			profit.setText(Integer.toString(profit2));
		}}}
	}
	private int selectmakesaletbl() {
		int row=makesaletbl.getSelectedRow();
		//DefaultTableModel model=(DefaultTableModel)makesaletbl.getModel();	
		return row;
	}

	private void fetchstocktbl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
			Statement st=con.createStatement();
			String sql="select * from stocktable";
			//String sql="SELECT `productID`, `productname`, `quantity`-(SELECT SUM(`quantity_sold`) as qty FROM salestable where `ARTNO`= ANY (SELECT `productID` from `stocktable`)) as quantity, `b_price`, `s_price` FROM `stocktable`";
			ResultSet rs=st.executeQuery(sql);
			stocktbl.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();
			st.close();			
		}catch(Exception E) {
			E.printStackTrace();
		}
	}
	public void viewuserstbl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
			Statement st=con.createStatement();
			String sql= "select * from usertable";
			//String sql="Select userID,username,VARCHAR(MAX),CAST('' AS XML).value('xs:base64Binary(sql:column(pass))', \'VARBINARY(MAX)\') as password,role from usertable";
			ResultSet rs=st.executeQuery(sql);
			ResultSetMetaData rsmd=rs.getMetaData();
			
			userstbl.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();
			st.close();
		}catch(Exception E) {
			E.printStackTrace();
		}
	}

	public void fetchsalestbl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
			Statement st=con.createStatement();
			String sql="SELECT `ARTNO`, `productname`, SUM(`quantity_sold`) as quantity, SUM(`selling_price`) as selling_price, `seller`, `date` FROM `salestable` GROUP BY `ARTNO`";
			ResultSet rs=st.executeQuery(sql);
			ResultSetMetaData rsmd=rs.getMetaData();
			
			salestbl.setModel(DbUtils.resultSetToTableModel(rs));
			con.close();
			st.close();
		}catch(Exception E) {
			E.printStackTrace();
		}
	}

private static java.sql.Date getCurrentDate() {
    java.util.Date today = new java.util.Date();
    return new java.sql.Date(today.getTime());
}
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(240, 230, 140));
		frame.setBounds(0, 0, 1445, 970);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 41, 1350, 690);
		frame.getContentPane().add(tabbedPane);
		
		panel = new JPanel();
		tabbedPane.addTab("HOME", null, panel, null);
		tabbedPane.setEnabledAt(0, true);
		panel.setLayout(null);
		panel.setBounds(10, 52, 500, 500);
		
		lblNewLabel = new JLabel("Welcome to OLAM SALES AND FINACIAL MANAGEMENT SYSTEM. Follow th guide provided and incase of any technical");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(355, 75, 725, 17);
		panel.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel(" issues contact PROS3C COMPANY in the contact us button provided.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(469, 103, 555, 26);
		panel.add(lblNewLabel_1);
		
		btnNewButton = new JButton("GUIDE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str1 = "Add new Items in the stock using the add button provided and incase of removing or editing the existing item";
				String str2="the button provided will help very much";
				String str3="To make a sale Go to the make sale tab";
				String str4="to review your sales histiry go to the sales tab";
				String str5="to check on the performance of the products and maybe a detailed information about your business go to the statistics tab";
				String str6="to generate report go to the report tab";
				JOptionPane.showMessageDialog(null,str1+"\n"+str2+"\n"+str3+"\n"+str4+"\n"+str5+"\n"+str6);
			}
		});
		btnNewButton.setBounds(641, 154, 89, 23);
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton(" CONTACT US");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cont1="PROS3C COMPANY";
				String cont2="TEL 0796892391/0776836240";
				String cont3="random plaza 3rd floor";
				String cont4="Embakasi, Nairobi";
				JOptionPane.showMessageDialog(null,cont1+"\n"+cont2+"\n"+cont3+"\n"+cont4);
			}
		});
		btnNewButton_1.setBounds(620, 203, 144, 23);
		panel.add(btnNewButton_1);
		
		panel_1 = new JPanel();
		tabbedPane.addTab("MAKE SALE", null, panel_1, null);
		panel_1.setLayout(null);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 52, 1300, 500);
		panel_1.add(scrollPane_2);
		
		makesaletbl = new JTable();
		makesaletbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		}
		});
		makesaletbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		makesaletbl.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"artno", "productname", "quantity", "price", "total price"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		makesaletbl.getColumnModel().getColumn(0).setResizable(false);
		makesaletbl.getColumnModel().getColumn(1).setResizable(false);
		scrollPane_2.setViewportView(makesaletbl);
		
		JLabel lblNewLabel_3 = new JLabel("ART NO:");
		lblNewLabel_3.setBounds(24, 24, 46, 14);
		panel_1.add(lblNewLabel_3);
		
		search = new JTextField();
		search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
					String sql="Select * from stocktable where productID=?";
					PreparedStatement pst=con.prepareStatement(sql);
					pst.setString(1, search.getText());
					ResultSet rs=pst.executeQuery();
					makesaletbl.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}
		});
		search.setBounds(77, 21, 86, 20);
		panel_1.add(search);
		search.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("+");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel)makesaletbl.getModel();
				selectmakesaletbl();
				int row=makesaletbl.getSelectedRow();
				int qty=Integer.parseInt(model.getValueAt(row, 2).toString());
				qty=qty+1;
				model.setValueAt(qty, row, 2);
				for(int i=0;i<=makesaletbl.getRowCount();i++) {
					int q=Integer.parseInt(model.getValueAt(i, 2).toString());
					int price=Integer.parseInt(model.getValueAt(i, 3).toString());
					int total=q*price;
					model.setValueAt(total, i, 4);
					}
				fetchsalestbl();
				int sum=0;
				for(int i=0;i<=makesaletbl.getRowCount();i++) {
					sum=sum + Integer.parseInt(salestbl.getValueAt(i, 3).toString());
					salestotal.setText("Kshs. "+Integer.toString(sum)+".00");
				}
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_2.setBounds(249, 20, 53, 23);
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("-");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel)makesaletbl.getModel();
				int column = 0;
				int row=makesaletbl.getSelectedRow();
				int qty=Integer.parseInt(model.getValueAt(row, 2).toString());
				if(qty>1) {
				qty=qty-1;
				model.setValueAt(qty, row, 2);
				}else {
					JOptionPane.showMessageDialog(null, "quantity cant be zero");
				}
				for(int i=0;i<=makesaletbl.getRowCount();i++) {
					int q=Integer.parseInt(model.getValueAt(i, 2).toString());
					int price=Integer.parseInt(model.getValueAt(i, 3).toString());
					int total=q*price;
					model.setValueAt(total, i, 4);
					}
				fetchsalestbl();
				int sum=0;
				for(int i=0;i<=makesaletbl.getRowCount();i++) {
					sum=sum + Integer.parseInt(salestbl.getValueAt(i, 3).toString());
					salestotal.setText("Kshs. "+Integer.toString(sum)+".00");
				}
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_3.setBounds(322, 20, 46, 23);
		panel_1.add(btnNewButton_3);
		
		JButton btnNewButton_8 = new JButton("PRINT RECEIPT");
		btnNewButton_8.setBounds(725, 397, 116, 23);
		panel_1.add(btnNewButton_8);
		
		JLabel lblNewLabel_11 = new JLabel("discount");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_11.setBounds(1031, 24, 62, 14);
		panel_1.add(lblNewLabel_11);
		
		discount = new JTextField();
		discount.setBounds(1103, 21, 62, 20);
		panel_1.add(discount);
		discount.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("TOTAL");
		lblNewLabel_12.setBounds(1085, 589, 46, 14);
		panel_1.add(lblNewLabel_12);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(648, 52, 211, 208);
		panel_1.add(textArea);
		
		JButton save = new JButton("save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
					String sql="insert into makesaletable (productID,productname,price) Select productID,productname,s_price from stocktable where productID=?";
					PreparedStatement pst=con.prepareStatement(sql);
					pst.setString(1, search.getText());
					pst.execute();
					pst.close();
					fetchmakesaletbl();
					search.setText("");
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		save.setBounds(172, 20, 67, 23);
		panel_1.add(save);
		
		JButton view = new JButton("SELL");
		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel) makesaletbl.getModel();
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
					//String sql1="insert into salestable (ARTNO,productname,quantity_sold,selling_price,seller,date) values ('"+artno+"','"+prodname+"','"+qty+"','"+price+"','"+role+"','"+getCurrentDate()+"')";
					String sql="delete from makesaletable";
					Statement st=con.createStatement();
					
					for(int i=0;i<makesaletbl.getRowCount();i++) {
						String artno=(String)model.getValueAt(i, 0).toString();
						String prodname=model.getValueAt(i, 1).toString();
						int qty=Integer.parseInt(model.getValueAt(i, 2).toString());
						int price=Integer.parseInt(model.getValueAt(i, 4).toString());
						String role=loggeduser.getText().toString();
						
						String sql1="insert into salestable (ARTNO,productname,quantity_sold,selling_price,seller,date) values ('"+artno+"','"+prodname+"','"+qty+"','"+price+"','"+role+"','"+getCurrentDate()+"')";
						st.addBatch(sql1);
						st.executeBatch();
					}
					String sql2="";
					JOptionPane.showMessageDialog(null, "products sold");
					PreparedStatement pst1=con.prepareStatement(sql);
					pst1.execute();
					fetchmakesaletbl();
				
				}catch(Exception E) {
					E.printStackTrace();
				}
			}
		});
		view.setBounds(1240, 18, 67, 23);
		panel_1.add(view);
		
		JButton caltotal = new JButton("total");
		caltotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getsum();
			}
		});
		caltotal.setBounds(770, 308, 89, 23);
		panel_1.add(caltotal);
		
		total = new JLabel("");
		total.setBounds(1144, 583, 137, 20);
		panel_1.add(total);
		
		JButton remove = new JButton("remove");
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			DefaultTableModel model=(DefaultTableModel)makesaletbl.getModel();
			int i=makesaletbl.getSelectedRow();
			model.removeRow(i);
				
			}
		});
		remove.setBounds(598, 20, 116, 23);
		panel_1.add(remove);
		
		JButton btnNewButton_12 = new JButton("qty");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel) makesaletbl.getModel();
				for(int i=0;i<=makesaletbl.getRowCount();i++) {
					int q=Integer.parseInt(model.getValueAt(i, 2).toString());
					int price=Integer.parseInt(model.getValueAt(i, 3).toString());
					int total=q*price;
					model.setValueAt(total, i, 4);
					}
				fetchsalestbl();
				int sum=0;
				for(int i=0;i<=makesaletbl.getRowCount();i++) {
					sum=sum + Integer.parseInt(salestbl.getValueAt(i, 3).toString());
					salestotal.setText("Kshs. "+Integer.toString(sum)+".00");
				}
			}
		});
		btnNewButton_12.setBounds(416, 18, 62, 23);
		panel_1.add(btnNewButton_12);
		
		JButton btnNewButton_13 = new JButton("PRINT RECEIPT");
		btnNewButton_13.setBounds(74, 585, 154, 23);
		panel_1.add(btnNewButton_13);
		
		panel_2 = new JPanel();
		tabbedPane.addTab("SALES", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("search");
		lblNewLabel_4.setBounds(1150, 11, 46, 14);
		panel_2.add(lblNewLabel_4);
		
		salessearch = new JTextField();
		salessearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try{Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
				String sql="Select * from salestable where ARTNO=?";
				PreparedStatement pst=con.prepareStatement(sql);
				pst.setString(1, salessearch.getText());
				ResultSet rs=pst.executeQuery();
				salestbl.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		});
		salessearch.setBounds(1200, 8, 86, 20);
		panel_2.add(salessearch);
		salessearch.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 1300, 500);
		panel_2.add(scrollPane);
		
		salestbl = new JTable();
		scrollPane.setViewportView(salestbl);
		
		JLabel lblNewLabel_5 = new JLabel("TOTAL");
		lblNewLabel_5.setBounds(1069, 586, 46, 14);
		panel_2.add(lblNewLabel_5);
		
		JButton refreshsales = new JButton("refresh sales");
		refreshsales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fetchsalestbl();
				int sum=0;
				for(int i=0;i<=makesaletbl.getRowCount();i++) {
					sum=sum + Integer.parseInt(salestbl.getValueAt(i, 3).toString());
					salestotal.setText("Kshs. "+Integer.toString(sum)+".00");
					statsales.setText("Kshs. "+Integer.toString(sum)+".00");
				}
				profitcalc();
			}
		});
		refreshsales.setBounds(32, 13, 131, 23);
		panel_2.add(refreshsales);
		
		salestotal = new JLabel("");
		salestotal.setBounds(1125, 586, 136, 14);
		panel_2.add(salestotal);
		
		JLabel lblNewLabel_26 = new JLabel("PROFIT");
		lblNewLabel_26.setBounds(872, 586, 46, 14);
		panel_2.add(lblNewLabel_26);
		
		profit = new JLabel("");
		profit.setBounds(928, 596, 105, 14);
		panel_2.add(profit);
		
		panel_3 = new JPanel();
		tabbedPane.addTab("STOCK", null, panel_3, null);
		panel_3.setLayout(null);
		
		find = new JTextField();
		find.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
					String sql="Select * from stocktable where productID=?";
					PreparedStatement pst=con.prepareStatement(sql);
					pst.setString(1, find.getText());
					ResultSet rs=pst.executeQuery();
					stocktbl.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		find.setBounds(1217, 57, 86, 20);
		panel_3.add(find);
		find.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("search");
		lblNewLabel_2.setBounds(1161, 60, 46, 14);
		panel_3.add(lblNewLabel_2);
		
		JLabel lblNewLabel_6 = new JLabel("ART NO");
		lblNewLabel_6.setBounds(21, 14, 46, 14);
		panel_3.add(lblNewLabel_6);
		
		artno = new JTextField();
		artno.setBounds(64, 11, 62, 20);
		panel_3.add(artno);
		artno.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("product name");
		lblNewLabel_7.setBounds(152, 14, 86, 14);
		panel_3.add(lblNewLabel_7);
		
		prodname = new JTextField();
		prodname.setBounds(257, 11, 86, 20);
		panel_3.add(prodname);
		prodname.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("quantity");
		lblNewLabel_8.setBounds(362, 14, 46, 14);
		panel_3.add(lblNewLabel_8);
		
		qty = new JTextField();
		qty.setBounds(409, 11, 69, 20);
		panel_3.add(qty);
		qty.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Buying Price");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_9.setBounds(488, 14, 81, 14);
		panel_3.add(lblNewLabel_9);
		
		bp = new JTextField();
		bp.setBounds(599, 11, 86, 20);
		panel_3.add(bp);
		bp.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Selling Price");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_10.setBounds(678, 14, 104, 14);
		panel_3.add(lblNewLabel_10);
		
		sp = new JTextField();
		sp.setBounds(792, 11, 86, 20);
		panel_3.add(sp);
		sp.setColumns(10);
		
		JButton btnNewButton_5 = new JButton("ADD");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(artno.getText().isEmpty() || prodname.getText().isEmpty() || qty.getText().isEmpty() || bp.getText().isEmpty() || sp.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "fill all fields provided");
				}else {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
						String query="insert into stocktable (productID,productname,quantity,b_price,s_price,date) values (?,?,?,?,?,?)";
						PreparedStatement pst=con.prepareStatement(query);
						pst.setString(1, artno.getText());
						pst.setString(2, prodname.getText());
						pst.setInt(3, Integer.parseInt(qty.getText()));
						pst.setInt(4, Integer.parseInt(bp.getText()));
						pst.setInt(5, Integer.parseInt(sp.getText()));
						pst.setDate(6, getCurrentDate());
						
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Item added successfully");
						fetchstocktbl();
						pst.close();
						
						int sum=0;
						for(int i=0;i<=makesaletbl.getRowCount();i++) {
							sum=sum + Integer.parseInt(salestbl.getValueAt(i, 3).toString());
							statstock.setText("Kshs. "+Integer.toString(sum)+".00");}
						
						artno.setText("");
						prodname.setText("");
						qty.setText("");
						bp.setText("");
						sp.setText("");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		btnNewButton_5.setBounds(21, 56, 89, 23);
		panel_3.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("EDIT");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel)stocktbl.getModel();
				int row=stocktbl.getSelectedRow();
				String Id=model.getValueAt(row, 0).toString();
				String id=artno.getText().toString();
				String name=prodname.getText().toString();
				int q=Integer.parseInt(qty.getText().toString());
				int bprice=Integer.parseInt(bp.getText().toString());
				int sprice=Integer.parseInt(sp.getText().toString());
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
					String Query="UPDATE `stocktable` SET `productID`='"+id+"',`productname`='"+name+"',`quantity`='"+q+"',`b_price`='"+bprice+"',`s_price`='"+sprice+"' WHERE `productID`='"+Id+"'";
					PreparedStatement pst=con.prepareStatement(Query);
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Item changed");
					fetchstocktbl();
				}catch(Exception E) {
					E.printStackTrace();
				}
			}
		});
		btnNewButton_6.setBounds(139, 56, 89, 23);
		panel_3.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("DELETE");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(artno.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "enter the number in the artno field");
				}else {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
						String PID="'"+artno.getText().toString()+"'";
						String query="delete from stocktable where productID="+PID;
						//DELETE FROM stocktable WHERE productID='ksa1234'
						int opt=JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?","confirm",JOptionPane.YES_NO_OPTION);
						if(opt==0) {
							Statement st=con.createStatement();
							st.executeUpdate(query);
							fetchstocktbl();
							JOptionPane.showMessageDialog(null, "product deleted");
					}
						
					}catch(Exception E) {
						E.printStackTrace();
					}
					
				}
			}
		});
		btnNewButton_7.setBounds(257, 56, 89, 23);
		panel_3.add(btnNewButton_7);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(21, 90, 1300, 500);
		panel_3.add(scrollPane_1);
		
		stocktbl = new JTable();
		stocktbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model=(DefaultTableModel)stocktbl.getModel();
				int row=stocktbl.getSelectedRow();
				
				String id=model.getValueAt(row, 0).toString();
				String name=model.getValueAt(row, 1).toString();
				String q=model.getValueAt(row, 2).toString();
				String bprice=model.getValueAt(row, 3).toString();
				String sprice=model.getValueAt(row, 4).toString();
				
				artno.setText(id);
				prodname.setText(name);
				qty.setText(q);
				bp.setText(bprice);
				sp.setText(sprice);
			}
		});
		scrollPane_1.setViewportView(stocktbl);
		
		panel_4 = new JPanel();
		tabbedPane.addTab("STATISTICS", null, panel_4, null);
		panel_4.setLayout(null);
		panel_4.setBounds(22,57,1300,500);
		
		JLabel lblNewLabel_13 = new JLabel("Get the plotted graph of the shoe designs and the their quantities sold to know the ones that have low sales");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_13.setBounds(10, 35, 809, 14);
		panel_4.add(lblNewLabel_13);
		
		JLabel lblNewLabel_22 = new JLabel("SALES");
		lblNewLabel_22.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_22.setBounds(10, 11, 80, 25);
		panel_4.add(lblNewLabel_22);
		
		JButton btnNewButton_10 = new JButton("CHART");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
					String query="select ARTNO, quantity_sold from salestable";
				}catch(Exception E) {
					E.printStackTrace();
				}
			}
		});
		btnNewButton_10.setBounds(10, 61, 89, 23);
		panel_4.add(btnNewButton_10);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.LIGHT_GRAY);
		panel_7.setBounds(10, 138, 1300, 500);
		panel_4.add(panel_7);
		panel_7.setLayout(null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(533, 11, 643, 459);
		panel_7.add(scrollPane_4);
		
		stattbl = new JTable();
		scrollPane_4.setViewportView(stattbl);
		
		JLabel lblNewLabel_23 = new JLabel("STOCK");
		lblNewLabel_23.setBounds(20, 11, 46, 14);
		panel_7.add(lblNewLabel_23);
		
		JLabel lblNewLabel_24 = new JLabel("Goods on stock that have a quantity greater than");
		lblNewLabel_24.setBounds(20, 34, 260, 14);
		panel_7.add(lblNewLabel_24);
		
		JComboBox value = new JComboBox();
		value.setModel(new DefaultComboBoxModel(new String[] {"50", "100", "150", "200"}));
		value.setBounds(303, 30, 94, 22);
		panel_7.add(value);
		
		JButton btnNewButton_11 = new JButton("okay");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int v=Integer.parseInt(value.getSelectedItem().toString());
					JDateChooser dateChooser = new JDateChooser();
					Date dt=dateChooser.getDate();
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
					String query="select productID, quantity,b_price from stocktable where quantity >= '"+v+"'";
					PreparedStatement pst=con.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					
					stattbl.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
				}catch(Exception E) {
					E.printStackTrace();
				}
			}
		});
		btnNewButton_11.setBounds(420, 30, 89, 23);
		panel_7.add(btnNewButton_11);
		
		JLabel lblNewLabel_25 = new JLabel("Goods added to stock before date ");
		lblNewLabel_25.setBounds(20, 72, 220, 14);
		panel_7.add(lblNewLabel_25);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy/MM/dd");
		dateChooser.setBounds(303, 66, 94, 20);
		panel_7.add(dateChooser);
		
		Label label_1 = new Label("Total cost of goods in stock");
		label_1.setBounds(20, 110, 177, 22);
		panel_7.add(label_1);
		
		statstock = new Label("");
		statstock.setBounds(218, 110, 147, 22);
		panel_7.add(statstock);
		
		JButton btnNewButton_14 = new JButton("okay");
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JDateChooser dateChooser = new JDateChooser();
					Date dt=dateChooser.getDate();
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
					String query="select productID, quantity,b_price from stocktable where date >= '"+dt+"'";
					PreparedStatement pst=con.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					
					stattbl.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
				}catch(Exception E) {
					E.printStackTrace();
				}
			}
		});
		btnNewButton_14.setBounds(420, 64, 89, 23);
		panel_7.add(btnNewButton_14);
		
		Label label = new Label("TOTAL SALES");
		label.setBounds(10, 92, 89, 22);
		panel_4.add(label);
		
		statsales = new Label("");
		statsales.setBounds(118, 92, 142, 22);
		panel_4.add(statsales);
		
		panel_5 = new JPanel();
		tabbedPane.addTab("REPORTS", null, panel_5, null);
		panel_5.setLayout(null);
		
		Label label_2 = new Label("filter sales history by choosing the date");
		label_2.setFont(new Font("Dialog", Font.BOLD, 14));
		label_2.setBounds(29, 20, 333, 22);
		panel_5.add(label_2);
		
		JDateChooser choosedate = new JDateChooser();
		choosedate.setDateFormatString("yyyy-MM-dd");
		choosedate.setBounds(29, 61, 195, 20);
		panel_5.add(choosedate);
		
		Button button = new Button("before");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{JDateChooser dateChooser = new JDateChooser();
				Date dt=dateChooser.getDate();
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
				String query="select * from salestable where date <= '"+dt+"'";
				PreparedStatement pst=con.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				
				reporttbl.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				}catch(Exception E) {
					E.printStackTrace();
				}
			}
		});
		button.setBounds(292, 61, 70, 22);
		panel_5.add(button);
		
		Button button_1 = new Button("after");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{JDateChooser dateChooser = new JDateChooser();
				Date dt=dateChooser.getDate();
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
				String query="select * from salestable where date >= '"+dt+"'";
				PreparedStatement pst=con.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				
				reporttbl.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				}catch(Exception E) {
					E.printStackTrace();
				}
			}
		});
		button_1.setBounds(411, 59, 70, 22);
		panel_5.add(button_1);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(29, 107, 1300, 500);
		panel_5.add(scrollPane_5);
		
		reporttbl = new JTable();
		scrollPane_5.setViewportView(reporttbl);
		
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("MANAGE", null, panel_6, null);
		panel_6.setLayout(null);
		
		JLabel lblNewLabel_15 = new JLabel("manage users");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_15.setBounds(30, 11, 158, 26);
		panel_6.add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("you as the admin or the owner, you can add and delete users here");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_16.setBounds(30, 48, 465, 14);
		panel_6.add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("username");
		lblNewLabel_17.setBounds(453, 76, 70, 14);
		panel_6.add(lblNewLabel_17);
		
		adduname = new JTextField();
		adduname.setBounds(564, 73, 103, 20);
		panel_6.add(adduname);
		adduname.setColumns(10);
		
		JLabel lblNewLabel_18 = new JLabel("role");
		lblNewLabel_18.setBounds(238, 76, 46, 14);
		panel_6.add(lblNewLabel_18);
		
		JComboBox addrole = new JComboBox();
		addrole.setModel(new DefaultComboBoxModel(new String[] {"owner", "admin", "employee"}));
		addrole.setBounds(322, 73, 94, 22);
		panel_6.add(addrole);
		
		JLabel lblNewLabel_19 = new JLabel("password");
		lblNewLabel_19.setBounds(702, 76, 65, 14);
		panel_6.add(lblNewLabel_19);
		
		addpass = new JPasswordField();
		addpass.setBounds(777, 73, 120, 20);
		panel_6.add(addpass);
		
		JLabel lblNewLabel_20 = new JLabel("confirm password");
		lblNewLabel_20.setBounds(942, 76, 116, 14);
		panel_6.add(lblNewLabel_20);
		
		confpass = new JPasswordField();
		confpass.setBounds(1055, 73, 130, 20);
		panel_6.add(confpass);
		
		JButton addusers = new JButton("add user");
		addusers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
					String query="insert into usertable (userID,username,password,role) values (?,?,?,?)";
					PreparedStatement pst=con.prepareStatement(query);
					
					String pass=addpass.getText();
					String conpass=confpass.getText();
					Encoder encoder=Base64.getEncoder();
					String encodedpass=encoder.encodeToString(pass.getBytes());
					String encodedconpass=encoder.encodeToString(conpass.getBytes());
					

					pst.setString(1, userID.getText());
					pst.setString(2, adduname.getText());
					pst.setString(3, encodedpass);
					pst.setString(4, (String) addrole.getSelectedItem());
					
					if(encodedpass.matches(encodedconpass)) {
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "User added successfully");
						pst.close();
						viewuserstbl();
					}else {
						JOptionPane.showMessageDialog(null, "password does not match");
						addpass.setText("");
						confpass.setText("");
					}
					userID.setText("");
					adduname.setText("");
					addpass.setText("");
					confpass.setText("");
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		addusers.setBounds(31, 162, 100, 23);
		panel_6.add(addusers);
		
		JButton editusers = new JButton("edit user");
		editusers.setBounds(195, 162, 89, 23);
		panel_6.add(editusers);
		
		JButton deleteusers = new JButton("delete user");
		deleteusers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userID.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "enter the ID in the userID field");
				}else {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/osfms","root","");
						String usrname=userID.getText().toString();
						String query="delete from usertable where userID="+usrname;
						int opt=JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?","confirm",JOptionPane.YES_NO_OPTION);
						if(opt==0) {
							Statement st=con.createStatement();
							st.executeUpdate(query);
							viewuserstbl();
							JOptionPane.showMessageDialog(null, "user deleted");
					}
						
					}catch(Exception E) {
						E.printStackTrace();
					}
					
				}
			}
		});
		deleteusers.setBounds(349, 162, 113, 23);
		panel_6.add(deleteusers);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(35, 196, 1300, 400);
		panel_6.add(scrollPane_3);
		
		userstbl = new JTable();
		scrollPane_3.setViewportView(userstbl);
		
		JLabel lblNewLabel_21 = new JLabel("userID");
		lblNewLabel_21.setBounds(40, 76, 46, 14);
		panel_6.add(lblNewLabel_21);
		
		userID = new JTextField();
		userID.setBounds(94, 73, 94, 20);
		panel_6.add(userID);
		userID.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnNewButton_4.setToolTipText("logout");
			}
		});
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opt=JOptionPane.showConfirmDialog(null,"Are you sure you want to log out?","confirm",JOptionPane.YES_NO_OPTION);
				if(opt==0) {
					login Login=new login();
					Login.frame.setVisible(true);
					frame.dispose();
				}
			}
		});
		btnNewButton_4.setIcon(new ImageIcon("C:\\files\\PROJECT\\index3.jpg"));
		btnNewButton_4.setBounds(1317, 14, 24, 23);
		frame.getContentPane().add(btnNewButton_4);
		
		loggeduser = new JLabel("");
		loggeduser.setIcon(new ImageIcon("C:\\files\\BISF\\project\\index1000.png"));
		loggeduser.setHorizontalAlignment(SwingConstants.LEFT);
		loggeduser.setBounds(1106, 20, 71, 17);
		frame.getContentPane().add(loggeduser);
		
		JLabel lblNewLabel_14 = new JLabel("OLAM SALES AND FINANCIAL MANAGEMENT SYSTEM POS");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_14.setBounds(366, 11, 598, 14);
		frame.getContentPane().add(lblNewLabel_14);
		
		c_date = new JLabel("");
		c_date.setBounds(1192, 14, 71, 20);
		frame.getContentPane().add(c_date);
		
		JButton btnNewButton_9 = new JButton("");
		btnNewButton_9.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnNewButton_9.setToolTipText("switch user");
			}
		});
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adduser ad=new adduser();
				ad.frame.setVisible(true);		
			}
		});
		btnNewButton_9.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_9.setIcon(new ImageIcon("C:\\files\\BISF\\project\\images4.jpg"));
		btnNewButton_9.setBounds(1021, 14, 57, 23);
		frame.getContentPane().add(btnNewButton_9);
	}
}
