package minhasseries.recomendados;

import javax.swing.event.TableModelEvent;

import minhasseries.modelos.ModeloTabela;

// Classe Singleton
public final class Gerenciador {

	public static Gerenciador INSTANCE;

	public static synchronized Gerenciador getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Gerenciador();
		return INSTANCE;
	}

	public static void init() {
		getInstance();
	}

	private static Algoritmo algoritmo;

	private Gerenciador() {

		// O algoritmo inicial é o de recomendação aleatória:
		setAlgoritmo(new RecomendacaoAleatoria());

		adicionarListeners();
	}

	private void adicionarListeners() {
		ModeloTabela.getPrincipal().addTableModelListener((TableModelEvent e) -> {
			algoritmo.principalModificado(e);
		});

		ModeloTabela.getFavoritos().addTableModelListener((TableModelEvent e) -> {
			// Escolha de algoritmo quando há inserção de favorito
			if (ModeloTabela.getFavoritos().isEmpty())
				setAlgoritmo(new RecomendacaoAleatoria());
			else if (!(algoritmo instanceof RecomendacaoPorEmissora))
				setAlgoritmo(new RecomendacaoPorEmissora());

			algoritmo.favoritosModificado(e);
		});
	}

	public Algoritmo getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(Algoritmo a) {
		algoritmo = a;
	}
}
