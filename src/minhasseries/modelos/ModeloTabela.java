package minhasseries.modelos;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

public class ModeloTabela extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private static ModeloTabela FAVORITOS;
	private static ModeloTabela PRINCIPAL;
	private static ModeloTabela RECOMENDADOS;

	public static ModeloTabela getFavoritos() {
		if (FAVORITOS == null)
			FAVORITOS = new ModeloTabela();
		return FAVORITOS;
	}

	public static ModeloTabela getPrincipal() {
		if (PRINCIPAL == null)
			PRINCIPAL = new ModeloTabela();
		return PRINCIPAL;
	}

	public static ModeloTabela getRecomendados() {
		if (RECOMENDADOS == null)
			RECOMENDADOS = new ModeloTabela();
		return RECOMENDADOS;
	}

	public static final int COLUNA_NAO_ENCONTRADA = -1;

	public static final int COLUNA_POSTER = 0;
	public static final int COLUNA_NOME = 1;
	public static final int COLUNA_EMISSORA = 2;

	private String[] colunas = { "Poster", "Nome", "Emissora" };
	private List<Serie> series;

	public ModeloTabela() {
		series = new ArrayList<>();
	}

	public void carregarCSV(String filename) {
		try (Scanner sc = new Scanner(new FileReader(filename))) {
			while (sc.hasNext()) {
				String line = sc.nextLine();
				try {
					String nome = line.split(";")[0];
					String tipo = line.split(";")[1];
					String duracao = line.split(";")[2];
					String pais = line.split(";")[3];
					String idioma = line.split(";")[4];
					String emissora = line.split(";")[5];
					String transmissao = line.split(";")[6];
					String numTemporadas = line.split(";")[7];
					String numEpisodios = line.split(";")[8];
					int numTemp = Integer.parseInt(numTemporadas.trim());
					int numEp = Integer.parseInt(numEpisodios.trim());
					Serie serie = new Serie(nome, tipo, duracao, pais, idioma, emissora, transmissao, numTemp, numEp);
					addSerie(serie);
				} catch (Exception e) {
					System.out.println("Erro ao carregar linha: " + line);
				}
			}
		} catch (Exception e) {
			System.out.println("Impossivel carregar lista do arquivo: " + filename);
		}
	}

	public Serie getSerie(int row) {
		return series.get(row);
	}

	public void removeSerie(int row) {
		series.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public void removeSerie(String nome) {
		if (!contemIgnoreCase(nome))
			return;
		for (int i = 0; i < getRowCount(); i++) {
			if (nome.equalsIgnoreCase(getSerie(i).getNome())) {
				series.remove(i);
				fireTableRowsDeleted(i, i);
				return;
			}
		}
	}

	public void addSerie(Serie serie) {
		if (contem(serie))
			return;
		series.add(serie);
		int ultimoIndice = getRowCount() - 1;
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	public void addListaDeSeries(List<Serie> novasSeries) {
		int tamanhoAntigo = getRowCount();
		List<Serie> addList = new ArrayList<>();
		for (Serie serie : novasSeries) {
			if (!contem(serie))
				addList.add(serie);
		}
		series.addAll(addList);
		if (series.size() != tamanhoAntigo)
			fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}

	public boolean contem(Serie serie) {
		return contem(serie.getNome());
	}

	public boolean contemIgnoreCase(String nome) {
		for (int linha = 0; linha < getRowCount(); linha++) {
			if (series.get(linha).getNome().equalsIgnoreCase(nome)) {
				return true;
			}
		}
		return false;
	}

	public boolean contem(String nome) {
		for (int linha = 0; linha < getRowCount(); linha++) {
			if (series.get(linha).getNome().equals(nome)) {
				return true;
			}
		}
		return false;
	}

	public void limpar() {
		series.clear();
		fireTableDataChanged();
	}

	public boolean isEmpty() {
		return series.isEmpty();
	}

	public List<Serie> getSeries() {
		return series;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return series.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Serie serie = series.get(row);
		if (col == COLUNA_POSTER) {
			return serie.getPoster();
		} else if (col == COLUNA_NOME) {
			return serie.getNome();
		} else if (col == COLUNA_EMISSORA) {
			return serie.getEmissora();
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int col) {
		if (col == COLUNA_POSTER) {
			return ImageIcon.class;
		} else if (col == COLUNA_NOME) {
			return String.class;
		} else if (col == COLUNA_EMISSORA) {
			return String.class;
		}
		return null;
	}

}
