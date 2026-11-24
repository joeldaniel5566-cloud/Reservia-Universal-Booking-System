package cia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;

class RoundedTextField extends JTextField {

    public RoundedTextField() {
        super();
        setBorder(null); 
        setOpaque(false);
        setBackground(Color.WHITE);
        
    }


@Override
protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    int arc = getHeight();
    
    g2.setColor(getBackground());
    g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arc, arc));

    super.paintComponent(g2);
    g2.dispose();
}

@Override
protected void paintBorder(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int arc = getHeight();
    g2.setColor(new Color(220,225,230)); // Border color
    g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arc, arc));
    g2.dispose();
}

@Override
public Insets getInsets() {
    return new Insets(0, 18, 0, 18); 
}
}