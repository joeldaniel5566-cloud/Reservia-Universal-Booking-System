package cia;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class transactionPage extends JPanel {

    JPanel pages,navbar,navbtn,logo,transactions;

    JLabel l1,title,subtitle;

    JButton back;
    
    CardLayout cardLayout;

    DefaultTableCellRenderer renderer,renderer1;
    
    DefaultTableModel model;
    
    
    
    JTable transactionTable;
    
    JScrollPane scrollPane;
    public void refreshTrans() {

	    model.setRowCount(0);

	    for (Object[] row : WalletData.getTransactions(session.userId)) {
	        model.addRow(row);
	    }
	}

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if (visible && model != null) {
            refreshTrans();
        }
    }
    
    public transactionPage(JPanel pages,CardLayout cardLayout) {
    	this.pages=pages;
    	this.cardLayout=cardLayout;
    	setLayout(new BorderLayout());
 		setBackground(new Color(11,22,40));

        transactions = new JPanel();
        transactions.setLayout(null);
        transactions.setBackground(new Color(11, 22, 40));

        title = new JLabel("Transactions");

        title.setBounds(500, 18, 500, 50);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman", Font.BOLD, 38));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        transactions.add(title);
        
        subtitle = new JLabel("Your complete transaction history");

        subtitle.setBounds(500, 60, 500, 35);
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Times New Roman",Font.PLAIN,15));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);

        transactions.add(subtitle);

        back = new JButton("← Back");

        back.setBounds(80, 40, 100, 40);
        back.setForeground(new Color(212, 175, 55));
        back.setBackground(new Color(11, 22, 40));
        back.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);

        transactions.add(back);

        String[] columns = {
                "Type",
                "Description",
                "Booking ID",
                "Date",
                "Amount",
                "Status"
        };

        model =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };
                renderer = new DefaultTableCellRenderer();

	    		renderer.setHorizontalAlignment(SwingConstants.CENTER);
	    		renderer.setVerticalAlignment(SwingConstants.CENTER);
	    		renderer.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        transactionTable = new JTable(model);
        refreshTrans();
        
        transactionTable.setRowHeight(60);

        transactionTable.setFont(new Font("Times New Roman",Font.PLAIN,16));

        transactionTable.setForeground(Color.WHITE);

        transactionTable.setBackground(new Color(11, 35, 50));

        transactionTable.setGridColor(new Color(40, 60, 75));

        transactionTable.setShowVerticalLines(false);

        transactionTable.setShowHorizontalLines(true);

        transactionTable.setSelectionBackground(new Color(11, 35, 50));

        transactionTable.setSelectionForeground(Color.WHITE);

        transactionTable.setRowSelectionAllowed(false);

        transactionTable.getTableHeader().setFont(new Font("Times New Roman",Font.BOLD,16));

        transactionTable.getTableHeader().setForeground(new Color(212, 175, 55));

        transactionTable.getTableHeader().setBackground(new Color(11, 22, 40));

        transactionTable.getTableHeader().setPreferredSize(new Dimension(0, 55));


        int[] widths = {
                150,
                260,
                180,
                300,
                180,
                150
        };


        for (int i = 0; i < widths.length; i++) {
        	transactionTable
                    .getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(widths[i]);
        }


        renderer1 = new DefaultTableCellRenderer();

        renderer1.setForeground(Color.WHITE);

        renderer1.setBackground(new Color(11, 35, 50));

        renderer1.setFont(new Font("Times New Roman",Font.PLAIN,16));

        renderer1.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));


        for (int i = 0; i < columns.length; i++) {
            transactionTable
                    .getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(renderer);
        }

        scrollPane = new JScrollPane(transactionTable);

        scrollPane.setBounds(
                80, 120,
                1350, 600
        );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(212, 175, 55)
                )
        );

        scrollPane.getViewport().setBackground(
                new Color(11, 35, 50)
        );

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_NEVER
        );


        transactions.add(scrollPane);
        
        back.addActionListener(e->{
        	cardLayout.show(pages,"WALLET");
        });


        add(
                transactions,
                BorderLayout.CENTER
        );
    }
}
