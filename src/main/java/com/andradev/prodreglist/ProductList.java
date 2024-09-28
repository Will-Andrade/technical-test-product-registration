package main.java.com.andradev.prodreglist;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductList extends JFrame {
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton addButton;
	
	private List<Product> products;
	
	private static final String DATA_FILE = "products.csv";
	private static final String DISPLAY_FILE = "formatted_products.txt";
	
	public ProductList() {
		super("Listagem de Produtos");
		products = new ArrayList<>();
		
		setLayout(new BorderLayout());
		
		tableModel = new DefaultTableModel(new Object[]{"Nome", "Valor"}, 0) {
			// Telling swing cells should be static
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		JScrollPane tableScroll = new JScrollPane(table);
		add(tableScroll, BorderLayout.CENTER);
		
		// Add products button
		addButton = new JButton("Register new Product");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		add(buttonPanel, BorderLayout.SOUTH);
		
		// Opens product creation form
		addButton.addActionListener(e -> openForm());
		
		loadProductsFromFile();
		
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	private void openForm() {
		ProductForm form = new ProductForm(this, this);
		form.setVisible(true);
	}
	
	public void addProduct(Product product) {
		products.add(product);
		updateTable();
		saveProductToFile(product);
		saveFormattedProducts();
	}
	
	private void updateTable() {
		tableModel.setRowCount(0);
		
		products.sort(Comparator.comparingDouble(Product::getValue));
		
		for (Product p : products) {
			tableModel.addRow(new Object[]{p.getName(), String.format("R$ %.2f", p.getValue())});
		}
	}
	
	private void saveProductToFile(Product product) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE, true))) {
			// Saves to CSV
			String line = String.format("%s,%s,%.2f,%b",
				escapeCSV(product.getName()),
				escapeCSV(product.getDescription()),
				product.getValue(),
				product.isAvailable());
			
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error trying to save the product to the file",
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private String escapeCSV(String field) {
		if (field.contains(",") || field.contains("\"")) {
			field = field.replace("\"", "\"\"");
			field = "\"" + field + "\"";
		}
		
		return field;
	}
	
	private void loadProductsFromFile() {
		File file = new File(DATA_FILE);
		
		if (!file.exists()) {
			return;
		}
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			
			while ((line = reader.readLine()) != null) {
				// Skipping empty lines
				if (line.trim().isEmpty()) continue;
				
				// Splitting the line by its delimiters considering fields between quotes
				String[] fields = parseCSVLine(line);
				if (fields.length != 4) continue; // Invalid format
				
				String name = fields[0];
				String description = fields[1];
				Double value = Double.parseDouble(fields[2]);
				Boolean available = Boolean.parseBoolean(fields[3]);
				
				Product product = new Product(name, description, value, available);
				products.add(product);
			}
			
			updateTable();
		} catch (IOException | NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Error while loading products from the CSV " +
				"file.", "Error",	JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// Parsing for CSV
	private String[] parseCSVLine(String line) {
		List<String> fields = new ArrayList<>();
		boolean inQuotes = false;
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (inQuotes) {
				if (c == '\"') {
					if (i + 1 < line.length() && line.charAt(i + 1) == '\"') {
						sb.append('\"');
						i++;
					} else {
						inQuotes = false;
					}
				} else {
					sb.append(c);
				}
			} else {
				if (c == '\"') {
					inQuotes = true;
				} else if (c == ',') {
					fields.add(sb.toString());
					sb.setLength(0);
				} else {
					sb.append(c);
				}
			}
		}
		
		fields.add(sb.toString());
		return fields.toArray(new String[0]);
	}
	
	// Saving products in a formatted file
	private void saveFormattedProducts() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(DISPLAY_FILE))) {
			writer.write(String.format("%-25s %-10s %-12s", "Product", "Value", "Available"));
			writer.newLine();
			
			for (Product p : products) {
				writer.write(String.format("%-25s R$%-9.2f %-12b", p.getName(), p.getValue(), p.isAvailable()));
				writer.newLine();
			}

			JOptionPane.showMessageDialog(this, "Formatted file 'formatted_products.txt' " +
				"saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error while saving products in formatted " +
				"file.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new ProductList().setVisible(true);
		});
	}
}

