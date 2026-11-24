package cia;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.table.*;

	public class Wallet extends JPanel{
		RoundedPanel balance,rupees,transfer,refund,transaction1,transaction2,transaction3;
		JPanel pages,walet,mybal,pay,secure,money; 
		JLabel l1,num,amt,icon,w1,w2,w3,action,recent,recent_trans,mon1,mon2,mon3,trans1,trans2,trans3,ref1,ref2,ref3;
		DefaultTableModel model;
		JTable transactionTable;
		JScrollPane scrollPane;
		JButton add,with,viewAll;
		RoundedButton bt;
		CardLayout cardLayout;
		
		transactionPage transactionPage;
		addmoney addMoneyPage;
		refund refundPage;

		public void setTransactionPage(transactionPage transactionPage) {
		    this.transactionPage = transactionPage;
		}
		
		public void setAddMoneyPage(addmoney addMoneyPage) {
		    this.addMoneyPage = addMoneyPage;
		}
		
		public void setRefundPage(refund refundPage) {
		    this.refundPage = refundPage;
		}
		
		public void refreshWallet() {

		    long currentBalance = WalletData.getBalance(session.userId);
		    amt.setText("₹" + currentBalance);

		    model.setRowCount(0);

		    for (Object[] row : WalletData.getTransactions(session.userId)) {
		        model.addRow(row);
		    }
		}
		public Wallet(JPanel pages,CardLayout cardLayout) {
			      
			 		this.pages = pages;
			 		this.cardLayout = cardLayout;
				   		   
			 		
			 		setLayout(new BorderLayout());
			 		setBackground(new Color(11,22,40));
			       //Wallet
			       walet = new JPanel ();
			       walet.setBackground(new Color(11,22,40));
			       walet.setLayout(null);
			       
			       w1 = new JLabel("💳");
			       w1.setBounds(619, 17, 100, 40);
			       w1.setForeground(new Color(212,175,55));
			       w1.setBackground(new Color(11,22,40));
			       w1.setFont(new Font("Seoge UI Symbol",Font.PLAIN,55));
			       w1.setHorizontalAlignment(SwingConstants.CENTER);
			       walet.add(w1);
			       
			       w2 = new JLabel("My Wallet");
			       w2.setBounds(640, 18, 300, 40);
			       w2.setForeground(Color.white);
			       w2.setFont(new Font("Times new Roman",Font.BOLD, 38));
			       w2.setHorizontalAlignment(SwingConstants.CENTER);
			       walet.add(w2);
			       
			       w3 = new JLabel ("Secure payments, Instant Rewards!");
			       w3.setFont(new Font ("Times new Roman", Font.PLAIN,15));
			       w3.setForeground(Color.white);
			       w3.setBounds(605, 55, 300, 40);
			       w3.setHorizontalAlignment(SwingConstants.CENTER);			       
			       walet.add(w3);
			       
			       balance = new RoundedPanel();
			       balance.setLayout(new GridLayout(1,3,20,0));
			       balance.setBorder(BorderFactory.createLineBorder(new Color(212,175,55)));
			       balance.setBackground(new Color(11,22,40));
			       walet.add(balance);
			       balance.setBounds(80, 100, 625, 220);
			       
			       mybal = new JPanel();
			       mybal.setLayout(null);
			       
			       num = new JLabel ("Wallet Balance");
			       num.setBounds(135, 15, 300, 40);
			       num.setFont(new Font ("Times new Roman",Font.BOLD,30));			 
			       num.setForeground(Color.white);	
			       num.setHorizontalAlignment(SwingConstants.CENTER);
			       mybal.add(num);
			       mybal.setBackground(new Color (11,22,40));
			       
			       amt = new JLabel ("₹0");
			       amt.setBounds(125, 75, 300, 45);
			       amt.setForeground(new Color(212,175,55));
			       amt.setHorizontalAlignment(SwingConstants.CENTER);
			       amt.setFont(new Font("Times new Roman",Font.PLAIN,60));
			       mybal.add(amt);
			       
			       icon = new JLabel ("👛");
			       icon.setBounds(400,70,300,60);
			       icon.setForeground(new Color(212,175,55));
			       icon.setHorizontalAlignment(SwingConstants.CENTER);
			       icon.setFont(new Font("Seoge UI Symbol",Font.PLAIN,68));
			       mybal.add(icon);
			       
			       add = new JButton ("+ Add Money");
			       add.setBackground(new Color (212,175,55));
			       add.setForeground(Color.black);
			       add.setFont(new Font("Times New Roman",Font.BOLD,15));
			       add.setHorizontalAlignment(SwingConstants.CENTER);
			       add.setBounds(82, 150, 190, 40);
			       add.setFocusPainted(false);
			       mybal.add(add);
			       add.addActionListener(e->{
			    	   session.bal=1;
			    	   addMoneyPage.updateMode();
			    	   cardLayout.show(pages, "ADD");
			       });
			      
			       with = new JButton (" - Withdraw");
			       with.setBackground(new Color(11,22,40));
			       with.setForeground(new Color(212,175,55));
			       with.setFont(new Font("Times New Roman",Font.BOLD,15));
			       with.setHorizontalAlignment(SwingConstants.CENTER);
			       with.setBounds(302, 150, 190, 40);
			       with.setFocusPainted(false);
			       mybal.add(with);
			       with.addActionListener(e->{
			    	   session.bal=2;
			    	   addMoneyPage.updateMode();
			    	   cardLayout.show(pages, "ADD");
			       });
			       
			       balance.add(mybal);
			       add(walet,BorderLayout.CENTER);
			       
			       rupees = new RoundedPanel ();
			       rupees.setLayout(null);
			       rupees.setBounds(742, 101, 200, 220);
			       walet.add(rupees);
			       
			       mon1 = new JLabel("+");
			       mon1.setBounds(55, 10, 90, 50);
			       mon1.setForeground(new Color(212,175,55));
			       mon1.setFont(new Font("Seoge UI Symbol",Font.BOLD, 75));
			       mon1.setHorizontalAlignment(SwingConstants.CENTER);
			       rupees.add(mon1);
			       
			       mon2 = new JLabel ("Add Money");
			       mon2.setBounds(0, 60, 200, 60);
			       mon2.setForeground(Color.white);
			       mon2.setFont(new Font ("Times new Roman", Font.PLAIN,25));
			       mon2.setHorizontalAlignment(SwingConstants.CENTER);
			       rupees.add(mon2);
			       
			       mon3 = new JLabel ("Top Up Your Wallet");
			       mon3.setBounds(0, 90, 200, 60);
			       mon3.setForeground(Color.white);
			       mon3.setFont(new Font("Times new Roman",Font.PLAIN,13));
			       mon3.setHorizontalAlignment(SwingConstants.CENTER);
			       rupees.add(mon3);
			       
			       bt = new RoundedButton ("➜");
			       bt.setFont(new Font("Seoge UI Symbol",Font.BOLD,15));
			       bt.setForeground(Color.white);
			       bt.setBackground(new Color(20,80,180));
			       bt.setBounds(72, 145, 55, 55);
			       rupees.add(bt);
			       bt.addActionListener(e->{
			    	   session.bal=1;
			    	   addMoneyPage.updateMode();
			    	   cardLayout.show(pages, "ADD");
			       });
			       
			       transfer = new RoundedPanel ();
			       transfer.setLayout(null);
			       transfer.setBounds(983, 101, 200, 220);
			       walet.add(transfer);
			       
			       trans1 = new JLabel("📄");
			       trans1.setBounds(55, 17, 90, 50);
			       trans1.setForeground(new Color(212,175,55));
			       trans1.setFont(new Font("Seoge UI Symbol",Font.BOLD, 50));
			       trans1.setHorizontalAlignment(SwingConstants.CENTER);
			       transfer.add(trans1);
			       
			       trans2 = new JLabel ("Transactions");
			       trans2.setBounds(0, 60, 200, 60);
			       trans2.setForeground(Color.white);
			       trans2.setFont(new Font ("Times new Roman", Font.PLAIN,25));
			       trans2.setHorizontalAlignment(SwingConstants.CENTER);
			       transfer.add(trans2);
			       
			       trans3 = new JLabel ("View Entire History");
			       trans3.setBounds(0, 90, 200, 60);
			       trans3.setForeground(Color.white);
			       trans3.setFont(new Font("Times new Roman",Font.PLAIN,13));
			       trans3.setHorizontalAlignment(SwingConstants.CENTER);
			       transfer.add(trans3);
			       
			       bt = new RoundedButton ("➜");
			       bt.setFont(new Font("Seoge UI Symbol",Font.BOLD,15));
			       bt.setForeground(Color.white);
			       bt.setBackground(new Color(20,80,180));
			       bt.setBounds(72, 145, 55, 55);
			       transfer.add(bt);
			       
			       bt.addActionListener(e->{
			    	   transactionPage.refreshTrans();
			    	   cardLayout.show(pages, "TRANSACTIONS");
			       });
			       
			       refund = new RoundedPanel ();
			       refund.setLayout(null);
			       refund.setBounds(1225, 101, 200, 220);
			       walet.add(refund);
			       
			       ref1 = new JLabel("🔄");
			       ref1.setBounds(55, 15, 90, 50);
			       ref1.setForeground(new Color(212,175,55));
			       ref1.setFont(new Font("Seoge UI Symbol",Font.PLAIN, 47));
			       ref1.setHorizontalAlignment(SwingConstants.CENTER);
			       refund.add(ref1);
			       
			       ref2 = new JLabel ("Refunds");
			       ref2.setBounds(0, 60, 200, 60);
			       ref2.setForeground(Color.white);
			       ref2.setFont(new Font ("Times new Roman", Font.PLAIN,25));
			       ref2.setHorizontalAlignment(SwingConstants.CENTER);
			       refund.add(ref2);
			       
			       ref3 = new JLabel ("View Refunds");
			       ref3.setBounds(0, 90, 200, 60);
			       ref3.setForeground(Color.white);
			       ref3.setFont(new Font("Times new Roman",Font.PLAIN,13));
			       ref3.setHorizontalAlignment(SwingConstants.CENTER);
			       refund.add(ref3); 
			       
			       bt = new RoundedButton ("➜");
			       bt.setFont(new Font("Seoge UI Symbol",Font.BOLD,15));
			       bt.setForeground(Color.white);
			       bt.setBackground(new Color(20,80,180));
			       bt.setBounds(72, 145, 55, 55);
			       refund.add(bt);
			       
			       bt.addActionListener(e->{
			    	   refundPage.refreshRefunds();
			    	   cardLayout.show(pages, "REFUNDS");
			       });
			       
			       recent_trans= new JLabel("Recent Transactions");
			       recent_trans.setBounds(65, 320, 200, 60);
			       recent_trans.setForeground(new Color(212,175,55));
			       recent_trans.setFont(new Font ("Times new Roman", Font.PLAIN,20));
			       recent_trans.setHorizontalAlignment(SwingConstants.CENTER);
			       walet.add(recent_trans);
			       
			       viewAll = new JButton("View All Transactions  →");
			       viewAll.setBounds(1200, 320, 250, 60);
			       viewAll.setForeground(new Color(70, 140, 230));
			       viewAll.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			       viewAll.setBackground(new Color (212,175,55));
			       viewAll.setOpaque(false);
			       viewAll.setContentAreaFilled(false);
			       viewAll.setBorderPainted(false);
			       viewAll.setFocusPainted(false);
			       viewAll.setCursor(new Cursor(Cursor.HAND_CURSOR));
			       walet.add(viewAll);
			       
			       viewAll.addActionListener(e->{
			    	   transactionPage.refreshTrans();
			    	   cardLayout.show(pages, "TRANSACTIONS");
			       });
			       
			       transaction1 =new RoundedPanel();
			       transaction1.setLayout(new BorderLayout());
			       transaction1.setBounds(80, 380, 1350, 300);
			       
			       String[] columns = {
			    		    "Type",
			    		    "Description",
			    		    "Booking ID",
			    		    "Date",
			    		    "Amount",
			    		    "Status"
			    		};

			    		model = new DefaultTableModel(columns, 0) {
			    		    @Override
			    		    public boolean isCellEditable(int row, int column) {
			    		        return false;
			    		    }
			    		};

			    		transactionTable = new JTable(model);
			    		refreshWallet();
			    		
			    		transactionTable.setRowSelectionAllowed(false);
			    		transactionTable.setColumnSelectionAllowed(false);
			    		transactionTable.setCellSelectionEnabled(false);
			    		
			    		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

			    		renderer.setHorizontalAlignment(SwingConstants.CENTER);
			    		renderer.setVerticalAlignment(SwingConstants.CENTER);
			    		renderer.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

			    		int[] widths = {180, 250, 200, 350, 200, 170};

			    		transactionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			    		for (int i = 0; i < widths.length; i++) {
			    		    transactionTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
			    		    transactionTable.getColumnModel().getColumn(i).setWidth(widths[i]);
			    		    transactionTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
			    		}

			    		
			    		transactionTable.setRowHeight(62);
			    		transactionTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
			    		transactionTable.setForeground(Color.WHITE);
			    		transactionTable.setBackground(new Color(11, 35, 50));
			    		transactionTable.setGridColor(new Color(40, 60, 75));
			    		transactionTable.setShowVerticalLines(false);

			    		transactionTable.getTableHeader().setFont(
			    		        new Font("Times New Roman", Font.BOLD, 16)
			    		);

			    		transactionTable.getTableHeader().setForeground(
			    		        new Color(212, 175, 55)
			    		);

			    		transactionTable.getTableHeader().setBackground(
			    		        new Color(11, 22, 40)
			    		);

			    		transactionTable.getTableHeader().setPreferredSize(
			    		        new Dimension(0, 50)
			    		);

			    		scrollPane = new JScrollPane(transactionTable);

			    		scrollPane.setHorizontalScrollBarPolicy(
			    			    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
			    			);

			    			scrollPane.setVerticalScrollBarPolicy(
			    			    JScrollPane.VERTICAL_SCROLLBAR_NEVER
			    			);
			    		scrollPane.setBorder(
			    		    BorderFactory.createLineBorder(new Color(212, 175, 55))
			    		);

			    		scrollPane.getViewport().setBackground(
			    		    new Color(11, 35, 50)
			    		);

			    		transaction1.add(scrollPane);
			    		walet.add(transaction1);
			   
			    }
	}