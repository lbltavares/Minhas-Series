package minhasseries.app;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URLEncoder;

import javax.swing.JOptionPane;

import minhasseries.modelos.ModeloTabela;
import minhasseries.modelos.Serie;

// Utilitarios
public final class Util {

	public static void browse(String url) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI(url));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			JOptionPane.showInputDialog(null, "Não suportado para este computador." + System.lineSeparator()
					+ "Copie a URL abaixo e cole no navegador: ", url);
		}
	}

	public static String toUTF8(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			System.err.println("Erro ao tentar transformar a String " + str + " para UTF-8: " + e.getMessage());
			return str;
		}
	}

	public static void criarCSV(ModeloTabela modelo, String filename) {
		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(filename))) {
			for (int row = 0; row < modelo.getRowCount(); row++) {
				Serie s = modelo.getSerie(row);
				bw.write(s.toCSV());
			}
		} catch (Exception e) {
			System.err.println("Erro ao salvar em " + filename + ": " + e.getMessage());
		}
	}

	public static void saveState() {
		criarCSV(ModeloTabela.getFavoritos(), App.CAMINHO_CSV + "favoritos.csv");
		criarCSV(ModeloTabela.getPrincipal(), App.CAMINHO_CSV + "series.csv");
	}
}
