package etf.openpgp.af18273dij18203d.front;

import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import etf.openpgp.af18273dij18203d.back.ManageKeysController;

public class GenerateKeysWindow extends JFrame {

	public GenerateKeysWindow() {
		init();
		setComponents();
	}
	
	public void init() {
		setVisible(true);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("Generate keys");
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        dispose();
		    }
		});
	}
	
	public void setComponents() {
		
		JPanel panel = new JPanel(new GridLayout(6, 1));
		panel.setBorder(new EmptyBorder(10, 30, 10, 30));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		topPanel.add(Box.createHorizontalGlue());
		JButton backButton = new JButton("Back");
		backButton.addActionListener((ev) -> {
	        dispose();
		});
		topPanel.add(backButton);
		panel.add(topPanel);
		
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("Name"));
		JTextField nameField = new JTextField(16);
		namePanel.add(nameField);
		panel.add(namePanel);
		
		JPanel emailPanel = new JPanel();
		emailPanel.add(new JLabel("Email"));
		JTextField emailField = new JTextField(16);
		emailPanel.add(emailField);
		panel.add(emailPanel);

		JPanel algorithmsPanel = new JPanel();
		algorithmsPanel.add(new JLabel("Algorithm"));
		String algorithms[] = {"RSA 1024", "RSA 2048", "RSA 4096"};
		JComboBox<String> algorithmsBox = new JComboBox<>(algorithms);
		algorithmsPanel.add(algorithmsBox);
		panel.add(algorithmsPanel);

		JPanel passwordPanel = new JPanel();
		passwordPanel.add(new JLabel("Password"));
		JTextField passwordField = new JTextField(16);
		passwordPanel.add(passwordField);
		panel.add(passwordPanel);
		
		JPanel buttonPanel = new JPanel();
		JButton generateButton = new JButton("Generate");
		generateButton.addActionListener((ev) -> {
			try {
				int keySize = Integer.parseInt(algorithmsBox.getSelectedItem().toString().substring(4));
				ManageKeysController.generateKeys(nameField.getText(), emailField.getText(), passwordField.getText(), keySize);
				ManageKeysWindow.initializePublicKeysList();
				ManageKeysWindow.initializeSecretKeysList();
				dispose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		buttonPanel.add(generateButton);
		panel.add(buttonPanel);
		
		add(panel);
	}
	
}
