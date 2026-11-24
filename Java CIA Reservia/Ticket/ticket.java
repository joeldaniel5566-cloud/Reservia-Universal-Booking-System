package cia;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class RoundPanel extends JPanel {

    RoundPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(15, 40, 55));

        g2.fillRoundRect(
            0,
            0,
            getWidth() - 1,
            getHeight() - 1,
            30,
            30
        );

        g2.setColor(new Color(180, 150, 60));
        g2.setStroke(new BasicStroke(1));

        g2.drawRoundRect(
            1,
            1,
            getWidth() - 3,
            getHeight() - 3,
            30,
            30
        );

        g2.drawLine(
            15,
            80,
            getWidth() - 20,
            80
        );

        g2.dispose();
    }
}


public class ticket extends JPanel {

    private final Color NAVY = new Color(11, 22, 40);
    private final Color GOLD = new Color(212, 175, 55);

    private JPanel pages;
    private CardLayout cardLayout;
    private JPanel p1;
    private RoundPanel r1;

    private JLabel uid;
    private JLabel bid;
    private JLabel type;
    private JLabel name;
    private JLabel location;
    private JLabel details;
    private JLabel date;
    private JLabel time;
    private JLabel charge;

    private JButton down;
    private JButton back;
    private JLabel title;
    private JLabel head;
    private JLabel con;

	JLabel amt;


