import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class splash extends JFrame {

	private JPanel contentPane;
	private JLabel percentage;
	private JProgressBar myprogress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
        splash Mysplash = new splash();
        Mysplash.setVisible(true);
        try{
            for(int i=0;i<=100;i++){
                Thread.sleep(40);
                Mysplash.myprogress.setValue(i);
                Mysplash.percentage.setText(Integer.toString(i)+"%");
            }
        }catch(Exception e){
        }
        Mysplash.dispose();
        login Login = new login();
        Login.frame.setVisible(true);
        
	}

	/**
	 * Create the frame.
	 */
	public splash() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(420, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 210));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		myprogress = new JProgressBar();
		myprogress.setBounds(0, 247, 434, 14);
		contentPane.add(myprogress);
		
		percentage = new JLabel("%");
		percentage.setFont(new Font("Tahoma", Font.BOLD, 17));
		percentage.setBounds(180, 222, 97, 14);
		contentPane.add(percentage);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\files\\BISF\\project\\1.png"));
		lblNewLabel_1.setBounds(63, 11, 274, 200);
		contentPane.add(lblNewLabel_1);
	}
}
