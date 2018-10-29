package minhasseries.gui.paineis;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import minhasseries.app.App;
import minhasseries.app.Util;
import minhasseries.gui.IconeBtn;
import minhasseries.gui.Janela;
import minhasseries.gui.Tabela;
import minhasseries.gui.dialogs.SerieInfoDialog;
import minhasseries.modelos.ModeloTabela;
import minhasseries.modelos.Serie;

public class PainelBotoes extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 2;

	// Orientação:
	private final int ORIENTACAO; // HORIZONTAL ou VERTICAL

	// Cores padrão
	public Color COR_FUNDO = new Color(200, 200, 200);
	public Color COR_GRADIENTE = new Color(80, 80, 80);

	public Color COR_BOTOES_FUNDO = new Color(130, 130, 130);
	public Color COR_BOTOES_GRADIENTE = new Color(130, 130, 130);

	// Botões padrão:
	public IconeBtn BOTAO_YOUTUBE, BOTAO_INFO, BOTAO_ADD_FAVORITO, BOTAO_REMOVER, BOTAO_GOOGLE, BOTAO_IMDB,
			BOTAO_ROTTEN_TOMATOES;

	// Lista botões:
	private List<JButton> botoes;

	// Para posicionar os botões no painelControle:
	private GridBagConstraints gc;

	// Tabela alvo:
	private Tabela tabela;

	public PainelBotoes(Tabela tabela, int orientacao) {
		super(new GridBagLayout());
		this.tabela = tabela;
		ORIENTACAO = orientacao;
		init();
		initGridBagConstraints();
		initBotoesPadrao();
	}

	private void init() {
		setBackground(COR_FUNDO);
		setBorder(BorderFactory.createEtchedBorder());
		botoes = new ArrayList<>();
	}

	private void initGridBagConstraints() {
		gc = new GridBagConstraints();
		if (ORIENTACAO == HORIZONTAL) {
			gc.insets = new Insets(10, 0, 10, 0);
		} else {
			gc.insets = new Insets(0, 10, 0, 10);
		}
		gc.gridx = gc.gridy = 0;
	}

	private void initBotoesPadrao() {
		BOTAO_YOUTUBE = new IconeBtn(App.CAMINHO_ICONES + "32/youtube.png", "Pesquisar Trailer");
		BOTAO_INFO = new IconeBtn(App.CAMINHO_ICONES + "32/info.png", "Ver detalhes");
		BOTAO_ADD_FAVORITO = new IconeBtn(App.CAMINHO_ICONES + "32/star.png", "Adicionar aos Favoritos");
		BOTAO_REMOVER = new IconeBtn(App.CAMINHO_ICONES + "32/trash.png", "Remover Série");
		BOTAO_GOOGLE = new IconeBtn(App.CAMINHO_ICONES + "32/google.png", "Pesquisar no Google");
		BOTAO_IMDB = new IconeBtn(App.CAMINHO_ICONES + "32/imdb.png", "Pesquisar no IMDB");
		BOTAO_ROTTEN_TOMATOES = new IconeBtn(App.CAMINHO_ICONES + "32/rotten.png", "Pesquisar no Rotten Tomatoes");

		BOTAO_YOUTUBE.addActionListener((ActionEvent e) -> {
			Serie[] series = tabela.getSeriesSelecionadas();
			if (series.length > 0) {
				String search_query = Util.toUTF8(series[0].getNome() + " Trailer");
				Util.browse("https://www.youtube.com/results?search_query=" + search_query);
			}
		});

		BOTAO_ROTTEN_TOMATOES.addActionListener((ActionEvent e) -> {
			Serie[] series = tabela.getSeriesSelecionadas();
			if (series.length > 0) {
				String search_query = Util.toUTF8(series[0].getNome());
				Util.browse("https://www.rottentomatoes.com/search/?search=" + search_query);
			}
		});

		BOTAO_GOOGLE.addActionListener((ActionEvent e) -> {
			Serie[] series = tabela.getSeriesSelecionadas();
			if (series.length > 0) {
				Util.browse("https://www.google.com/search?q=" + Util.toUTF8(series[0].getNome()));
			}
		});

		BOTAO_IMDB.addActionListener((ActionEvent e) -> {
			Serie[] series = tabela.getSeriesSelecionadas();
			if (series.length > 0) {
				Util.browse("https://www.imdb.com/find?ref_=nv_sr_fn&q=" + Util.toUTF8(series[0].getNome()) + "&s=all");
			}
		});

		BOTAO_INFO.addActionListener((ActionEvent e) -> {
			Serie[] series = tabela.getSeriesSelecionadas();
			if (series.length > 0) {
				new SerieInfoDialog(Janela.getInstance(), series[0]);
			}
		});

		BOTAO_ADD_FAVORITO.addActionListener((ActionEvent e) -> {
			ModeloTabela favoritos = ModeloTabela.getFavoritos();
			Serie[] series = tabela.getSeriesSelecionadas();
			if (series.length == 1) {
				favoritos.addSerie(series[0]);
			} else if (series.length > 0) {
				favoritos.addListaDeSeries(Arrays.asList(series));
			}
			tabela.repaint();
		});

		BOTAO_REMOVER.addActionListener((ActionEvent e) -> {
			Serie[] series = tabela.getSeriesSelecionadas();
			if (series.length > 0) {
				int opt = JOptionPane.showConfirmDialog(getParent(),
						"Tem certeza que deseja remover " + series.length + " item(s)?");
				if (opt == JOptionPane.OK_OPTION) {
					for (int i = 0; i < series.length; i++) {
						if (tabela.getModelo().contemIgnoreCase(series[i].getNome())) {
							tabela.getModelo().removeSerie(series[i].getNome());
						}
					}
					tabela.repaint();
				}
			}
		});
	}

	public void addButton(JButton btn) {
		add(btn, gc);
		botoes.add(btn);

		if (ORIENTACAO == VERTICAL)
			gc.gridy++;
		else
			gc.gridx++;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint grad = new GradientPaint( //
				0, 0, //
				COR_FUNDO, //
				0, getHeight(), //
				COR_GRADIENTE); //
		g2d.setPaint(grad);
		g2d.fill(new Rectangle2D.Float(0, 0, getWidth(), getHeight()));
	}

	// --- Setters ---

	public void setCorBotoes(Color c) {
		if (c != null)
			COR_BOTOES_FUNDO = c;
	}

	public void setCorGradiente(Color c) {
		if (c != null)
			COR_GRADIENTE = c;
	}

	public void setCorFundo(Color c) {
		if (c != null)
			COR_FUNDO = c;
	}

}
