
package cia;import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.*;

public class movie extends JPanel {
	JPanel mov,head,mainPanel,pages;
	JScrollPane scrollPane;
	RoundedPanel mov1,mov2,mov3,mov4,mov5,mov6,mov7,mov8,mov9,mov10;
	JLabel icon,t1;
	
	JLabel Poster1,Poster2,Poster3,Poster4,Poster5,Poster6,Poster7,Poster8,Poster9,Poster10;
	
	JLabel name1,name2,name3,name4,name5,name6,name7,name8,name9,name10;
	
	JLabel price1,price2,price3,price4,price5,price6,price7,price8,price9,price10;
	
	JLabel rate1,rate2,rate3,rate4,rate5,rate6,rate7,rate8,rate9,rate10;
	
	JLabel th1,th2,th3,th4,th5,th6,th7,th8,th9,th10;
		
	JLabel r1,r2,r3,r4,r5,r6,r7,r8,r9,r10;

	JLabel g1,g2,g3,g4,g5,g6,g7,g8,g9,g10;
	
	JLabel time1,time2,time3,time4,time5,time6,time7,time8,time9,time10;
	
	JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;
	
	CardLayout cardLayout;
    Wallet walletPage;
    transactionPage transactionPage;
    ticket ticketPage;
    
    private void bookMovie(String movieName, int ticketPrice,String theatre,String showTime) {

        String input = JOptionPane.showInputDialog(
                this,
                "How many seats do you want to book? (1-10)"
        );

        if (input == null) {
            return;
        }

        try {
            int seatsCount = Integer.parseInt(input);

            if (seatsCount < 1 || seatsCount > 10) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a number between 1 and 10."
                );
                return;
            }

            long totalPrice= (long) ticketPrice * seatsCount;
            TheatreSeatSelectionPanel seatingGrid =
                    new TheatreSeatSelectionPanel(
                            movieName,
                            seatsCount,
                            totalPrice,
                            theatre,
                            showTime,
                            pages,
                            cardLayout,
                            walletPage,
                            transactionPage,
                            ticketPage
                    );

            pages.add(seatingGrid, "MOVIE_SEATS");
            cardLayout.show(pages, "MOVIE_SEATS");

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number."
            );
        }
    }
    
	movie(JPanel pages, CardLayout cardLayout,Wallet walletPage,transactionPage transactionPage,ticket ticketPage){
	    this.pages = pages;
	    this.cardLayout = cardLayout;
	    this.walletPage = walletPage;
	    this.transactionPage = transactionPage;
	    this.ticketPage = ticketPage;

	    setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(11, 22, 40));
        mainPanel.setLayout(null);

		
		head = new JPanel();
		head.setBackground(new Color(11,22,40));	
		head.setLayout(null);
		head.setBounds(35, 55, 400, 50);		
		
		icon = new JLabel ("🎬");
		icon.setForeground(new Color(212,175,55));
		icon.setFont(new Font("Seoge UI Symbol", Font.BOLD, 35));
		icon.setBounds(0, 0, 300, 40);
		head.add(icon);
		
		t1 = new JLabel ("Movie Booking");
		t1.setForeground(Color.white);
		t1.setFont(new Font("Times new Roman",Font.BOLD,37));
		t1.setHorizontalAlignment(SwingConstants.CENTER);
		t1.setBounds(25, 0, 300, 45);
		head.add(t1);
		mainPanel.add(head);
		

		mov = new JPanel();
		mov.setBackground(new Color(11,22,40));
		mov.setLayout(null);
		mov.setPreferredSize(new Dimension(1536,900));
		
		//mov1
		mov1 = new RoundedPanel();
		mov1.setLayout(null);
		mov1.setBounds(180, 15, 500, 200);
		mov.add(mov1);
		
		Poster1 = new JLabel();
		
		ImageIcon icon = new ImageIcon("poster/dhurandhar.jpg");

			Image img = icon.getImage();

			Image scaled = img.getScaledInstance(
					110, 160, Image.SCALE_SMOOTH
			);

			Poster1.setIcon(new ImageIcon(scaled));
			Poster1.setBounds(20, 20, 130, 160);
			mov1.add(Poster1);
		
		
		name1 = new JLabel ("Dhurandhar");
		name1.setForeground(new Color (212,175,55));
		name1.setFont(new Font("Times new Roman",Font.BOLD,37));
		name1.setHorizontalAlignment(SwingConstants.CENTER);
		name1.setBounds(85, 15, 300, 50);
		mov1.add(name1);
		
		g1 = new JLabel (" - Action thriller");
		g1.setForeground(new Color(212,175,55));
		g1.setFont(new Font("Times new Roman",Font.PLAIN,18));
		g1.setHorizontalAlignment(SwingConstants.CENTER);
		g1.setBounds(245, 25, 300, 40);
		mov1.add(g1);
		
		rate1 = new JLabel ("Rating: 8.2");
		rate1.setForeground(Color.white); 
		rate1.setFont(new Font("Times new Roman",Font.PLAIN,15));
		rate1.setBounds(145, 60, 300, 40);
		mov1.add(rate1);
		
		r1 = new JLabel ("4h 10m");
		r1.setForeground(Color.white);
		r1.setFont(new Font("Times new Roman",Font.PLAIN,15));
		r1.setBounds(255, 60, 300, 40);
		mov1.add(r1);
		
		th1 = new JLabel ("PVR INOX");
		th1.setForeground(Color.white);
		th1.setFont(new Font("Times new Roman",Font.PLAIN,15));
		th1.setBounds(145,90,300,40);
		mov1.add(th1);

		time1 = new JLabel ("4pm show");
		time1.setForeground(Color.white);
		time1.setFont(new Font("Times new Roman",Font.PLAIN,15));
		time1.setBounds(255, 90, 300, 40);
		mov1.add(time1);
		
		price1 = new JLabel ("₹ 800 ");
		price1.setForeground(Color.white);
		price1.setFont(new Font ("Times new Roman",Font.BOLD,30));
		price1.setBounds(145,125,300,60);
