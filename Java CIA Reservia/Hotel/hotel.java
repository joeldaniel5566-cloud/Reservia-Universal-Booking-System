package cia;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class hotel extends JPanel {

    private final Color NAVY=new Color(11,22,40);
    private final Color PANEL=new Color(17,31,53);
    private final Color BLUE=new Color(20,80,180);
    private final Color GOLD=new Color(212,175,55);

    private JComboBox<String> destinationBox;
    private JSpinner checkInSpinner,checkOutSpinner,adultSpinner,childSpinner;
    private JLabel nightsLabel;
    private JPanel resultsPanel,pages;
    private CardLayout cardLayout;
    private Wallet walletPage;
    private transactionPage transactionPage;
    private ticket ticketPage;
    private JLabel animationLabel;
    private Timer paymentTimer;

    
    class RoundedButton2 extends JButton {
        private int radius=20;

        public RoundedButton2(String text) {
            super(text);
            setContentAreaFilled(false);
            setOpaque(false);
            setFocusPainted(false);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            if(getModel().isPressed())
                g2.setColor(getBackground().darker());
            else if(getModel().isRollover())
                g2.setColor(getBackground().brighter());
            else
                g2.setColor(getBackground());

            g2.fillRoundRect(
                    0,0,getWidth(),getHeight(),
                    radius,radius
            );
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public hotel(JPanel pages,CardLayout cardLayout,Wallet walletPage,transactionPage transactionPage,ticket ticketPage) {
    	this.pages = pages;
        this.cardLayout = cardLayout;
        this.walletPage = walletPage;
        this.transactionPage = transactionPage;
        this.ticketPage = ticketPage;
        
    	setLayout(new BorderLayout());

        JPanel hotelPanel=new JPanel();
        hotelPanel.setBackground(NAVY);
        hotelPanel.setLayout(null);

        JLabel titleIcon=new JLabel("🏨");
        titleIcon.setBounds(25,18,100,60);
        titleIcon.setForeground(GOLD);
        titleIcon.setFont(new Font("Segoe UI Emoji",Font.PLAIN,45));
        titleIcon.setHorizontalAlignment(SwingConstants.CENTER);
        hotelPanel.add(titleIcon);

        JLabel title=new JLabel("Hotel Booking");
        title.setBounds(60,10,300,50);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman",Font.BOLD,30));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        hotelPanel.add(title);

        JLabel subtitle=new JLabel("Search hotels, compare rooms and book your stay");
        subtitle.setBounds(55,31,430,60);
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Times New Roman",Font.PLAIN,15));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        hotelPanel.add(subtitle);

        JButton myBookings=new JButton("My Bookings");
        myBookings.setBounds(1300,30,200,40);
        myBookings.setForeground(GOLD);
        myBookings.setFont(new Font("Times New Roman",Font.PLAIN,20));
        myBookings.setFocusPainted(false);
        myBookings.setBorderPainted(false);
        myBookings.setContentAreaFilled(false);
        myBookings.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hotelPanel.add(myBookings);

        RoundedPanel search=new RoundedPanel();
        search.setBounds(30,100,1450,250);
        search.setLayout(null);
        search.setBackground(PANEL);
        hotelPanel.add(search);

        String[] destinations={"Mumbai","Pune","Delhi","Chennai","Madikeri"};
        destinationBox=new JComboBox<>(destinations);
        destinationBox.setSelectedItem("Mumbai");
        styleComboBox(destinationBox);
        JPanel destinationPanel=createComboPanel("Destination",destinationBox);
        destinationPanel.setBounds(50,35,280,75);
        search.add(destinationPanel);

        JLabel checkInLabel=new JLabel("Check-in Date");
        checkInLabel.setBounds(420,40,220,25);
        checkInLabel.setForeground(Color.WHITE);
        checkInLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        search.add(checkInLabel);

        SpinnerDateModel checkInModel=new SpinnerDateModel(new Date(),null,null,java.util.Calendar.DAY_OF_MONTH);
        checkInSpinner=new JSpinner(checkInModel);
        checkInSpinner.setEditor(new JSpinner.DateEditor(checkInSpinner,"dd MMM yyyy"));
        checkInSpinner.setFont(new Font("Times New Roman",Font.PLAIN,16));
        checkInSpinner.setBounds(420,70,220,40);
        search.add(checkInSpinner);

        JLabel checkOutLabel=new JLabel("Check-out Date");
        checkOutLabel.setBounds(720,40,220,25);
        checkOutLabel.setForeground(Color.WHITE);
        checkOutLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        search.add(checkOutLabel);

        SpinnerDateModel checkOutModel=new SpinnerDateModel(
                new Date(System.currentTimeMillis()+24*60*60*1000),
                null,null,java.util.Calendar.DAY_OF_MONTH
        );

        checkOutSpinner=new JSpinner(checkOutModel);
        checkOutSpinner.setEditor(new JSpinner.DateEditor(checkOutSpinner,"dd MMM yyyy"));
        checkOutSpinner.setFont(new Font("Times New Roman",Font.PLAIN,16));
        checkOutSpinner.setBounds(720,70,220,40);
        search.add(checkOutSpinner);

        JLabel nightsTitle=new JLabel("Nights");
        nightsTitle.setBounds(720,113,100,20);
        nightsTitle.setForeground(new Color(180,190,205));
        nightsTitle.setFont(new Font("Times New Roman",Font.ITALIC,13));
        search.add(nightsTitle);

        nightsLabel=new JLabel("1 Night");
        nightsLabel.setBounds(775,113,150,20);
        nightsLabel.setForeground(GOLD);
        nightsLabel.setFont(new Font("Times New Roman",Font.BOLD,13));
        search.add(nightsLabel);

        JLabel adultLabel=new JLabel("Adults");
        adultLabel.setBounds(1050,40,100,25);
        adultLabel.setForeground(Color.WHITE);
        adultLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        search.add(adultLabel);

        adultSpinner=new JSpinner(new SpinnerNumberModel(1,1,5,1));
        adultSpinner.setFont(new Font("Times New Roman",Font.PLAIN,16));
        adultSpinner.setBounds(1050,70,100,40);
        search.add(adultSpinner);

        JLabel childLabel=new JLabel("Children");
        childLabel.setBounds(1250,40,100,25);
        childLabel.setForeground(Color.WHITE);
        childLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        search.add(childLabel);

        childSpinner=new JSpinner(new SpinnerNumberModel(0,0,4,1));
        childSpinner.setFont(new Font("Times New Roman",Font.PLAIN,16));
        childSpinner.setBounds(1250,70,100,40);
        search.add(childSpinner);

        JLabel guestInfo=new JLabel("Maximum 5 guests");
        guestInfo.setBounds(1050,113,250,25);
        guestInfo.setForeground(new Color(180,190,205));
        guestInfo.setFont(new Font("Times New Roman",Font.ITALIC,13));
        search.add(guestInfo);

        ChangeListener guestListener=e->validateGuests();
        adultSpinner.addChangeListener(guestListener);
        childSpinner.addChangeListener(guestListener);

        checkInSpinner.addChangeListener(e->updateNights());
        checkOutSpinner.addChangeListener(e->updateNights());

        RoundedButton2 searchButton=new RoundedButton2("Search Hotels");
        searchButton.setBounds(600,150,250,50);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(BLUE);
        searchButton.setFont(new Font("Times New Roman",Font.BOLD,18));
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        search.add(searchButton);
        searchButton.addActionListener(e->searchHotels());

        resultsPanel=new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel,BoxLayout.Y_AXIS));
        resultsPanel.setBackground(NAVY);
        resultsPanel.setBorder(new EmptyBorder(0,0,0,0));

        JScrollPane scrollPane=new JScrollPane(resultsPanel);
        scrollPane.setBounds(30,370,1450,350);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(NAVY);
        hotelPanel.add(scrollPane);

        add(hotelPanel,BorderLayout.CENTER);
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Times New Roman",Font.PLAIN,16));
        comboBox.setForeground(Color.WHITE);
        comboBox.setBackground(NAVY);
        comboBox.setPreferredSize(new Dimension(150,30));
        comboBox.setBorder(null);
        comboBox.setFocusable(false);
    }

    private JPanel createComboPanel(String text,JComboBox<String> comboBox) {
        JPanel panel=new JPanel(new BorderLayout(0,7));
        panel.setOpaque(false);
        JLabel label=new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Times New Roman",Font.PLAIN,15));
        panel.add(label,BorderLayout.NORTH);
        panel.add(comboBox,BorderLayout.CENTER);
        return panel;
    }

    private void updateNights() {
        Date checkIn=(Date)checkInSpinner.getValue();
        Date checkOut=(Date)checkOutSpinner.getValue();

        long difference=checkOut.getTime()-checkIn.getTime();
        long nights=difference/(1000*60*60*24);

        if(nights<=0) {
            nightsLabel.setText("Invalid dates");
            nightsLabel.setForeground(Color.RED);
        } else {
            nightsLabel.setText(nights+(nights==1?" Night":" Nights"));
            nightsLabel.setForeground(GOLD);
        }
    }

    private void validateGuests() {
        int adults=(Integer)adultSpinner.getValue();
        int children=(Integer)childSpinner.getValue();

        if(adults+children>5) {
            if(adultSpinner.getValue().equals(adults)) {
                if(children>0)
                    childSpinner.setValue(5-adults);
            }
        }
    }

    private void searchHotels() {
        String destination=destinationBox.getSelectedItem().toString();
        Date checkIn=(Date)checkInSpinner.getValue();
        Date checkOut=(Date)checkOutSpinner.getValue();

        int adults=(Integer)adultSpinner.getValue();
        int children=(Integer)childSpinner.getValue();

        if(adults+children>5) {
            JOptionPane.showMessageDialog(
                    this,
                    "Adults and children together cannot exceed 5 guests.",
                    "Guest Limit",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if(!checkOut.after(checkIn)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Check-out date must be after check-in date.",
                    "Invalid Dates",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        long nights=(checkOut.getTime()-checkIn.getTime())/(1000*60*60*24);

        String checkInDate=new SimpleDateFormat("yyyy-MM-dd").format(checkIn);
        String checkOutDate=new SimpleDateFormat("yyyy-MM-dd").format(checkOut);

        resultsPanel.removeAll();

        String sql="SELECT h.hotel_id,h.hotel_name,h.destination,h.address,h.rating,h.description,"
                +"COUNT(r.room_id) AS available_rooms "
                +"FROM hoteldetails5 h "
                +"LEFT JOIN hotelrooms4 r ON h.hotel_id=r.hotel_id "
                +"AND r.availability='Available' "
                +"WHERE h.destination=? "
                +"GROUP BY h.hotel_id,h.hotel_name,h.destination,h.address,h.rating,h.description";

        try(Connection con=DBConnection.getConnection();
        	PreparedStatement pst=con.prepareStatement(sql))
        {

            pst.setString(1,destination);
            ResultSet rs=pst.executeQuery();

            boolean found=false;

            while(rs.next()) {
                found=true;

                addHotelResult(
                        rs.getInt("hotel_id"),
                        rs.getString("hotel_name"),
                        destination,
                        rs.getString("address"),
                        rs.getDouble("rating"),
                        rs.getString("description"),
                        rs.getInt("available_rooms"),
                        adults,
                        children,
                        checkInDate,
                        checkOutDate,
                        nights
                );
            }

            if(!found) {
                JLabel noResult=new JLabel("No hotels found for this destination.");
                noResult.setForeground(Color.WHITE);
                noResult.setFont(new Font("Times New Roman",Font.PLAIN,18));
                noResult.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultsPanel.add(Box.createVerticalStrut(30));
                resultsPanel.add(noResult);
            }

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n"+ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void addHotelResult(
            int hotelId,
            String hotelName,
            String destination,
            String address,
            double rating,
            String description,
            int availableRooms,
            int adults,
            int children,
            String checkInDate,
            String checkOutDate,
            long nights) {

        RoundedPanel card=new RoundedPanel();
        card.setLayout(null);
        card.setMaximumSize(new Dimension(1400,160));
        card.setPreferredSize(new Dimension(1400,160));
        card.setMinimumSize(new Dimension(1400,160));
        card.setBackground(PANEL);

        JLabel name=new JLabel(hotelName);
        name.setBounds(30,15,500,30);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Times New Roman",Font.BOLD,21));
        card.add(name);

        JLabel location=new JLabel(
                destination+"  •  Rating: "+String.format("%.1f",rating)
        );
        location.setBounds(30,50,500,25);
        location.setForeground(GOLD);
        location.setFont(new Font("Times New Roman",Font.BOLD,16));
        card.add(location);

        JLabel addressLabel=new JLabel(address);
        addressLabel.setBounds(30,80,500,25);
        addressLabel.setForeground(Color.WHITE);
        addressLabel.setFont(new Font("Times New Roman",Font.PLAIN,14));
        card.add(addressLabel);

        JLabel descriptionLabel=new JLabel(description);
        descriptionLabel.setBounds(30,110,650,25);
        descriptionLabel.setForeground(new Color(180,190,205));
        descriptionLabel.setFont(new Font("Times New Roman",Font.PLAIN,13));
        card.add(descriptionLabel);

        JLabel roomsTitle=new JLabel("Available Rooms");
        roomsTitle.setBounds(760,20,180,25);
        roomsTitle.setForeground(Color.WHITE);
        roomsTitle.setFont(new Font("Times New Roman",Font.PLAIN,14));
        card.add(roomsTitle);

        JLabel roomsLabel=new JLabel(String.valueOf(availableRooms));
        roomsLabel.setBounds(760,45,180,35);
        roomsLabel.setForeground(GOLD);
        roomsLabel.setFont(new Font("Times New Roman",Font.BOLD,23));
        card.add(roomsLabel);

        JLabel stayLabel=new JLabel(
                nights+" "+(nights==1?"Night":"Nights")
                +" • "+adults+" Adult(s) • "+children+" Child(ren)"
        );
        stayLabel.setBounds(760,85,350,25);
        stayLabel.setForeground(Color.WHITE);
        stayLabel.setFont(new Font("Times New Roman",Font.PLAIN,13));
        card.add(stayLabel);

        RoundedButton2 bookButton=new RoundedButton2("Book");
        bookButton.setBounds(1150,50,150,45);
        bookButton.setBackground(BLUE);
        bookButton.setForeground(Color.WHITE);
        bookButton.setFont(new Font("Times New Roman",Font.BOLD,17));
        bookButton.setFocusPainted(false);
        bookButton.setBorderPainted(false);
        bookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.add(bookButton);

        bookButton.addActionListener(e->showAvailableRooms(
                hotelId,
                hotelName,
                destination,
                checkInDate,
                checkOutDate,
                adults,
                children,
                nights
        ));

        resultsPanel.add(card);
        resultsPanel.add(Box.createVerticalStrut(15));
    }

    private void showAvailableRooms(
            int hotelId,
            String hotelName,
            String destination,
            String checkInDate,
            String checkOutDate,
            int adults,
            int children,
            long nights) {

        resultsPanel.removeAll();

        JLabel heading=new JLabel(hotelName+" - Available Rooms");
        heading.setForeground(GOLD);
        heading.setFont(new Font("Times New Roman",Font.BOLD,24));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsPanel.add(Box.createVerticalStrut(10));
        resultsPanel.add(heading);
        resultsPanel.add(Box.createVerticalStrut(15));

        int guests=adults+children;

        String sql="SELECT room_id,room_number,room_type,capacity,price_per_night "
                +"FROM hotelrooms4 "
                +"WHERE hotel_id=? "
                +"AND availability='Available' "
                +"AND capacity>=? "
                +"ORDER BY room_number";

        try(Connection con=DBConnection.getConnection();PreparedStatement pst=con.prepareStatement(sql)) {

            pst.setInt(1,hotelId);
            pst.setInt(2,guests);

            ResultSet rs=pst.executeQuery();

            boolean found=false;

            while(rs.next()) {
                found=true;

                addRoomResult(
                        rs.getInt("room_id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getInt("capacity"),
                        rs.getDouble("price_per_night"),
                        hotelName,
                        destination,
                        checkInDate,
                        checkOutDate,
                        adults,
                        children,
                        nights
                );
            }

            if(!found) {
                JLabel noRooms=new JLabel(
                        "No rooms available for "+guests+" guests."
                );
                noRooms.setForeground(Color.WHITE);
                noRooms.setFont(new Font("Times New Roman",Font.PLAIN,18));
                noRooms.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultsPanel.add(noRooms);
            }

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Database Error:\n"+ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void addRoomResult(
            int roomId,
            String roomNumber,
            String roomType,
            int capacity,
            double price,
            String hotelName,
            String destination,
            String checkInDate,
            String checkOutDate,
            int adults,
            int children,
            long nights) {

        RoundedPanel card=new RoundedPanel();
        card.setLayout(null);
        card.setMaximumSize(new Dimension(1400,130));
        card.setPreferredSize(new Dimension(1400,130));
        card.setMinimumSize(new Dimension(1400,130));
        card.setBackground(PANEL);

        JLabel room=new JLabel("Room "+roomNumber);
        room.setBounds(30,20,300,30);
        room.setForeground(Color.WHITE);
        room.setFont(new Font("Times New Roman",Font.BOLD,20));
        card.add(room);

        JLabel type=new JLabel(roomType);
        type.setBounds(30,55,300,25);
        type.setForeground(GOLD);
        type.setFont(new Font("Times New Roman",Font.BOLD,16));
        card.add(type);

        JLabel capacityLabel=new JLabel("Capacity: "+capacity+" guests");
        capacityLabel.setBounds(350,35,250,25);
        capacityLabel.setForeground(Color.WHITE);
        capacityLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        card.add(capacityLabel);

        long total=Math.round(price * nights);

        JLabel priceLabel=new JLabel(
                "₹"+String.format("%.2f",price)+" / night"
        );
        priceLabel.setBounds(700,20,250,30);
        priceLabel.setForeground(GOLD);
        priceLabel.setFont(new Font("Times New Roman",Font.BOLD,20));
        card.add(priceLabel);

        JLabel totalLabel=new JLabel(
                "Total: ₹"+total
                +" for "+nights+" "+(nights==1?"night":"nights")
        );
        totalLabel.setBounds(700,55,350,25);
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setFont(new Font("Times New Roman",Font.PLAIN,14));
        card.add(totalLabel);

        RoundedButton2 bookRoomButton=new RoundedButton2("Book Room");
        bookRoomButton.setBounds(1150,40,150,45);
        bookRoomButton.setBackground(BLUE);
        bookRoomButton.setForeground(Color.WHITE);
        bookRoomButton.setFont(new Font("Times New Roman",Font.BOLD,17));
        bookRoomButton.setFocusPainted(false);
        bookRoomButton.setBorderPainted(false);
        bookRoomButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.add(bookRoomButton);

        bookRoomButton.addActionListener(e->bookRoom(
                roomId,
                roomNumber,
                roomType,
                hotelName,
                destination,
                checkInDate,
                checkOutDate,
                adults,
                children,
                price,
                nights,
                total
        ));

        resultsPanel.add(card);
        resultsPanel.add(Box.createVerticalStrut(15));
    }

    private void bookRoom(
            int roomId,
            String roomNumber,
            String roomType,
            String hotelName,
            String destination,
            String checkInDate,
            String checkOutDate,
            int adults,
            int children,
            double price,
            long nights,
            long total) {

        long totalAmount = Math.round(total);

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Hotel: " + hotelName +
                "\nRoom: " + roomNumber + " - " + roomType +
                "\nCheck-in: " + checkInDate +
                "\nCheck-out: " + checkOutDate +
                "\nAdults: " + adults +
                "\nChildren: " + children +
                "\nNights: " + nights +
                "\nTotal Price: ₹" + totalAmount +
                "\n\nConfirm booking?",
                "Confirm Booking",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        Connection con = null;

        try {

            con = DBConnection.getConnection();

            con.setAutoCommit(false);


            String walletSql =
                    "UPDATE wallet " +
                    "SET balance = balance - ? " +
                    "WHERE user_id = ? AND balance >= ?";

            try (PreparedStatement walletPs =
                         con.prepareStatement(walletSql)) {

                walletPs.setLong(1, totalAmount);
                walletPs.setInt(2, session.userId);
                walletPs.setLong(3, totalAmount);

                int walletUpdated = walletPs.executeUpdate();

                if (walletUpdated != 1) {

                    con.rollback();

                    JOptionPane.showMessageDialog(
                            this,
                            "Insufficient wallet balance.",
                            "Payment Failed",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }
            }


            String roomSql =
                    "UPDATE hotelrooms4 " +
                    "SET availability = 'Booked' " +
                    "WHERE room_id = ? AND availability = 'Available'";

            try (PreparedStatement roomPs =
                         con.prepareStatement(roomSql)) {

                roomPs.setInt(1, roomId);

                int roomUpdated = roomPs.executeUpdate();

                if (roomUpdated != 1) {

                    con.rollback();

                    JOptionPane.showMessageDialog(
                            this,
                            "Sorry, this room is no longer available.",
                            "Room Unavailable",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }
            }

            int bookingId = BookingManager.createBooking(
                    con,
                    session.userId,
                    "Hotel",
                    hotelName,
                    "Room " + roomNumber +
                    " • " + roomType +
                    " • " + adults + " Adult(s)" +
                    " • " + children + " Child(ren)",
                    destination,
                    java.sql.Date.valueOf(checkInDate),
                    null,
                    totalAmount
            );

            String transactionSql =
                    "INSERT INTO transaction " +
                    "(user_id, transaction_type, description, booking_id, " +
                    "transaction_date, amount, status) " +
                    "VALUES (?, ?, ?, ?, NOW(), ?, ?)";

            try (PreparedStatement transactionPs =
                         con.prepareStatement(transactionSql)) {

                transactionPs.setInt(1, session.userId);
                transactionPs.setString(2, "Hotel");

                transactionPs.setString(
                        3,
                        "Hotel Booking - " + hotelName +
                        " Room " + roomNumber
                );

                transactionPs.setString(4, String.valueOf(bookingId));

                transactionPs.setLong(5, -totalAmount);

                transactionPs.setString(6, "Success");

                transactionPs.executeUpdate();
            }


            con.commit();


            walletPage.refreshWallet();
            transactionPage.refreshTrans();

            searchHotels();
            showPaymentAnimation(
            	    bookingId,
            	    hotelName,
            	    destination,
            	    roomNumber,
            	    roomType,
            	    checkInDate,
            	    checkOutDate,
            	    totalAmount
            	);

            
            
            

        } catch (SQLException ex) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Booking failed:\n" + ex.getMessage(),
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
    private void showPaymentAnimation(
            int bookingId,
            String hotelName,
            String destination,
            String roomNumber,
            String roomType,
            String checkInDate,
            String checkOutDate,
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
            	    hotelName,
            	    destination,
            	    roomNumber,
            	    roomType,
            	    checkInDate,
            	    checkOutDate,
            	    totalAmount
            );
        });

        paymentTimer.setRepeats(false);
        paymentTimer.start();
    }
    private void showTicketAfterAnimation(
    		int bookingId,
            String hotelName,
            String destination,
            String roomNumber,
            String roomType,
            String checkInDate,
            String checkOutDate,
            long totalAmount) {

    	ticketPage.showTicket(
                session.userId,
                bookingId,
                "Hotel",
                hotelName,
                destination,
                "Room " + roomNumber +
                " • " + roomType ,
                checkInDate,
                checkOutDate,
                totalAmount
        );}

}