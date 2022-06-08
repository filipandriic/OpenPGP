package etf.openpgp.af18273dij18203d.front;

import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JPasswordField;

public class SendMessageWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6565822958035798223L;
	private static WelcomeWindow welcomeWindow;
	private File file;
	private File outputDirectory;

	private final JFileChooser fileChooser = new JFileChooser();
	private JPasswordField password;
	private JComboBox<Long> privateKey;

	public SendMessageWindow() {
		welcomeWindow = WelcomeWindow.getInstance();
		init();
		setComponents();
	}

	@Override
	public void dispose() {
		super.dispose();
		welcomeWindow.setVisible(true);
	}

	public void init() {
		setVisible(true);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("Send/Sign files");

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				dispose();
			}
		});
	}

	public void setComponents() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 10, 786, 553);
		panel.setBorder(new EmptyBorder(10, 30, 10, 30));

		JPanel topPanel = new JPanel();
		topPanel.setBounds(30, 12, 726, 27);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		topPanel.add(Box.createHorizontalGlue());
		JButton backButton = new JButton("Back");
		backButton.addActionListener((ev) -> {
			dispose();
		});
		getContentPane().setLayout(null);
		panel.setLayout(null);
		topPanel.add(backButton);

		panel.add(topPanel);

		getContentPane().add(panel);

		JRadioButton encryptRadio = new JRadioButton("");
		encryptRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		encryptRadio.setBounds(111, 141, 26, 21);
		encryptRadio.setVerticalAlignment(SwingConstants.TOP);
		encryptRadio.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(encryptRadio);

		JRadioButton signRadio = new JRadioButton("");
		signRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		signRadio.setBounds(111, 200, 26, 21);
		signRadio.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(signRadio);

		JRadioButton compressRadio = new JRadioButton("");
		compressRadio.setBounds(111, 255, 26, 21);
		compressRadio.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(compressRadio);

		JCheckBox radio64 = new JCheckBox("");
		radio64.setBounds(111, 311, 26, 21);
		radio64.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(radio64);

		JLabel lblNewLabel = new JLabel("Encrypt:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setBounds(30, 141, 75, 21);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Sign:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(30, 200, 75, 21);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Compress:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(30, 255, 75, 21);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Radix-64:");
		lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setBounds(30, 311, 75, 21);
		panel.add(lblNewLabel_3);
		
		JComboBox<String> algorithm = new JComboBox<String>();
		algorithm.setModel(new DefaultComboBoxModel<String>(new String[] {"3DES", "AES"}));
		algorithm.setFont(new Font("Verdana", Font.PLAIN, 12));
		algorithm.setBounds(282, 141, 111, 21);
		panel.add(algorithm);
		
		JLabel lblNewLabel_4 = new JLabel("Select algorithm:");
		lblNewLabel_4.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(160, 141, 119, 21);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Choose public key:");
		lblNewLabel_5.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(434, 141, 133, 21);
		panel.add(lblNewLabel_5);
		
		JLabel passphrase = new JLabel("Passphrase:");
		passphrase.setFont(new Font("Verdana", Font.PLAIN, 12));
		passphrase.setBounds(160, 200, 111, 21);
		panel.add(passphrase);
		
		password = new JPasswordField();
		password.setBounds(282, 200, 111, 21);
		panel.add(password);
		password.setColumns(10);
		
		JLabel lblNewLabel_5_1 = new JLabel("Choose private key:");
		lblNewLabel_5_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_5_1.setBounds(434, 200, 135, 21);
		panel.add(lblNewLabel_5_1);
		
		privateKey = new JComboBox<Long>();
		privateKey.setFont(new Font("Verdana", Font.PLAIN, 12));
		privateKey.setBounds(577, 200, 135, 21);
		panel.add(privateKey);
		
		JLabel lblNewLabel_6 = new JLabel("Choose file:");
		lblNewLabel_6.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel_6.setBounds(30, 362, 177, 27);
		panel.add(lblNewLabel_6);
		
		JButton selectFile = new JButton("Browse");
		selectFile.setFont(new Font("Verdana", Font.PLAIN, 14));
		selectFile.setBounds(260, 362, 133, 26);
		panel.add(selectFile);
		
		JLabel lblNewLabel_6_1 = new JLabel("Choose output location:");
		lblNewLabel_6_1.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel_6_1.setBounds(30, 422, 198, 27);
		panel.add(lblNewLabel_6_1);
		
		JButton selectDirectory = new JButton("Browse");
		selectDirectory.setFont(new Font("Verdana", Font.PLAIN, 14));
		selectDirectory.setBounds(260, 422, 133, 26);
		panel.add(selectDirectory);
		
		JButton encryptButton = new JButton("Encypt/Sign file");
		encryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		encryptButton.setFont(new Font("Verdana", Font.BOLD, 16));
		encryptButton.setBounds(518, 391, 194, 58);
		panel.add(encryptButton);
		
		JList<Long> publicKeys = new JList<Long>();
		publicKeys.setFont(new Font("Verdana", Font.PLAIN, 12));
		publicKeys.setBounds(577, 141, 135, 21);
		panel.add(publicKeys);
	}
}