//		rate1.setHorizontalAlignment(SwingConstants.CENTER);
		mov1.add(price1);
				
		b1 = new JButton("Book");
		b1.setBackground(new Color(212,175,55));
		b1.setForeground(Color.black);
		b1.setFont(new Font("Times new Roman",Font.PLAIN,20));
		b1.setHorizontalAlignment(SwingConstants.CENTER);
		b1.setBorderPainted(false);
		b1.setFocusPainted(false);
		b1.setBounds(265, 140, 130, 40);
		mov1.add(b1);
		
		b1.addActionListener(e -> bookMovie("Dhurandhar", 800,"PVR INOX", "4pm"));


		//mov2
		mov2 = new RoundedPanel();
		mov2.setLayout(null);
		mov2.setBounds(850, 15, 500, 200);
		mov.add(mov2);

		Poster2 = new JLabel();
		
		ImageIcon icon1 = new ImageIcon("poster/cocktail2.jpg");

			Image img1 = icon1.getImage();

			Image scaled1 = img1.getScaledInstance(
					110, 160, Image.SCALE_SMOOTH
			);

			Poster2.setIcon(new ImageIcon(scaled1));
			Poster2.setBounds(20, 20, 130, 160);
			mov2.add(Poster2);
		
		
		name2 = new JLabel ("Cocktail 2");
		name2.setForeground(new Color (212,175,55));
		name2.setFont(new Font("Times new Roman",Font.BOLD,37));
		name2.setHorizontalAlignment(SwingConstants.CENTER);
		name2.setBounds(70, 15, 300, 50);
		mov2.add(name2);
		
		g2 = new JLabel (" - Romantic Comedy");
		g2.setForeground(new Color(212,175,55));
		g2.setFont(new Font("Times new Roman",Font.PLAIN,18));
		g2.setHorizontalAlignment(SwingConstants.CENTER);
		g2.setBounds(235, 25, 300, 40);
		mov2.add(g2);
		
		rate2 = new JLabel ("Rating: 6.6");
		rate2.setForeground(Color.white); 
		rate2.setFont(new Font("Times new Roman",Font.PLAIN,15));
		rate2.setBounds(145, 60, 300, 40);
		mov2.add(rate2);
		
		r2 = new JLabel ("2h 50m");
		r2.setForeground(Color.white);
		r2.setFont(new Font("Times new Roman",Font.PLAIN,15));
		r2.setBounds(255, 60, 300, 40);
		mov2.add(r2);
		
		th2 = new JLabel ("Metro");
		th2.setForeground(Color.white);
		th2.setFont(new Font("Times new Roman",Font.PLAIN,15));
		th2.setBounds(145,90,300,40);
		mov2.add(th2);
		
		time2 = new JLabel ("11am show");
		time2.setForeground(Color.white);
		time2.setFont(new Font("Times new Roman",Font.PLAIN,15));
		time2.setBounds(255, 90, 300, 40);
		mov2.add(time2);
		
		price2 = new JLabel ("₹ 500 ");
		price2.setForeground(Color.white);
		price2.setFont(new Font ("Times new Roman",Font.BOLD,30));
		price2.setBounds(145,125,300,60);
