package common.ui;

	import javax.swing.*;
	import java.awt.GridLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	
	public class createAccount{
public createAccount (){

	        JFrame window = new JFrame("Create Account");
	        window.setBounds(100,100,500,600);
	        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        window.setLayout(new GridLayout(3,1));
	        

	        JPanel email = new JPanel();
	        email.setLayout(new GridLayout(1,2));
	        JLabel emailLabel = new JLabel("Email:");
	        JTextField email1 = new JTextField();
	        email.add(emailLabel);
	        email.add(email1);
	        
	        JPanel password = new JPanel();
	        password.setLayout(new GridLayout(1,2));
	        JLabel pwdLabel = new JLabel("Password:");
	        JTextField pwd = new JTextField();
	        password.add(pwdLabel);
	        password.add(pwd);
	   
	        JPanel checkbox = new JPanel();
	        checkbox.setLayout(new GridLayout(1,2));
	        ButtonGroup group = new ButtonGroup();
	        JCheckBox checkBox1 = new JCheckBox("Job Owner");
	        JCheckBox checkBox2 = new JCheckBox("Vehicle Owner");
	        group.add(checkBox1);
	        group.add(checkBox2);
	        checkbox.add(checkBox1);
	        checkbox.add(checkBox2);
	        
	        window.add(email);
	        window.add(password);
	        window.add(checkbox);
	        
	        JPanel button = new JPanel();
	        window.setLayout(new GridLayout(4,3));
	        JButton btn1 = new JButton("OK");
	        JButton btn2 = new JButton("Cancel");
	        button.add(btn1);
	        button.add(btn2);
	        window.add(button);
	        
	  
	     	  btn1.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    String email = email1.getText();
	                    String password = pwd.getText();
	                    
	                    if (isValidEmail(email)) {
	           
	                        JOptionPane.showMessageDialog(window, "Account created successfully!");
	                    } else {
	                        JOptionPane.showMessageDialog(window, "Invalid email!Please try again!");
	                    }
	                }
	            });
	            
	            window.setVisible(true);
	        }
	    
	   
	   
	   private static boolean isValidEmail(String email) {
	       if (email == null || email.isEmpty())
	           return false;
	       if (!email.contains("@"))
	           return false;
	       if (!email.contains("."))
	           return false;
	       return true;
	   
	   }
	    
}
