package main.java.com.andradev.prodreglist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductForm extends JDialog {
	private JTextField nameField;
	private JTextArea descriptionArea;
	private JTextField valueField;
	private JRadioButton availableYes;
	private JRadioButton availableNo;
	private JButton saveButton;
	private JButton cancelButton;
	
	private ProductList productList;
	
	public ProductForm(JFrame parent, ProductList productList) {
		super(parent, "Register Product", true);
		this.productList = productList;
		
		setLayout(new BorderLayout());
		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5,5,5,5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		formPanel.add(new JLabel("Product Name:"), gbc);
		
		nameField = new JTextField(20);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		formPanel.add(nameField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		formPanel.add(new JLabel("Product Description:"), gbc);
		
		descriptionArea = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(descriptionArea);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		formPanel.add(scrollPane, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		formPanel.add(new JLabel("Product Value:"), gbc);
		
		valueField = new JTextField(10);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		formPanel.add(valueField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.EAST;
		formPanel.add(new JLabel("Available for Sale:"), gbc);
		
		availableYes = new JRadioButton("Yes");
		availableNo = new JRadioButton("No");
		ButtonGroup group = new ButtonGroup();
		group.add(availableYes);
		group.add(availableNo);
		availableYes.setSelected(true);
		
		JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		radioPanel.add(availableYes);
		radioPanel.add(availableNo);
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		formPanel.add(radioPanel, gbc);
		
		add(formPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		saveButton = new JButton("Save");
		cancelButton = new JButton("Cancel");
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveProduct();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		pack();
		setLocationRelativeTo(parent);
	}
	
	private void saveProduct() {
		String name = nameField.getText().trim();
		String description = descriptionArea.getText().trim();
		String valueText = valueField.getText().trim();
		Boolean available = availableYes.isSelected();
		
		if (name.isEmpty() || description.isEmpty() || valueText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please, fill all the fields.", "Error",
				JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		
		double valor;
		try {
			valor = Double.parseDouble(valueText);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Invalid Value.", "Error",
				JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Product product = new Product(name, description, valor, available);
		productList.addProduct(product);
		dispose();
	}
}

