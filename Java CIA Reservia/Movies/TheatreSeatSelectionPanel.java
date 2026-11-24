package cia;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class TheatreSeatSelectionPanel extends JPanel {

    private String movieName;
    private JPanel seatsPanel,pages;
    private CardLayout cardLayout;

    private Wallet walletPage;
    private transactionPage transactionPage;
    private String theatre;
    private String showTime;
    private ticket ticketPage;
    private JLabel animationLabel;
    private Timer paymentTimer;
    
    private JLabel selectedLabel;
    private int maxSeats;
    private int selectedSeats = 0;
    private long total;
    private java.util.List<String> selectedSeatNumbers =
            new java.util.ArrayList<>();

    private void processPayment() {

        if (selectedSeats != maxSeats) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select exactly " + maxSeats + " seat(s).",
                    "Seat Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int userId = session.userId;

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);

            String walletSQL =
                    "UPDATE wallet " +
                    "SET balance = balance - ? " +
                    "WHERE user_id = ? AND balance >= ?";

            PreparedStatement walletPs =
                    con.prepareStatement(walletSQL);

            walletPs.setLong(1, total);
            walletPs.setInt(2, userId);
            walletPs.setLong(3, total);

            int walletUpdated = walletPs.executeUpdate();

            walletPs.close();

            if (walletUpdated == 0) {

                con.rollback();

                JOptionPane.showMessageDialog(
                        this,
                        "Insufficient wallet balance!",
                        "Payment Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            String seatSQL =
                    "UPDATE movie_bookings " +
                    "SET status = 'BOOKED' " +
                    "WHERE movie_name = ? AND seat_number = ?";

            PreparedStatement seatPs =
                    con.prepareStatement(seatSQL);

            for (String seatNumber : selectedSeatNumbers) {

                seatPs.setString(1, movieName);
                seatPs.setString(2, seatNumber);

                seatPs.addBatch();
            }
            seatPs.executeBatch();
            seatPs.close();
            
            String bookingDetails =
                    "Seats: " + String.join(", ", selectedSeatNumbers);

            int bookingId = BookingManager.createBooking(
                    con,
                    userId,
                    "Movie",
                    movieName,
                    bookingDetails,
                    theatre,
                    java.sql.Date.valueOf(java.time.LocalDate.now()),
                    java.sql.Time.valueOf(
                            showTime.equalsIgnoreCase("4pm") ? "16:00:00" :
                            showTime.equalsIgnoreCase("11am") ? "11:00:00" :
                            showTime.equalsIgnoreCase("1pm") ? "13:00:00" :
                            showTime.equalsIgnoreCase("8pm") ? "20:00:00" :
                            showTime.equalsIgnoreCase("10pm") ? "22:00:00" :
                            showTime.equalsIgnoreCase("8am") ? "08:00:00" :
                            showTime.equalsIgnoreCase("10am") ? "10:00:00" :
                            "12:00:00"
                    ),
                    total
            );

            String transactionSQL =
                    "INSERT INTO transaction " +
                    "(user_id, transaction_type, description, booking_id, " +
                    "transaction_date, amount, status) " +
                    "VALUES (?, ?, ?, ?, NOW(), ?, ?)";

            PreparedStatement transactionPs =
                    con.prepareStatement(transactionSQL);

            transactionPs.setInt(1, userId);
            transactionPs.setString(2, "Movie");

            transactionPs.setString(
                    3,
                    "Movie Booking - " + movieName
            );

            transactionPs.setString(4, String.valueOf(bookingId));

            transactionPs.setLong(5, -total);

            transactionPs.setString(6, "Success");

            transactionPs.executeUpdate();

            transactionPs.close();

            con.commit();

            walletPage.refreshWallet();
            transactionPage.refreshTrans();

            showPaymentAnimation(
            		userId,
            	    bookingId,
            	    bookingDetails,
            	    total
            	);


          
        } catch (SQLException ex) {

            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Payment failed:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {

            try {

                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void showPaymentAnimation(int userId,
            int bookingId,
            String bookingDetails,
            long total) {

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

            showTicketAfterAnimation(userId,
                bookingId,
                bookingDetails,
                total
            );
        });

        paymentTimer.setRepeats(false);
        paymentTimer.start();
    }
    private void showTicketAfterAnimation(
    		int userId,
            int bookingId,
            String bookingDetails,
            long totalAmount) {

    	  ticketPage.showTicket(
                  userId,
                  bookingId,
                  "Movie",
                  movieName,
                  theatre,
                  "Seats: " + String.join(", ", selectedSeatNumbers),
                  java.time.LocalDate.now().toString(),
                  showTime,
                  total
          );

    }
    
    public TheatreSeatSelectionPanel(String movieName, int ticketsCount,long totalPrice,String theatre,String showTime,JPanel pages,CardLayout cardLayout,Wallet walletPage,transactionPage transactionPage,ticket ticketPage) {
        this.movieName = movieName;
        this.maxSeats = ticketsCount;
        this.pages = pages;
        this.cardLayout = cardLayout;
        this.walletPage = walletPage;
        this.transactionPage = transactionPage;
        this.total = totalPrice;
        this.theatre = theatre;
        this.showTime = showTime;
        this.ticketPage = ticketPage;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(22, 22, 26)); 

       
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(229, 9, 20)); 
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Select Cinema Seats");
        title.setFont(new Font("Times New Roman", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

        JLabel movieLabel = new JLabel("Movie: " + movieName);
        movieLabel.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        movieLabel.setForeground(Color.WHITE);

        header.add(title, BorderLayout.WEST);
        header.add(movieLabel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

       
        JPanel centerContainer = new JPanel(new BorderLayout(10, 20));
        centerContainer.setBackground(new Color(22, 22, 26));

        seatsPanel = new JPanel();
        seatsPanel.setLayout(new GridLayout(0, 15, 8, 8));
        seatsPanel.setBackground(new Color(22, 22, 26));
        seatsPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));


        loadSeats(movieName);
        
        JScrollPane scrollPane = new JScrollPane(seatsPanel);
        scrollPane.setBorder(null);
        centerContainer.add(scrollPane, BorderLayout.CENTER);


        JPanel screenPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        screenPanel.setBackground(new Color(22, 22, 26));
        JLabel screenLabel = new JLabel("___________________ CINEMA SCREEN THIS WAY ___________________");
        screenLabel.setFont(new Font("Arial", Font.BOLD, 14));
        screenLabel.setForeground(new Color(100, 110, 120));
        screenPanel.add(screenLabel);
        centerContainer.add(screenPanel, BorderLayout.SOUTH);

        add(centerContainer, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        selectedLabel = new JLabel("Selected Seats: 0 / " + maxSeats);
        selectedLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JButton payButton = new JButton("PAY");
        payButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        payButton.setBackground(new Color(229, 9, 20));
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);

        bottomPanel.add(selectedLabel, BorderLayout.WEST);
        bottomPanel.add(payButton, BorderLayout.EAST);

        payButton.addActionListener(e -> {
            processPayment();
        });

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadSeats(String movieName) {
        String sql = "SELECT seat_number, seat_type, status " +
                     "FROM movie_bookings " +
                     "WHERE movie_name = ? " +
                     "ORDER BY LEFT(seat_number, 1) DESC, CAST(SUBSTRING(seat_number, 2) AS UNSIGNED) ASC";

        boolean recordsFound = false;

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, movieName);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                recordsFound = true;
                String seatNumber = rs.getString("seat_number");
                String type = rs.getString("seat_type"); 
                String status = rs.getString("status");

                createSeatButton(seatNumber, type, status);
            }

            rs.close();
            pst.close();
            con.close();

            if (!recordsFound) {
                System.out.println("Warning: Seat metadata not loaded in DB for: " + movieName + ". Rendering generic template layout.");
                char[] rows = {'J','I','H','G','F','E','D','C','B','A'};
                for (char r : rows) {
                    for (int n = 1; n <= 15; n++) {
                        String seatId = r + "" + n;
                        String type = (r == 'I' || r == 'J') ? "RECLINER" : (r >= 'F') ? "PREMIUM" : "NORMAL";
                        createSeatButton(seatId, type, "AVAILABLE");
                    }
                }
            }

            seatsPanel.revalidate();
            seatsPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Unable to load movie seating schematic layout:\n" + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createSeatButton(String seatNumber, String type, String status) {

        JButton seatButton = new JButton(seatNumber);

        seatButton.setFont(new Font("Arial", Font.PLAIN, 10));
        seatButton.setFocusPainted(false);
        seatButton.setPreferredSize(new Dimension(50, 45));

        if (status.equalsIgnoreCase("BOOKED")) {

            seatButton.setBackground(new Color(200, 60, 60));
            seatButton.setForeground(Color.WHITE);
            seatButton.setEnabled(false);
            seatButton.setToolTipText("Occupied");

        } else {

            seatButton.setBackground(new Color(40, 140, 80));
            seatButton.setForeground(Color.WHITE);
            seatButton.setToolTipText(type);

            seatButton.addActionListener(e -> {

                Color availableColor = new Color(40, 140, 80);
                Color activeSelectedColor = new Color(60, 120, 200);

                if (seatButton.getBackground().equals(availableColor)) {

                    if (selectedSeats >= maxSeats) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Ticket limits reached. You can only choose up to "
                                + maxSeats + " seat(s)."
                        );

                        return;
                    }

                    seatButton.setBackground(activeSelectedColor);

                    selectedSeats++;

                    selectedSeatNumbers.add(seatNumber);

                } else {

                    seatButton.setBackground(availableColor);

                    selectedSeats--;

                    selectedSeatNumbers.remove(seatNumber);
                }

                selectedLabel.setText(
                        "Selected Seats: " + selectedSeats + " / " + maxSeats
                );
            });
        }

        seatsPanel.add(seatButton);
    }}