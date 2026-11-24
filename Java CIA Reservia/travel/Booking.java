package cia;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Booking extends JPanel{

	JPanel book1,pages,book;
	CardLayout cardLayout;
		
	RoundedPanel train,bus,airport,all;
	
	JLabel title,sub,b1,b2,b3,tr1,tr2,tr3,a1,a2,a3;

	RoundedButton bt,bt1,bt2;
	public Booking (JPanel pages,CardLayout cardLayout){
		
		this.pages=pages;
    	this.cardLayout=cardLayout;
    	setLayout(new BorderLayout());
 		setBackground(new Color(11,22,40));
       
        book1 = new JPanel();
        book1.setBackground(new Color(11,22,40));
        book1.setLayout(null);
       
        title = new JLabel ("Choose Your Travel Mode!");
        title.setForeground(Color.WHITE);
	    title.setFont(new Font("Times New Roman",Font.BOLD,50));
	    title.setHorizontalAlignment(SwingConstants.CENTER);
	    title.setBounds(460,100,600,50);
        book1.add(title);
        
        JButton back = new JButton("← Back");

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

        book1.add(back);
        
        
        train = new RoundedPanel ();
        train.setLayout(null);
        train.setBounds(301, 250, 220, 300);
        book1.add(train);
        
        tr1 = new JLabel("🚆");
        tr1.setBounds(10, 25, 200, 60);
        tr1.setForeground(new Color(212,175,55));
        tr1.setFont(new Font("Seoge UI Symbol",Font.PLAIN, 65));
        tr1.setHorizontalAlignment(SwingConstants.CENTER);
        train.add(tr1);
        
        tr2 = new JLabel ("Trains");
        tr2.setBounds(10, 90, 200, 60);
        tr2.setForeground(Color.white);
        tr2.setFont(new Font ("Times new Roman", Font.PLAIN,28));
        tr2.setHorizontalAlignment(SwingConstants.CENTER);
        train.add(tr2);
        
        tr3 = new JLabel ("Check available trains");
        tr3.setBounds(10, 120, 200, 60);
        tr3.setForeground(Color.white);
        tr3.setFont(new Font("Times new Roman",Font.PLAIN,15));
        tr3.setHorizontalAlignment(SwingConstants.CENTER);
        train.add(tr3);
        
        bt = new RoundedButton ("➜");
        bt.setFont(new Font("Seoge UI Symbol",Font.BOLD,15));
        bt.setForeground(Color.white);
        bt.setBackground(new Color(20,80,180));
        bt.setBounds(80, 190, 65, 65);
        train.add(bt);
        bt.addActionListener(e->{
        	cardLayout.show(pages, "TRAINS");
        });
        
        bus = new RoundedPanel ();
        bus.setLayout(null);
        bus.setBounds(650, 250, 220, 300);
        book1.add(bus);
        
        
        b1 = new JLabel("🚌");
        b1.setBounds(10, 25, 200, 60);
        b1.setForeground(new Color(212,175,55));
        b1.setFont(new Font("Seoge UI Symbol",Font.PLAIN, 65));
        b1.setHorizontalAlignment(SwingConstants.CENTER);
        bus.add(b1);
        
        b2 = new JLabel ("Buses");
        b2.setBounds(10, 90, 200, 60);
        b2.setForeground(Color.white);
        b2.setFont(new Font ("Times new Roman", Font.PLAIN,28));
        b2.setHorizontalAlignment(SwingConstants.CENTER);
        bus.add(b2);
        
        b3 = new JLabel ("Check available buses");
        b3.setBounds(10, 120, 200, 60);
        b3.setForeground(Color.white);
        b3.setFont(new Font("Times new Roman",Font.PLAIN,15));
        b3.setHorizontalAlignment(SwingConstants.CENTER);
        bus.add(b3);
        
        bt1 = new RoundedButton ("➜");
        bt1.setFont(new Font("Seoge UI Symbol",Font.BOLD,15));
        bt1.setForeground(Color.white);
        bt1.setBackground(new Color(20,80,180));
        bt1.setBounds(80, 190, 65, 65);
        bus.add(bt1);
        
        bt1.addActionListener(e->{
        	cardLayout.show(pages, "BUSES");
        });
        
        airport = new RoundedPanel ();
        airport.setLayout(null);
        airport.setBounds(1001, 250, 220, 300);
        book1.add(airport);
        
        
        a1 = new JLabel("✈");
        a1.setBounds(10, 25, 200, 60);
        a1.setForeground(new Color(212,175,55));
        a1.setFont(new Font("Seoge UI Symbol",Font.PLAIN, 65));
        a1.setHorizontalAlignment(SwingConstants.CENTER);
        airport.add(a1);
        
        a2 = new JLabel ("Air");
        a2.setBounds(10, 90, 200, 60);
        a2.setForeground(Color.white);
        a2.setFont(new Font ("Times new Roman", Font.PLAIN,28));
        a2.setHorizontalAlignment(SwingConstants.CENTER);
        airport.add(a2);
        
        a3 = new JLabel ("Check available trains");
        a3.setBounds(10, 120, 200, 60);
        a3.setForeground(Color.white);
        a3.setFont(new Font("Times new Roman",Font.PLAIN,15));
        a3.setHorizontalAlignment(SwingConstants.CENTER);
        airport.add(a3);
        
        bt2 = new RoundedButton ("➜");
        bt2.setFont(new Font("Seoge UI Symbol",Font.BOLD,15));
        bt2.setForeground(Color.white);
        bt2.setBackground(new Color(20,80,180));
        bt2.setBounds(80, 190, 65, 65);
        airport.add(bt2);
        
        bt2.addActionListener(e->{
        	cardLayout.show(pages, "FLIGHTS");
        });
        
        add(book1,BorderLayout.CENTER);
        }
}
