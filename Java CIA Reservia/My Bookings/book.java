package cia;
import java.awt.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Dimension;

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
        setBorderPainted(false);
        setFocusPainted(false);
        setBackground(new Color(11, 35, 50));
        setForeground(Color.WHITE);
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        setText(value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {

    JButton button;
    String text;
    int row;
    book bookingPage;
    public ButtonEditor(book bookingPage) {
        super(new JCheckBox());

        this.bookingPage = bookingPage;

        button = new JButton();
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(new Color(11, 35, 50));
        button.setForeground(Color.WHITE);

        button.addActionListener(e -> {

            fireEditingStopped();

            if (text.equals("View Ticket")) {

                bookingPage.showTicket(row);

            } else if (text.equals("Cancel")) {
                bookingPage.cancelBooking(row);
            }
        });
    }
    	
    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected,
            int row, int column) {
    	this.row = row;
        text = value.toString();
        button.setText(text);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }
}

public class book extends JPanel{

    JLabel title, sub;
    JButton back;
    private JPanel pages;
    private CardLayout cardLayout;
    private ticket ticketPage;
    private Wallet walletPage;
    private DefaultTableModel model;
    private JTable table;

    public book(JPanel pages, CardLayout cardLayout, ticket ticketPage,Wallet walletPage) {
    	this.pages = pages;
    	this.cardLayout = cardLayout;
        this.ticketPage = ticketPage;
        this.walletPage=walletPage;
    	
    	setLayout(null);
    	setBackground(new Color(11, 22, 40));

        back = new JButton("← Back");
        back.setBounds(45, 40, 100, 40);
        back.setForeground(new Color(212, 175, 55));
        back.setBackground(new Color(11, 22, 40));
        back.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.setFocusPainted(false);
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
		add(back);
		back.addActionListener(e -> {
		    cardLayout.show(pages, "HOME");
		});
		
        title = new JLabel("My Bookings");
        title.setBounds(500, 18, 500, 50);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman", Font.BOLD, 38));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        sub = new JLabel("Manage your bookings");
        sub.setBounds(500, 60, 500, 35);
        sub.setForeground(Color.WHITE);
        sub.setFont(new Font("Times New Roman",Font.PLAIN,15));
        sub.setHorizontalAlignment(SwingConstants.CENTER);
        add(sub);

        String[] columns = {
            "Booking ID","Booking Type", "Name", "Booking Details", "Date",
            "Total Amount", "Booking Status", "Tickets", "Cancellation"
        };

        model = new DefaultTableModel(
        	    new Object[][] {},
        	    columns
        	) {
        	    @Override
        	    public boolean isCellEditable(
        	            int row,
        	            int column) {

        	        return column == 7 || column == 8;
        	    }
        	};

        table = new JTable(model);
        	
        table.getTableHeader().setBackground(new Color(212, 175, 55));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setPreferredSize(new Dimension(0, 55));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Booking ID
        table.getColumnModel().getColumn(1).setPreferredWidth(140);
        table.getColumnModel().getColumn(2).setPreferredWidth(160);
        table.getColumnModel().getColumn(3).setPreferredWidth(240);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(140);
        table.getColumnModel().getColumn(6).setPreferredWidth(170);
        table.getColumnModel().getColumn(7).setPreferredWidth(180);
        table.getColumnModel().getColumn(8).setPreferredWidth(180);
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBounds(55, 120, 1400, 600);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());

        table.setBorder(BorderFactory.createEmptyBorder());
        table.setShowGrid(false);

        scrollPane.setBackground(new Color(11, 22, 40));
        scrollPane.getViewport().setBackground(new Color(11, 35, 50));

        add(scrollPane);

