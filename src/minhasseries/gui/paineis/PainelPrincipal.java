package minhasseries.gui.paineis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;

import minhasseries.app.App;
import minhasseries.gui.Tabela;
import minhasseries.modelos.ModeloTabela;

public class PainelPrincipal extends JPanel {
	private static final long serialVersionUID = 1L;

	// Cores padrão:
	public Color COR_FUNDO = new Color(90, 90, 90);

	// Componentes:
	private Tabela tabela;
	private PainelBusca painelBusca;
	private PainelBotoes painelBotoes;

	public PainelPrincipal() {
		super(new BorderLayout());
		initComponents();
	}

	private void initComponents() {
		tabela = new Tabela(ModeloTabela.getPrincipal()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				if (getModelo().isEmpty()) {
					String txt1 = "Não há itens nesta tabela";
					String txt2 = "Experimente arrastar um arquivo .CSV no formato:";
					String txt3 = "Nome; Tipo; Duracao; Pais; Idioma; Emissora; Transmissao; Num Temporadas; Num Episodios";

					int w1 = g.getFontMetrics().stringWidth(txt1);
					int w2 = g.getFontMetrics().stringWidth(txt2);
					int w3 = g.getFontMetrics().stringWidth(txt3);
					int h = g.getFontMetrics().getHeight();

					try {
						BufferedImage icone = ImageIO.read(new File(App.CAMINHO_ICONES + "64/file.png"));
						g.drawImage(icone, getWidth() / 2 - icone.getWidth() / 2, getHeight() / 2 - icone.getHeight(),
								icone.getWidth(), icone.getHeight(), null);
					} catch (IOException e) {
						e.printStackTrace();
					}

					g.drawString(txt1, getWidth() / 2 - w1 / 2, getHeight() / 2 + h);
					g.drawString(txt2, getWidth() / 2 - w2 / 2, getHeight() / 2 + h * 2);
					g.drawString(txt3, getWidth() / 2 - w3 / 2, getHeight() / 2 + h * 3);

				} else
					super.paintComponent(g);
			}
		};

		tabela.setTransferHandler(new TransferHandler() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean canImport(TransferHandler.TransferSupport support) {
				for (DataFlavor flavor : support.getDataFlavors()) {
					if (flavor.isFlavorJavaFileListType()) {
						return true;
					}
				}
				return false;
			}

			@SuppressWarnings("unchecked")
			@Override
			public boolean importData(TransferHandler.TransferSupport support) {
				if (!this.canImport(support))
					return false;
				List<File> files;
				try {
					files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
				} catch (Exception ex) {
					return false;
				}

				for (File file : files) {
					tabela.getModelo().carregarCSV(file.getAbsolutePath());
				}
				return true;
			}
		});

		painelBusca = new PainelBusca(tabela);
		painelBotoes = new PainelBotoes(tabela, PainelBotoes.HORIZONTAL);

		painelBotoes.addButton(painelBotoes.BOTAO_INFO);
		painelBotoes.addButton(painelBotoes.BOTAO_ADD_FAVORITO);
		painelBotoes.addButton(painelBotoes.BOTAO_YOUTUBE);
		painelBotoes.addButton(painelBotoes.BOTAO_GOOGLE);
		painelBotoes.addButton(painelBotoes.BOTAO_IMDB);
		painelBotoes.addButton(painelBotoes.BOTAO_ROTTEN_TOMATOES);

		add(new JScrollPane(tabela), BorderLayout.CENTER);
		add(painelBusca, BorderLayout.NORTH);
		add(painelBotoes, BorderLayout.SOUTH);

	}

	public Tabela getTabela() {
		return tabela;
	}

}
