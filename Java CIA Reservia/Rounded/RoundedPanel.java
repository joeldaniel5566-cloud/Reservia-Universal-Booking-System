package cia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

class RoundedPanel extends JPanel {
	private int radius = 25;
	public RoundedPanel() {
		setOpaque(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(255,255,255,15));
		g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
		g2.setColor(new Color(255,255,255,60));
		g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
		g2.dispose();
		super.paintComponent(g);
	}
}

