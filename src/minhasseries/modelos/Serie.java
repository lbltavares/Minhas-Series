package minhasseries.modelos;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;

import minhasseries.app.App;

public class Serie {

	private String nome, tipo, duracao, pais, idioma, emissora, transmissao;
	private int numTemporadas, numEpisodios;
	private ImageIcon poster;

	public Serie(String nome, String tipo, String duracao, String pais, String idioma, String emissora,
			String transmissao, int numTemporadas, int numEpisodios) {
		setNome(nome);
		setTipo(tipo);
		setPais(pais);
		setIdioma(idioma);
		setDuracao(duracao);
		setEmissora(emissora);
		setTransmissao(transmissao);
		setNumEpisodios(numEpisodios);
		setNumTemporadas(numTemporadas);
		setPoster(App.CAMINHO_POSTERS + nome + ".jpg");
	}

	// Setters

	public void setPoster(String posterPath) {
		if (Files.exists(Paths.get(posterPath))) {
			this.poster = new ImageIcon(posterPath);
		} else {
			this.poster = new ImageIcon(App.CAMINHO_POSTERS + "default.png");
		}
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public void setEmissora(String emissora) {
		this.emissora = emissora;
	}

	public void setTransmissao(String transmissao) {
		this.transmissao = transmissao;
	}

	public void setNumTemporadas(int numTemporadas) {
		this.numTemporadas = numTemporadas;
	}

	public void setNumEpisodios(int numEpisodios) {
		this.numEpisodios = numEpisodios;
	}

	public void setPoster(ImageIcon poster) {
		this.poster = poster;
	}

	// Getters

	public ImageIcon getPoster() {
		return poster;
	}

	public String getNome() {
		return nome;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDuracao() {
		return duracao;
	}

	public String getPais() {
		return pais;
	}

	public String getIdioma() {
		return idioma;
	}

	public String getEmissora() {
		return emissora;
	}

	public String getTransmissao() {
		return transmissao;
	}

	public int getNumTemporadas() {
		return numTemporadas;
	}

	public int getNumEpisodios() {
		return numEpisodios;
	}

	// Utilitarios

	public String toCSV() {
		return getNome() + ";" //
				+ getTipo() + ";" //
				+ getDuracao() + ";" //
				+ getPais() + ";" //
				+ getIdioma() + ";" //
				+ getEmissora() + ";" //
				+ getTransmissao() + ";" //
				+ getNumTemporadas() + ";" //
				+ getNumEpisodios() + System.lineSeparator(); //
	}
}