    public ticket(JPanel pages, CardLayout cardLayout) {

        this.pages = pages;
        this.cardLayout = cardLayout;

        setLayout(null);
        setBackground(NAVY);

		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(new Color(11,22,40));
		p1.setBounds(0, 0, 1536, 800);
		add(p1);
		
		r1 = new RoundPanel();
		r1.setLayout(null);
		r1.setBackground(new Color(11,22,40));
		r1.setBounds(455, 135, 610, 440);
		p1.add(r1);
		
		title = new JLabel("Reservia");
		title.setForeground(new Color(212,175,55));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Times new Roman",Font.BOLD,35));
		title.setBounds(130, 15, 300, 60);
		r1.add(title);
		
		head = new JLabel("Ticket");
		head.setForeground(Color.white);
		head.setFont(new Font("Times new Roman",Font.PLAIN,55));
		head.setBounds(665, 45, 300, 40);
		p1.add(head);
		
		con = new JLabel("Booking Confirmed");
		con.setForeground(Color.white);
		con.setHorizontalAlignment(SwingConstants.CENTER);
		con.setFont(new Font("Times new Roman", Font.PLAIN, 25));
		con.setBounds(132,90, 300, 60);
		r1.add(con);
				
		uid = new JLabel ("User id: ");
		uid.setForeground(Color.white);
		uid.setFont(new Font("Times new Roman", Font.PLAIN,18));
		uid.setBounds(35, 155, 300, 35);
		r1.add(uid);
		
		bid = new JLabel ("Booking id: ");
		bid.setForeground(Color.white);
		bid.setFont(new Font("Times new Roman", Font.PLAIN,18));
		bid.setBounds(315, 155, 300, 35);
		r1.add(bid);

		type = new JLabel ("Type: ");
		type.setForeground(Color.white);
		type.setFont(new Font("Times new Roman", Font.PLAIN,18));
		type.setBounds(35, 190, 300, 35);
		r1.add(type);
		
		name = new JLabel("Name: ");
		name.setForeground(Color.white);
		name.setFont(new Font("Times new Roman", Font.PLAIN,18));
		name.setBounds(315, 190, 300,35);
		r1.add(name);
		
		location = new JLabel("Location: ");
		location.setForeground(Color.white);
		location.setFont(new Font("Times new Roman", Font.PLAIN,18));
		location.setBounds(35, 225, 300,35);
		r1.add(location);
		
		details = new JLabel("Details: ");
		details.setForeground(Color.white);
		details.setFont(new Font("Times new Roman", Font.PLAIN,18));
		details.setBounds(315, 225, 300,35);
		r1.add(details);
		
		date = new JLabel("Date: ");
		date.setForeground(Color.white);
		date.setFont(new Font("Times new Roman", Font.PLAIN,18));
		date.setBounds(35, 260, 300,35);
		r1.add(date);
		
		time = new JLabel("");
		time.setForeground(Color.white);
		time.setFont(new Font("Times new Roman", Font.PLAIN,18));
		time.setBounds(315, 260, 300,35);
		r1.add(time);
		
		amt = new JLabel("Amount");
		amt.setForeground(Color.white);
		amt.setFont(new Font("Times new Roman", Font.PLAIN,18));
		amt.setBounds(250, 305, 300,35);
		r1.add(amt);
		
		charge = new JLabel("₹0");
		charge.setForeground(Color.white);
		charge.setFont(new Font("Times new Roman", Font.PLAIN,55));
		charge.setBounds(215, 350, 300,45);
		r1.add(charge);
		
		down = new  JButton("Download Ticket");
		down.setBackground(new Color(212,175,55));
		down.setForeground(Color.black);
		down.setFont(new Font("Times new Roman",Font.PLAIN,25));
		down.setBounds(595, 615, 300, 50);
		down.setBorderPainted(false);
		down.setFocusPainted(false);
		p1.add(down);
				
		down.addActionListener(e -> {
			try {
		        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    } catch (Exception e1) {
		        e1.printStackTrace();
		    }
		    
		    JFileChooser chooser = new JFileChooser();

		    chooser.setSelectedFile(
		        new File("Reservia_Ticket.png")
		    );

		    int result = chooser.showSaveDialog(this);
		    
		    

		    if (result == JFileChooser.APPROVE_OPTION) {

		        File file = chooser.getSelectedFile();
		        try {
		            BufferedImage image = new BufferedImage(
		                r1.getWidth(), 
		                r1.getHeight(), 
		                BufferedImage.TYPE_INT_RGB
		            );
		            
		            Graphics2D g2d = image.createGraphics();
		            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		            r1.paint(g2d);
		            g2d.dispose();
		            
		            ImageIO.write(image, "png", file);
		            
		            JOptionPane.showMessageDialog(this, "Ticket downloaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
		        }catch (IOException ex) {
		            JOptionPane.showMessageDialog(this, "Error saving ticket: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        }
		    }
		});
		
		  back = new JButton("← Back");

	        back.setBounds(20, 20, 100, 40);
	        back.setForeground(new Color(212, 175, 55));
	        back.setBackground(new Color(11, 22, 40));
	        back.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        back.setFocusPainted(false);
	        back.setBorderPainted(false);
	        back.setContentAreaFilled(false);
	        
	        back.addActionListener(e->{
	        	cardLayout.show(pages, "HOME");
	        });
		p1.add(back);
    }

    public void showTicket(
        int userId,
        long bookingId,
        String bookingType,
        String bookingName,
        String bookingLocation,
        String bookingDetails,
        String bookingDate,
        String bookingTime,
        long amount
    ) {

        uid.setText("User ID: " + userId);

        bid.setText("Booking ID: " + bookingId);

        type.setText("Type: " + bookingType);

        name.setText("Name: " + bookingName);

        location.setText("Location: " + bookingLocation);

        details.setText("Details: " + bookingDetails);

        date.setText("Date: " + bookingDate);
        if(bookingType=="Hotel") {date.setText("Check-In: " + bookingDate);}
        else {date.setText("Date: " + bookingDate);}
        
        if(bookingType=="Hotel") {time.setText("Check-Out: " + bookingTime);}
        else{time.setText("Time: " + bookingTime);}

        

        charge.setText("₹" + amount);

        cardLayout.show(
            pages,
            "TICKET"
        );
    }

    private void downloadTicket() {

        JFileChooser chooser = new JFileChooser();

        chooser.setSelectedFile(
            new File("Reservia_Ticket.png")
        );

        int result =
            chooser.showSaveDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();

        try {

            BufferedImage image =
                new BufferedImage(
                    r1.getWidth(),
                    r1.getHeight(),
                    BufferedImage.TYPE_INT_RGB
                );

            Graphics2D g2d =
                image.createGraphics();

            g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            r1.paint(g2d);

            g2d.dispose();

            ImageIO.write(
                image,
                "png",
                file
            );

            JOptionPane.showMessageDialog(
                this,
                "Ticket downloaded successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Error saving ticket:\n"
                + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}