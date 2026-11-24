package cia;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SeatSelectionPanel extends JPanel {

    private String trainNumber,trainName,from,to,journeyDate,departure,arrival;
    private JPanel seatsPanel,pages;
    private int adults,children;
    private JLabel selectedLabel;
    private int maxSeats;
    private long total;
    private int selectedSeats = 0;
    private java.util.List<Integer> selectedSeatNumbers =
            new java.util.ArrayList<>();
    private CardLayout cardLayout;
    private Wallet walletPage;
    private transactionPage transactionPage;
    private ticket ticketPage;
    private JLabel animationLabel;
    private Timer paymentTimer;

    
    private void makePayment(long total) {

        if (selectedSeats != maxSeats) {
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
        	    "UPDATE trainseats " +
        	    "SET status = 'BOOKED' " +
        	    "WHERE train_number = ? AND seat_number = ?";
        
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
                        " " + trainNumber +
                        " • " + selectedSeatNumbers ;

                int bookingId = BookingManager.createBooking(
                        con,
                        session.userId,
                        "Train",
                        trainName,
                        bookingDetails,
                        from + " → " + to,
                        java.sql.Date.valueOf(journeyDate),
                        Time.valueOf(departure),
                        totalAmount
                );

                transactionStmt.setInt(1, session.userId);
                transactionStmt.setString(2, "Train");
                transactionStmt.setString(
                    3,
                    "Train Booking - " + trainNumber
                );	
                transactionStmt.setString(4, String.valueOf(bookingId));

                transactionStmt.setLong(5, -totalAmount);

                transactionStmt.setString(6, "Success");

                transactionStmt.executeUpdate();
                
                for (int seatNumber : selectedSeatNumbers) {

                    seatStmt.setString(1, trainNumber);
                    seatStmt.setInt(2, seatNumber);

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
                 "Train",
                 trainName,
                 from + " → " + to,
                 "Train No: " + trainNumber +
                 " • Seats: " + selectedSeatNumbers,
                 journeyDate,
                 departure + " - " + arrival,
                 totalAmount
         );
    }


    public SeatSelectionPanel(String trainName,String trainNumber,String from,String to,String journeyDate,String departure,String arrival,int adults,int children,int passengers,long totalp,JPanel pages,CardLayout cardLayout,Wallet walletPage,transactionPage transactionPage,ticket ticketPage) {
    	this.trainName = trainName;
    	this.trainNumber = trainNumber;
    	this.from = from;
    	this.to = to;
    	this.journeyDate = journeyDate;
    	this.departure = departure;
    	this.arrival = arrival;

    	this.adults = adults;
    	this.children = children;

    	this.maxSeats = passengers;
    	this.total = totalp;

    	this.pages = pages;
    	this.cardLayout = cardLayout;
    	this.walletPage = walletPage;
    	this.transactionPage = transactionPage;
    	this.ticketPage = ticketPage;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 245, 245));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(74, 44, 42));
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Select Your Seats");
        title.setFont(new Font("Times New Roman", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

        JLabel trainLabel = new JLabel("Train No: " + trainNumber);
        trainLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        trainLabel.setForeground(Color.WHITE);

        header.add(title, BorderLayout.WEST);
        header.add(trainLabel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        seatsPanel = new JPanel();
        seatsPanel.setLayout(new GridLayout(0, 4, 15, 15));
        seatsPanel.setBackground(new Color(245, 245, 245));
        seatsPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        loadSeats(trainNumber);

        JScrollPane scrollPane = new JScrollPane(seatsPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        selectedLabel = new JLabel(
            "Selected Seats: 0 / " + maxSeats
        );

        selectedLabel.setFont(
            new Font("Times New Roman", Font.BOLD, 18)
        );

        JButton continueButton = new JButton("PROCEED TO PAY");
        continueButton.setFont(new Font("Arial", Font.BOLD, 14));
        continueButton.setBackground(new Color(34, 139, 34));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        
        
        bottomPanel.add(selectedLabel, BorderLayout.WEST);
        bottomPanel.add(continueButton, BorderLayout.EAST);
        continueButton.addActionListener(e -> {
            makePayment(total);
        });

        add(bottomPanel, BorderLayout.SOUTH);
    }

    
    
    private void loadSeats(String trainNumber) {

        String sql =
            "SELECT seat_number, coach, seat_type, status " +
            "FROM trainseats " +
            "WHERE train_number = ? " +
            "ORDER BY seat_number";

        try {
            Connection con =DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, trainNumber);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int seatNumber = rs.getInt("seat_number");
                String coach = rs.getString("coach");
                String type = rs.getString("seat_type");
                String status = rs.getString("status");

                JButton seatButton = new JButton(
                    coach + "-" + seatNumber
                );

                seatButton.setFont(
                    new Font("Arial", Font.BOLD, 14)
                );

                seatButton.setFocusPainted(false);

                seatButton.setPreferredSize(
                    new Dimension(100, 55)
                );

                if (status.equalsIgnoreCase("BOOKED")) {

                    seatButton.setBackground(
                        new Color(200, 60, 60)
                    );

                    seatButton.setForeground(Color.WHITE);
                    seatButton.setEnabled(false);

                    seatButton.setToolTipText(
                        "Booked"
                    );

                } else {

                    seatButton.setBackground(
                        new Color(100, 180, 100)
                    );

                    seatButton.setForeground(Color.WHITE);

                    seatButton.setToolTipText(type);

                    seatButton.addActionListener(e -> {

                        Color available =
                            new Color(100, 180, 100);

                        Color selected =
                            new Color(60, 120, 200);

                        if (seatButton.getBackground()
                                .equals(available)) {

                            if (selectedSeats >= maxSeats) {

                                JOptionPane.showMessageDialog(
                                    this,
                                    "You can select only " +
                                    maxSeats + " seat(s)."
                                );

                                return;
                            }

                            seatButton.setBackground(selected);
                            selectedSeats++;
                            selectedSeatNumbers.add(seatNumber);

                        } else {

                            seatButton.setBackground(available);
                            selectedSeats--;
                            selectedSeatNumbers.remove(
                                    Integer.valueOf(seatNumber)
                                );
                        }

                        selectedLabel.setText(
                            "Selected Seats: " +
                            selectedSeats +
                            " / " +
                            maxSeats
                        );
                    });
                }

                seatsPanel.add(seatButton);
            }

            rs.close();
            pst.close();
            con.close();

            seatsPanel.revalidate();
            seatsPanel.repaint();

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                "Unable to load seats from database:\n" +
                e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}