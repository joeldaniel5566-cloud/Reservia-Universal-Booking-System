package cia;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class refund extends JPanel {

    JPanel pages,navbar,navbtn,logo,refunds;

    JLabel l1,title,subtitle;

    JButton back;
    
    CardLayout cardLayout;

    DefaultTableCellRenderer renderer,renderer1;
    
    DefaultTableModel model;
    
    
    
    JTable refundTable;
    
    JScrollPane scrollPane;
    public void refreshRefunds() {

        model.setRowCount(0);

        String sql =
            "SELECT r.refund_id, r.booking_id, " +
            "b.booking_type, b.booking_name, " +
            "r.refund_amount, r.refund_date, r.status " +
            "FROM refund r " +
            "JOIN bookings b " +
            "ON r.booking_id = b.booking_id " +
            "WHERE r.user_id = ? " +
            "ORDER BY r.refund_date DESC";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setInt(1, session.userId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int refundId =
                    rs.getInt("refund_id");

                int bookingId =
                    rs.getInt("booking_id");

                String type =
                    rs.getString("booking_type");

                String name =
                    rs.getString("booking_name");

                long amount =
                    rs.getLong("refund_amount");

                Timestamp refundDate =
                    rs.getTimestamp("refund_date");

                String status =
                    rs.getString("status");

                String dateText = "";

                if (refundDate != null) {
                    dateText =
                        new SimpleDateFormat(
                            "dd MMM yyyy, hh:mm a"
                        ).format(refundDate);
                }

                model.addRow(new Object[] {
                    refundId,
                    bookingId,
                    type,
                    name,
                    "₹" + amount,
                    dateText,
                    status
                });
            }

            rs.close();

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                "Unable to load refunds:\n" +
                ex.getMessage(),
                "Refund Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        if (visible && model != null) {
            refreshRefunds();
        }
    }
    
    public refund(JPanel pages,CardLayout cardLayout) {
    	this.pages=pages;
    	this.cardLayout=cardLayout;
    	setLayout(new BorderLayout());
 		setBackground(new Color(11,22,40));

        refunds = new JPanel();
        refunds.setLayout(null);
        refunds.setBackground(new Color(11, 22, 40));

        title = new JLabel("Refunds");

        title.setBounds(500, 18, 500, 50);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman", Font.BOLD, 38));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        refunds.add(title);
        
        subtitle = new JLabel("Your complete refund history");

        subtitle.setBounds(500, 60, 500, 35);
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Times New Roman",Font.PLAIN,15));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);

        refunds.add(subtitle);

        back = new JButton("← Back");

        back.setBounds(80, 40, 100, 40);
        back.setForeground(new Color(212, 175, 55));
        back.setBackground(new Color(11, 22, 40));
        back.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);

        refunds.add(back);

        String[] columns = {
                "Refund ID",
                "Booking ID",
                "Type",
                "Name",
                "Refund Amount",
                "Refund Date",
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

        refundTable = new JTable(model);
        refreshRefunds();
        
        refundTable.setRowHeight(60);

        refundTable.setFont(new Font("Times New Roman",Font.PLAIN,16));

        refundTable.setForeground(Color.WHITE);

        refundTable.setBackground(new Color(11, 35, 50));

        refundTable.setGridColor(new Color(40, 60, 75));

        refundTable.setShowVerticalLines(false);

        refundTable.setShowHorizontalLines(true);

        refundTable.setSelectionBackground(new Color(11, 35, 50));

        refundTable.setSelectionForeground(Color.WHITE);

        refundTable.setRowSelectionAllowed(false);

        refundTable.getTableHeader().setFont(new Font("Times New Roman",Font.BOLD,16));

        refundTable.getTableHeader().setForeground(new Color(212, 175, 55));

        refundTable.getTableHeader().setBackground(new Color(11, 22, 40));

        refundTable.getTableHeader().setPreferredSize(new Dimension(0, 55));


        int[] widths = {
        		150,
                150,
                180,
                300,
                180,
                250,
                150
        };


        for (int i = 0; i < widths.length; i++) {
        	refundTable
                    .getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(widths[i]);
        }

        for (int i = 0; i < columns.length; i++) {
            refundTable
                    .getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(renderer);
        }

        scrollPane = new JScrollPane(refundTable);

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
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );


        refunds.add(scrollPane);
        
        back.addActionListener(e->{
        	cardLayout.show(pages,"WALLET");
        });


        add(
                refunds,
                BorderLayout.CENTER
        );
    }
}
