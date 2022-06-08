package etf.openpgp.af18273dij18203d.front;

import java.awt.Color;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ReceiveMessageWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static WelcomeWindow welcomeWindow;
	private File file;
	private File sigFile;
    private File outputDirectory;

    private final JFileChooser fileChooser = new JFileChooser();
    private JTextField filename;
    private JTextField signame;

    
	public ReceiveMessageWindow() {
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
		setTitle("Receive/Verify files");
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        dispose();
		    }
		});
	}
	
	public void setComponents() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 1, 786, 562);
		panel.setBorder(new EmptyBorder(10, 30, 10, 30));
		
		JPanel topPanel = new JPanel();
		topPanel.setBounds(30, 11, 726, 41);
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
		panel.setBorder(new EmptyBorder(10, 30, 10, 30));
		getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Select file to decrypt/signature to verify:");
		lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 111, 334, 41);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Decryption/verification status:");
		lblNewLabel_1.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel_1.setBounds(466, 111, 259, 41);
		panel.add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.BLACK);
		separator.setBounds(411, 51, 9, 501);
		panel.add(separator);
		
		filename = new JTextField();
		filename.setFont(new Font("Verdana", Font.PLAIN, 12));
		filename.setBounds(199, 162, 181, 30);
		panel.add(filename);
		filename.setColumns(10);
		
		signame = new JTextField();
		signame.setFont(new Font("Verdana", Font.PLAIN, 12));
		signame.setBounds(199, 275, 181, 30);
		panel.add(signame);
		signame.setColumns(10);
		
		
		JButton chooseFileButton = new JButton("Browse");
		chooseFileButton.setFont(new Font("Verdana", Font.PLAIN, 12));
		chooseFileButton.setBounds(283, 206, 97, 30);
		chooseFileButton.addActionListener((e) -> {
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
			    this.file = fileChooser.getSelectedFile();
			    this.filename.setText(this.file.getName());
			    
			    String fileName = this.file.getName().split("\\.")[0];
			    String sigpath = String.join("\\",this.file.getParent(), fileName.concat(".sig"));
			    this.sigFile = new File(sigpath);
			    
			    if(!this.sigFile.exists()) {
			    	this.signame.setText("None");
			    	this.sigFile=null;
			    }
			    else {
			    	signame.setText(fileName.concat(".sig"));
			    }
			}
			
		});
		panel.add(chooseFileButton);
		
		JLabel lblNewLabel_2 = new JLabel("Selected encrypted file:");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(41, 165, 148, 22);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Selected signature file:");
		lblNewLabel_3.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(41, 277, 148, 22);
		panel.add(lblNewLabel_3);
		
		JButton decryptButton = new JButton("Decrypt/Verify");
		decryptButton.setFont(new Font("Verdana", Font.BOLD, 14));
		decryptButton.setBounds(114, 373, 173, 41);
		panel.add(decryptButton);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Verdana", Font.PLAIN, 12));
		textPane.setBounds(520, 339, 218, 128);
		panel.add(textPane);
		
		JLabel lblNewLabel_4 = new JLabel("Signature:");
		lblNewLabel_4.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(430, 339, 107, 30);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Decryption ");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_5.setFont(new Font("Verdana", Font.BOLD, 14));
		lblNewLabel_5.setBounds(433, 162, 148, 30);
		panel.add(lblNewLabel_5);
		
		JLabel decryptionStatus = new JLabel("");
		decryptionStatus.setFont(new Font("Verdana", Font.BOLD, 14));
		decryptionStatus.setBounds(582, 162, 121, 30);
		panel.add(decryptionStatus);
		
		JButton saveButton = new JButton("Save file");
		saveButton.setEnabled(false);
		saveButton.setFont(new Font("Verdana", Font.PLAIN, 12));
		saveButton.setBounds(520, 212, 162, 24);
		saveButton.addActionListener((e) -> {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
			    this.outputDirectory = fileChooser.getSelectedFile();
			    this.filename.setText(this.file.getName());
			    
			    String fileName = this.file.getName().split("\\.")[0];
			    String sigpath = String.join("\\",this.file.getParent(), fileName.concat(".sig"));
			    this.sigFile = new File(sigpath);
			    
			    if(!this.sigFile.exists()) {
			    	this.signame.setText("None");
			    	this.sigFile=null;
			    }
			    else {
			    	signame.setText(fileName.concat(".sig"));
			    }
			}
			
		});
		panel.add(saveButton);
	}
}
