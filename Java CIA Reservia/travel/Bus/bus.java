package cia;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class bus extends JPanel {

    private final Color NAVY=new Color(11,22,40);
    private final Color PANEL=new Color(17,31,53);
    private final Color BLUE=new Color(20,80,180);
    private final Color GOLD=new Color(212,175,55);

    private JComboBox<String> fromBox,toBox;
    private JSpinner dateSpinner,adultSpinner,childSpinner;
    private JPanel resultsPanel,pages;
    private java.util.List<BusCard> busCards = new java.util.ArrayList<>();
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
    
    public bus(JPanel pages,CardLayout cardLayout,Wallet walletPage,transactionPage transactionPage,ticket ticketPage) {
    	this.pages=pages;
    	this.cardLayout=cardLayout;
    	this.walletPage=walletPage;
    	this.transactionPage=transactionPage;
        this.ticketPage = ticketPage;
        setLayout(new BorderLayout());

        JPanel train1=new JPanel();
        train1.setBackground(NAVY);
        train1.setLayout(null);

        JLabel titleIcon=new JLabel("🚌");
        titleIcon.setBounds(25,18,100,60);
        titleIcon.setForeground(GOLD);
        titleIcon.setFont(new Font("Segoe UI Symbol",Font.PLAIN,50));
        titleIcon.setHorizontalAlignment(SwingConstants.CENTER);
        train1.add(titleIcon);

        JLabel title=new JLabel("Bus Booking");
        title.setBounds(60,17,300,50);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Times New Roman",Font.BOLD,30));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        train1.add(title);

        JLabel subtitle=new JLabel("Search buses, compare routes and book your journey");
        subtitle.setBounds(80,37,400,60);
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Times New Roman",Font.PLAIN,15));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        train1.add(subtitle);

        JButton myBookings=new JButton("My Bookings");
        myBookings.setBounds(1300,30,200,40);
        myBookings.setForeground(GOLD);
        myBookings.setFont(new Font("Times New Roman",Font.PLAIN,20));
        myBookings.setFocusPainted(false);
        myBookings.setBorderPainted(false);
        myBookings.setContentAreaFilled(false);
        myBookings.setCursor(new Cursor(Cursor.HAND_CURSOR));
        train1.add(myBookings);

        RoundedPanel search=new RoundedPanel();
        search.setBounds(30,100,1450,250);
        search.setLayout(null);
        search.setBackground(PANEL);
        train1.add(search);

        String[] bus_stations={"Mumbai","Nashik","Pune","Aurangabad","Kolhapur"};

        fromBox=new JComboBox<>(bus_stations);
        fromBox.setSelectedItem("Mumbai");
        styleComboBox(fromBox);
        JPanel fromPanel=createComboPanel("From",fromBox);
        fromPanel.setBounds(40,35,220,75);
        search.add(fromPanel);

        toBox=new JComboBox<>(bus_stations);
        toBox.setSelectedItem("Pune");
        styleComboBox(toBox);
        JPanel toPanel=createComboPanel("To",toBox);
        toPanel.setBounds(400,35,220,75);
        search.add(toPanel);

        RoundedButton swapButton=new RoundedButton("⇄");
        swapButton.setBounds(300,55,55,50);
        swapButton.setFont(new Font("Segoe UI Symbol",Font.BOLD,20));
        swapButton.setForeground(Color.WHITE);
        swapButton.setBackground(BLUE);
        swapButton.setBorderPainted(false);
        swapButton.setFocusPainted(false);
        swapButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        search.add(swapButton);

        swapButton.addActionListener(e -> {
            Object from=fromBox.getSelectedItem();
            Object to=toBox.getSelectedItem();
            fromBox.setSelectedItem(to);
            toBox.setSelectedItem(from);
        });

        JLabel dateLabel=new JLabel("Departure Date");
        dateLabel.setBounds(820,40,220,25);
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        search.add(dateLabel);

        SpinnerDateModel dateModel=new SpinnerDateModel(new Date(),null,null,java.util.Calendar.DAY_OF_MONTH);
        dateSpinner=new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner,"dd MMM yyyy"));
        dateSpinner.setFont(new Font("Times New Roman",Font.PLAIN,16));
        dateSpinner.setBounds(820,70,220,40);
        search.add(dateSpinner);

        JLabel adultLabel=new JLabel("Adults");
        adultLabel.setBounds(1100,40,100,25);
        adultLabel.setForeground(Color.WHITE);
        adultLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        search.add(adultLabel);

        adultSpinner=new JSpinner(new SpinnerNumberModel(1,1,10,1));
        adultSpinner.setFont(new Font("Times New Roman",Font.PLAIN,16));
        adultSpinner.setBounds(1100,70,100,40);
        search.add(adultSpinner);

        JLabel childLabel=new JLabel("Children");
        childLabel.setBounds(1230,40,100,25);
        childLabel.setForeground(Color.WHITE);
        childLabel.setFont(new Font("Times New Roman",Font.PLAIN,15));
        search.add(childLabel);

        childSpinner=new JSpinner(new SpinnerNumberModel(0,0,10,1));
        childSpinner.setFont(new Font("Times New Roman",Font.PLAIN,16));
        childSpinner.setBounds(1230,70,100,40);
        search.add(childSpinner);
        
        adultSpinner.addChangeListener(e -> updateAllPrices());
        childSpinner.addChangeListener(e -> updateAllPrices());

        JLabel fareInfo=new JLabel("Children fare is 50% of adult fare");
        fareInfo.setBounds(1100,113,250,25);
        fareInfo.setForeground(new Color(180,190,205));
        fareInfo.setFont(new Font("Times New Roman",Font.ITALIC,13));
        search.add(fareInfo);

        RoundedButton2 searchButton=new RoundedButton2("Search Buses");
        searchButton.setBounds(600,150,250,50);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(BLUE);
        searchButton.setFont(new Font("Times New Roman",Font.BOLD,18));
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        search.add(searchButton);

        searchButton.addActionListener(e -> searchBuses());

        resultsPanel=new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel,BoxLayout.Y_AXIS));
        resultsPanel.setBackground(NAVY);
        resultsPanel.setBorder(new EmptyBorder(0,0,0,0));

        JScrollPane scrollPane=new JScrollPane(resultsPanel);
        scrollPane.setBounds(30,370,1450,350);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(NAVY);
        train1.add(scrollPane);

        add(train1,BorderLayout.CENTER);
        setVisible(true);
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

    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    private void searchBuses() {
        String from=fromBox.getSelectedItem().toString();
        String to=toBox.getSelectedItem().toString();
        String date=new SimpleDateFormat("yyyy-MM-dd").format((Date)dateSpinner.getValue());

        if(from.equals(to)) {
            JOptionPane.showMessageDialog(this,"From and To stations cannot be the same.");
            return;
        }

        resultsPanel.removeAll();
        busCards.clear();

        String sql="SELECT * FROM busdetails WHERE source_city=? AND destination_city=?";

        try(Connection con=getConnection(); PreparedStatement pst=con.prepareStatement(sql)) {
            pst.setString(1,from);
            pst.setString(2,to);
            ResultSet rs=pst.executeQuery();

            boolean found=false;

            while(rs.next()) {
                found=true;

           
                String busName=rs.getString("bus_name");
                String busNumber=rs.getString("bus_number");
                String departure=rs.getString("departure_time");
                String arrival=rs.getString("arrival_time");
                double price=rs.getDouble("price");

                addBusResult(busName,busNumber,from,to,departure,arrival,price,date);
            }

            if(!found) {
                JLabel noResult=new JLabel("No buses found for this route.");
                noResult.setForeground(Color.WHITE);
                noResult.setFont(new Font("Times New Roman",Font.PLAIN,18));
                noResult.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultsPanel.add(Box.createVerticalStrut(30));
                resultsPanel.add(noResult);
            }

        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(this,"Database Error:\n"+ex.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

  
    private void addBusResult(String busName,String busNumber,String from,String to,String departure,String arrival,double adultFare,String date) {
        int adults=(Integer)adultSpinner.getValue();
        int children=(Integer)childSpinner.getValue();
        long childFare=(long)adultFare/2;
        long total=(long)((adults*adultFare)+(children*childFare));

        RoundedPanel card=new RoundedPanel();
        card.setLayout(null);
        card.setMaximumSize(new Dimension(1400,150));
        card.setPreferredSize(new Dimension(1400,150));
        card.setMinimumSize(new Dimension(1400,150));
        card.setBackground(PANEL);

        JLabel name=new JLabel(busName+" ("+busNumber+")");
        name.setBounds(30,15,450,30);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Times New Roman",Font.BOLD,20));
        card.add(name);

        JLabel route=new JLabel(from+"  →  "+to);
        route.setBounds(30,55,450,25);
        route.setForeground(Color.WHITE);
        route.setFont(new Font("Times New Roman",Font.PLAIN,16));
        card.add(route);

        JLabel time=new JLabel(departure+"  -  "+arrival);
        time.setBounds(30,90,300,25);
        time.setForeground(Color.WHITE);
        time.setFont(new Font("Times New Roman",Font.BOLD,17));
        card.add(time);

        JLabel priceTitle=new JLabel("Total Price");
        priceTitle.setBounds(850,20,180,25);
        priceTitle.setForeground(Color.WHITE);
        priceTitle.setFont(new Font("Times New Roman",Font.PLAIN,14));
        card.add(priceTitle);

        JLabel priceLabel=new JLabel("₹"+total);
        priceLabel.setBounds(850,45,220,35);
        priceLabel.setForeground(GOLD);
        priceLabel.setFont(new Font("Times New Roman",Font.BOLD,23));
        card.add(priceLabel);

        JLabel fareDetails=new JLabel("Adult ₹"+adultFare+" | Child ₹"+childFare);
        fareDetails.setBounds(850,82,250,25);
        fareDetails.setForeground(Color.WHITE);
        fareDetails.setFont(new Font("Times New Roman",Font.PLAIN,13));
        card.add(fareDetails);

        RoundedButton2 bookButton=new RoundedButton2("Book");
        bookButton.setBounds(1150,45,150,45);
        bookButton.setBackground(BLUE);
        bookButton.setForeground(Color.WHITE);
        bookButton.setFont(new Font("Times New Roman",Font.BOLD,17));
        bookButton.setFocusPainted(false);
        bookButton.setBorderPainted(false);
        bookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.add(bookButton);

        bookButton.addActionListener(e -> {
        	  int currentAdults = (Integer) adultSpinner.getValue();
      	    int currentChildren = (Integer) childSpinner.getValue();

      	    long currentChildFare = (long)adultFare / 2;

      	    long currentTotal =
      	        (long) ((currentAdults * adultFare) +
      	                (currentChildren * currentChildFare));

      	    busShowSeatSelection(busName,
      	    	    busNumber,
      	    	    from,
      	    	    to,
      	    	    date,
      	    	    departure,
      	    	    arrival,
      	    	    currentAdults,
      	    	    currentChildren,
      	    	    currentTotal);
        });

        BusCard tc=new BusCard(card,priceLabel,fareDetails,adultFare);
        busCards.add(tc);

        resultsPanel.add(card);
        resultsPanel.add(Box.createVerticalStrut(15));
    }
   


    private void bookTrain(String BusName,String date,String from,String to,int adults,int children,double adultFare,double total) {

        int choice=JOptionPane.showConfirmDialog(
                this,
                "Bus: "+BusName+
                "\nFrom: "+from+
                "\nTo: "+to+
                "\nDate: "+date+
                "\nAdults: "+adults+
                "\nChildren: "+children+
                "\nTotal Fare: ₹"+String.format("%.2f",total)+
                "\n\nConfirm booking?",
                "Confirm Booking",
                JOptionPane.YES_NO_OPTION
        );

        if(choice!=JOptionPane.YES_OPTION) return;

            }

   
    
    private void updateAllPrices() {
    	int adults = (Integer)adultSpinner.getValue();
    	int children = (Integer)childSpinner.getValue();
    	for(BusCard tc:busCards) {
    		tc.updatePrice(adults, children);
    	}
    	resultsPanel.revalidate();
    	resultsPanel.repaint();
    }
    
    class BusCard {
    	RoundedPanel card;
    	JLabel priceLabel,fareDetails;
    	double adultFare;
    	
    	BusCard(RoundedPanel card, JLabel priceLabel, JLabel fareDetails, double adultFare) {
    		this.card = card;
    		this.priceLabel = priceLabel;
    		this.fareDetails = fareDetails;
    		this.adultFare = adultFare;
    	}
    	
    	void updatePrice(int adults,int children) {
    		double childFare = adultFare/2;
    		long total = (long)((adults*adultFare)+(children*childFare));
    		priceLabel.setText("₹"+total);
    		fareDetails.setText("Adult: ₹"+String.format("%.2f",adultFare)+"| Child: ₹"+String.format("%.2f",childFare));
    	}
    }
    
    private void busShowSeatSelection(
            String busName,
            String busNumber,
            String from,
            String to,
            String date,
            String departure,
            String arrival,
            int adults,
            int children,
            long total) {

        BusSeatSelectionPanel busSeatPanel =
            new BusSeatSelectionPanel(
                busName,
                busNumber,
                from,
                to,
                date,
                departure,
                arrival,
                adults,
                children,
                total,
                pages,
                cardLayout,
                walletPage,
                transactionPage,
                ticketPage
            );

        pages.add(busSeatPanel, "BUSSEATS");

        cardLayout.show(pages, "BUSSEATS");
    }
}