//		rate1.setHorizontalAlignment(SwingConstants.CENTER);
		mov2.add(price2);
		
		b2 = new JButton("Book");
		b2.setBackground(new Color(212,175,55));
		b2.setForeground(Color.black);
		b2.setFont(new Font("Times new Roman",Font.PLAIN,20));
		b2.setHorizontalAlignment(SwingConstants.CENTER);
		b2.setBorderPainted(false);
		b2.setFocusPainted(false);
		b2.setBounds(255, 140, 130, 40);
		mov2.add(b2);
		b2.addActionListener(e -> bookMovie("Cocktail2", 500, "Metro", "11am"));
		
		//mov3
		mov3 = new RoundedPanel();
		mov3.setLayout(null);
		mov3.setBounds(180, 235, 500, 200);
		mov.add(mov3);
		
		Poster3 = new JLabel();
		ImageIcon icon2 = new ImageIcon("poster/alpha.jpg");

		Image img2 = icon2.getImage();

		Image scaled2 = img2.getScaledInstance(
		    110, 160, Image.SCALE_SMOOTH
		);

		Poster3.setIcon(new ImageIcon(scaled2));
		Poster3.setBounds(20, 20, 130, 160);
		mov3.add(Poster3);
		
		name3 = new JLabel ("Alpha");
		name3.setForeground(new Color (212,175,55));
		name3.setFont(new Font("Times new Roman",Font.BOLD,37));
		name3.setHorizontalAlignment(SwingConstants.CENTER);
		name3.setBounds(35, 15, 300, 50);
		mov3.add(name3);
		
		g3 = new JLabel (" - Action Thiller");
		g3.setForeground(new Color(212,175,55));
		g3.setFont(new Font("Times new Roman",Font.PLAIN,18));
		g3.setHorizontalAlignment(SwingConstants.CENTER);
		g3.setBounds(145, 25, 300, 40);
		mov3.add(g3);
		
		rate3 = new JLabel ("Rating: 3.0");
		rate3.setForeground(Color.white); 
		rate3.setFont(new Font("Times new Roman",Font.PLAIN,15));
		rate3.setBounds(145, 60, 300, 40);
		mov3.add(rate3);
		
		r3 = new JLabel ("2h 20m");
		r3.setForeground(Color.white);
		r3.setFont(new Font("Times new Roman",Font.PLAIN,15));
		r3.setBounds(255, 60, 300, 40);
		mov3.add(r3);
		
		th3 = new JLabel ("Cinepolis Mall");
		th3.setForeground(Color.white);
		th3.setFont(new Font("Times new Roman",Font.PLAIN,15));
		th3.setBounds(145,90,300,40);
		mov3.add(th3);
		
		time3 = new JLabel ("1pm show");
		time3.setForeground(Color.white);
		time3.setFont(new Font("Times new Roman",Font.PLAIN,15));
		time3.setBounds(255, 90, 300, 40);
		mov3.add(time3);
		
		b3 = new JButton("Book");
		b3.setBackground(new Color(212,175,55));
		b3.setForeground(Color.black);
		b3.setFont(new Font("Times new Roman",Font.PLAIN,20));
		b3.setHorizontalAlignment(SwingConstants.CENTER);
		b3.setBorderPainted(false);
		b3.setFocusPainted(false);
		b3.setBounds(255, 140, 130, 40);
		mov3.add(b3);
		b3.addActionListener(e -> bookMovie("Alpha", 250, "Cinepolis Mall", "1pm"));
		
		price3 = new JLabel ("₹ 250 ");
		price3.setForeground(Color.white);
		price3.setFont(new Font ("Times new Roman",Font.BOLD,30));
		price3.setBounds(145,125,300,60);
