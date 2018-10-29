package minhasseries.gui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import minhasseries.app.App;
import minhasseries.modelos.ModeloTabela;
import minhasseries.modelos.Serie;

public class Tabela extends JTable {
	private static final long serialVersionUID = 1L;

	private ModeloTabela model;

	public Tabela(ModeloTabela model) {
		super(model);
		this.model = model;
		init();
	}

	private void init() {
		setOpaque(false);
		setFillsViewportHeight(true);
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setFont(new Font("sansserif", Font.PLAIN, 17));
		setRowHeight(getRowHeight() + 100);
		setShowGrid(false);
		ajustarLarguras();
		centralizarHeaders();
	}

	private void ajustarLarguras() {
		getColumnModel().getColumn(ModeloTabela.COLUNA_NOME).setCellRenderer(new NomeCellRenderer());
		getColumnModel().getColumn(ModeloTabela.COLUNA_POSTER).setCellRenderer(new PosterCellRenderer());
		getColumnModel().getColumn(ModeloTabela.COLUNA_EMISSORA).setCellRenderer(new EmissoraCellRenderer());
		getColumnModel().getColumn(ModeloTabela.COLUNA_POSTER).setMaxWidth(180);
		getColumnModel().getColumn(ModeloTabela.COLUNA_POSTER).setPreferredWidth(180);
		getColumnModel().getColumn(ModeloTabela.COLUNA_POSTER).setMinWidth(180);
		getColumnModel().getColumn(ModeloTabela.COLUNA_NOME).setPreferredWidth(280);
	}

	private void centralizarHeaders() {
		JTableHeader header = getTableHeader();
		header.setFont(new Font("Dialog", Font.BOLD, 15));
		header.setAlignmentX(Component.CENTER_ALIGNMENT);
		header.setDefaultRenderer(new HeaderRenderer(this));
	}

	public Serie[] getSeriesSelecionadas() {
		int[] linhasSelecionadas = getSelectedRows();
		Serie[] series = new Serie[linhasSelecionadas.length];
		for (int i = 0; i < linhasSelecionadas.length; i++) {
			int linhaConvertida = convertRowIndexToModel(linhasSelecionadas[i]);
			Serie serie = model.getSerie(linhaConvertida);
			series[i] = serie;
		}
		return series;
	}

	public ModeloTabela getModelo() {
		return model;
	}

	// ------------ Cell Renderers ------------

	private static class HeaderRenderer implements TableCellRenderer {

		DefaultTableCellRenderer renderer;

		public HeaderRenderer(JTable table) {
			renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
			renderer.setHorizontalAlignment(JLabel.CENTER);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int col) {
			return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		}
	}

	private class PosterCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setHorizontalAlignment(SwingConstants.CENTER);
			setIcon(new ImageIcon(value.toString()));
			setText("");
			setBorder(null);
			return this;
		}
	}

	private class EmissoraCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setHorizontalAlignment(SwingConstants.CENTER);
			setBorder(noFocusBorder);
			return this;
		}
	}

	private class NomeCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			setHorizontalAlignment(SwingConstants.LEFT);
			setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
			setFont(new Font("Courier", Font.BOLD, 17));

			if (ModeloTabela.getFavoritos().contemIgnoreCase(value.toString())) {
				setIcon(new ImageIcon(App.CAMINHO_ICONES + "24/star.png"));
			} else if (ModeloTabela.getRecomendados().contemIgnoreCase(value.toString())) {
				setIcon(new ImageIcon(App.CAMINHO_ICONES + "24/recommended.png"));
			} else {
				setIcon(null);
			}

			return this;
		}
	}
}
