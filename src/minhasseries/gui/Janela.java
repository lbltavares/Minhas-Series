package minhasseries.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.TableModelEvent;

import minhasseries.app.App;
import minhasseries.app.Util;
import minhasseries.gui.paineis.PainelFavoritos;
import minhasseries.gui.paineis.PainelPrincipal;
import minhasseries.gui.paineis.PainelRecomendados;
import minhasseries.modelos.ModeloTabela;

// Classe singleton
public final class Janela extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;

	private static Janela INSTANCE;

	public static synchronized Janela getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Janela();
		return INSTANCE;
	}

	public static final String ICONE = App.CAMINHO_ICONES + "24/television.png";

	private JTabbedPane tabbedPane;

	private Janela() {
		super(App.TITULO);
		initLookAndFeel();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		initComponents();
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(800, 700));
		addWindowListener(this);

		try {
			this.setIconImage(ImageIO.read(new File(ICONE)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initComponents() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setPreferredSize(new Dimension(800, 600));
		mainPanel.setBackground(new Color(220, 220, 220));

		tabbedPane = new JTabbedPane();
		initPainelSeries();
		initPainelFavoritos();
		initPainelRecomendados();
		mainPanel.add(tabbedPane, BorderLayout.CENTER);
		setContentPane(mainPanel);
		pack();
	}

	private void initPainelSeries() {
		PainelPrincipal painelPrincipal = new PainelPrincipal();
		tabbedPane.add(painelPrincipal);
		JLabel tab = new JLabel("Principal");
		ModeloTabela.getPrincipal().addTableModelListener((TableModelEvent e) -> {
			tab.setText("Principal (" + ModeloTabela.getPrincipal().getRowCount() + ")");
		});
		tab.setIcon(new ImageIcon(App.CAMINHO_ICONES + "24/player.png"));
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tab);
	}

	private void initPainelFavoritos() {
		PainelFavoritos painelFavoritos = new PainelFavoritos();
		tabbedPane.add(painelFavoritos);
		JLabel tab = new JLabel("Favoritos");
		ModeloTabela.getFavoritos().addTableModelListener((TableModelEvent e) -> {
			tab.setText("Favoritos (" + ModeloTabela.getFavoritos().getRowCount() + ")");
		});
		tab.setIcon(new ImageIcon(App.CAMINHO_ICONES + "24/star.png"));
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tab);
	}

	private void initPainelRecomendados() {
		PainelRecomendados painelRecomendados = new PainelRecomendados();
		tabbedPane.add(painelRecomendados);
		JLabel tab = new JLabel("Recomendados");
		ModeloTabela.getRecomendados().addTableModelListener((TableModelEvent e) -> {
			tab.setText("Recomendados (" + ModeloTabela.getRecomendados().getRowCount() + ")");
		});
		tab.setIcon(new ImageIcon(App.CAMINHO_ICONES + "24/recommended.png"));
		tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tab);
	}

	// --- Look and Feel ---

	private void initLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					return;
				}
			}
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Impossivel iniciar o Look And Feel");
		}
	}

	// --- Eventos da Janela ---

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		int i = JOptionPane.showConfirmDialog(this, "Deseja salvar as alterações?", "Sair",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

		if (i == JOptionPane.CANCEL_OPTION)
			return;
		else if (i == JOptionPane.OK_OPTION)
			Util.saveState();

		dispose();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowOpened(WindowEvent e) {
		ModeloTabela.getPrincipal().carregarCSV(App.CAMINHO_CSV + "series.csv");
		ModeloTabela.getFavoritos().carregarCSV(App.CAMINHO_CSV + "favoritos.csv");
		ModeloTabela.getPrincipal().addListaDeSeries(ModeloTabela.getFavoritos().getSeries());
	}

}
