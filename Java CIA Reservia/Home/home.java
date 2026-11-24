package cia;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class home extends JPanel{
	JPanel main;
	RoundedPanel travel,movie,hotel,restaurant;
	JLabel l2,l3,l4,ticon,tl,tl1,eicon,E,E1,hicon,H,H1,ricon,R,R1;
	RoundedTextField t1;
	RoundedButton travelButton, movieButton, hotelButton, restaurantButton;
	
	
	JPanel pages;
	CardLayout cardLayout;
	private String placeholder = "Search movies, destination, hotels...";
	
	private void performSearch() {

	    String search = t1.getText().trim().toLowerCase();

	    if(search.isEmpty() ||
	       search.equals("search movies, destinations, hotels...")) {

	        JOptionPane.showMessageDialog(
	            this,
	            "Please enter something to search.",
	            "Search",
	            JOptionPane.WARNING_MESSAGE
	        );

	        return;
	    }

	    if(search.contains("movie") ||
	       search.contains("movies") ||
	       search.contains("cinema") ||
	       search.contains("film")) {

	        cardLayout.show(pages, "MOVIES");
	        return;
	    }

	    if(search.contains("hotel") ||
	       search.contains("hotels") ||
	       search.contains("stay") ||
	       search.contains("stays")) {

	        cardLayout.show(pages, "HOTELS");
	        return;
	    }

	    if(search.contains("restaurant") ||
	       search.contains("restaurants") ||
	       search.contains("food") ||
	       search.contains("dine")) {

	        cardLayout.show(pages, "RESTAURANTS");
	        return;
	    }

	    if(search.contains("travel")) {

	        cardLayout.show(pages, "TRAVEL");
	        return;
	    }
	    if(
	 	   search.contains("flight") ||
	 	   search.contains("flights")) {
	 	   cardLayout.show(pages, "FLIGHTS");
	 	    return;
	 	  }
	    if(search.contains("train") ||
	    		search.contains("trains")){
		 	   cardLayout.show(pages, "TRAINS");
		 	    return;
		 	  }
	    if(search.contains("bus") ||
	 	   search.contains("buses")){
		 	   cardLayout.show(pages, "BUSES");
		 	    return;
		 	  }
	    JOptionPane.showMessageDialog(
	        this,
	        "No matching category found.\n\n" +
	        "Try searching for:\n" +
	        "Movies\n" +
	        "Hotels\n" +
	        "Restaurants\n" +
	        "Flights\n" +
	        "Trains\n" +
	        "Buses",
	        "Search",
	        JOptionPane.INFORMATION_MESSAGE
	    );
	}
	
	
	public home(JPanel pages,CardLayout cardLayout) {
		
		this.pages=pages;
		this.cardLayout=cardLayout;
		
		setLayout(new BorderLayout());
	    setBackground(new Color(11,22,40));
		
		
	    
	    main = new JPanel();
	    main.setLayout(null);
	    main.setBackground(new Color(11,22,40));
	    
	    l2 = new JLabel("Welcome Back!");
	    l2.setForeground(Color.WHITE);
	    l2.setFont(new Font("Times New Roman",Font.BOLD,50));
	    l2.setHorizontalAlignment(SwingConstants.CENTER);
	    l2.setBounds(518,45,500,50);
	    main.add(l2);
	    
	    l3 = new JLabel("Where would you want to go?");
	    l3.setForeground(Color.WHITE);
	    l3.setFont(new Font("Times New Roman",Font.PLAIN,20));
	    l3.setHorizontalAlignment(SwingConstants.CENTER);
	    l3.setBounds(518,105,500,35);
	    main.add(l3);
	    
	    JLabel search1=new JLabel("🔍");
	    search1.setBounds(980,165,400,45);
	    search1.setForeground(Color.WHITE);
	    search1.setFont(new Font("Segoe UI Symbol",Font.PLAIN,26));
	    
	    main.add(search1);
	    
	    t1 = new RoundedTextField();
	    t1.setBounds(568,165,400,45);
	    t1.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
	    t1.setHorizontalAlignment(SwingConstants.CENTER);
	    t1.setText("Search movies, destination, hotels...");
	    t1.setBackground(Color.WHITE);
	    main.add(t1);
	    
	    
	    t1.addFocusListener(new FocusAdapter() {
	    	@Override 
	    	public void focusGained(FocusEvent e) {
	    		if(t1.getText().equals(placeholder)) {
	    			t1.setText("");
	    		}
	    	}
	    	@Override
	    	public void focusLost(FocusEvent e) {
	    		if(t1.getText().isEmpty()) {
	    			t1.setText(placeholder);
	    		}
	    	}
	    });
	    
	    t1.addActionListener(e -> performSearch());
	    
	    l4 = new JLabel("BOOK SOMETHING");
	    l4.setForeground(new Color(212,175,55));
	    l4.setBounds(150,230,400,45);
	    l4.setFont(new Font("Times New Roman",Font.BOLD,23));
	    main.add(l4);
	    
	    travel = new RoundedPanel();
	    travel.setLayout(null);
	    travel.setBounds(150,310,220,300);
	    main.add(travel);
	    ticon = new JLabel("✈");
        ticon.setBounds(10, 25, 200, 60);
        ticon.setForeground(new Color(212, 175, 55));
        ticon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 65));
        ticon.setHorizontalAlignment(SwingConstants.CENTER);

        travel.add(ticon);


        tl = new JLabel("Travel");
        tl.setBounds(10, 90, 200, 60);
        tl.setForeground(Color.WHITE);
        tl.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        tl.setHorizontalAlignment(SwingConstants.CENTER);

        travel.add(tl);


        tl1 = new JLabel("Flights | Train | Buses");
        tl1.setBounds(10, 120, 200, 60);
        tl1.setForeground(Color.WHITE);
        tl1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        tl1.setHorizontalAlignment(SwingConstants.CENTER);

        travel.add(tl1);


        travelButton = new RoundedButton("➜");
        travelButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        travelButton.setBounds(80, 190, 65, 65);

        travelButton.addActionListener(e->{
        	cardLayout.show(pages,"TRAVEL");
        });
        
        travel.add(travelButton);
        
        

	    
	    movie = new RoundedPanel();
        movie.setLayout(null);
        movie.setBounds(490, 310, 220, 300);

        main.add(movie);


        eicon = new JLabel("🎬");
        eicon.setBounds(10, 25, 200, 60);
        eicon.setForeground(new Color(212, 175, 55));
        eicon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 65));
        eicon.setHorizontalAlignment(SwingConstants.CENTER);

        movie.add(eicon);


        E = new JLabel("Movies");
        E.setBounds(10, 90, 200, 60);
        E.setForeground(Color.WHITE);
        E.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        E.setHorizontalAlignment(SwingConstants.CENTER);

        movie.add(E);


        E1 = new JLabel("Now Showing");
        E1.setBounds(10, 120, 200, 60);
        E1.setForeground(Color.WHITE);
        E1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        E1.setHorizontalAlignment(SwingConstants.CENTER);

        movie.add(E1);


        movieButton = new RoundedButton("➜");
        movieButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        movieButton.setBounds(80, 190, 65, 65);

        movie.add(movieButton);
        
        movieButton.addActionListener(e->{
        	cardLayout.show(pages, "MOVIES");
        	});

        hotel = new RoundedPanel();
        hotel.setLayout(null);
        hotel.setBounds(830, 310, 220, 300);

        main.add(hotel);


        hicon = new JLabel("🏨");
        hicon.setBounds(10, 25, 200, 60);
        hicon.setForeground(new Color(212, 175, 55));
        hicon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 65));
        hicon.setHorizontalAlignment(SwingConstants.CENTER);

        hotel.add(hicon);


        H = new JLabel("Stays");
        H.setBounds(10, 90, 200, 60);
        H.setForeground(Color.WHITE);
        H.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        H.setHorizontalAlignment(SwingConstants.CENTER);

        hotel.add(H);


        H1 = new JLabel("Hotels | Homestays");
        H1.setBounds(10, 120, 200, 60);
        H1.setForeground(Color.WHITE);
        H1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        H1.setHorizontalAlignment(SwingConstants.CENTER);

        hotel.add(H1);


        hotelButton = new RoundedButton("➜");
        hotelButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        hotelButton.setBounds(80, 190, 65, 65);

        hotel.add(hotelButton);
        
        hotelButton.addActionListener(e->{
        	cardLayout.show(pages,"HOTELS");
        });
        

        restaurant = new RoundedPanel();
        restaurant.setLayout(null);
        restaurant.setBounds(1170, 310, 220, 300);

        main.add(restaurant);


        ricon = new JLabel("🍽️");
        ricon.setBounds(10, 25, 200, 60);
        ricon.setForeground(new Color(212, 175, 55));
        ricon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 70));
        ricon.setHorizontalAlignment(SwingConstants.CENTER);

        restaurant.add(ricon);


        R = new JLabel("Restaurant");
        R.setBounds(10, 90, 200, 60);
        R.setForeground(Color.WHITE);
        R.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        R.setHorizontalAlignment(SwingConstants.CENTER);

        restaurant.add(R);


        R1 = new JLabel("Dine-In");
        R1.setBounds(10, 120, 200, 60);
        R1.setForeground(Color.WHITE);
        R1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        R1.setHorizontalAlignment(SwingConstants.CENTER);

        restaurant.add(R1);


        restaurantButton = new RoundedButton("➜");
        restaurantButton.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        restaurantButton.setBounds(80, 190, 65, 65);

        restaurant.add(restaurantButton);
        
        restaurantButton.addActionListener(e->{
        	cardLayout.show(pages,"RESTAURANTS");
        });

        add(main, BorderLayout.CENTER);
        
	}
	
	public void resetSearchBar() {
	    t1.setText(placeholder);
	}
	
}