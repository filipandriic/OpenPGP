package etf.openpgp.af18273dij18203d.front;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class WelcomeWindow extends JFrame {
	
	private static WelcomeWindow instance;
	
	private WelcomeWindow() {
		init();
		addComponents();
	}
	
	public static WelcomeWindow getInstance() {
		if (instance == null)
			instance = new WelcomeWindow();
		return instance;
	}
	
	public void init() {
		setVisible(true);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("pgp");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 1));
	}
	
	public void addComponents() {
		JPanel panel = new JPanel(new GridLayout(1, 1));
		
		JLabel title = new JLabel("PGP Protocol", JLabel.CENTER);
		title.setFont(new Font("Monospaced", Font.BOLD, 30));
		panel.add(title);
		add(panel);
		
		panel = new JPanel(new GridLayout(1, 3, 10, 0));
		panel.setSize(100, 300);
		panel.setBorder(new EmptyBorder(10, 30, 10, 30));
		
		JButton manageKeysButton = new JButton("Manage keys");
		manageKeysButton.addActionListener((ev) -> {
			setVisible(false);
			new ManageKeysWindow();
		});
		panel.add(manageKeysButton);

		JButton sendMessageButton = new JButton("Send a message");
		sendMessageButton.addActionListener((ev) -> {
			setVisible(false);
			new SendMessageWindow();
		});
		panel.add(sendMessageButton);

		JButton receiveMessageButton = new JButton("Receive a message");
		receiveMessageButton.addActionListener((ev) -> {
			setVisible(false);
			new ReceiveMessageWindow();
		});
		panel.add(receiveMessageButton);
		
		add(panel);
	}
	
}