//		rate1.setHorizontalAlignment(SwingConstants.CENTER);
		mov3.add(price3);
		
		
		//mov4
		mov4 = new RoundedPanel();
		mov4.setLayout(null);
		mov4.setBounds(850, 235, 500, 200);
		mov.add(mov4);
		
		Poster4 = new JLabel();
		ImageIcon icon3 = new ImageIcon("poster/themask.jpg");

		Image img3 = icon3.getImage();

		Image scaled3 = img3.getScaledInstance(
		    110, 160, Image.SCALE_SMOOTH
		);
		Poster4.setIcon(new ImageIcon(scaled3));
		Poster4.setBounds(20, 20, 130, 160);
		mov4.add(Poster4);

		name4 = new JLabel ("The Mask");
		name4.setForeground(new Color (212,175,55));
		name4.setFont(new Font("Times new Roman",Font.BOLD,37));
		name4.setHorizontalAlignment(SwingConstants.CENTER);
		name4.setBounds(65, 15, 300, 50);
		mov4.add(name4);
		
		g4 = new JLabel (" - Action Comedy");
		g4.setForeground(new Color(212,175,55));
		g4.setFont(new Font("Times new Roman",Font.PLAIN,18));
		g4.setHorizontalAlignment(SwingConstants.CENTER);
		g4.setBounds(210, 25, 300, 40);
		mov4.add(g4);
		
		rate4 = new JLabel ("Rating: 7.0");
		rate4.setForeground(Color.white); 
		rate4.setFont(new Font("Times new Roman",Font.PLAIN,15));
		rate4.setBounds(145, 60, 300, 40);
		mov4.add(rate4);
		
		r4 = new JLabel ("1h 41m");
		r4.setForeground(Color.white);
		r4.setFont(new Font("Times new Roman",Font.PLAIN,15));
		r4.setBounds(255, 60, 300, 40);
		mov4.add(r4);
		
		th4 = new JLabel ("Mukta A2");
		th4.setForeground(Color.white);
		th4.setFont(new Font("Times new Roman",Font.PLAIN,15));
		th4.setBounds(145,90,300,40);
		mov4.add(th4);
		
		time4 = new JLabel ("8pm show");
		time4.setForeground(Color.white);
		time4.setFont(new Font("Times new Roman",Font.PLAIN,15));
		time4.setBounds(255, 90, 300, 40);
		mov4.add(time4);
		
		price4 = new JLabel ("₹ 450 ");
		price4.setForeground(Color.white);
		price4.setFont(new Font ("Times new Roman",Font.BOLD,30));
		price4.setBounds(145,125,300,60);
//		rate1.setHorizontalAlignment(SwingConstants.CENTER);
		mov4.add(price4);
		
		b4 = new JButton("Book");
		b4.setBackground(new Color(212,175,55));
		b4.setForeground(Color.black);
		b4.setFont(new Font("Times new Roman",Font.PLAIN,20));
		b4.setHorizontalAlignment(SwingConstants.CENTER);
		b4.setBorderPainted(false);
		b4.setFocusPainted(false);
		b4.setBounds(255, 140, 130, 40);
		mov4.add(b4);
		b4.addActionListener(e -> bookMovie("The Mask", 450, "Mukta A2", "8pm"));
		
		
		//mov5
		mov5 = new RoundedPanel();
		mov5.setLayout(null);
		mov5.setBounds(180, 455, 500, 200);
		mov.add(mov5);
		
		Poster5 = new JLabel();
		ImageIcon icon4 = new ImageIcon("poster/michael.jpg");

		Image img4 = icon4.getImage();

		Image scaled4 = img4.getScaledInstance(
		    110, 160, Image.SCALE_SMOOTH
		);
		Poster5.setIcon(new ImageIcon(scaled4));
		Poster5.setBounds(20, 20, 130, 160);
		mov5.add(Poster5);

		name5 = new JLabel ("Michael");
		name5.setForeground(new Color (212,175,55));
		name5.setFont(new Font("Times new Roman",Font.BOLD,37));
		name5.setHorizontalAlignment(SwingConstants.CENTER);
		name5.setBounds(50, 15, 300, 50);
		mov5.add(name5);
		
		g5 = new JLabel (" - Drama Musical");
		g5.setForeground(new Color(212,175,55));
		g5.setFont(new Font("Times new Roman",Font.PLAIN,18));
		g5.setHorizontalAlignment(SwingConstants.CENTER);
		g5.setBounds(180, 25, 300, 40);
		mov5.add(g5);
		
		rate5 = new JLabel ("Rating: 7.4");
		rate5.setForeground(Color.white); 
		rate5.setFont(new Font("Times new Roman",Font.PLAIN,15));
		rate5.setBounds(145, 60, 300, 40);
		mov5.add(rate5);
		
		r5 = new JLabel ("2h 7m");
		r5.setForeground(Color.white);
		r5.setFont(new Font("Times new Roman",Font.PLAIN,15));
		r5.setBounds(255, 60, 300, 40);
		mov5.add(r5);
		
		th5 = new JLabel ("Regal Cinema");
		th5.setForeground(Color.white);
		th5.setFont(new Font("Times new Roman",Font.PLAIN,15));
		th5.setBounds(145,90,300,40);
		mov5.add(th5);
		
		time5 = new JLabel ("10pm show");
		time5.setForeground(Color.white);
		time5.setFont(new Font("Times new Roman",Font.PLAIN,15));
		time5.setBounds(255, 90, 300, 40);
		mov5.add(time5);
		
		price5 = new JLabel ("₹ 550 ");
		price5.setForeground(Color.white);
		price5.setFont(new Font ("Times new Roman",Font.BOLD,30));
		price5.setBounds(145,125,300,60);
