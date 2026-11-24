package cia;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import java.sql.*;

   
	
	public class addmoney extends JPanel{
		JPanel main,add,pages,navbar,logo,navbtn,balance1; 
		RoundedPanel p2,pay,pay1;
		JLabel l1,l2,l3,t1,t2,amt,sel,enter,method;
		JButton home,explore,booking,wallet,b1,b2,b3,b4,method1,method2,method3,method4,payment,back;
		JTextField e1;
		CardLayout cardLayout;
		Wallet walletPage;
		
		public void updateMode() {

		    if (session.bal == 1) {

		        t1.setText("Add Money");
		        method.setText("Select Your Payment Method:");
		        payment.setText("Add");
		        method2.setText("Debit Card");
		        method3.setVisible(true);
		        method4.setVisible(true);

		    } else {

		        t1.setText("Withdraw Money");
		        method.setText("Select Your Withdrawal Method:");
		        payment.setText("Withdraw");
		        method2.setText("Bank");
		        method3.setVisible(false);
		        method4.setVisible(false);
		    }}
		
		public void refreshWallet() {

		    long currentBalance = WalletData.getBalance(session.userId);
		    amt.setText("₹" + currentBalance);

		    }
		
		@Override
		public void setVisible(boolean visible) {
		    super.setVisible(visible);

		    if (visible && amt != null) {
		        refreshWallet();
		    }
		}
		
		private void addMoney() {

		    String amountText = e1.getText().trim();

		    if (amountText.equals("") ||
		        amountText.equals("Enter The Amount")) {

		        JOptionPane.showMessageDialog(
		            this,
		            "Please enter an amount."
		        );

		        return;
		    }

		    long amount;

		    try {

		        amount = Long.parseLong(amountText);

		    } catch (NumberFormatException ex) {

		        JOptionPane.showMessageDialog(
		            this,
		            "Please enter a valid whole amount."
		        );

		        return;
		    }

		    if (amount <= 0) {

		        JOptionPane.showMessageDialog(
		            this,
		            "Amount must be greater than ₹0."
		        );

		        return;
		    }

		    String paymentMethod = "";

		    if (method1.getBackground().equals(Color.WHITE)) {

		        paymentMethod = "UPI";

		    }
		    else if (method2.getBackground().equals(Color.WHITE)) {

		        paymentMethod = "Debit Card";

		    }
		    else if (method3.getBackground().equals(Color.WHITE)) {

		        paymentMethod = "Credit Card";

		    }
		    else if (method4.getBackground().equals(Color.WHITE)) {

		        paymentMethod = "Net Banking";
		    }


		    if (paymentMethod.equals("")) {

		        JOptionPane.showMessageDialog(
		            this,
		            "Please select a payment method."
		        );

		        return;
		    }

		    if (session.bal == 1) {

		        String updateWallet =
		            "UPDATE wallet " +
		            "SET balance = balance + ? " +
		            "WHERE user_id = ?";


		        String insertTransaction =
		            "INSERT INTO transaction " +
		            "(user_id, transaction_type, description, " +
		            "booking_id, transaction_date, amount, status) " +
		            "VALUES (?, ?, ?, ?, NOW(), ?, ?)";


		        try (
		            Connection cn = DBConnection.getConnection()
		        ) {

		            cn.setAutoCommit(false);

		            try (
		                PreparedStatement walletStmt =
		                    cn.prepareStatement(updateWallet);

		                PreparedStatement transactionStmt =
		                    cn.prepareStatement(insertTransaction)
		            ) {


		                walletStmt.setLong(1, amount);
		                walletStmt.setInt(2, session.userId);

		                int updated =
		                    walletStmt.executeUpdate();


		                if (updated == 0) {

		                    JOptionPane.showMessageDialog(
		                        this,
		                        "Wallet not found."
		                    );

		                    cn.rollback();
		                    return;
		                }


		                transactionStmt.setInt(1, session.userId);

		                transactionStmt.setString(
		                    2,
		                    "Wallet"
		                );

		                transactionStmt.setString(
		                    3,
		                    "Wallet Top-up"
		                );

		                transactionStmt.setString(
		                    4,
		                    "-"
		                );

		                transactionStmt.setLong(
		                    5,
		                    amount
		                );

		                transactionStmt.setString(
		                    6,
		                    "Success"
		                );

		                transactionStmt.executeUpdate();

		                cn.commit();


		                JOptionPane.showMessageDialog(
		                    this,
		                    "₹" + amount +
		                    " added successfully!"
		                );


		                e1.setText(
		                    "Enter The Amount"
		                );

		                walletPage.refreshWallet();
		                cardLayout.show(
		                    pages,
		                    "WALLET"
		                );

		            }

		            catch (SQLException ex) {

		                cn.rollback();

		                JOptionPane.showMessageDialog(
		                    this,
		                    "Transaction failed: "
		                    + ex.getMessage()
		                );
		            }

		        }

		        catch (SQLException ex) {

		            JOptionPane.showMessageDialog(
		                this,
		                "Database error: "
		                + ex.getMessage()
		            );
		        }
		    }

		    else if (session.bal == 2) {

		        String updateWallet =
		            "UPDATE wallet " +
		            "SET balance = balance - ? " +
		            "WHERE user_id = ? " +
		            "AND balance >= ?";


		        String insertTransaction =
		            "INSERT INTO transaction " +
		            "(user_id, transaction_type, description, " +
		            "booking_id, transaction_date, amount, status) " +
		            "VALUES (?, ?, ?, ?, NOW(), ?, ?)";


		        try (
		            Connection cn = DBConnection.getConnection()
		        ) {

		            cn.setAutoCommit(false);

		            try (
		                PreparedStatement walletStmt =
		                    cn.prepareStatement(updateWallet);

		                PreparedStatement transactionStmt =
		                    cn.prepareStatement(insertTransaction)
		            ) {


		                walletStmt.setLong(1, amount);
		                walletStmt.setInt(2, session.userId);
		                walletStmt.setLong(3, amount);

		                int updated =
		                    walletStmt.executeUpdate();


		                if (updated == 0) {

		                    JOptionPane.showMessageDialog(
		                        this,
		                        "Insufficient wallet balance."
		                    );

		                    cn.rollback();
		                    return;
		                }

		                transactionStmt.setInt(1, session.userId);

		                transactionStmt.setString(
		                    2,
		                    "Wallet"
		                );

		                transactionStmt.setString(
		                    3,
		                    "Wallet Withdrawal"
		                );

		                transactionStmt.setString(
		                    4,
		                    "-"
		                );

		                transactionStmt.setLong(
		                    5,
		                    -amount
		                );

		                transactionStmt.setString(
		                    6,
		                    "Success"
		                );

		                transactionStmt.executeUpdate();
		                
		                cn.commit();


		                JOptionPane.showMessageDialog(
		                    this,
		                    "₹" + amount +
		                    " withdrawn successfully!"
		                );


		                e1.setText(
		                    "Enter The Amount"
		                );

		                walletPage.refreshWallet();
		                cardLayout.show(
		                    pages,
		                    "WALLET"
		                );

		            }

		            catch (SQLException ex) {

		                cn.rollback();

		                JOptionPane.showMessageDialog(
		                    this,
		                    "Transaction failed: "
		                    + ex.getMessage()
		                );
		            }

		        }

		        catch (SQLException ex) {

		            JOptionPane.showMessageDialog(
		                this,
		                "Database error: "
		                + ex.getMessage()
		            );
		        }
		    }
		}		
		
		addmoney(JPanel pages,CardLayout cardLayout,Wallet walletPage){
			
			this.pages=pages;
			this.cardLayout=cardLayout;
			this.walletPage=walletPage;
	        setLayout(new BorderLayout());
	        
	        add = new JPanel();
	        add.setBackground(new Color(11,22,40));
	        add.setLayout(null);
	        add(add);
	        
	        if(session.bal==1) {
	        t1 = new JLabel ("Add Money");
	        }else
	        {
	        t1 = new JLabel ("Withdraw Money");
	        }
	        t1.setForeground(Color.white);
	        t1.setFont(new Font("Times new Roman",Font.BOLD,50));
	        t1.setHorizontalAlignment(SwingConstants.CENTER);
	        t1.setBounds(515, 0, 500, 85);
	        add.add(t1);
	        
	        back = new JButton("← Back");

	        back.setBounds(80, 20, 100, 40);
	        back.setForeground(new Color(212, 175, 55));
	        back.setBackground(new Color(11, 22, 40));
	        back.setFont(new Font("Times New Roman", Font.PLAIN, 20));

	        back.setFocusPainted(false);
	        back.setBorderPainted(false);
	        back.setContentAreaFilled(false);

	        add.add(back);
	        
	        back.addActionListener(e->{
	        	cardLayout.show(pages, "WALLET");
	        });
	        
	        balance1 = new JPanel();
	        balance1.setBackground(new Color (11,22,40));
	        balance1.setBorder(BorderFactory.createLineBorder(new Color(212,175,55)));
	        balance1.setBounds(455, 85, 595,70);
	        add.add(balance1);
	        balance1.setLayout(null);
	        
	        t2 = new JLabel ("Current Balance: ");
	        t2.setForeground(Color.white);
	        t2.setHorizontalAlignment(SwingConstants.CENTER);
	        t2.setFont(new Font("Times new Roman",Font.PLAIN,25));
	        t2.setBounds(115, 15, 300, 40);
	        balance1.add(t2);
	        
	        amt = new JLabel ("₹0");
	        amt.setForeground(new Color(212,175,55));
	        amt.setHorizontalAlignment(SwingConstants.CENTER);
	        amt.setFont(new Font("Times new Roman",Font.PLAIN,25));
	        amt.setBounds(260, 15, 300, 40);
	        balance1.add(amt);
	        refreshWallet();
	        
	        p2 = new RoundedPanel();
	        p2.setBackground(new Color(11,22,40));
	        p2.setLayout(null);
	        p2.setBounds(455, 185, 600, 260);
	        add.add(p2);
	       
	        sel = new JLabel ("Select Amount:");
	        sel.setForeground(Color.white);
	        sel.setFont(new Font("Times new Roman",Font.BOLD,22));
	        sel.setHorizontalAlignment(SwingConstants.CENTER);
	        sel.setBounds(20, 10, 300, 40);
	        p2.add(sel);
	       
	        b1 = new JButton ("₹ 500");
	        b1.setBackground(new Color (11,22,40));
	        b1.setForeground(Color.white);
	        b1.setBorder(BorderFactory.createLineBorder(new Color(212,175,55)));
	        b1.setFont(new Font("Times new Roman",Font.PLAIN,18));
	        b1.setBounds(20, 60, 120, 45);
	        b1.setContentAreaFilled(false);
	        b1.setFocusPainted(false);
	        p2.add(b1); 
	       
	        b2 = new JButton ("₹ 1000");
	        b2.setBackground(new Color (11,22,40));
	        b2.setForeground(Color.white);
	        b2.setBorder(BorderFactory.createLineBorder(new Color(212,175,55)));
	        b2.setFont(new Font("Times new Roman",Font.PLAIN,18));
	        b2.setBounds(165, 60, 120, 45);
	        b2.setContentAreaFilled(false);
	        b2.setFocusPainted(false);
	        p2.add(b2);
	       
	        b3 = new JButton ("₹ 2000");
	        b3.setBackground(new Color(11,22,40));
	        b3.setForeground(Color.white);
	        b3.setBorder(BorderFactory.createLineBorder(new Color(212,175,55)));
	        b3.setFont(new Font("Times new Roman",Font.PLAIN,18));
	        b3.setBounds(315, 60, 120, 45);
	        b3.setContentAreaFilled(false);
	        b3.setFocusPainted(false);
	        p2.add(b3);
	       
	        b4 = new JButton ("₹ 3000");
	        b4.setBackground(new Color(11,22,40));
	        b4.setForeground(Color.white);
	        b4.setContentAreaFilled(false);
	        b4.setFont(new Font("Times new Roman",Font.PLAIN,18));
	        b4.setBounds(460, 60, 120, 45);
	        b4.setBorder(BorderFactory.createLineBorder(new Color(212,175,55)));
	        b4.setFocusPainted(false);
	        p2.add(b4);
	        
	       
	        
	        enter = new JLabel ("Enter Amount:");
	        enter.setBackground(new Color(11,22,40));
	        enter.setForeground(Color.white);
	        enter.setFont(new Font("Times new Roman",Font.BOLD,22));
	        enter.setBounds(20, 140, 300, 40);
	        p2.add(enter);
	        
	        e1 = new JTextField();
	        e1.setBounds(20, 190, 550, 45);
	        e1.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
	        e1.setBorder(BorderFactory.createLineBorder(new Color(212,175,55)));
		    e1.setHorizontalAlignment(SwingConstants.CENTER);
		    e1.setText("Enter The Amount");
		    e1.setFont(new Font("Times New Roman",Font.BOLD,18));
		    e1.setForeground(Color.black);
		    e1.setBackground(Color.white);
	        p2.add(e1);
	        
	        b1.addActionListener(e->{
	        	e1.setText("500");
	        });
	        b2.addActionListener(e->{
	        	e1.setText("1000");
	        });
	        b3.addActionListener(e->{
	        	e1.setText("2000");
	        });
	        b4.addActionListener(e->{
	        	e1.setText("3000");
	        });
	        
	        String placeholder = "Enter The Amount";
		    e1.addFocusListener(new FocusAdapter() {
		    	@Override 
		    	public void focusGained(FocusEvent e) {
		    		if(e1.getText().equals(placeholder)) {
		    			e1.setText("");
		    		}
		    	}
		    	@Override
		    	public void focusLost(FocusEvent e) {
		    		if(e1.getText().isEmpty()) {
		    			e1.setText(placeholder);
		    		}
		    	}
		    });
		    
		    pay = new RoundedPanel();
		    pay.setBounds(455, 480, 600, 220);
		    pay.setBackground(new Color(11,22,40));
		    pay.setLayout(null);
	        add.add(pay);
	       
	        if(session.bal==1) {
	        method = new JLabel("Select Your Payment Method:");
	        }else {
		    method = new JLabel("Select Your Withdrawal Method:");
	        }
	        method.setForeground(Color.white);
	        method.setFont(new Font("Times new Roman", Font.BOLD,20));
	        method.setBounds(15,15,300,40);
	        pay.add(method);
	        
	        method1 = new JButton("UPI");
	        method1.setBackground(new Color(11,22,40));
	        method1.setForeground(Color.white);
	        method1.setFont(new Font("Times new Roman",Font.PLAIN,17));
	        method1.setBorder(BorderFactory.createLineBorder(new Color(212,175,55)));
	        method1.setBounds(15, 65, 130, 45);
	        method1.setContentAreaFilled(false);
	        method1.setFocusPainted(false);
	        pay.add(method1);
	        
	        if(session.bal==1) {
	        method2 = new JButton("Debit Card");
	        }
	        else {
	        	method2 = new JButton("Bank");
	        }
	        method2.setBackground(new Color(11,22,40));
	        method2.setForeground(Color.white);
	        method2.setFont(new Font("Times new Roman",Font.PLAIN,17));
	        method2.setBorder(BorderFactory.createLineBorder(new Color(212,175,55)));
	        method2.setBounds(160, 65, 130, 45);
	        method2.setContentAreaFilled(false);
	        method2.setFocusPainted(false);
	        pay.add(method2);
	        
	        method3 = new JButton("Credit Card");
	        method3.setBackground(new Color(11,22,40));
	        method3.setForeground(Color.white);
	        method3.setFont(new Font("Times new Roman",Font.PLAIN,17));
	        method3.setBorder(BorderFactory.createLineBorder(new Color(212,175,55)));
	        method3.setBounds(305, 65, 130, 45);
	        method3.setContentAreaFilled(false);
	        method3.setFocusPainted(false);
	        
	        
	        method4 = new JButton("Net Banking");
	        method4.setBackground(new Color(11,22,40));
	        method4.setForeground(Color.white);
	        method4.setFont(new Font("Times new Roman",Font.PLAIN,17));
	        method4.setBorder(BorderFactory.createLineBorder(new Color(212,175,55)));
	        method4.setBounds(450, 65, 130, 45);
	        method4.setContentAreaFilled(false);
	        method4.setFocusPainted(false);
	        
	        if(session.bal==1) {
	        pay.add(method3);
	        pay.add(method4);
	        }
	        method1.addActionListener(e->{
	        	method1.setContentAreaFilled(true);
	        	method1.setBackground(Color.white);
	        	method1.setForeground(Color.black);
	        	method2.setContentAreaFilled(false);
	        	method3.setContentAreaFilled(false);
	        	method4.setContentAreaFilled(false);
	        	method2.setForeground(Color.white);
	        	method3.setForeground(Color.white);
	        	method4.setForeground(Color.white);
	        });
	        
	        method2.addActionListener(e->{
	        	method2.setContentAreaFilled(true);
	        	method2.setBackground(Color.white);
	        	method2.setForeground(Color.black);
	        	method1.setContentAreaFilled(false);
	        	method3.setContentAreaFilled(false);
	        	method4.setContentAreaFilled(false);
	        	method1.setForeground(Color.white);
	        	method3.setForeground(Color.white);
	        	method4.setForeground(Color.white);
	        });
	        
	        method3.addActionListener(e->{
	        	method3.setContentAreaFilled(true);
	        	method3.setBackground(Color.white);
	        	method3.setForeground(Color.black);
	        	method2.setContentAreaFilled(false);
	        	method1.setContentAreaFilled(false);
	        	method4.setContentAreaFilled(false);
	        	method1.setForeground(Color.white);
	        	method2.setForeground(Color.white);
	        	method4.setForeground(Color.white);
	        });
	        
	        method4.addActionListener(e->{
	        	method4.setContentAreaFilled(true);
	        	method4.setBackground(Color.white);
	        	method4.setForeground(Color.black);
	        	method2.setContentAreaFilled(false);
	        	method3.setContentAreaFilled(false);
	        	method1.setContentAreaFilled(false);
	        	method1.setForeground(Color.white);
	        	method2.setForeground(Color.white);
	        	method3.setForeground(Color.white);
	        });
	        
	        if (session.bal==1) {
	           payment = new JButton ("Add");
	        }else {
	        	payment=new JButton("Withdraw");
	        }
		       
		       payment.setForeground(Color.black);
		       payment.setFont(new Font("Times New Roman",Font.BOLD,20));
		       payment.setHorizontalAlignment(SwingConstants.CENTER);
		       payment.setBounds(202, 145, 200, 50);
		       payment.setFocusPainted(false);
		       payment.setBackground(new Color (212,175,55));
		       pay.add(payment);
		       
		       payment.addActionListener(e->{
		    	   addMoney();
		       });
		    
		}

}