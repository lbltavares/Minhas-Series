package minhasseries.gui.paineis;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import minhasseries.gui.Tabela;
import minhasseries.modelos.ModeloTabela;

public class PainelRecomendados extends JPanel {
	private static final long serialVersionUID = 1L;

	// Cores padrão:
	public Color COR_FUNDO = new Color(90, 90, 90);

	// Componentes:
	private Tabela tabela;
	private PainelBusca painelBusca;
	private PainelBotoes painelBotoes;

	public PainelRecomendados() {
		super(new BorderLayout());
		initComponents();
	}

	private void initComponents() {
		tabela = new Tabela(ModeloTabela.getRecomendados());
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

}
