package minhasseries.gui.paineis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import minhasseries.app.App;
import minhasseries.gui.Tabela;

public class PainelBusca extends JPanel implements DocumentListener {
	private static final long serialVersionUID = 1L;

	// Cores padrão
	public Color COR_FUNDO = new Color(190, 190, 190);
	public Color COR_GRADIENTE = new Color(100, 100, 100);
	public Color COR_TEXTO_STATUS = new Color(240, 240, 240);
	public Color COR_TEXTO_PESQUISA = new Color(255, 255, 255);
	public Color COR_TEXTO_PESQUISA_FUNDO = new Color(120, 120, 120);

	// Componentes
	private JLabel status;
	private JTextField field;
	private Tabela tabela;

	// Sorter para o TableModel da tabela
	private TableRowSorter<TableModel> sorter;

	public PainelBusca(Tabela tabela) {
		super(new BorderLayout());
		this.tabela = tabela;
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setBackground(COR_FUNDO);
		initIconeLupa();
		initField();
		initStatus();
		addRowSorter();
	}

	private void initStatus() {
		status = new JLabel("");
		status.setFont(new Font("Arial", Font.BOLD, 15));
		status.setForeground(COR_TEXTO_STATUS);
		add(status, BorderLayout.SOUTH);
	}

	private void initIconeLupa() {
		JLabel iconeLabel = new JLabel(new ImageIcon(App.CAMINHO_ICONES + "24/search.png"));
		iconeLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(iconeLabel, BorderLayout.WEST);
	}

	private void initField() {
		field = new JTextField();
		field.setFont(new Font("Arial", Font.BOLD, 19));
		field.getDocument().addDocumentListener(this);
		field.setBackground(COR_TEXTO_PESQUISA_FUNDO);
		field.setForeground(COR_TEXTO_PESQUISA);
		add(field, BorderLayout.CENTER);
	}

	private void addRowSorter() {
		sorter = new TableRowSorter<>(tabela.getModel());
		tabela.setRowSorter(sorter);
	}

	// --- Getters ---

	public JTextField getTextField() {
		return field;
	}

	public RowSorter<TableModel> getRowSorter() {
		return sorter;
	}

	//

	private void filtrar() {
		String text = field.getText();
		if (text.trim().length() == 0) {
			sorter.setRowFilter(null);
			status.setText("");
		} else {
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
			status.setText("Mostrando resultados para " + "\"" + text + "\" (encontrados " + tabela.getRowCount()
					+ " em " + tabela.getModel().getRowCount() + ")");
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
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

	@Override
	public void insertUpdate(DocumentEvent e) {
		filtrar();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		filtrar();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}
}
