package etf.openpgp.af18273dij18203d.front;

import java.awt.GridLayout;
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

public class SendMessageWindow extends JFrame {
	private static WelcomeWindow welcomeWindow;
	private File file;
    private File outputDirectory;

    private final JFileChooser fileChooser = new JFileChooser();
    
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
		encryptRadio.setBounds(111, 141, 103, 21);
		encryptRadio.setVerticalAlignment(SwingConstants.TOP);
		encryptRadio.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(encryptRadio);
		
		JRadioButton signRadio = new JRadioButton("");
		signRadio.setBounds(111, 200, 103, 21);
		signRadio.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(signRadio);
		
		JRadioButton compressRadio = new JRadioButton("");
		compressRadio.setBounds(111, 255, 103, 21);
		compressRadio.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(compressRadio);
		
		JCheckBox radio64 = new JCheckBox("");
		radio64.setBounds(111, 311, 93, 21);
		radio64.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(radio64);
		
		JLabel lblNewLabel = new JLabel("Encrypt:");
		lblNewLabel.setBounds(42, 141, 63, 13);
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		panel.add(lblNewLabel);
	}
}
