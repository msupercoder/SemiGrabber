
import javax.swing.*;
import javax.swing.UIManager.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
/**
 * SemiGrabber.java
 * Purpose: Grab Entire Website and Save it for offline use .
 * @author Mohamed Saif Eldeen
 * @version 0.1 2/29/2016
 */

public class SemiGrabber extends JFrame {
	JLabel main_label , app_name , website_url; 
	JTextField url_text ; 
	JButton folder,grab;
	JFileChooser chooser;
	String data_folder = "";
	String wget_call = "";
	public SemiGrabber () {
		super("SemiGrabber Beta v0.1"); 
		setSize(500,250); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		this.setIconImage(new ImageIcon(getClass().getResource("grabber_icon.png")).getImage());
		main_label = new JLabel();
		main_label.setLayout(null);
		app_name = new JLabel("SemiGrabber Beta v0.1");
		app_name.setSize(250,50);
		app_name.setLocation(130,5);
		app_name.setFont(new Font("Arial",Font.BOLD , 17));
		main_label.add(app_name);
		url_text = new JTextField("Enter URL To Grab ");
		url_text.setSize(340,40);
		url_text.setLocation(80,50);
		url_text.setFont(new Font("Arial",Font.BOLD , 16));
		url_text.setForeground(Color.gray);
		main_label.add(url_text);
		url_text.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent arg0) {
                //url_text.setText("Enter URL To Grab ");
            }

            @Override
            public void focusGained(FocusEvent arg0) {
                url_text.setText("");
            }
        });
        folder = new JButton("Select Folder");
        folder.setBounds(80,100,340,30);
    	main_label.add(folder);
    	grab = new JButton("Grab");
    	grab.setBounds(220,145,70,30);
    	main_label.add(grab);
		add(main_label);
		setVisible(true);
	folder.addActionListener(new ActionListener () {
		public void actionPerformed (ActionEvent e) {
		chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(".."));
    	chooser.setDialogTitle("choosertitle");
    	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	chooser.setAcceptAllFileFilterUsed(false);
    	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      	JOptionPane.showMessageDialog(null, "Website Will Be Grabbed in : " + chooser.getSelectedFile());
      	data_folder = chooser.getSelectedFile().getName();
    	} else {
      	JOptionPane.showMessageDialog(null,"No Selection !");
    	}
		}
	});
		grab.addActionListener(new ActionListener () {
		public void actionPerformed (ActionEvent e) {
		wget_call = "wget --mirror -p --convert-links -P ./"+data_folder+" "+url_text.getText();
		String output = executeCommand(wget_call);
		System.out.println(output);
		JOptionPane.showMessageDialog(null,"Downloaded Successfully");
		}
	});
	}
	public static void main (String []args) {
		try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            new SemiGrabber();
        } catch(Exception e) {
            e.printStackTrace();
        }
} 
private String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}
}