//		rate1.setHorizontalAlignment(SwingConstants.CENTER);
		mov5.add(price5);
		
		b5 = new JButton("Book");
		b5.setBackground(new Color(212,175,55));
		b5.setForeground(Color.black);
		b5.setFont(new Font("Times new Roman",Font.PLAIN,20));
		b5.setHorizontalAlignment(SwingConstants.CENTER);
		b5.setBorderPainted(false);
		b5.setFocusPainted(false);
		b5.setBounds(255, 140, 130, 40);
		mov5.add(b5);
		b5.addActionListener(e -> bookMovie("Michael", 550, "Regal Cinema", "10pm"));
		
		//mov6
		mov6 = new RoundedPanel();
		mov6.setLayout(null);
		mov6.setBounds(850, 455, 500, 200);
		mov.add(mov6);
		
		Poster6 = new JLabel();
		ImageIcon icon5 = new ImageIcon("poster/dhamal4.jpg");

		Image img5 = icon5.getImage();

		Image scaled5 = img5.getScaledInstance(
		    110, 160, Image.SCALE_SMOOTH
		);
		Poster6.setIcon(new ImageIcon(scaled5));
		Poster6.setBounds(20, 20, 130, 160);
		mov6.add(Poster6);

		name6 = new JLabel ("Dhamaal 4");
		name6.setForeground(new Color (212,175,55));
		name6.setFont(new Font("Times new Roman",Font.BOLD,37));
		name6.setHorizontalAlignment(SwingConstants.CENTER);
		name6.setBounds(75, 15, 300, 50);
		mov6.add(name6);
		
		g6 = new JLabel (" - Comedy");
		g6.setForeground(new Color(212,175,55));
		g6.setFont(new Font("Times new Roman",Font.PLAIN,18));
		g6.setHorizontalAlignment(SwingConstants.CENTER);
		g6.setBounds(200, 25, 300, 40);
		mov6.add(g6);
		
		rate6 = new JLabel ("Rating: 8.1");
		rate6.setForeground(Color.white); 
		rate6.setFont(new Font("Times new Roman",Font.PLAIN,15));
		rate6.setBounds(145, 60, 300, 40);
		mov6.add(rate6);
		
		r6 = new JLabel ("2h 23m");
		r6.setForeground(Color.white);
		r6.setFont(new Font("Times new Roman",Font.PLAIN,15));
		r6.setBounds(255, 60, 300, 40);
		mov6.add(r6);
		
		th6 = new JLabel ("G7 Multiplex");
		th6.setForeground(Color.white);
		th6.setFont(new Font("Times new Roman",Font.PLAIN,15));
		th6.setBounds(145,90,300,40);
		mov6.add(th6);
		
		time6 = new JLabel ("8am show");
		time6.setForeground(Color.white);
		time6.setFont(new Font("Times new Roman",Font.PLAIN,15));
		time6.setBounds(255, 90, 300, 40);
		mov6.add(time6);
		
		price6 = new JLabel ("₹ 950 ");
		price6.setForeground(Color.white);
		price6.setFont(new Font ("Times new Roman",Font.BOLD,30));
		price6.setBounds(145,125,300,60);
