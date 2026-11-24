package cia;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

public class restaurant extends JPanel{
    private final Color NAVY=new Color(11,22,40);
    private final Color PANEL=new Color(17,31,53);
    private final Color BLUE=new Color(20,80,180);
    private final Color GOLD=new Color(212,175,55);

    private JComboBox<String> destinationBox;
    private JSpinner bookingDateSpinner;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;
    private JSpinner guestSpinner;
    private JLabel durationLabel;
    private JPanel resultsPanel,pages;
    private CardLayout cardLayout;
    private Wallet walletPage;
    private transactionPage transactionPage;
    private ticket ticketPage;
    
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
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

            if(getModel().isPressed())
                g2.setColor(getBackground().darker());
            else if(getModel().isRollover())
                g2.setColor(getBackground().brighter());
            else
                g2.setColor(getBackground());

            g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);
            g2.dispose();

            super.paintComponent(g);
        }
    }

    public restaurant(JPanel pages,CardLayout cardLayout,Wallet walletPage,transactionPage transactionPage,ticket ticketPage){
    	this.pages=pages;
    	this.cardLayout=cardLayout;
    	this.walletPage=walletPage;
    	this.transactionPage=transactionPage;
    	this.ticketPage=ticketPage;
    	setLayout(new BorderLayout());

        JPanel mainPanel=new JPanel();
        mainPanel.setBackground(NAVY);
        mainPanel.setLayout(null);

        JLabel icon=new JLabel("🍽");
        icon.setBounds(25,18,55,55);
        icon.setForeground(GOLD);
        icon.setFont(new Font("Segoe UI Emoji",Font.PLAIN,40));
        mainPanel.add(icon);

        JLabel title=new JLabel("Restaurant Booking");
        title.setBounds(75,15,350,45);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman",Font.BOLD,30));
        mainPanel.add(title);

        JLabel subtitle=new JLabel("Find a restaurant and reserve your table");
        subtitle.setBounds(78,48,400,30);
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Times New Roman",Font.PLAIN,15));
        mainPanel.add(subtitle);

        JButton myBookings=new JButton("My Bookings");
        myBookings.setBounds(1300,30,180,40);
        myBookings.setForeground(GOLD);
        myBookings.setFont(new Font("Times New Roman",Font.PLAIN,20));
        myBookings.setFocusPainted(false);
        myBookings.setBorderPainted(false);
        myBookings.setContentAreaFilled(false);
        mainPanel.add(myBookings);

        RoundedPanel searchPanel=new RoundedPanel();
        searchPanel.setBounds(30,100,1450,250);
        searchPanel.setBackground(PANEL);
        searchPanel.setLayout(null);
        mainPanel.add(searchPanel);

        JLabel destinationLabel=new JLabel("Destination");
        destinationLabel.setBounds(55,30,250,25);
        destinationLabel.setForeground(Color.WHITE);
        destinationLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        searchPanel.add(destinationLabel);

        destinationBox=new JComboBox<>(new String[]{"Mumbai","Pune","Delhi","Chennai","Madikeri"});
        destinationBox.setBounds(55,65,250,42);
        styleCombo(destinationBox);
        searchPanel.add(destinationBox);

        JLabel dateLabel=new JLabel("Booking Date");
        dateLabel.setBounds(390,30,230,25);
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        searchPanel.add(dateLabel);

        bookingDateSpinner=new JSpinner(new SpinnerDateModel(new Date(),null,null,Calendar.DAY_OF_MONTH));
        bookingDateSpinner.setBounds(390,65,230,42);
        bookingDateSpinner.setEditor(new JSpinner.DateEditor(bookingDateSpinner,"dd MMM yyyy"));
        bookingDateSpinner.setFont(new Font("Times New Roman",Font.PLAIN,16));
        searchPanel.add(bookingDateSpinner);

        JLabel startLabel=new JLabel("Start Time");
        startLabel.setBounds(700,30,190,25);
        startLabel.setForeground(Color.WHITE);
        startLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        searchPanel.add(startLabel);

        SpinnerDateModel startModel=new SpinnerDateModel(new Date(),null,null,Calendar.MINUTE);
        startTimeSpinner=new JSpinner(startModel);
        startTimeSpinner.setBounds(700,65,190,42);
        startTimeSpinner.setEditor(new JSpinner.DateEditor(startTimeSpinner,"hh:mm a"));
        startTimeSpinner.setFont(new Font("Times New Roman",Font.PLAIN,16));
        searchPanel.add(startTimeSpinner);

        JLabel endLabel=new JLabel("End Time");
        endLabel.setBounds(975,30,190,25);
        endLabel.setForeground(Color.WHITE);
        endLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        searchPanel.add(endLabel);

        Calendar endCalendar=Calendar.getInstance();
        endCalendar.add(Calendar.HOUR_OF_DAY,1);

        SpinnerDateModel endModel=new SpinnerDateModel(endCalendar.getTime(),null,null,Calendar.MINUTE);
        endTimeSpinner=new JSpinner(endModel);
        endTimeSpinner.setBounds(975,65,190,42);
        endTimeSpinner.setEditor(new JSpinner.DateEditor(endTimeSpinner,"hh:mm a"));
        endTimeSpinner.setFont(new Font("Times New Roman",Font.PLAIN,16));
        searchPanel.add(endTimeSpinner);

        JLabel durationTitle=new JLabel("Duration");
        durationTitle.setBounds(975,115,80,20);
        durationTitle.setForeground(new Color(180,190,205));
        durationTitle.setFont(new Font("Times New Roman",Font.ITALIC,13));
        searchPanel.add(durationTitle);

        durationLabel=new JLabel("1 Hour");
        durationLabel.setBounds(1045,115,150,20);
        durationLabel.setForeground(GOLD);
        durationLabel.setFont(new Font("Times New Roman",Font.BOLD,13));
        searchPanel.add(durationLabel);

        JLabel guestLabel=new JLabel("Guests");
        guestLabel.setBounds(1250,30,250,25);
        guestLabel.setForeground(Color.WHITE);
        guestLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        searchPanel.add(guestLabel);

        guestSpinner=new JSpinner(new SpinnerNumberModel(1,1,5,1));
        guestSpinner.setBounds(1250,65,120,42);
        guestSpinner.setFont(new Font("Times New Roman",Font.PLAIN,16));
        searchPanel.add(guestSpinner);

        JLabel guestInfo=new JLabel("Maximum 5 guests");
        guestInfo.setBounds(1250,115,200,20);
        guestInfo.setForeground(new Color(180,190,205));
        guestInfo.setFont(new Font("Times New Roman",Font.ITALIC,13));
        searchPanel.add(guestInfo);

        startTimeSpinner.addChangeListener(e->updateDuration());
        endTimeSpinner.addChangeListener(e->updateDuration());

        RoundedButton2 searchButton=new RoundedButton2("Search Restaurants");
        searchButton.setBounds(570,165,270,50);
        searchButton.setBackground(BLUE);
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Times New Roman",Font.BOLD,18));
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchPanel.add(searchButton);
        searchButton.addActionListener(e->searchRestaurants());

        resultsPanel=new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel,BoxLayout.Y_AXIS));
        resultsPanel.setBackground(NAVY);
        resultsPanel.setBorder(new EmptyBorder(15,30,20,30));

        JScrollPane scrollPane=new JScrollPane(resultsPanel);
        scrollPane.setBounds(0,370,1536,390);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(NAVY);
        mainPanel.add(scrollPane);

        add(mainPanel,BorderLayout.CENTER);
    }

    private Connection getConnection()throws SQLException{
        return DBConnection.getConnection();
    }

    private void styleCombo(JComboBox<String> combo){
        combo.setFont(new Font("Times New Roman",Font.PLAIN,16));
        combo.setForeground(Color.WHITE);
        combo.setBackground(NAVY);
        combo.setFocusable(false);
    }

    private int getDurationHours(){
        Date start=(Date)startTimeSpinner.getValue();
        Date end=(Date)endTimeSpinner.getValue();

        Calendar startCal=Calendar.getInstance();
        startCal.setTime(start);

        Calendar endCal=Calendar.getInstance();
        endCal.setTime(end);

        int startMinutes=startCal.get(Calendar.HOUR_OF_DAY)*60+startCal.get(Calendar.MINUTE);
        int endMinutes=endCal.get(Calendar.HOUR_OF_DAY)*60+endCal.get(Calendar.MINUTE);
        int difference=endMinutes-startMinutes;

        if(difference<=0)return -1;
        return (difference+59)/60;
    }

    private void updateDuration(){
        int hours=getDurationHours();

        if(hours<=0){
            durationLabel.setText("Invalid Time");
            durationLabel.setForeground(Color.RED);
            return;
        }

        durationLabel.setText(hours+" "+(hours==1?"Hour":"Hours"));
        durationLabel.setForeground(GOLD);
    }

    private void searchRestaurants(){
        int hours=getDurationHours();

        if(hours<=0){
            JOptionPane.showMessageDialog(this,"End time must be after start time.","Invalid Time",JOptionPane.WARNING_MESSAGE);
            return;
        }

        String destination=destinationBox.getSelectedItem().toString();
        int guests=(Integer)guestSpinner.getValue();
        Date bookingDate=(Date)bookingDateSpinner.getValue();
        Date startTime=(Date)startTimeSpinner.getValue();
        Date endTime=(Date)endTimeSpinner.getValue();

        resultsPanel.removeAll();

        String sql="SELECT restaurant_id,restaurant_name,destination,address,rating,cuisine,description FROM restaurantdetails WHERE destination=? ORDER BY rating DESC";

        try(Connection con=getConnection();PreparedStatement pst=con.prepareStatement(sql)){
            pst.setString(1,destination);
            ResultSet rs=pst.executeQuery();
            boolean found=false;

            while(rs.next()){
                found=true;

                addRestaurantCard(
                    rs.getInt("restaurant_id"),
                    rs.getString("restaurant_name"),
                    rs.getString("destination"),
                    rs.getString("address"),
                    rs.getDouble("rating"),
                    rs.getString("cuisine"),
                    rs.getString("description"),
                    guests,
                    hours,
                    bookingDate,
                    startTime,
                    endTime
                );
            }

            if(!found){
                JLabel noResult=new JLabel("No restaurants found in "+destination+".");
                noResult.setForeground(Color.WHITE);
                noResult.setFont(new Font("Times New Roman",Font.PLAIN,18));
                noResult.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultsPanel.add(noResult);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this,"Database Error:\n"+ex.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void addRestaurantCard(
        int restaurantId,
        String restaurantName,
        String destination,
        String address,
        double rating,
        String cuisine,
        String description,
        int guests,
        long hours,
        Date bookingDate,
        Date startTime,
        Date endTime){

        RoundedPanel card=new RoundedPanel();
        card.setLayout(null);
        card.setMaximumSize(new Dimension(1400,180));
        card.setPreferredSize(new Dimension(1400,180));
        card.setMinimumSize(new Dimension(1400,180));
        card.setBackground(PANEL);

        JLabel name=new JLabel(restaurantName);
        name.setBounds(30,15,600,30);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Times New Roman",Font.BOLD,21));
        card.add(name);

        JLabel ratingLabel=new JLabel(destination+"  •  Rating: "+String.format("%.1f",rating));
        ratingLabel.setBounds(30,50,500,25);
        ratingLabel.setForeground(GOLD);
        ratingLabel.setFont(new Font("Times New Roman",Font.BOLD,16));
        card.add(ratingLabel);

        JLabel cuisineLabel=new JLabel("Cuisine: "+cuisine);
        cuisineLabel.setBounds(30,80,500,25);
        cuisineLabel.setForeground(Color.WHITE);
        cuisineLabel.setFont(new Font("Times New Roman",Font.PLAIN,14));
        card.add(cuisineLabel);

        JLabel addressLabel=new JLabel(address);
        addressLabel.setBounds(30,110,650,25);
        addressLabel.setForeground(new Color(180,190,205));
        addressLabel.setFont(new Font("Times New Roman",Font.PLAIN,13));
        card.add(addressLabel);

        JLabel duration=new JLabel(hours+" "+(hours==1?"Hour":"Hours")+"  •  "+guests+" Guest(s)");
        duration.setBounds(760,35,300,25);
        duration.setForeground(Color.WHITE);
        duration.setFont(new Font("Times New Roman",Font.PLAIN,15));
        card.add(duration);

        JLabel date=new JLabel(new java.text.SimpleDateFormat("dd MMM yyyy").format(bookingDate));
        date.setBounds(760,65,300,25);
        date.setForeground(GOLD);
        date.setFont(new Font("Times New Roman",Font.PLAIN,14));
        card.add(date);

        JLabel descriptionLabel=new JLabel(description);
        descriptionLabel.setBounds(760,95,350,25);
        descriptionLabel.setForeground(new Color(180,190,205));
        descriptionLabel.setFont(new Font("Times New Roman",Font.PLAIN,13));
        card.add(descriptionLabel);

        RoundedButton2 tableButton=new RoundedButton2("Select Table");
        tableButton.setBounds(1150,55,170,45);
        tableButton.setBackground(BLUE);
        tableButton.setForeground(Color.WHITE);
        tableButton.setFont(new Font("Times New Roman",Font.BOLD,17));
        tableButton.setFocusPainted(false);
        tableButton.setBorderPainted(false);
        tableButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.add(tableButton);

        tableButton.addActionListener(e->openTablePanel(
            restaurantId,
            restaurantName,
            destination,
            guests,
            hours,
            bookingDate,
            startTime,
            endTime
        ));

        resultsPanel.add(card);
        resultsPanel.add(Box.createVerticalStrut(15));
    }

    private void openTablePanel(
        int restaurantId,
        String restaurantName,
        String destination,
        int guests,
        long hours,
        Date bookingDate,
        Date startTime,
        Date endTime){

    	RestaurantTableSelection tablePanel =
                new RestaurantTableSelection(
                        restaurantId,
                        restaurantName,
                        destination,
                        guests,
                        hours,
                        bookingDate,
                        startTime,
                        endTime,
                        pages,
                        cardLayout,
                        walletPage,
                        transactionPage,
                        ticketPage
                );
    	pages.add(tablePanel, "RESTAURANT_TABLES");

        cardLayout.show(pages, "RESTAURANT_TABLES");
    }
}