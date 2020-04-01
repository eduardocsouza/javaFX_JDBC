package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.entities.Produto;
import model.services.ProdutoService;

public class FormController implements Initializable{
	
	private Produto prod;

	private ProdutoService serviceProd;
	
	@FXML
	private TextField textNome;
	
	@FXML
	private TextField textPreco;
	
	@FXML
	private TextField textQtd;
	
	@FXML
	private Button btnAdd;
	
	@FXML
	private Button btnCancel;
	
	
	public void setProd(Produto prod) {
		this.prod = prod;
		this.textNome.setText(prod.getNome());
		this.textPreco.setText(String.valueOf(prod.getPreco()));
		this.textQtd.setText(String.valueOf(prod.getQtd()));
	}
	
		
	public void setProduto(ProdutoService prodService) {
		this.serviceProd = prodService;
	}
		
	public void onAddProd(ActionEvent event) {
				
		prod.setNome(textNome.getText());
		prod.setPreco(Double.parseDouble(textPreco.getText()));
		prod.setQtd(Integer.parseInt(textQtd.getText()));
		
		serviceProd.insertProd(prod);
		
		Utils.stageAtual(event).close();
		
		
	}
	
	
	
	
	public void onCloseBtn(ActionEvent event) {
		Utils.stageAtual(event).close();
	}


	@Override
	public void initialize(URL localUrl, ResourceBundle resourceBundle) {
		initializeForm();
		
	}
	
	private void initializeForm() {
		Constraints.setTextFieldDouble(textPreco);
		Constraints.setTextFieldMaxLength(textNome, 20);
		Constraints.setTextFieldInteger(textQtd);
	}

}