//		rate1.setHorizontalAlignment(SwingConstants.CENTER);
		mov6.add(price6);
		
		b6 = new JButton("Book");
		b6.setBackground(new Color(212,175,55));
		b6.setForeground(Color.black);
		b6.setFont(new Font("Times new Roman",Font.PLAIN,20));
		b6.setHorizontalAlignment(SwingConstants.CENTER);
		b6.setBorderPainted(false);
		b6.setFocusPainted(false);
		b6.setBounds(255, 140, 130, 40);
		mov6.add(b6);
		b6.addActionListener(e -> bookMovie("Dhamaal 4", 950, "G7 Multiplex", "8am"));
		
		//mov7
		mov7 = new RoundedPanel();
		mov7.setLayout(null);
		mov7.setBounds(180, 675, 500, 200);
		mov.add(mov7);
		
		Poster7 = new JLabel();
		ImageIcon icon6 = new ImageIcon("poster/bhootbangla.jpg");

		Image img6 = icon6.getImage();

		Image scaled6 = img6.getScaledInstance(
		    110, 160, Image.SCALE_SMOOTH
		);
		Poster7.setIcon(new ImageIcon(scaled6));
		Poster7.setBounds(20, 20, 130, 160);
		mov7.add(Poster7);

		name7 = new JLabel ("Bhoot Bangla");
		name7.setForeground(new Color (212,175,55));
		name7.setFont(new Font("Times new Roman",Font.BOLD,37));
		name7.setHorizontalAlignment(SwingConstants.CENTER);
		name7.setBounds(95, 20, 300, 50);
		mov7.add(name7);
		
		g7 = new JLabel (" - Comedy Horror");
		g7.setForeground(new Color(212,175,55));
		g7.setFont(new Font("Times new Roman",Font.PLAIN,18));
		g7.setHorizontalAlignment(SwingConstants.CENTER);
		g7.setBounds(265, 30, 300, 40);
		mov7.add(g7);
		
		rate7 = new JLabel ("Rating: 5.0");
		rate7.setForeground(Color.white); 
		rate7.setFont(new Font("Times new Roman",Font.PLAIN,15));
		rate7.setBounds(145, 60, 300, 40);
		mov7.add(rate7);
		
		r7 = new JLabel ("2h 44m");
		r7.setForeground(Color.white);
		r7.setFont(new Font("Times new Roman",Font.PLAIN,15));
		r7.setBounds(255, 60, 300, 40);
		mov7.add(r7);
		
		th7 = new JLabel ("Chitra Cinema");
		th7.setForeground(Color.white);
		th7.setFont(new Font("Times new Roman",Font.PLAIN,15));
		th7.setBounds(145,90,300,40);
		mov7.add(th7);
		
		time7 = new JLabel ("8pm show");
		time7.setForeground(Color.white);
		time7.setFont(new Font("Times new Roman",Font.PLAIN,15));
		time7.setBounds(255, 90, 300, 40);
		mov7.add(time7);
		
		price7 = new JLabel ("₹ 350 ");
		price7.setForeground(Color.white);
		price7.setFont(new Font ("Times new Roman",Font.BOLD,30));
		price7.setBounds(145,125,300,60);
		mov7.add(price7);
		
		b7 = new JButton("Book");
		b7.setBackground(new Color(212,175,55));
		b7.setForeground(Color.black);
		b7.setFont(new Font("Times new Roman",Font.PLAIN,20));
		b7.setHorizontalAlignment(SwingConstants.CENTER);
		b7.setBorderPainted(false);
		b7.setFocusPainted(false);
		b7.setBounds(255, 140, 130, 40);
		mov7.add(b7);
		b7.addActionListener(e -> bookMovie("Bhoot Bangla", 350, "Chitra Cinema", "8pm"));
		
		//mov8
		mov8 = new RoundedPanel();
		mov8.setLayout(null);
		mov8.setBounds(850, 675, 500, 200);
		mov.add(mov8);
		
		Poster8 = new JLabel();
		ImageIcon icon7 = new ImageIcon("poster/spiderman.jpg");

		Image img7 = icon7.getImage();

		Image scaled7 = img7.getScaledInstance(
		    110, 160, Image.SCALE_SMOOTH
		);
		Poster8.setIcon(new ImageIcon(scaled7));
		Poster8.setBounds(20, 20, 130, 160);
		mov8.add(Poster8);

		name8 = new JLabel ("SpiderMan");
		name8.setForeground(new Color (212,175,55));
		name8.setFont(new Font("Times new Roman",Font.BOLD,37));
		name8.setHorizontalAlignment(SwingConstants.CENTER);
		name8.setBounds(80, 15, 300, 50);
		mov8.add(name8);
		
		g8 = new JLabel (" - Action Adventure");
		g8.setForeground(new Color(212,175,55));
		g8.setFont(new Font("Times new Roman",Font.PLAIN,18));
		g8.setHorizontalAlignment(SwingConstants.CENTER);
		g8.setBounds(240, 25, 300, 40);
		mov8.add(g8);
		
		rate8 = new JLabel ("Rating: 8");
		rate8.setForeground(Color.white); 
		rate8.setFont(new Font("Times new Roman",Font.PLAIN,15));
		rate8.setBounds(145, 60, 300, 40);
		mov8.add(rate8);
		
		r8 = new JLabel ("2h 30m");
		r8.setForeground(Color.white);
		r8.setFont(new Font("Times new Roman",Font.PLAIN,15));
		r8.setBounds(255, 60, 300, 40);
		mov8.add(r8);
		
		th8 = new JLabel ("Liberty Cinema");
		th8.setForeground(Color.white);
		th8.setFont(new Font("Times new Roman",Font.PLAIN,15));
		th8.setBounds(145,90,300,40);
		mov8.add(th8);
		
		time8 = new JLabel ("10am show");
		time8.setForeground(Color.white);
		time8.setFont(new Font("Times new Roman",Font.PLAIN,15));
		time8.setBounds(255, 90, 300, 40);
		mov8.add(time8);
		
		price8 = new JLabel ("₹ 590 ");
		price8.setForeground(Color.white);
		price8.setFont(new Font ("Times new Roman",Font.BOLD,30));
		price8.setBounds(145,125,300,60);
