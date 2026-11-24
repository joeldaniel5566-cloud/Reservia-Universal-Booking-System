package cia;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.border.*;

public class RegistrationPage extends JFrame{
	Color blue = new Color(11,22,40);
	Color panelColor = new Color(255,247,247);
	JPanel mainPanel,registrationPanel;
	JLabel registrationTitle,fullNameLabel,emailLabel,phoneLabel,usernameLabel,passwordLabel,confirmPasswordLabel,title,motto;
	JTextField fullNameField,emailField,phoneField,usernameField;
	JPasswordField passwordField,confirmPasswordField;
	JButton signUpButton,alreadyMember,passwordLock,confirmPasswordLock;
	JFrame register;
	
	public RegistrationPage() {
		setTitle("Reservia-Registration");
		setSize(1536,840);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		mainPanel = new JPanel(null);
		mainPanel.setBackground(blue);
		add(mainPanel);
		
		registrationPanel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(panelColor);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
				g2.dispose();
			}
		};
		registrationPanel.setOpaque(false);
		registrationPanel.setBounds(85, 40, 590, 710);
		mainPanel.add(registrationPanel);
		
		registrationTitle = new JLabel("Create Account");
		registrationTitle.setFont(new Font("Serif",Font.BOLD,30));
		registrationTitle.setForeground(Color.BLACK);
		registrationTitle.setBounds(200,25,250,45);
		registrationPanel.add(registrationTitle);
		
		fullNameLabel = new JLabel("Full Name: ");
		fullNameLabel.setFont(new Font("Arial",Font.PLAIN,16));
		fullNameLabel.setBounds(110,105,150,25);
		registrationPanel.add(fullNameLabel);
		
		fullNameField = new JTextField();
		fullNameField.setFont(new Font("Arial",Font.PLAIN,16));
		fullNameField.setBounds(110,135,370,35);
		fullNameField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY,1,true),BorderFactory.createEmptyBorder(0,10,0,10)));
		registrationPanel.add(fullNameField);
		
		emailLabel = new JLabel("Email: ");
		emailLabel.setFont(new Font("Arial",Font.PLAIN,16));
		emailLabel.setBounds(110,185,150,25);
		registrationPanel.add(emailLabel);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Arial",Font.PLAIN,16));
		emailField.setBounds(110,215,370,35);
		emailField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY,1,true),BorderFactory.createEmptyBorder(0,10,0,10)));
		registrationPanel.add(emailField);
		
		phoneLabel = new JLabel("Phone Number: ");
		phoneLabel.setFont(new Font("Arial",Font.PLAIN,16));
		phoneLabel.setBounds(110,265,150,25);
		registrationPanel.add(phoneLabel);
		
		phoneField = new JTextField();
		phoneField.setFont(new Font("Arial",Font.PLAIN,16));
		phoneField.setBounds(110,295,370,35);
		phoneField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY,1,true),BorderFactory.createEmptyBorder(0,10,0,10)));
		registrationPanel.add(phoneField);
		
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setFont(new Font("Arial",Font.PLAIN,16));
		usernameLabel.setBounds(110,345,150,25);
		registrationPanel.add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font("Arial",Font.PLAIN,16));
		usernameField.setBounds(110,375,370,35);
		usernameField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY,1,true),BorderFactory.createEmptyBorder(0,10,0,10)));
		registrationPanel.add(usernameField);
		
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(new Font("Arial",Font.PLAIN,16));
		passwordLabel.setBounds(110,425,150,25);
		registrationPanel.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial",Font.PLAIN,16));
		passwordField.setBounds(110,455,370,35);
		passwordField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY,1,true),BorderFactory.createEmptyBorder(0,10,0,10)));
		registrationPanel.add(passwordField);
		
		passwordLock = new JButton("🔓");
		passwordLock.setBounds(475,455,55,35);
		passwordLock.setOpaque(false);
		passwordLock.setContentAreaFilled(false);
		passwordLock.setBorderPainted(false);
		registrationPanel.add(passwordLock);
		passwordLock.addActionListener(e -> {
			if(passwordField.getEchoChar()==0) {
				passwordField.setEchoChar('*');
				passwordLock.setText("🔓");
			}
			else {
				passwordField.setEchoChar((char) 0);
				passwordLock.setText("🔒");
			}
		});
		
		confirmPasswordLabel = new JLabel("Confirm Password: ");
		confirmPasswordLabel.setFont(new Font("Arial",Font.PLAIN,16));
		confirmPasswordLabel.setBounds(110,505,150,25);
		registrationPanel.add(confirmPasswordLabel);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setFont(new Font("Arial",Font.PLAIN,16));
		confirmPasswordField.setBounds(110,535,370,35);
		confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY,1,true),BorderFactory.createEmptyBorder(0,10,0,10)));
		registrationPanel.add(confirmPasswordField);
		
		confirmPasswordLock = new JButton("🔓");
		confirmPasswordLock.setBounds(475,535,55,35);
		confirmPasswordLock.setOpaque(false);
		confirmPasswordLock.setContentAreaFilled(false);
		confirmPasswordLock.setBorderPainted(false);
		registrationPanel.add(confirmPasswordLock);
		confirmPasswordLock.addActionListener(e -> {
			if(confirmPasswordField.getEchoChar()==0) {
				confirmPasswordField.setEchoChar('*');
				confirmPasswordLock.setText("🔓");
			}
			else {
				confirmPasswordField.setEchoChar((char) 0);
				confirmPasswordLock.setText("🔒");
			}
		});

		signUpButton = new JButton("Sign Up"){
			@Override 
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(blue);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 45);
				super.paintComponent(g2);
				g2.dispose();
			}};
			signUpButton.setFont(new Font("Serif",Font.BOLD,18));
			signUpButton.setForeground(new Color(212,175,55));
			signUpButton.setBackground(blue);
			signUpButton.setBounds(210,600,170,45);
			signUpButton.setFocusPainted(false);
			signUpButton.setBorderPainted(false);
			signUpButton.setContentAreaFilled(false);
			registrationPanel.add(signUpButton);
			
			title = new JLabel("WELCOME TO RESERVIA");
			title.setForeground(new Color(212,175,55));
			title.setFont(new Font("Serif",Font.BOLD,34));
			title.setBounds(900,260,500,50);
			mainPanel.add(title);
			
			motto = new JLabel("The Honest Booking Platform");
			motto.setForeground(new Color(212,175,55));
			motto.setFont(new Font("Serif",Font.ITALIC,14));
			motto.setHorizontalAlignment(SwingConstants.CENTER);
			motto.setBounds(995,305,250,25);
			mainPanel.add(motto);
			
			alreadyMember = new JButton("Already a member? Login");
			alreadyMember.setForeground(new Color(212,175,55));
			alreadyMember.setFont(new Font("Serif",Font.PLAIN,15));
			alreadyMember.setBounds(975,450,300,30);
			alreadyMember.setBorderPainted(false);
			alreadyMember.setContentAreaFilled(false);
			alreadyMember.setFocusPainted(false);
			alreadyMember.setCursor(new Cursor(Cursor.HAND_CURSOR));
			mainPanel.add(alreadyMember);
			alreadyMember.addActionListener(e -> {
				new LoginPage();
				dispose();
			});
			
			signUpButton.addActionListener(e -> {
				String name = fullNameField.getText();
				String emailText = emailField.getText();
				String phoneText = phoneField.getText();
				String username = usernameField.getText();
			    String password = new String(passwordField.getPassword());
			    String confirmPassword = new String(confirmPasswordField.getPassword());

			    if (name.isEmpty()|| emailText.isEmpty()|| phoneText.isEmpty()|| 
			    		 username.isEmpty()||password.isEmpty()|| confirmPassword.isEmpty()) {
			    	JOptionPane.showMessageDialog(register, "Please enter all the fields ");
			    }
			    else if (!phoneText.matches("\\d{10}")) {
					JOptionPane.showMessageDialog(register, "Please enter a valid 10-digit phone number");
					phoneField.setText("");
				} else if (!emailText.contains("@")) {
				    JOptionPane.showMessageDialog(register, "Please enter a valid email");
				    emailField.setText("");
				} else if (!password.equals(confirmPassword)) {

			        JOptionPane.showMessageDialog(
			            register,
			            "Passwords do not match!"
			        );
			        passwordField.setText("");//-
			        confirmPasswordField.setText("");//-

			    } else {
			        String sql = "INSERT INTO users "
			                   + "(fullname, email, phone, username, epassword) "
			                   + "VALUES (?, ?, ?, ?, ?)";

			        try {

			            Connection cn = DBConnection.getConnection();

			            PreparedStatement pst = cn.prepareStatement(sql);

			            pst.setString(1, name);
			            pst.setString(2, emailText);
			            pst.setString(3, phoneText);
			            pst.setString(4, username);
			            pst.setString(5, password);

			            pst.executeUpdate();

			            JOptionPane.showMessageDialog(
			                register,
			                "Account created successfully!"
			            );

			            pst.close();
			            cn.close();
			            
			            fullNameField.setText("");
			            phoneField.setText("");
			            emailField.setText("");
			            usernameField.setText("");
			            passwordField.setText("");
			            confirmPasswordField.setText("");

			        } catch (SQLException ex) {

			            JOptionPane.showMessageDialog(
			                register,
			                "Database Error: " + ex.getMessage()
			            );
			        }
			    }
			});
			
		setVisible(true);
	}

public static void main(String[] args) {
	new RegistrationPage();
}

}