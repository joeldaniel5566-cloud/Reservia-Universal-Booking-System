package cia;
import java.awt.*;
import java.sql.*;

import javax.swing.*;

public class profile extends JPanel{
	
	JPanel p1;
	
	RoundedPanel r1,r2,r3,r4;
	
	JLabel t1,t2,icon,name,uid, mail, icon1, mail1, icon2, ph, ph1, icon3,
	member, member1, icon4, wallet, wallet1, amt, icon5, tr1, tr2, book1, book2;
	
	JButton w1,tr3,book3,log,back;
	private JPanel pages;
	private CardLayout cardLayout;

	private Wallet walletPage;
	private transactionPage transactionPage;
	
	public void refreshProfile() {

	    long balance = WalletData.getBalance(session.userId);

	    amt.setText("₹" + balance);

	    revalidate();
	    repaint();
	}
	
	private void loadProfile() {

	    String sql =
	        "SELECT fullname, email, phone, created_at " +
	        "FROM users WHERE user_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setInt(1, session.userId);

	        try (ResultSet rs = pst.executeQuery()) {

	            if (rs.next()) {

	                name.setText(rs.getString("fullname"));

	                uid.setText(
	                    "User ID: " + session.userId
	                );

	                mail.setText(
	                    rs.getString("email")
	                );

	                ph1.setText(
	                    rs.getString("phone")
	                );

	                java.sql.Timestamp created =
	                    rs.getTimestamp("created_at");

	                if (created != null) {

	                    java.text.SimpleDateFormat sdf =
	                        new java.text.SimpleDateFormat(
	                            "MMMM yyyy"
	                        );

	                    member1.setText(
	                        sdf.format(created)
	                    );
	                }
	            }
	        }

	    } catch (SQLException ex) {

	        ex.printStackTrace();

	        JOptionPane.showMessageDialog(
	            this,
	            "Unable to load profile:\n" +
	            ex.getMessage(),
	            "Database Error",
	            JOptionPane.ERROR_MESSAGE
	        );
	    }
	}
	
	profile(JPanel pages,
	        CardLayout cardLayout,
	        Wallet walletPage,
	        transactionPage transactionPage){
		
		this.pages = pages;
		this.cardLayout = cardLayout;
		this.walletPage = walletPage;
		this.transactionPage = transactionPage;
		
		setLayout(new BorderLayout());
		setBackground(new Color(11,22,40));
		
		p1 = new JPanel();
		p1.setBackground(new Color(11,22,40));
		p1.setLayout(null);
		add(p1,BorderLayout.CENTER);
		
		back = new JButton("Back →");
		back.setForeground(new Color(212,175,55));
		back.setBounds(1300, 50, 250, 40);
		back.setFont(new Font("Times new Roman",Font.PLAIN,24));
		back.setOpaque(false);
		back.setContentAreaFilled(false);
		back.setBorderPainted(false);
		back.setFocusPainted(false);
		back.setCursor(new Cursor(Cursor.HAND_CURSOR));
		p1.add(back);
		back.addActionListener(e -> {
		    cardLayout.show(pages, "HOME");
		});
		
		t1 = new JLabel("My Profile");
		t1.setForeground(new Color (212,175,55));
		t1.setFont(new Font("Times new Roman",Font.PLAIN,45));
		t1.setBounds(70, 35, 300, 50);
		p1.add(t1);
		
		t2 = new JLabel("Manage your Account details");
		t2.setForeground(Color.white);
		t2.setFont(new Font("Times new Roman",Font.PLAIN,18));
		t2.setBounds(70, 85, 300, 40);
		p1.add(t2);
	
		r1 = new RoundedPanel();
		r1.setBackground(new Color(11,22,40));
		r1.setLayout(null);
		r1.setBounds(70, 145, 1400, 250);
		p1.add(r1);
		
		JPanel profileIcon = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);

		        Graphics2D g2 = (Graphics2D) g;
		        g2.setRenderingHint(
		            RenderingHints.KEY_ANTIALIASING,
		            RenderingHints.VALUE_ANTIALIAS_ON
		        );

		        Color gold = new Color(212, 175, 55);

		        // Circle
		        g2.setColor(gold);
		        g2.setStroke(new BasicStroke(2));
		        g2.drawOval(3, 3, 174, 174);

		        // Head
		        g2.fillOval(65, 25, 50, 50);

		        // Shoulders / body
		        g2.fillRoundRect(50, 85, 80, 60, 30, 20);
		    }
		};

		profileIcon.setOpaque(false);
		profileIcon.setBounds(60, 33, 250, 250);
		r1.add(profileIcon);
		
		name = new JLabel("Krisha Mohile");
		name.setForeground(Color.white);
		name.setFont(new Font("Times new Roman",Font.PLAIN,38));
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setBounds(275, 45, 300, 50);
		r1.add(name);
		
		uid = new JLabel("User ID: 1");
		uid.setForeground(Color.white);
		uid.setFont(new Font("Times new Roman",Font.PLAIN,18));
		uid.setHorizontalAlignment(SwingConstants.CENTER);
		uid.setBounds(275, 100, 300, 50);
		r1.add(uid);
		
		
		log = new JButton("Logout");
		log.setForeground(Color.red);
		log.setFont(new Font("Times new Roman",Font.PLAIN,20));
		log.setHorizontalAlignment(SwingConstants.CENTER);
		log.setBounds(360, 160, 130, 50);
		log.setBorder(BorderFactory.createLineBorder(Color.RED, 2, true));
		log.setContentAreaFilled(false);
		log.setFocusPainted(false);
		log.setCursor(new Cursor(Cursor.HAND_CURSOR));
		r1.add(log);
		log.addActionListener(e -> {

		    int choice = JOptionPane.showConfirmDialog(
		        this,
		        "Are you sure you want to logout?",
		        "Logout",
		        JOptionPane.YES_NO_OPTION
		    );

		    if (choice == JOptionPane.YES_OPTION) {

		        session.userId = 0;

		        new LoginPage();
		    }
		});
		
		JPanel divider = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);

		        Graphics2D g2 = (Graphics2D) g.create();

		        g2.setColor(new Color(60, 75, 95));
		        g2.setStroke(new BasicStroke(1));

		        g2.drawLine(
		            0, 0,
		            0, getHeight()
		        );

		        g2.dispose();
		    }
		};

		divider.setOpaque(false);
		divider.setBounds(600, 25, 1, 200);
		r1.add(divider);
		
		icon1 = new JLabel("✉");
		icon1.setForeground(new Color(212, 175, 55));
		icon1.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 85));
		icon1.setBounds(710, 25, 90, 90);
		r1.add(icon1);
		
		mail1 = new JLabel("Email");
		mail1.setForeground(Color.white);
		mail1.setFont(new Font("Times new Roman",Font.PLAIN,18));
		mail1.setHorizontalAlignment(SwingConstants.CENTER);
		mail1.setBounds(600, 130, 300, 50);
		r1.add(mail1);
		
		mail = new JLabel("krishaa.mohile@gmail.com");
		mail.setForeground(Color.white);
		mail.setFont(new Font("Times new Roman",Font.PLAIN,20));
		mail.setHorizontalAlignment(SwingConstants.CENTER);
		mail.setBounds(605, 165, 300, 50);
		r1.add(mail);
		
		JPanel divider1 = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);

		        Graphics2D g2 = (Graphics2D) g.create();

		        g2.setColor(new Color(60, 75, 95));
		        g2.setStroke(new BasicStroke(1));

		        g2.drawLine(
		            0, 0,
		            0, getHeight()
		        );

		        g2.dispose();
		    }
		};

		divider1.setOpaque(false);
		divider1.setBounds(905, 25, 1, 200);
		r1.add(divider1);
		
		icon2 = new JLabel("📞");
		icon2.setForeground(new Color(212, 175, 55));
		icon2.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 75));
		icon2.setBounds(990, 25, 80, 80);
		r1.add(icon2);
		
		ph = new JLabel("Phone Number");
		ph.setForeground(Color.white);
		ph.setFont(new Font("Times new Roman",Font.PLAIN,18));
		ph.setHorizontalAlignment(SwingConstants.CENTER);
		ph.setBounds(880, 130, 300, 50);
		r1.add(ph);
		
		ph1 = new JLabel("7021227306");
		ph1.setForeground(Color.white);
		ph1.setFont(new Font("Times new Roman",Font.PLAIN,20));
		ph1.setHorizontalAlignment(SwingConstants.CENTER);
		ph1.setBounds(880, 165, 300, 50);
		r1.add(ph1);
		
		JPanel divider2 = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);

		        Graphics2D g2 = (Graphics2D) g.create();

		        g2.setColor(new Color(60, 75, 95));
		        g2.setStroke(new BasicStroke(1));

		        g2.drawLine(
		            0, 0,
		            0, getHeight()
		        );

		        g2.dispose();
		    }
		};

		divider2.setOpaque(false);
		divider2.setBounds(1150, 25, 1, 200);
		r1.add(divider2);
		
		icon3 = new JLabel("📆");
		icon3.setForeground(new Color(212, 175, 55));
		icon3.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 75));
		icon3.setBounds(1225, 25, 80, 80);
		r1.add(icon3);
		
		member = new JLabel("Account Created");
		member.setForeground(Color.white);
		member.setFont(new Font("Times new Roman",Font.PLAIN,18));
		member.setHorizontalAlignment(SwingConstants.CENTER);
		member.setBounds(1115, 130, 300, 50);
		r1.add(member);
		
		member1 = new JLabel("September 2026");
		member1.setForeground(Color.white);
		member1.setFont(new Font("Times new Roman",Font.PLAIN,20));
		member1.setHorizontalAlignment(SwingConstants.CENTER);
		member1.setBounds(1115, 165, 300, 50);
		r1.add(member1);
		
		r2 = new RoundedPanel();
		r2.setBackground(new Color(11,22,40));
		r2.setLayout(null);
		r2.setBounds(265, 435, 310, 250);
		p1.add(r2);
		
		wallet1 = new JLabel("Available Balance");
		wallet1.setForeground(Color.white);
		wallet1.setFont(new Font("Times new Roman",Font.PLAIN,28));
		wallet1.setHorizontalAlignment(SwingConstants.CENTER);
		wallet1.setBounds(5, 15, 300, 50);
		r2.add(wallet1);
		
		long balance = WalletData.getBalance(session.userId);
		amt = new JLabel("₹" + balance);
		amt.setForeground(new Color(212,175,55));
		amt.setFont(new Font("Times new Roman",Font.PLAIN,42));
		amt.setHorizontalAlignment(SwingConstants.CENTER);
		amt.setBounds(0, 90, 300, 50);
		r2.add(amt);
		
		w1 = new JButton ("View Wallet →");
		w1.setForeground(Color.black);
		w1.setBackground(new Color(212,175,55));
		w1.setFont(new Font("Times new Roman",Font.PLAIN,20));
		w1.setHorizontalAlignment(SwingConstants.CENTER);
		w1.setBounds(30, 185, 250, 40);
		w1.setBorderPainted(false);
		w1.setFocusPainted(false);
		r2.add(w1);
		w1.addActionListener(e -> {
		    walletPage.refreshWallet();
		    cardLayout.show(pages, "WALLET");
		});
		
		r3 = new RoundedPanel();
		r3.setBackground(new Color(11,22,40));
		r3.setLayout(null);
		r3.setBounds(608, 435, 310, 250);
		p1.add(r3);
			
		tr1 = new JLabel("Transactions");
		tr1.setForeground(Color.white);
		tr1.setFont(new Font("Times new Roman",Font.PLAIN,28));
		tr1.setHorizontalAlignment(SwingConstants.CENTER);
		tr1.setBounds(0, 15, 300, 50);
		r3.add(tr1);
		
		tr2 = new JLabel("📄");
		tr2.setForeground(new Color(212,175,55));
		tr2.setFont(new Font("Seoge UI Symbol",Font.PLAIN,85));
		tr2.setHorizontalAlignment(SwingConstants.CENTER);
		tr2.setBounds(15, 70, 280, 90);
		r3.add(tr2);
		
		tr3 = new JButton("View Transactions →");
		tr3.setForeground(Color.black);
		tr3.setFont(new Font("Times new Roman",Font.PLAIN,20));
		tr3.setHorizontalAlignment(SwingConstants.CENTER);
		tr3.setBounds(30, 185, 250, 40);
		tr3.setBackground(new Color(212,175,55));
		tr3.setBorderPainted(false);
		tr3.setFocusPainted(false);
		r3.add(tr3);
		tr3.addActionListener(e -> {
		    transactionPage.refreshTrans();
		    cardLayout.show(pages, "TRANSACTIONS");
		});
		
		r4 = new RoundedPanel();
		r4.setBackground(new Color(11,22,40));
		r4.setLayout(null);
		r4.setBounds(950, 435, 310, 250);
		p1.add(r4);
			
		book1 = new JLabel("My Bookings");
		book1.setForeground(Color.white);
		book1.setFont(new Font("Times new Roman",Font.PLAIN,28));
		book1.setHorizontalAlignment(SwingConstants.CENTER);
		book1.setBounds(5, 15, 300, 50);
		r4.add(book1);
		
		book2 = new JLabel("🎟️");
		book2.setForeground(new Color(212,175,55));
		book2.setFont(new Font("Seoge UI Symbol",Font.PLAIN,85));
		book2.setHorizontalAlignment(SwingConstants.CENTER);
		book2.setBounds(10, 65, 280, 90);
		r4.add(book2);
		
		book3 = new JButton("View Bookings →");
		book3.setForeground(Color.black);
		book3.setFont(new Font("Times new Roman",Font.PLAIN,20));
		book3.setHorizontalAlignment(SwingConstants.CENTER);
		book3.setBounds(30, 185, 250, 40);
		book3.setBackground(new Color(212,175,55));
		book3.setBorderPainted(false);
		book3.setFocusPainted(false);
		r4.add(book3);
		
		loadProfile();
	}
}