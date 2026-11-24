package cia;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestaurantTableSelection extends JPanel{
    private final Color NAVY=new Color(11,22,40);
    private final Color PANEL=new Color(17,31,53);
    private final Color BLUE=new Color(20,80,180);
    private final Color GOLD=new Color(212,175,55);
    private final Color GREEN=new Color(35,150,85);
    private final Color RED=new Color(170,55,55);

    private int restaurantId;
    private String restaurantName,destination;
    private int guests;
    private long hours;
    private int selectedTableId=-1;
    private String selectedTableNumber="";
    private Date bookingDate;
    private Date startTime;
    private Date endTime;
    private JButton selectedButton=null;
    private JPanel pages;
    private CardLayout cardLayout;
    private Wallet walletPage;
    private transactionPage transactionPage;
    private ticket ticketPage;
    private JLabel animationLabel;
    private Timer paymentTimer;


    public RestaurantTableSelection( int restaurantId,String restaurantName,String destination,int guests,long hours,Date bookingDate,Date startTime,Date endTime,JPanel pages,CardLayout cardLayout,Wallet walletPage,transactionPage transactionPage,ticket ticketPage){
        this.restaurantId=restaurantId;
        this.restaurantName=restaurantName;
        this.destination=destination;
        this.guests=guests;
        this.hours=hours;
        this.bookingDate=bookingDate;
        this.startTime=startTime;
        this.endTime=endTime;
        
        this.pages = pages;
        this.cardLayout = cardLayout;
        this.walletPage = walletPage;
        this.transactionPage = transactionPage;
        this.ticketPage=ticketPage;
        
        setLayout(new BorderLayout());

        JPanel mainPanel=new JPanel(new BorderLayout());
        mainPanel.setBackground(NAVY);

        JPanel headerPanel=new JPanel();
        headerPanel.setBackground(NAVY);
        headerPanel.setLayout(new BoxLayout(headerPanel,BoxLayout.Y_AXIS));

        JLabel title=new JLabel(restaurantName+" - Select Your Table",SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman",Font.BOLD,28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        String dateText=new SimpleDateFormat("dd MMM yyyy").format(bookingDate);
        String startText=new SimpleDateFormat("hh:mm a").format(startTime);
        String endText=new SimpleDateFormat("hh:mm a").format(endTime);

        JLabel info=new JLabel(dateText+"  •  "+startText+" - "+endText+"  •  "+hours+" "+(hours==1?"Hour":"Hours")+"  •  "+guests+" Guest(s)",SwingConstants.CENTER);
        info.setForeground(GOLD);
        info.setFont(new Font("Times New Roman",Font.BOLD,16));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(Box.createVerticalStrut(20));
        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(info);
        headerPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(headerPanel,BorderLayout.NORTH);

        JPanel tablePanel=new JPanel(new GridLayout(0,5,20,20));
        tablePanel.setBackground(NAVY);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));
        loadTables(tablePanel);

        JScrollPane scrollPane=new JScrollPane(tablePanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(NAVY);
        mainPanel.add(scrollPane,BorderLayout.CENTER);

        JPanel bottomPanel=new JPanel();
        bottomPanel.setBackground(NAVY);

        JButton continueButton=new JButton("Book Selected Table");
        continueButton.setBackground(BLUE);
        continueButton.setForeground(Color.WHITE);
        continueButton.setFont(new Font("Times New Roman",Font.BOLD,18));
        continueButton.setFocusPainted(false);
        continueButton.addActionListener(e->continueBooking());

        bottomPanel.add(continueButton);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);

        add(mainPanel,BorderLayout.CENTER);
    }

    private Connection getConnection()throws SQLException{
        return DBConnection.getConnection();
    }

    private void loadTables(JPanel tablePanel){
        String sql="SELECT table_id,table_number,table_type,capacity,price_per_hour,availability FROM restauranttables WHERE restaurant_id=? AND capacity>=? ORDER BY table_number";

        try(Connection con=getConnection();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setInt(1,restaurantId);
            pst.setInt(2,guests);

            ResultSet rs=pst.executeQuery();

            while(rs.next()){
                int tableId=rs.getInt("table_id");
                String tableNumber=rs.getString("table_number");
                String tableType=rs.getString("table_type");
                int capacity=rs.getInt("capacity");
                double price=rs.getDouble("price_per_hour");
                String availability=rs.getString("availability");

                boolean booked=isTableBooked(tableId,bookingDate,startTime,endTime);

                JButton tableButton=new JButton("<html><center>TABLE "+tableNumber+"<br>"+tableType+"<br>Capacity: "+capacity+"<br>₹"+price+"/hr</center></html>");

                tableButton.setFont(new Font("Times New Roman",Font.PLAIN,14));
                tableButton.setFocusPainted(false);

                if(booked){
                    tableButton.setBackground(RED);
                    tableButton.setForeground(Color.WHITE);
                    tableButton.setEnabled(false);
                }else if(availability.equalsIgnoreCase("Booked")){
                    tableButton.setBackground(RED);
                    tableButton.setForeground(Color.WHITE);
                    tableButton.setEnabled(false);
                }else{
                    tableButton.setBackground(GREEN);
                    tableButton.setForeground(Color.WHITE);
                    tableButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    tableButton.addActionListener(e->selectTable(tableButton,tableId,tableNumber));
                }

                tablePanel.add(tableButton);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,"Database Error:\n"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isTableBooked(int tableId,Date date,Date start,Date end){
        String sql="SELECT booking_id FROM restaurantbookings WHERE table_id=? AND booking_date=? AND booking_status='CONFIRMED' AND start_time < ? AND end_time > ?";

        try(Connection con=getConnection();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setInt(1,tableId);
            pst.setDate(2,new java.sql.Date(date.getTime()));
            pst.setTime(3,new java.sql.Time(end.getTime()));
            pst.setTime(4,new java.sql.Time(start.getTime()));

            ResultSet rs=pst.executeQuery();
            return rs.next();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,"Availability Check Failed:\n"+ex.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
            return true;
        }
    }

    private void selectTable(JButton button,int tableId,String tableNumber){
        if(selectedButton!=null){
            selectedButton.setBackground(GREEN);
        }

        selectedTableId=tableId;
        selectedTableNumber=tableNumber;
        selectedButton=button;
        button.setBackground(BLUE);
    }

    private void continueBooking(){
        if(selectedTableId==-1){
            JOptionPane.showMessageDialog(this,"Please select one table first.","Select Table",JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(isTableBooked(selectedTableId,bookingDate,startTime,endTime)){
            JOptionPane.showMessageDialog(this,"This table is already booked for the selected date and time.","Table Unavailable",JOptionPane.WARNING_MESSAGE);
            return;
        }

        double price=getTablePrice(selectedTableId);
        long total=Math.round(price * hours);

        String dateText=new SimpleDateFormat("dd MMM yyyy").format(bookingDate);
        String startText=new SimpleDateFormat("hh:mm a").format(startTime);
        String endText=new SimpleDateFormat("hh:mm a").format(endTime);

        int choice=JOptionPane.showConfirmDialog(
            this,
            "Restaurant: "+restaurantName+
            "\nDate: "+dateText+
            "\nTime: "+startText+" - "+endText+
            "\nTable: "+selectedTableNumber+
            "\nGuests: "+guests+
            "\nDuration: "+hours+" "+(hours==1?"Hour":"Hours")+
            "\nPrice per hour: ₹"+String.format("%.2f",price)+
            "\nTotal Price: ₹"+total+
            "\n\nConfirm booking?",
            "Confirm Booking",
            JOptionPane.YES_NO_OPTION
        );

        if(choice==JOptionPane.YES_OPTION){
            bookTable(price,total);
        }
    }

    private double getTablePrice(int tableId){
        String sql="SELECT price_per_hour FROM restauranttables WHERE table_id=?";

        try(Connection con=getConnection();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setInt(1,tableId);

            ResultSet rs=pst.executeQuery();

            if(rs.next()){
                return rs.getDouble("price_per_hour");
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,"Database Error:\n"+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

        return 0;
    }

    private void bookTable(double price, long total) {

        String checkSql =
                "SELECT booking_id " +
                "FROM restaurantbookings " +
                "WHERE table_id=? " +
                "AND booking_date=? " +
                "AND booking_status='CONFIRMED' " +
                "AND start_time < ? " +
                "AND end_time > ?";

        String insertSql =
                "INSERT INTO restaurantbookings " +
                "(restaurant_id, table_id, booking_date, start_time, " +
                "end_time, guests, price_per_hour, total_price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String updateSql =
                "UPDATE restauranttables " +
                "SET availability='Booked' " +
                "WHERE table_id=?";

        String walletSql =
                "UPDATE wallet " +
                "SET balance = balance - ? " +
                "WHERE user_id = ? AND balance >= ?";

        String transactionSql =
                "INSERT INTO transaction " +
                "(user_id, transaction_type, description, booking_id," +
                "transaction_date, amount, status) " +
                "VALUES (?, ?, ?, ?,NOW(), ?, ?)";

        Connection con = null;

        try {

            con = getConnection();

            con.setAutoCommit(false);

            try (PreparedStatement check =
                         con.prepareStatement(checkSql)) {

                check.setInt(1, selectedTableId);

                check.setDate(
                        2,
                        new java.sql.Date(bookingDate.getTime())
                );

                check.setTime(
                        3,
                        new java.sql.Time(endTime.getTime())
                );

                check.setTime(
                        4,
                        new java.sql.Time(startTime.getTime())
                );

                try (ResultSet rs = check.executeQuery()) {

                    if (rs.next()) {

                        con.rollback();

                        JOptionPane.showMessageDialog(
                                this,
                                "This table has already been booked for this time slot.",
                                "Booking Failed",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }
                }
            }


            try (PreparedStatement walletPs =
                         con.prepareStatement(walletSql)) {

                walletPs.setLong(1, total);
                walletPs.setInt(2, session.userId);
                walletPs.setLong(3, total);

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


            try (PreparedStatement pst =
                         con.prepareStatement(
                                 insertSql
                                 )) {

                pst.setInt(1, restaurantId);
                pst.setInt(2, selectedTableId);

                pst.setDate(
                        3,
                        new java.sql.Date(bookingDate.getTime())
                );

                pst.setTime(
                        4,
                        new java.sql.Time(startTime.getTime())
                );

                pst.setTime(
                        5,
                        new java.sql.Time(endTime.getTime())
                );

                pst.setInt(6, guests);
                pst.setDouble(7, price);
                pst.setLong(8, total);

                int inserted = pst.executeUpdate();

                if (inserted != 1) {

                    con.rollback();

                    JOptionPane.showMessageDialog(
                            this,
                            "Could not create the restaurant booking.",
                            "Booking Failed",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                
                    }

            try (PreparedStatement update =
                         con.prepareStatement(updateSql)) {

                update.setInt(1, selectedTableId);

                int updated = update.executeUpdate();

                if (updated != 1) {

                    con.rollback();

                    JOptionPane.showMessageDialog(
                            this,
                            "Could not update table status.",
                            "Booking Failed",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }
            }
            int bookingId = BookingManager.createBooking(
                    con,
                    session.userId,
                    "Restaurant",
                    restaurantName,
                    "Table " + selectedTableNumber +
                            " • " + guests + " Guest(s)",
                    destination,
                    new java.sql.Date(bookingDate.getTime()),
                    new java.sql.Time(startTime.getTime()),
                    total
            );

            try (PreparedStatement transactionPs =
                         con.prepareStatement(transactionSql)) {

                transactionPs.setInt(1, session.userId);

                transactionPs.setString(
                        2,
                        "Restaurant"
                );

                transactionPs.setString(
                        3,
                        "Restaurant Booking - "
                        + restaurantName
                        + " Table "
                        + selectedTableNumber
                );

                transactionPs.setString(
                       4,
                        String.valueOf(bookingId)
                );
                // Negative = money spent
                transactionPs.setLong(
                        5,
                        -total
                );

                transactionPs.setString(
                        6,
                        "Success"
                );

                transactionPs.executeUpdate();
            }

            con.commit();

            walletPage.refreshWallet();
            transactionPage.refreshTrans();
            
            showPaymentAnimation(
            	    bookingId, 
            	    total
            	);

        } catch (SQLException ex) {

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Booking Failed:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } finally {

            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    private void showPaymentAnimation(
            int bookingId,
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
                totalAmount
            );
        });

        paymentTimer.setRepeats(false);
        paymentTimer.start();
    }
    private void showTicketAfterAnimation(
            int bookingId,
            long totalAmount) {

    	ticketPage.showTicket(
        	    session.userId,
        	    bookingId,
        	    "Restaurant",
        	    restaurantName,
        	    destination,
        	    "Table " + selectedTableNumber +
        	        " • " + guests + " Guest(s)",
        	    new SimpleDateFormat("dd MMM yyyy").format(bookingDate),
        	    new SimpleDateFormat("hh:mm a").format(startTime),
        	    totalAmount
        	);
    }
}