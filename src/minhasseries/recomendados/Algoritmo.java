package minhasseries.recomendados;

import javax.swing.event.TableModelEvent;

public interface Algoritmo {

	String getNome();

	void favoritosModificado(TableModelEvent e);

	void principalModificado(TableModelEvent e);

}
