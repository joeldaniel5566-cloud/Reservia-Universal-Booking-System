package cia;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class flightSeatSelectionPane extends JPanel {

    private String flightNumber;
    private JPanel seatsPanel,pages;
    private JLabel selectedLabel;
    private int maxSeats;
    private int selectedSeats = 0;
    private long total;
    private java.util.List<String> selectedSeatNumbers =
            new java.util.ArrayList<>();
    private CardLayout cardLayout;
    private Wallet walletPage;
    private transactionPage transactionPage;
    private String flightName,from,to,departure,arrival,date;
    private JLabel animationLabel;
    private Timer paymentTimer;

    private int adults;
    private int children;

    private ticket ticketPage;
    
    private void makePayment(long total) {

        if (selectedSeats == 0) {
            JOptionPane.showMessageDialog(
                this,
                "Please select at least one seat."
            );
            return;
        }

        long totalAmount = total;

        String updateWallet =
            "UPDATE wallet " +
            "SET balance = balance - ? " +
            "WHERE user_id = ? AND balance >= ?";

        String insertTransaction =
            "INSERT INTO transaction " +
            "(user_id, transaction_type, description, booking_id, " +
            "transaction_date, amount, status) " +
            "VALUES (?, ?, ?, ?, NOW(), ?, ?)";

        String updateSeat =
        	    "UPDATE flightseats " +
        	    "SET status = 'BOOKED' " +
        	    "WHERE flight_number = ? " +
        	    "AND seat_number = ? " +
        	    "AND status = 'AVAILABLE'";
        
        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try (
                PreparedStatement walletStmt =
                    con.prepareStatement(updateWallet);

                PreparedStatement transactionStmt =
                    con.prepareStatement(insertTransaction);
                PreparedStatement seatStmt =
                    con.prepareStatement(updateSeat)
            ) {

                walletStmt.setLong(1, totalAmount);
                walletStmt.setInt(2, session.userId);
                walletStmt.setLong(3, totalAmount);

                int updated = walletStmt.executeUpdate();

                if (updated == 0) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Insufficient wallet balance."
                    );

                    con.rollback();
                    return;
                }
                
                String bookingDetails =
                	    " " + flightNumber +
                	    " • " + selectedSeatNumbers;
                
                int bookingId = BookingManager.createBooking(
                	    con,
                	    session.userId,
                	    "Flight",
                	    flightName,
                	    bookingDetails,
                	    from + " → " + to,
                	    java.sql.Date.valueOf(date),
                	    Time.valueOf(departure),
                	    totalAmount
                	);

                transactionStmt.setInt(1, session.userId);
                transactionStmt.setString(2, "Flight");
                transactionStmt.setString(
                    3,
                    "Flight Booking - " + flightNumber
                );	
                transactionStmt.setString(4,String.valueOf(bookingId));

                transactionStmt.setLong(5, -totalAmount);

                transactionStmt.setString(6, "Success");

                transactionStmt.executeUpdate();
                
                for (String seatNumber : selectedSeatNumbers) {

                    seatStmt.setString(1, flightNumber);
                    seatStmt.setString(2, seatNumber);

                    seatStmt.executeUpdate();
                }

                con.commit();
                
                walletPage.refreshWallet();
                transactionPage.refreshTrans();

                showPaymentAnimation(
                	    bookingId,
                	    bookingDetails,
                	    totalAmount
                	);
            }

        } catch (SQLException ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                "Payment failed:\n" + ex.getMessage(),
                "Payment Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showPaymentAnimation(
            int bookingId,
            String bookingDetails,
            long totalAmount) {

        removeAll();

        setLayout(new BorderLayout());
        setBackground(new Color(11, 22, 40));

        java.net.URL gifURL =
                getClass().getResource("/cia/payment_success/payment2.gif");

        ImageIcon icon = new ImageIcon(gifURL);

        animationLabel = new JLabel(icon);
        animationLabel.setHorizontalAlignment(
            SwingConstants.CENTER
        );
        animationLabel.setVerticalAlignment(
            SwingConstants.CENTER
        );

        add(animationLabel, BorderLayout.CENTER);

        revalidate();
        repaint();

        // Change this according to your GIF duration
        paymentTimer = new Timer(5000, e -> {

            paymentTimer.stop();

            showTicketAfterAnimation(
                bookingId,
                bookingDetails,
                totalAmount
            );
        });

        paymentTimer.setRepeats(false);
        paymentTimer.start();
    }
    private void showTicketAfterAnimation(
            int bookingId,
            String bookingDetails,
            long totalAmount) {

    	ticketPage.showTicket(
        	    session.userId,
        	    bookingId,
        	    "Flight",
        	    flightName,
        	    from + " → " + to,
        	    bookingDetails,
        	    date,
        	    departure + " - " + arrival,
        	    totalAmount
        	);
    }
    
    
    public flightSeatSelectionPane(
            String flightName,
            String flightNumber,
            String from,
            String to,
            String departure,
            String arrival,
            String date,
            int adults,
            int children,
            long totalp,
            JPanel pages,
            CardLayout cardLayout,
            Wallet walletPage,
            transactionPage transactionPage,
            ticket ticketPage) {

        this.flightName = flightName;
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;

        this.adults = adults;
        this.children = children;

        this.maxSeats = adults + children;

        this.total = totalp;

        this.pages = pages;
        this.cardLayout = cardLayout;
        this.walletPage = walletPage;
        this.transactionPage = transactionPage;
        this.ticketPage = ticketPage;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(240, 244, 248));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(18, 38, 58)); // Deep Aviation Navy Blue
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Select Aircraft Seats");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        JLabel flightLabel = new JLabel("Flight No: " + flightNumber);
        flightLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        flightLabel.setForeground(Color.WHITE);

        header.add(title, BorderLayout.WEST);
        header.add(flightLabel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        seatsPanel = new JPanel();
        seatsPanel.setLayout(new GridLayout(0, 7, 10, 12));
        seatsPanel.setBackground(new Color(240, 244, 248));
        seatsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        loadFlightSeats(flightNumber);

        JScrollPane scrollPane = new JScrollPane(seatsPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        selectedLabel = new JLabel("Selected Seats: 0 / " + maxSeats);
        selectedLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton continueButton = new JButton("PROCEED TO PAY");
        continueButton.setFont(new Font("Arial", Font.BOLD, 14));
        continueButton.setBackground(new Color(34,139,34));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);

        bottomPanel.add(selectedLabel, BorderLayout.WEST);
        bottomPanel.add(continueButton, BorderLayout.EAST);
        continueButton.addActionListener(e -> {
            makePayment(total);
        });
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadFlightSeats(String flightNumber) {
        String sql = "SELECT seat_number, seat_type, status FROM flightseats WHERE flight_number = ? ORDER BY seat_number";
        boolean recordsFound = false;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, flightNumber);
            ResultSet rs = pst.executeQuery();

            int seatCounter = 0;
            while (rs.next()) {
                recordsFound = true;
                String seatNumber = rs.getString("seat_number");
                String type = rs.getString("seat_type");
                String status = rs.getString("status");

                if (seatCounter % 6 == 3) {
                    addCenterAisleWalkway();
                }

                createFlightSeatButton(seatNumber, type, status);
                seatCounter++;
            }

            if (!recordsFound) {
                System.out.println("No DB seats found for " + flightNumber + ". Rendering default aircraft layout.");
                char[] columns = {'A', 'B', 'C', 'D', 'E', 'F'};
                for (int row = 1; row <= 10; row++) {
                    for (int col = 0; col < 6; col++) {
                        if (col == 3) {
                            addCenterAisleWalkway();
                        }
                        String seatId = row + "" + columns[col];
                        String type = (col == 0 || col == 5) ? "WINDOW" : (col == 1 || col == 4) ? "MIDDLE" : "AISLE";
                        createFlightSeatButton(seatId, type, "AVAILABLE");
                    }
                }
            }

            seatsPanel.revalidate();
            seatsPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            loadMockFallbackLayout();
        }
    }

    private void addCenterAisleWalkway() {
        JLabel aisleLabel = new JLabel("AISLE", SwingConstants.CENTER);
        aisleLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        aisleLabel.setForeground(new Color(140, 150, 160));
        seatsPanel.add(aisleLabel);
    }

    private void createFlightSeatButton(String seatNumber, String type, String status) {
        JButton seatButton = new JButton(seatNumber);
        seatButton.setFont(new Font("Arial", Font.BOLD, 11));
        seatButton.setFocusPainted(false);
        seatButton.setPreferredSize(new Dimension(65, 50));

        if (status.equalsIgnoreCase("BOOKED")) {
            seatButton.setBackground(new Color(180, 185, 190)); // Slate gray for locked cabin seats
            seatButton.setForeground(Color.WHITE);
            seatButton.setEnabled(false);
        } else {
            seatButton.setBackground(new Color(53, 114, 239)); // Deep sky blue for available flight cabin seats
            seatButton.setForeground(Color.WHITE);
            seatButton.setToolTipText(type);

            seatButton.addActionListener(e -> {
                Color available = new Color(53, 114, 239);
                Color selected = new Color(43, 203, 186); // Vibrant Teal for active booking choice

                if (seatButton.getBackground().equals(available)) {
                    if (selectedSeats >= maxSeats) {
                        JOptionPane.showMessageDialog(this, "You have already reached the maximum seat selection allowance of " + maxSeats);
                        return;
                    }
                    seatButton.setBackground(selected);
                    selectedSeats++;
                    selectedSeatNumbers.add(seatNumber);
                } else {
                    seatButton.setBackground(available);
                    selectedSeats--;
                    selectedSeatNumbers.remove(seatNumber);
                }
                selectedLabel.setText("Selected Seats: " + selectedSeats + " / " + maxSeats);
            });
        }
        seatsPanel.add(seatButton);
    }

    private void loadMockFallbackLayout() {
        seatsPanel.removeAll();
        char[] columns = {'A', 'B', 'C', 'D', 'E', 'F'};
        for (int row = 1; row <= 10; row++) {
            for (int col = 0; col < 6; col++) {
                if (col == 3) {
                    addCenterAisleWalkway();
                }
                createFlightSeatButton(row + "" + columns[col], "ECONOMY", "AVAILABLE");
            }
        }
        seatsPanel.revalidate();
        seatsPanel.repaint();
    }
}