package cia;
import javax.swing.*;
import javax.swing.border.*;


import java.awt.*;
import java.sql.*;

public class LoginPage extends JFrame {
		Color blue = new Color(11,22,40);
		Color panelColor = new Color(255,247,247);
		JPanel mainPanel,loginPanel;
		JLabel title,motto,loginTitle,emailLabel,passwordLabel,forgotPassword;
		JButton createAccount,loginButton,passwordLock;
		JTextField emailField;
		JPasswordField passwordField;
		JFrame login;
		
		public LoginPage() {
			setTitle("Reservia");
			setSize(1536,840);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);

			mainPanel = new JPanel(null);
			mainPanel.setBackground(blue);
			add(mainPanel);
			
			title = new JLabel("WELCOME TO RESERVIA");
			title.setForeground(new Color(212,175,55));
			title.setFont(new Font("Serif",Font.BOLD,34));
			title.setBounds(180,260,500,50);
			mainPanel.add(title);
			
			motto = new JLabel("The Honest Booking Platform");
			motto.setForeground(new Color(212,175,55));
			motto.setFont(new Font("Serif",Font.ITALIC,14));
			motto.setBounds(300,305,250,25);
			mainPanel.add(motto);
			
			createAccount = new JButton("New to Reservia? Create an Account");
			createAccount.setForeground(new Color(212,175,55));
			createAccount.setFont(new Font("Serif",Font.PLAIN,15));
			createAccount.setBounds(230,450,300,30);
			createAccount.setBorderPainted(false);
			createAccount.setContentAreaFilled(false);
			createAccount.setFocusPainted(false);
			createAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));
			mainPanel.add(createAccount);
			createAccount.addActionListener(e -> {
				new RegistrationPage();
				dispose();
			});
			
			loginPanel = new JPanel(null) {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setColor(panelColor);
					g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
				}
			};
			loginPanel.setOpaque(false);
			loginPanel.setBounds(850, 80, 590, 610);
			mainPanel.add(loginPanel);
			
			loginTitle = new JLabel("Login");
			loginTitle.setFont(new Font("Serif",Font.BOLD,32));
			loginTitle.setBounds(255,75,150,45);
			loginPanel.add(loginTitle);    
			
			emailLabel = new JLabel("Email: ");
			emailLabel.setFont(new Font("Arial",Font.PLAIN,16));
			emailLabel.setBounds(105,165,100,25);
			loginPanel.add(emailLabel);
			
			emailField = new JTextField();
			emailField.setFont(new Font("Arial",Font.PLAIN,16));
			emailField.setBounds(105,195,400,35);
			emailField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY,1,true),BorderFactory.createEmptyBorder(0,10,0,10)));
			loginPanel.add(emailField);
			
			passwordLabel = new JLabel("Password: ");
			passwordLabel.setFont(new Font("Arial",Font.PLAIN,16));
			passwordLabel.setBounds(105,285,100,25);
			loginPanel.add(passwordLabel);
			
			passwordField = new JPasswordField();
			passwordField.setFont(new Font("Arial",Font.PLAIN,16));
			passwordField.setBounds(105,315,400,35);
			passwordField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.GRAY,1,true),BorderFactory.createEmptyBorder(0,10,0,10)));
			loginPanel.add(passwordField);
			
			passwordLock = new JButton("🔓");
			passwordLock.setBounds(495,315,55,35);
			passwordLock.setOpaque(false);
			passwordLock.setContentAreaFilled(false);
			passwordLock.setBorderPainted(false);
			loginPanel.add(passwordLock);
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
			
			forgotPassword = new JLabel("Forgot Password?");
			forgotPassword.setFont(new Font("Serif",Font.PLAIN,14));
			forgotPassword.setBounds(105,345,150,25);
			loginPanel.add(forgotPassword);
			
			loginButton = new JButton("Sign In"){
			@Override 
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(blue);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 45);
				super.paintComponent(g2);
				g2.dispose();
			}};
			loginButton.setFont(new Font("Serif",Font.BOLD,18));
			loginButton.setForeground(new Color(212,175,55));
			loginButton.setBackground(blue);
			loginButton.setBounds(230,465,160,45);
			loginButton.setFocusPainted(false);
			loginButton.setBorderPainted(false);
			loginButton.setContentAreaFilled(false);
			loginPanel.add(loginButton);
			loginButton.addActionListener(e -> {
				String email = emailField.getText().trim();
				String password = new String(passwordField.getPassword()).trim();
				if(email.isEmpty() && password.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Please Enter your Email And Password.","Login Error",JOptionPane.ERROR_MESSAGE);
				}
				else if(email.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Please Enter Your Email","Login Error",JOptionPane.ERROR_MESSAGE);
				}
				else if(password.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Please Enter Your Password","Login Error",JOptionPane.ERROR_MESSAGE);
				}
				else {
					String sql = "SELECT * FROM users "
			                   + "WHERE email = ? AND epassword = ?";
			        try {
			            Connection con = DBConnection.getConnection();
			            PreparedStatement pst =
			                    con.prepareStatement(sql);

			            pst.setString(1, email);
			            pst.setString(2, password);
			            ResultSet rs = pst.executeQuery();
			            if (rs.next()) {

			                JOptionPane.showMessageDialog(
			                   login,
			                    "Login successful!"
			                );
			            } else {

			                JOptionPane.showMessageDialog(
			                    login,
			                    "Invalid username or password"
			                );
			            }

			            rs.close();
			            pst.close();
			            con.close();

			        } catch (SQLException ex) {

			            JOptionPane.showMessageDialog(
			                login,
			                "Database Error: " + ex.getMessage()
			            );
			        }

				}});
			
			
			setVisible(true);
		}
		
		public static void main(String[] args) {
			new LoginPage();
		}}

