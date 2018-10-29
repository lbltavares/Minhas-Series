package minhasseries.recomendados;

import javax.swing.event.TableModelEvent;

import minhasseries.modelos.ModeloTabela;
import minhasseries.modelos.Serie;

public class RecomendacaoAleatoria implements Algoritmo {

	/*
	 * Adiciona s�ries na tabela de recomendados com probabilidade de
	 * probRecomendacao toda vez que uma s�rie � adicionada na tabela principal
	 * 
	 */

	private double prob = 0.5;

	@Override
	public String getNome() {
		return "Recomenda��o Aleat�ria";
	}

	private void update() {
		ModeloTabela recomendados = ModeloTabela.getRecomendados();
		ModeloTabela principal = ModeloTabela.getPrincipal();
		ModeloTabela favoritos = ModeloTabela.getFavoritos();

		recomendados.limpar();

		for (Serie serie : principal.getSeries()) {
			if (favoritos.contem(serie))
				continue;
			else if (Math.random() < getProb())
				recomendados.addSerie(serie);
		}
	}

	// Getter e Setter para a probabilidade:

	public void setProb(double p) {
		prob = p;
	}

	public double getProb() {
		return prob;
	}

	// Listeners:

	@Override
	public void favoritosModificado(TableModelEvent e) {
		update();
	}

	@Override
	public void principalModificado(TableModelEvent e) {
		update();
	}

}
