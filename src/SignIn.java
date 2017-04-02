import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignIn extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField txtPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignIn frame = new SignIn();
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
	public SignIn() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(40, 43, 130, 26);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(70, 22, 61, 16);
		contentPane.add(lblNewLabel);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(199, 43, 130, 26);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("IP Address");
		lblNewLabel_1.setBounds(226, 22, 79, 16);
		contentPane.add(lblNewLabel_1);
		
		txtPort = new JTextField();
		txtPort.setBounds(341, 43, 87, 26);
		contentPane.add(txtPort);
		txtPort.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Port");
		lblNewLabel_2.setBounds(367, 22, 61, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblAddress = new JLabel("eg: 102.1.1.1");
		lblAddress.setBounds(219, 66, 87, 16);
		contentPane.add(lblAddress);
		
		JLabel lblPort = new JLabel("eg: 4444");
		lblPort.setBounds(351, 66, 61, 16);
		contentPane.add(lblPort);
		
		JButton btnJoin = new JButton("Join Chat");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String address = txtAddress.getText();
				int port = Integer.parseInt(txtPort.getText());
				login(name, address, port);   //TODO: handle illegal formats 
			}
		});
		btnJoin.setBounds(160, 141, 117, 29);
		contentPane.add(btnJoin);
	}
	
	private void login(String name, String address, int port)
	{
		dispose();
		new Client(name, address, port);
	}
}
