package minhasseries.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import minhasseries.app.App;
import minhasseries.app.Util;
import minhasseries.modelos.Serie;

public final class SerieInfoDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private Serie serie;

	private static final Color COR_FUNDO = Color.WHITE;
	private static final Color COR_GRADIENTE = Color.BLACK;

	public SerieInfoDialog(Component component, Serie serie) {
		this.serie = serie;
		setTitle("Informações de " + serie.getNome());
		setResizable(false);
		setModal(true);
		initComponents();
		setLocationRelativeTo(component);
		setVisible(true);
	}

	private void initComponents() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(createCoverPanel(), BorderLayout.WEST);
		mainPanel.add(createTablePanel(), BorderLayout.EAST);
		setContentPane(mainPanel);
		pack();
	}

	private JLabel createCoverLabel() {
		ImageIcon icon;
		JLabel iconLbl = new JLabel();
		if (new File("res/posters/" + serie.getNome() + ".jpg").exists()) {
			ImageIcon ii = new ImageIcon(App.CAMINHO_POSTERS + serie.getNome() + ".jpg");
			Image img = ii.getImage();
			BufferedImage bi = new BufferedImage((int) (img.getWidth(null) * 2.0f), (int) (img.getHeight(null) * 2.0f),
					BufferedImage.TYPE_INT_RGB);
			Graphics g = bi.createGraphics();
			g.drawImage(img, 0, 0, bi.getWidth(), bi.getHeight(), null);
			icon = new ImageIcon(bi);
			iconLbl.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50, 20), 5));
		} else {
			icon = new ImageIcon(App.CAMINHO_ICONES + "128/video-player.png");
		}
		iconLbl.setIcon(icon);
		return iconLbl;
	}

	private JPanel createCoverPanel() {
		JPanel coverPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint redtowhite = new GradientPaint( //
						getWidth() / 2, 0, // Primeira posição do gradiente
						COR_FUNDO, // Primeira cor do gradiente
						getWidth() / 2, getHeight(), // Segunda posição do gradiente
						COR_GRADIENTE);// Segunda cor do gradiente
				g2d.setPaint(redtowhite);
				g2d.fill(new Rectangle2D.Float(0, 0, getWidth(), getHeight()));
			}
		};
		coverPanel.setLayout(new BoxLayout(coverPanel, BoxLayout.X_AXIS));
		coverPanel.add(createCoverLabel());
		coverPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		return coverPanel;
	}

	private JTable createTable() {
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] { //
				{ "Título: ", serie.getNome() }, //
				{ "Tipo: ", serie.getTipo() }, //
				{ "Duração: ", serie.getDuracao() }, //
				{ "País: ", serie.getPais() }, //
				{ "Idioma: ", serie.getIdioma() }, //
				{ "Emissora: ", serie.getEmissora() }, //
				{ "Transmissão: ", serie.getTransmissao() }, //
				{ "Nº Temporadas: ", serie.getNumTemporadas() }, //
				{ "Nº Episódios: ", serie.getNumEpisodios() }, //
		}, new String[] { "Descrição", "" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int x, int y) {
				return false;
			}
		});
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setFont(new Font("sansserif", Font.PLAIN, 17));
		table.setRowHeight(table.getRowHeight() + 15);
		table.setFillsViewportHeight(true);
		table.setShowGrid(true);
		table.setGridColor(new Color(90, 90, 90));
		return table;
	}

	private JButton createIMDBButton() {
		JButton imdbBtn = new JButton(new ImageIcon(App.CAMINHO_ICONES + "64/imdb.png"));
		imdbBtn.setIconTextGap(20);
		imdbBtn.setText("Pesquisar no IMDB");
		imdbBtn.setFont(new Font("Arial", Font.PLAIN, 20));
		imdbBtn.setToolTipText("Pesquisar no IMDB");
		imdbBtn.setBackground(new Color(150, 150, 150));
		imdbBtn.addActionListener((ActionEvent e) -> {
			Util.browse("https://www.imdb.com/find?ref_=nv_sr_fn&q=" + Util.toUTF8(serie.getNome()) + "&s=all");
		});
		return imdbBtn;
	}

	private JButton createGoogleButton() {
		JButton googleBtn = new JButton(new ImageIcon(App.CAMINHO_ICONES + "64/google.png"));
		googleBtn.setIconTextGap(20);
		googleBtn.setText("Pesquisar no Google");
		googleBtn.setFont(new Font("Arial", Font.PLAIN, 20));
		googleBtn.setToolTipText("Pesquisar no Google");
		googleBtn.setBackground(new Color(150, 150, 150));
		googleBtn.addActionListener((ActionEvent e) -> {
			Util.browse("https://www.google.com/search?q=" + Util.toUTF8(serie.getNome()));
		});
		return googleBtn;
	}

	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Mais Detalhes"));
		((TitledBorder) (buttonsPanel.getBorder())).setTitleFont(new Font("Arial", Font.PLAIN, 20));
		((TitledBorder) (buttonsPanel.getBorder())).setTitleJustification(TitledBorder.LEFT);
		buttonsPanel.add(createIMDBButton(), BorderLayout.CENTER);
		buttonsPanel.add(createGoogleButton(), BorderLayout.SOUTH);
		return buttonsPanel;
	}

	private JPanel createTablePanel() {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Informações"));
		((TitledBorder) (tablePanel.getBorder())).setTitleFont(new Font("Arial", Font.PLAIN, 20));
		((TitledBorder) (tablePanel.getBorder())).setTitleJustification(TitledBorder.LEFT);
		tablePanel.add(new JScrollPane(createTable()), BorderLayout.CENTER);
		tablePanel.add(createButtonsPanel(), BorderLayout.SOUTH);
		return tablePanel;
	}
}
