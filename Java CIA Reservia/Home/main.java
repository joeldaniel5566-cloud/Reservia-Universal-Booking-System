package cia;
import java.awt.*;

import javax.swing.*;

public class main extends JFrame {

    JPanel pages,navbar,navbtn,logo,rightSpace;
    JLabel l1;
    CardLayout cardLayout;
    JButton home,profile,booking,wallet;
	JButton[] navButtons;
	Color navColor;

    public main() {

        setTitle("Reservia");
        setSize(1536, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        pages = new JPanel(cardLayout);
        
        navbar = new JPanel(new GridLayout(1,3));
		logo = new JPanel(new FlowLayout(FlowLayout.LEFT,20,10));
		navbtn = new JPanel(new FlowLayout(FlowLayout.CENTER,25,10));
	    rightSpace = new JPanel(new FlowLayout(FlowLayout.RIGHT,25,0));
	    
	    home = new JButton("Home");
	    booking = new JButton("Bookings");
	    wallet = new JButton("Wallet");
	    profile = new JButton("Profile");
	    
	    navButtons = new JButton[]{home,booking,wallet,profile};
	    for (JButton b:navButtons) {
	    	b.setOpaque(false);
	    	b.setContentAreaFilled(false);
	    	b.setBorderPainted(false);
	    	b.setFont(new Font("Times New Roman",Font.BOLD,18));
	    	b.setForeground(new Color(212,175,55));
	    	b.setFocusPainted(false);
	    }
	    
	    l1 = new JLabel("Reservia");
	    l1.setFont(new Font("Brush Script MT",Font.PLAIN,24));
	    l1.setForeground(new Color(212,175,55));
	    logo.add(l1);
	    
	    navbtn.add(home);
	    navbtn.add(booking);
	    navbtn.add(wallet);
	    navbtn.add(profile);
	    
	    JButton profileIcon = new JButton() {

	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);

	            Graphics2D g2 = (Graphics2D) g;

	            g2.setRenderingHint(
	                RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON
	            );

	            Color gold = new Color(212, 175, 55);

	            // Circle
	            g2.setColor(gold);
	            g2.setStroke(new BasicStroke(2));

	            g2.drawOval(3, 3, 38, 38);

	            // Head
	            g2.fillOval(14, 9, 16, 16);

	            // Shoulders / body
	            g2.fillRoundRect(10, 25, 24, 15, 10, 8);
	        }
	    };

	    profileIcon.setFocusPainted(false);
	    profileIcon.setBorderPainted(false);
	    profileIcon.setContentAreaFilled(false);
	    profileIcon.setOpaque(false); 
	    JPanel profileContainer = new JPanel(new FlowLayout(
	    	    FlowLayout.RIGHT, 15, 5
	    	));

	    	profileContainer.setOpaque(false);

	    	profileIcon.setPreferredSize(new Dimension(45, 45));

	    	profileContainer.add(profileIcon);
	    	

	    	rightSpace.add(profileContainer);
	    
	    navColor = new Color(11,22,40);
	    navbar.setBackground(navColor);
	    logo.setBackground(navColor);
	    navbtn.setBackground(navColor);
	    profileIcon.setBackground(navColor);
	    rightSpace.setBackground(navColor);
	    
	    navbar.add(logo);
	    navbar.add(navbtn);
	    navbar.add(rightSpace);
	    
	    navbar.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(255,255,255,50)));
	    
        home homePage = new home(pages, cardLayout);
        Wallet walletPage = new Wallet(pages, cardLayout);
        transactionPage transactionPage =
                new transactionPage(pages, cardLayout);
        walletPage.setTransactionPage(transactionPage);
        ticket ticketPage =new ticket(pages, cardLayout);
        Booking travel = new Booking(pages,cardLayout);
        train trainPage = new train(pages, cardLayout,walletPage,
                transactionPage,ticketPage);
        addmoney addmoney = new addmoney(pages,cardLayout,walletPage);
        walletPage.setAddMoneyPage(addmoney);
        bus busPage = new bus(pages, cardLayout,walletPage,
                transactionPage,ticketPage);
        flight flightPage = new flight(pages, cardLayout,walletPage,
                transactionPage,ticketPage);
        movie moviePage= new movie(pages,cardLayout,walletPage,transactionPage,ticketPage);
        hotel hotelPage= new hotel(pages,cardLayout,walletPage,transactionPage,ticketPage);
        restaurant restaurantPage= 
        		new restaurant(pages,cardLayout,walletPage,transactionPage,ticketPage);
        profile profilePage =new profile(pages,cardLayout,walletPage,transactionPage);
        book bookingPage =new book(pages,cardLayout,ticketPage,walletPage);
        refund refundPage=new refund(pages,cardLayout);
        walletPage.setRefundPage(refundPage);
        
        pages.add(homePage, "HOME");
        pages.add(walletPage, "WALLET");
        pages.add(transactionPage, "TRANSACTIONS");
        pages.add(travel, "TRAVEL");
        pages.add(trainPage, "TRAINS");
        pages.add(addmoney,"ADD");
        pages.add(busPage,"BUSES");
        pages.add(flightPage,"FLIGHTS");
        pages.add(moviePage,"MOVIES");
        pages.add(hotelPage,"HOTELS");
        pages.add(restaurantPage,"RESTAURANTS");
        pages.add(ticketPage, "TICKET");
        pages.add(profilePage, "PROFILE");
        pages.add(bookingPage,"BOOKINGS");
        pages.add(refundPage,"REFUNDS");
        
        home.addActionListener(e -> {
        	homePage.resetSearchBar();
            cardLayout.show(pages, "HOME");
        });

        wallet.addActionListener(e -> {
            cardLayout.show(pages, "WALLET");
        });
        
        booking.addActionListener(e -> {
        	bookingPage.refreshBookings();
            cardLayout.show(pages, "BOOKINGS");
        });
        
        profile.addActionListener(e -> {
        	profilePage.refreshProfile();
        	cardLayout.show(pages, "PROFILE");
        });
        
        profileIcon.addActionListener(e -> {
        	profilePage.refreshProfile();
        	cardLayout.show(pages, "PROFILE");
        });
        add(navbar, BorderLayout.NORTH);
        add(pages, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new main();
        });
    }
}