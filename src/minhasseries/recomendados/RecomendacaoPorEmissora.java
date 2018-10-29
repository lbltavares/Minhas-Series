package minhasseries.recomendados;

import java.util.HashMap;

import javax.swing.event.TableModelEvent;

import minhasseries.modelos.ModeloTabela;
import minhasseries.modelos.Serie;

public class RecomendacaoPorEmissora implements Algoritmo {

	private HashMap<String, Integer> emissoras;
	private ModeloTabela favoritos = ModeloTabela.getFavoritos();
	private ModeloTabela principal = ModeloTabela.getPrincipal();

	// Probabilidade de recomendar série de uma emissora que não está nos
	// favoritos
	private double probEmissoraNova = 0.1;

	public RecomendacaoPorEmissora() {
		emissoras = new HashMap<>();
		ModeloTabela.getRecomendados().limpar();
	}

	private void update() {
		emissoras.clear();
		ModeloTabela.getRecomendados().limpar();
		for (int i = 0; i < favoritos.getSeries().size(); i++) {
			Serie serie = favoritos.getSerie(i);
			String emissora = serie.getEmissora();
			if (!emissoras.containsKey(emissora)) {
				emissoras.put(emissora, 1);
			} else {
				emissoras.put(emissora, emissoras.get(emissora) + 1);
			}
		}
		preencherRecomendados();
	}

	private void preencherRecomendados() {
		for (int i = 0; i < principal.getSeries().size(); i++) {
			Serie serie = principal.getSerie(i);
			String emissora = serie.getEmissora();
			if (favoritos.contemIgnoreCase(serie.getNome())) {
				continue;
			} else if (emissoras.containsKey(emissora)) {
				double prob = ((emissoras.get(emissora) * 1.0) / (favoritos.getSeries().size() * 1.0));
				double randomVal = Math.random();
				if (randomVal <= prob) {
					ModeloTabela.getRecomendados().addSerie(serie);
				}
			} else if (Math.random() < probEmissoraNova) {
				ModeloTabela.getRecomendados().addSerie(serie);
			}

		}

	}

	@Override
	public void favoritosModificado(TableModelEvent e) {
		update();
	}

	@Override
	public void principalModificado(TableModelEvent e) {
		update();
	}

	@Override
	public String getNome() {
		return "Recomendação por Emissora";
	}

}
