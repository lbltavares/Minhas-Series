package minhasseries.app;

import java.awt.EventQueue;

import minhasseries.gui.Janela;

public class App {

	public static final String TITULO = "Minhas Séries";

	// Caminhos padrão
	public static String CAMINHO_CSV = "csv/";
	public static String CAMINHO_ICONES = "res/icons/";
	public static String CAMINHO_POSTERS = "res/posters/";

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {

			Janela.getInstance().setVisible(true);

			// Inicia o gerenciador de algoritmos de recomendação
			minhasseries.recomendados.Gerenciador.init();

		});
	}
}
