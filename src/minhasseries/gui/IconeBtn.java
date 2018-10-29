package minhasseries.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class IconeBtn extends JButton implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;

	private Dimension expandedSize = new Dimension(80, 80); // Tamanho padrão do icone expandido
	private Dimension collapsedSize = new Dimension(50, 50); // Tamanho padrão do icone minimizado

	private Color idleColor = new Color(200, 200, 200);
	private Color gradientIdleColor = new Color(240, 240, 240);

	private Color clickedColor = new Color(255, 255, 255);
	private Color gradientClickedColor = new Color(255, 255, 255);

	private boolean expandIcons = false; // Se true, os icones também serão expandidos
	private boolean isExpanded = false;
	private boolean mousePressed = false;

	private int mx, my;

	public IconeBtn(String icon, String toolTip) {
		super(new ImageIcon(icon));
		setToolTipText(toolTip);
		setOpaque(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setPreferredSize(collapsedSize);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public void setExpandIcons(boolean b) {
		expandIcons = b;
	}

	public void setCollapsedSize(Dimension d) {
		collapsedSize = d;
	}

	public void setExpandedSize(Dimension d) {
		expandedSize = d;
	}

	public Dimension getCollapsedSize() {
		return collapsedSize;
	}

	public Dimension getExpandedSize() {
		return expandedSize;
	}

	private void setTamanho(Dimension tamanho) {
		setPreferredSize(tamanho);
		setMaximumSize(tamanho);
		setMinimumSize(tamanho);
		setSize(tamanho);
	}

	private void setExpanded(boolean b) {
		isExpanded = b;
	}

	private void setMouseXY(int x, int y) {
		mx = x;
		my = y;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color color = mousePressed ? clickedColor : idleColor;
		Color gradColor = mousePressed ? gradientClickedColor : gradientIdleColor;
		double wFactor = isExpanded ? (expandedSize.getWidth() / collapsedSize.getWidth()) : 1;
		double hFactor = isExpanded ? (expandedSize.getHeight() / collapsedSize.getHeight()) : 1;
		Point gradP1 = new Point(isExpanded ? mx : 0, isExpanded ? -30 : 0);
		Point gradP2 = new Point(isExpanded ? getWidth() - mx : 0, isExpanded ? my : getHeight());
		GradientPaint grad = new GradientPaint(gradP1.x, gradP1.y, color, gradP2.x, gradP2.y, gradColor);
		g2d.setPaint(grad);
		g2d.fill(new Ellipse2D.Float(0, 0, getWidth(), getHeight()));
		if (expandIcons) {
			int imgw = (int) (getIcon().getIconWidth() * wFactor);
			int imgh = (int) (getIcon().getIconHeight() * hFactor);
			int imgx = (getWidth() / 2) - (imgw / 2);
			int imgy = (getHeight() / 2) - (imgh / 2);
			g2d.drawImage(((ImageIcon) getIcon()).getImage(), imgx, imgy, imgw, imgh, null);
		} else {
			super.paintComponent(g); // Pinta icone com tamanho padrão
		}
		g2d.dispose();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setTamanho(getExpandedSize());
		setExpanded(true);
		getParent().revalidate();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setTamanho(getCollapsedSize());
		setExpanded(false);
		getParent().revalidate();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setMouseXY(e.getX(), e.getY());
	}
}