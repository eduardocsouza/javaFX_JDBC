package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Produto;
import model.services.ProdutoService;

public class VendasController implements Initializable{

	private ProdutoService service;
	
	@FXML
	private TableView<Produto> tableVenda;
	
	@FXML
	private TableColumn<Produto, String> tableNome;
	
	@FXML
	private TableColumn<Produto, Double> tablePreco;
	
	
	@FXML
	private Label labelN;
	
	@FXML
	private TextField tQtd;
	
	@FXML
	private TextField tValorPago;
	
	@FXML
	private Label lableValor;
	
	@FXML
	private Label lableTroco;
	
	@FXML
	private Button btnCalc;
	
	@FXML
	private Button btnVoltar;
	
	
	private ObservableList<Produto> obsList;
	
	
	
	public void setProdutoService(ProdutoService service) {
		this.service = service;
	}
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rs) {
		initialize();
		
		tableVenda.getSelectionModel().selectedItemProperty().
		addListener((observable, oldValue, newValue) -> selectTableView(newValue));
		
	}
	
	private void selectTableView(Produto newValue) {
		Produto prod = tableVenda.getSelectionModel().getSelectedItem();
		if(prod == null) {
			labelN.setText("");
			lableValor.setText("");
		}else {
			labelN.setText(prod.getNome());
			lableValor.setText(String.valueOf(prod.getPreco()));
			lableTroco.setText("");
		}
	}



	public void initialize() {
		tableNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tablePreco.setCellValueFactory(new PropertyValueFactory<>("preco"));	
		
	}
	
	public void updateList() {
		List<Produto> list = service.findAll();		
		obsList = FXCollections.observableArrayList(list);
		tableVenda.setItems(obsList);
	}
	
	public void initializeLable() {
		Produto prod = tableVenda.getSelectionModel().getSelectedItem();
		if(prod == null) {
			labelN.setText("");
			lableValor.setText("");
		}else {
			labelN.setText(prod.getNome());
			lableValor.setText(String.valueOf(prod.getPreco()));
		}
	}
	
	public void getTroco() {
		int qtd, n2, qtdDb = 0;
		
		Produto prod = tableVenda.getSelectionModel().getSelectedItem();
		double n = prod.getPreco();
	    qtd = Integer.parseInt(tQtd.getText());
		double total = Double.parseDouble(tValorPago.getText()) - (qtd * n);
		lableTroco.setText(String.format("%.2f", total));
		tValorPago.setText("");
		tQtd.setText("");
		lableValor.setText("");
		
		n2 = prod.getQtd();
		
		qtdDb = n2 - qtd;
		
		prod.setQtd(qtdDb);
			
		service.insertProd(prod);
		
		
	}

}
