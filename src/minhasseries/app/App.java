package minhasseries.app;

import java.awt.EventQueue;

import minhasseries.gui.Janela;

public class App {

	public static final String TITULO = "Minhas S�ries";

	// Caminhos padr�o
	public static String CAMINHO_CSV = "csv/";
	public static String CAMINHO_ICONES = "res/icons/";
	public static String CAMINHO_POSTERS = "res/posters/";

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {

			Janela.getInstance().setVisible(true);

			// Inicia o gerenciador de algoritmos de recomenda��o
			minhasseries.recomendados.Gerenciador.init();

		});
	}
}