        table.setBackground(new Color(11, 35, 50));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(50, 70, 90));
        table.setBorder(null);

        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);

        table.setRowHeight(80);

        table.setRowSelectionAllowed(false);

        table.setSelectionBackground(new Color(11, 22, 40));
        table.setSelectionForeground(Color.WHITE);

        table.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);

        table.getColumn("Tickets").setCellRenderer(new ButtonRenderer());
        table.getColumn("Tickets").setCellEditor(new ButtonEditor(this));

        table.getColumn("Cancellation").setCellRenderer(new ButtonRenderer());
        table.getColumn("Cancellation").setCellEditor(new ButtonEditor(this));
        
        refreshBookings();

    }
    public void showTicket(int row) {

        int bookingId =
                Integer.parseInt(
                        table.getValueAt(row, 0)
                                .toString()
                                .replace("#", "")
                );

        String sql =
                "SELECT booking_type, booking_name, " +
                "booking_details, location, booking_date, " +
                "booking_time, amount " +
                "FROM bookings " +
                "WHERE booking_id = ? " +
                "AND user_id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement pst =
                        con.prepareStatement(sql)
        ) {

            pst.setInt(1, bookingId);
            pst.setInt(2, session.userId);

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {

                String type =
                        rs.getString("booking_type");

                String name =
                        rs.getString("booking_name");

                String details =
                        rs.getString("booking_details");

                String location =
                        rs.getString("location");

                Date date =
                        rs.getDate("booking_date");

                Time time =
                        rs.getTime("booking_time");

                long amount =
                        rs.getLong("amount");


                String dateText =
                        date != null
                        ? date.toString()
                        : "";


                String timeText =
                        time != null
                        ? time.toString()
                        : "";


                ticketPage.showTicket(

                        session.userId,

                        bookingId,

                        type,

                        name,

                        location,

                        details,

                        dateText,

                        timeText,

                        amount
                );
            }

            rs.close();

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load ticket:\n"
                    + ex.getMessage(),
                    "Ticket Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    public void refreshBookings() {

        model.setRowCount(0);

        String sql =
            "SELECT booking_id, booking_type, booking_name, " +
            "booking_details, booking_date, amount, status " +
            "FROM bookings " +
            "WHERE user_id = ? " +
            "AND status = 'Confirmed' " +
            "ORDER BY booking_id DESC";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql)
        ) {

            pst.setInt(1, session.userId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int bookingId =
                        rs.getInt("booking_id");

                String type =
                        rs.getString("booking_type");

                String name =
                        rs.getString("booking_name");

                String details =
                        rs.getString("booking_details");

                Date date =
                        rs.getDate("booking_date");

                long amount =
                        rs.getLong("amount");

                String status =
                        rs.getString("status");

                String dateText = "";

                if (date != null) {
                	dateText =
                	        new SimpleDateFormat("dd MMM yyyy")
                	            .format(date);                }

                model.addRow(new Object[] {

                    String.valueOf(bookingId),

                    type,

                    name,

                    details,

                    dateText,

                    "₹" + amount,

                    status,

                    "View Ticket",

                    "Cancel"
                });
            }

            rs.close();

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                "Unable to load bookings:\n"
                + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    public void cancelBooking(int row) {

        int bookingId = Integer.parseInt(
            table.getValueAt(row, 0).toString()
        );

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to cancel Booking ID "
            + bookingId + "?",
            "Cancel Booking",
            JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        String getBooking =
            "SELECT amount, status,booking_type,booking_name,booking_details " +
            "FROM bookings " +
            "WHERE booking_id = ? " +
            "AND user_id = ?";

        String updateWallet =
            "UPDATE wallet " +
            "SET balance = balance + ? " +
            "WHERE user_id = ?";

        String updateBooking =
            "UPDATE bookings " +
            "SET status = 'Cancelled' " +
            "WHERE booking_id = ? " +
            "AND user_id = ? " +
            "AND status = 'Confirmed'";

        String insertRefund =
            "INSERT INTO refund " +
            "(user_id, booking_id, refund_amount, status) " +
            "VALUES (?, ?, ?, ?)";

        String insertTransaction =
            "INSERT INTO transaction " +
            "(user_id, transaction_type, description, booking_id, " +
            "transaction_date, amount, status) " +
            "VALUES (?, ?, ?, ?, NOW(), ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try (
                PreparedStatement getStmt =
                    con.prepareStatement(getBooking);

                PreparedStatement walletStmt =
                    con.prepareStatement(updateWallet);

                PreparedStatement bookingStmt =
                    con.prepareStatement(updateBooking);

                PreparedStatement refundStmt =
                    con.prepareStatement(insertRefund);

                PreparedStatement transactionStmt =
                    con.prepareStatement(insertTransaction)
            ) {
            	
                getStmt.setInt(1, bookingId);
                getStmt.setInt(2, session.userId);

                ResultSet rs = getStmt.executeQuery();

                if (!rs.next()) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Booking not found."
                    );

                    con.rollback();
                    return;
                }

                long amount = rs.getLong("amount");
                String status = rs.getString("status");
                String bookingType = rs.getString("booking_type");
                String bookingDetails = rs.getString("booking_details");
                String bookingName=rs.getString("booking_name");
                
                rs.close();

                if (!"Confirmed".equalsIgnoreCase(status)) {

                    JOptionPane.showMessageDialog(
                        this,
                        "This booking cannot be cancelled.\n" +
                        "Current status: " + status
                    );

                    con.rollback();
                    return;
                }

                walletStmt.setLong(1, amount);
                walletStmt.setInt(2, session.userId);

                int walletUpdated = walletStmt.executeUpdate();

                if (walletUpdated == 0) {

                    con.rollback();

                    JOptionPane.showMessageDialog(
                        this,
                        "Unable to update wallet."
                    );

                    return;
                }

                bookingStmt.setInt(1, bookingId);
                bookingStmt.setInt(2, session.userId);

                int bookingUpdated =
                    bookingStmt.executeUpdate();

                if (bookingUpdated == 0) {

                    con.rollback();

                    JOptionPane.showMessageDialog(
                        this,
                        "Booking could not be cancelled."
                    );

                    return;
                }
                
                releaseResource(con, bookingId, bookingType,bookingName,bookingDetails);

                refundStmt.setInt(1, session.userId);
                refundStmt.setInt(2, bookingId);
                refundStmt.setLong(3, amount);
                refundStmt.setString(4, "Refunded");

                refundStmt.executeUpdate();

                transactionStmt.setInt(1, session.userId);
                transactionStmt.setString(2, "Refund");

                transactionStmt.setString(
                    3,
                    "Refund for Booking - " + bookingId
                );

                transactionStmt.setString(
                    4,
                    String.valueOf(bookingId)
                );

                transactionStmt.setLong(5, amount);
                transactionStmt.setString(6, "Success");

                transactionStmt.executeUpdate();
                
                con.commit();

                refreshBookings();
                walletPage.refreshWallet();

                

                JOptionPane.showMessageDialog(
                    this,
                    "Booking cancelled successfully!\n" +
                    "Refund Amount: ₹" + amount +
                    "\nAmount has been added to your wallet.",
                    "Refund Successful",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                "Cancellation failed:\n" +
                ex.getMessage(),
                "Refund Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void releaseResource(
            Connection con,
            int bookingId,
            String bookingType,
            String bookingName,
            String bookingDetails) throws SQLException {

        if (bookingType == null) {
            return;
        }

        if (bookingType.equalsIgnoreCase("Bus")) {
        	try {
        		String[] parts = bookingDetails.split("•");

                String busNumber = parts[0].trim();

                String seatsPart = parts[1]
                         .replace("[", "")
                         .replace("]", "")
                         .trim();

                String[] seats = seatsPart.split(",");

                String sql =
	                "UPDATE busseats " +
	                "SET status = 'AVAILABLE' " +
	                "WHERE bus_number = ?"+
	                "and seat_number = ?";

            try (PreparedStatement pst =
                    con.prepareStatement(sql)) {
            	 for (String seat : seats) {

                     pst.setString(1, busNumber);
                     pst.setInt(2, Integer.parseInt(seat.trim()));

                     pst.executeUpdate();
                 }
             }
        	}catch (Exception e) {

                throw new SQLException(
                    "Unable to release bus seats.",
                    e
                );
            }
        }

        else if (bookingType.equalsIgnoreCase("Train")) {

            try {
                String[] parts = bookingDetails.split("•");

                String trainNumber = parts[0].trim();

                String seatsPart = parts[1]
                        .replace("[", "")
                        .replace("]", "")
                        .trim();

                String[] seats = seatsPart.split(",");

                String sql =
                    "UPDATE trainseats " +
                    "SET status = 'AVAILABLE' " +
                    "WHERE train_number = ? " +
                    "AND seat_number = ?";

                try (PreparedStatement pst =
                        con.prepareStatement(sql)) {

                    for (String seat : seats) {

                        pst.setString(1, trainNumber);
                        pst.setInt(2, Integer.parseInt(seat.trim()));

                        pst.executeUpdate();
                    }
                }

            } catch (Exception e) {

                throw new SQLException(
                    "Unable to release train seats.",
                    e
                );
            }
        }

        else if (bookingType.equalsIgnoreCase("Flight")) {

        	  try {
                  String[] parts = bookingDetails.split("•");

                  String flightNumber = parts[0].trim();

                  String seatsPart = parts[1]
                          .replace("[", "")
                          .replace("]", "")
                          .trim();

                  String[] seats = seatsPart.split(",");

                  String sql =
                      "UPDATE flightseats " +
                      "SET status = 'AVAILABLE' " +
                      "WHERE flight_number = ? " +
                      "AND seat_number = ?";

                  try (PreparedStatement pst =
                          con.prepareStatement(sql)) {

                      for (String seat : seats) {

                          pst.setString(1, flightNumber);
                          pst.setString(2, seat.trim());

                          pst.executeUpdate();
                      }
                  }

              } catch (Exception e) {

                  throw new SQLException(
                      "Unable to release flight seats.",
                      e
                  );
              }
          }
        
    
        else if (bookingType.equalsIgnoreCase("Movie")) {

        	 try {
        	        String seatsPart = bookingDetails
        	                .replace("Seats:", "")
        	                .trim();

        	        String[] seats = seatsPart.split(",");

        	        String sql =
        	            "UPDATE movie_bookings " +
        	            "SET status = 'AVAILABLE' " +
        	            "WHERE movie_name = ? " +
        	            "AND seat_number = ?";

        	        try (PreparedStatement pst =
        	                con.prepareStatement(sql)) {

        	            for (String seat : seats) {

        	                pst.setString(1, bookingName);
        	                pst.setString(2, seat.trim());

        	                pst.executeUpdate();
        	            }
        	        }

        	    } catch (Exception e) {

        	        throw new SQLException(
        	            "Unable to release movie seats.",
        	            e
        	        );
        	    }
        }
        else if (bookingType.equalsIgnoreCase("Hotel")) {

            try {

                String roomPart = bookingDetails
                        .split("•")[0]
                        .trim();

                String roomNumber = roomPart
                        .replace("Room", "")
                        .trim();

                String sql =
                    "UPDATE hotelrooms4 r " +
                    "JOIN hoteldetails5 h " +
                    "ON r.hotel_id = h.hotel_id " +
                    "SET r.availability = 'Available' " +
                    "WHERE h.hotel_name = ? " +
                    "AND r.room_number = ?";

                try (PreparedStatement pst =
                        con.prepareStatement(sql)) {

                    pst.setString(1, bookingName);
                    pst.setString(2, roomNumber);

                    pst.executeUpdate();
                }

            } catch (Exception e) {

                throw new SQLException(
                    "Unable to release hotel room.",
                    e
                );
            }
        }
        else if (bookingType.equalsIgnoreCase("Restaurant")) {
            try {
                String tablePart = bookingDetails
                        .split("•")[0]
                        .trim();

                String tableNumber = tablePart
                        .replace("Table", "")
                        .trim();

                String updateTable =
                    "UPDATE restauranttables r " +
                    "JOIN restaurantdetails d " +
                    "ON r.restaurant_id = d.restaurant_id " +
                    "SET r.availability = 'Available' " +
                    "WHERE d.restaurant_name = ? " +
                    "AND r.table_number = ?";

                try (PreparedStatement pst =
                        con.prepareStatement(updateTable)) {

                    pst.setString(1, bookingName);
                    pst.setString(2, tableNumber);

                    int updated = pst.executeUpdate();

                    if (updated == 0) {
                        throw new SQLException(
                            "Restaurant table not found.\n" +
                            "Restaurant: " + bookingName + "\n" +
                            "Table: " + tableNumber
                        );
                    }
                }

                String cancelRestaurantBooking =
                    "UPDATE restaurantbookings rb " +
                    "JOIN restaurantdetails d " +
                    "ON rb.restaurant_id = d.restaurant_id " +
                    "JOIN restauranttables rt " +
                    "ON rb.table_id = rt.table_id " +
                    "SET rb.booking_status = 'CANCELLED' " +
                    "WHERE d.restaurant_name = ? " +
                    "AND rt.table_number = ? " +
                    "AND rb.booking_status = 'CONFIRMED'";

                try (PreparedStatement pst =
                        con.prepareStatement(cancelRestaurantBooking)) {

                    pst.setString(1, bookingName);
                    pst.setString(2, tableNumber);

                    pst.executeUpdate();
                }

            } catch (Exception e) {

                throw new SQLException(
                    "Unable to release restaurant table.\n" +
                    "Restaurant: " + bookingName + "\n" +
                    "Booking Details: " + bookingDetails + "\n" +
                    "Error: " + e.getMessage(),
                    e
                );
            }
        }
    }
}