//		rate1.setHorizontalAlignment(SwingConstants.CENTER);
		mov8.add(price8);
		
		b8 = new JButton("Book");
		b8.setBackground(new Color(212,175,55));
		b8.setForeground(Color.black);
		b8.setFont(new Font("Times new Roman",Font.PLAIN,20));
		b8.setHorizontalAlignment(SwingConstants.CENTER);
		b8.setBorderPainted(false);
		b8.setFocusPainted(false);
		b8.setBounds(255, 140, 130, 40);
		mov8.add(b8);
		b8.addActionListener(e -> bookMovie("Spiderman", 590, "Liberty Cinema", "10am"));
		
		//mov6
		mov9 = new RoundedPanel();
		mov9.setLayout(null);
		mov9.setBounds(180, 895, 500, 200);
		mov.add(mov9);
		
		Poster9 = new JLabel();
		ImageIcon icon8 = new ImageIcon("poster/samarth.jpg");

		Image img8 = icon8.getImage();

		Image scaled8 = img8.getScaledInstance(
		    110, 160, Image.SCALE_SMOOTH
		);
		Poster9.setIcon(new ImageIcon(scaled8));
		Poster9.setBounds(20, 20, 130, 160);
		mov9.add(Poster9);

		name9 = new JLabel ("Samarth");
		name9.setForeground(new Color (212,175,55));
		name9.setFont(new Font("Times new Roman",Font.BOLD,37));
		name9.setHorizontalAlignment(SwingConstants.CENTER);
		name9.setBounds(55, 20, 300, 50);
		mov9.add(name9);
		
		g9 = new JLabel (" - Drama Devotional");
		g9.setForeground(new Color(212,175,55));
		g9.setFont(new Font("Times new Roman",Font.PLAIN,18));
		g9.setHorizontalAlignment(SwingConstants.CENTER);
		g9.setBounds(195, 30, 300, 40);
		mov9.add(g9);
		
		rate9 = new JLabel ("Rating: 8.86");
		rate9.setForeground(Color.white); 
		rate9.setFont(new Font("Times new Roman",Font.PLAIN,15));
		rate9.setBounds(145, 60, 300, 40);
		mov9.add(rate9);
		
		r9 = new JLabel ("2h 26m");
		r9.setForeground(Color.white);
		r9.setFont(new Font("Times new Roman",Font.PLAIN,15));
		r9.setBounds(255, 60, 300, 40);
		mov9.add(r9);
		
		th9 = new JLabel ("G7 Multiplex");
		th9.setForeground(Color.white);
		th9.setFont(new Font("Times new Roman",Font.PLAIN,15));
		th9.setBounds(145,90,300,40);
		mov9.add(th9);
		
		time9 = new JLabel ("8am show");
		time9.setForeground(Color.white);
		time9.setFont(new Font("Times new Roman",Font.PLAIN,15));
		time9.setBounds(255, 90, 300, 40);
		mov9.add(time9);
		
		price9 = new JLabel ("₹ 850 ");
		price9.setForeground(Color.white);
		price9.setFont(new Font ("Times new Roman",Font.BOLD,30));
		price9.setBounds(145,125,300,60);
