package etf.openpgp.af18273dij18203d.front;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bouncycastle.openpgp.PGPEncryptedData;

import etf.openpgp.af18273dij18203d.back.KeyInfoWrapper;
import etf.openpgp.af18273dij18203d.back.ManageKeysController;
import etf.openpgp.af18273dij18203d.back.SendController;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;

public class SendMessageWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6565822958035798223L;
	private static WelcomeWindow welcomeWindow;
	private File file;
	private File outputDirectory;

	private final JFileChooser fileChooser = new JFileChooser();
	private JTextField password;

	private boolean encrypt;
	private boolean sign;
	private boolean radix;
	private boolean compress;
	private List<Long> publicKeys = new LinkedList<>();
	private int algorithm = PGPEncryptedData.TRIPLE_DES;

	private static JList<Object> publicKeysList = new JList<>();
	private static JComboBox<Object> secretKeysList = new JComboBox<>();

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
			welcomeWindow.setVisible(true);
			dispose();
		});

		getContentPane().setLayout(null);
		panel.setLayout(null);
		topPanel.add(backButton);

		panel.add(topPanel);

		getContentPane().add(panel);

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
		algorithm.setEnabled(false);
		algorithm.setModel(new DefaultComboBoxModel<String>(new String[] { "3DES", "AES" }));
		algorithm.setFont(new Font("Verdana", Font.PLAIN, 12));
		algorithm.setBounds(282, 141, 111, 21);
		algorithm.addActionListener(e->{
			if(algorithm.getSelectedItem()=="3DES") {
				this.algorithm = PGPEncryptedData.TRIPLE_DES;
			} else {
				this.algorithm = PGPEncryptedData.AES_128;
			}
		});
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

		password = new JTextField();
		password.setEnabled(false);
		password.setBounds(282, 200, 111, 21);
		panel.add(password);
		password.setColumns(10);

		JLabel lblNewLabel_5_1 = new JLabel("Choose private key:");
		lblNewLabel_5_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_5_1.setBounds(434, 200, 135, 21);
		panel.add(lblNewLabel_5_1);

		initializeSecretKeysList();
		secretKeysList.setEnabled(false);
		secretKeysList.setFont(new Font("Verdana", Font.PLAIN, 12));
		secretKeysList.setBounds(577, 200, 135, 21);
		
		panel.add(secretKeysList);

		JLabel lblNewLabel_6 = new JLabel("Choose file:");
		lblNewLabel_6.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel_6.setBounds(30, 362, 177, 27);
		panel.add(lblNewLabel_6);

		JButton selectFile = new JButton("Browse");
		selectFile.setFont(new Font("Verdana", Font.PLAIN, 14));
		selectFile.setBounds(260, 362, 133, 26);
		selectFile.addActionListener(e->{
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				this.file = fileChooser.getSelectedFile();
			}

		});
		panel.add(selectFile);

		JLabel lblNewLabel_6_1 = new JLabel("Choose output location:");
		lblNewLabel_6_1.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel_6_1.setBounds(30, 422, 198, 27);
		panel.add(lblNewLabel_6_1);

		JButton selectDirectory = new JButton("Browse");
		selectDirectory.setFont(new Font("Verdana", Font.PLAIN, 14));
		selectDirectory.setBounds(260, 422, 133, 26);
		selectDirectory.addActionListener((e) -> {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				this.outputDirectory = fileChooser.getSelectedFile();
			}

		});
		panel.add(selectDirectory);

		JButton encryptButton = new JButton("Encypt/Sign file");
		encryptButton.addActionListener((ev) -> {
			
			List<Object> selected = publicKeysList.getSelectedValuesList();
			
			publicKeys.clear();
			for (Object select : selected)
				publicKeys.add(((KeyInfoWrapper)select).getKeyID());
			
			if (file == null || (this.encrypt && publicKeys.isEmpty())) return;
			
			SendController.send(file, encrypt, sign, compress, radix, ((KeyInfoWrapper)secretKeysList.getSelectedItem()).getKeyID(), this.publicKeys, this.algorithm, this.password.getText());
		});
		encryptButton.setFont(new Font("Verdana", Font.BOLD, 16));
		encryptButton.setBounds(518, 391, 194, 58);
		panel.add(encryptButton);

		initializePublicKeysList();
		publicKeysList.setEnabled(false);
		publicKeysList.setFont(new Font("Verdana", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(publicKeysList);
		scrollPane.setBounds(577, 81, 135, 100);
		panel.add(scrollPane);

		JRadioButton encryptRadio = new JRadioButton("");
		encryptRadio.addActionListener((e) -> {
			this.encrypt = !this.encrypt;
			if (this.encrypt) {
				algorithm.setEnabled(true);
				publicKeysList.setEnabled(true);
			} else {
				algorithm.setEnabled(false);
				publicKeysList.setEnabled(false);
			}

		});
		encryptRadio.setBounds(111, 141, 26, 21);
		encryptRadio.setVerticalAlignment(SwingConstants.TOP);
		encryptRadio.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(encryptRadio);

		JRadioButton signRadio = new JRadioButton("");
		signRadio.addActionListener(e -> {
			this.sign = !this.sign;
			if (this.sign) {
				password.setEnabled(true);
				secretKeysList.setEnabled(true);
			} else {
				password.setEnabled(false);
				secretKeysList.setEnabled(false);
			}
		});
		signRadio.setBounds(111, 200, 26, 21);
		signRadio.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(signRadio);
		
		JCheckBox radio64 = new JCheckBox("");
		radio64.setEnabled(false);
		radio64.setBounds(111, 311, 26, 21);
		radio64.setFont(new Font("Verdana", Font.PLAIN, 12));
		radio64.addActionListener(e->{
			this.radix = !this.radix;
		});
		panel.add(radio64);

		JRadioButton compressRadio = new JRadioButton("");
		compressRadio.setBounds(111, 255, 26, 21);
		compressRadio.setFont(new Font("Verdana", Font.PLAIN, 12));
		compressRadio.addActionListener(e -> {
			this.compress = !this.compress;
			if (this.compress)
				radio64.setEnabled(true);
			else {
				radio64.setSelected(false);
				radio64.setEnabled(false);
				this.radix = false;
			}
		});
		panel.add(compressRadio);
	}
	
	public static void initializePublicKeysList() {
		publicKeysList.setListData(ManageKeysController.loadPublicKeyRingCollection().toArray());
	}
	
	public static void initializeSecretKeysList() {
		secretKeysList = new JComboBox<>(ManageKeysController.loadSecretKeyRingCollection().toArray());
	}
}