//		rate1.setHorizontalAlignment(SwingConstants.CENTER);
		mov9.add(price9);
		
		b9 = new JButton("Book");
		b9.setBackground(new Color(212,175,55));
		b9.setForeground(Color.black);
		b9.setFont(new Font("Times new Roman",Font.PLAIN,20));
		b9.setHorizontalAlignment(SwingConstants.CENTER);
		b9.setBorderPainted(false);
		b9.setFocusPainted(false);
		b9.setBounds(255, 140, 130, 40);
		mov9.add(b9);
		b9.addActionListener(e -> bookMovie("Samarth", 850, "G7 Multiplex", "8am"));
		
		//mov7
		mov10 = new RoundedPanel();
		mov10.setLayout(null);
		mov10.setBounds(850, 895, 500, 200);
		mov.add(mov10);
		
		Poster10 = new JLabel();
		ImageIcon icon9 = new ImageIcon("poster/mirzapur.jpg");

		Image img9 = icon9.getImage();

		Image scaled9 = img9.getScaledInstance(
		    110, 160, Image.SCALE_SMOOTH
		);
		Poster10.setIcon(new ImageIcon(scaled9));
		Poster10.setBounds(20, 20, 130, 160);
		mov10.add(Poster10);

		name10 = new JLabel ("Mirzapur");
		name10.setForeground(new Color (212,175,55));
		name10.setFont(new Font("Times new Roman",Font.BOLD,37));
		name10.setHorizontalAlignment(SwingConstants.CENTER);
		name10.setBounds(65, 15, 300, 50);
		mov10.add(name10);
		
		g10 = new JLabel (" - Gangster Thriller");
		g10.setForeground(new Color(212,175,55));
		g10.setFont(new Font("Times new Roman",Font.PLAIN,18));
		g10.setHorizontalAlignment(SwingConstants.CENTER);
		g10.setBounds(210, 25, 300, 40);
		mov10.add(g10);
		
		rate10 = new JLabel ("Rating: 'A' ");
		rate10.setForeground(Color.white); 
		rate10.setFont(new Font("Times new Roman",Font.PLAIN,15));
		rate10.setBounds(145, 60, 300, 40);
		mov10.add(rate10);
		
		r10 = new JLabel ("3h 17m");
		r10.setForeground(Color.white);
		r10.setFont(new Font("Times new Roman",Font.PLAIN,15));
		r10.setBounds(255, 60, 300, 40);
		mov10.add(r10);
		
		th10 = new JLabel ("Chitra Cinema");
		th10.setForeground(Color.white);
		th10.setFont(new Font("Times new Roman",Font.PLAIN,15));
		th10.setBounds(145,90,300,40);
		mov10.add(th10);
		
		time10 = new JLabel ("8pm show");
		time10.setForeground(Color.white);
		time10.setFont(new Font("Times new Roman",Font.PLAIN,15));
		time10.setBounds(255, 90, 300, 40);
		mov10.add(time10);
		
		price10 = new JLabel ("₹ 750 ");
		price10.setForeground(Color.white);
		price10.setFont(new Font ("Times new Roman",Font.BOLD,30));
		price10.setBounds(145,125,300,60);
		mov10.add(price10);
		
		b10 = new JButton("Book");
		b10.setBackground(new Color(212,175,55));
		b10.setForeground(Color.black);
		b10.setFont(new Font("Times new Roman",Font.PLAIN,20));
		b10.setHorizontalAlignment(SwingConstants.CENTER);
		b10.setBorderPainted(false);
		b10.setFocusPainted(false);
		b10.setBounds(255, 140, 130, 40);
		mov10.add(b10);
		b10.addActionListener(e -> bookMovie("Mirzapur", 750, "Chitra Cinema", "8pm"));
		
		mov.setPreferredSize(new Dimension(1500, 1200));
		scrollPane= new JScrollPane(mov);

        scrollPane.setBorder(null);

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        scrollPane.setBounds(0, 120, 1536, 650);

        mainPanel.add(scrollPane);
		
        add(mainPanel, BorderLayout.CENTER);

	}
	

}