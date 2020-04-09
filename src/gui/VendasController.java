package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Produto;
import model.entities.VendasProduto;
import model.services.ProdutoService;

public class VendasController implements Initializable{

	private ProdutoService service;
	
	
	@FXML
	private TableView<Produto> tableVenda;
	
	@FXML
	private TableColumn<Produto, Integer> tableCodigo;
	
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
	
	@FXML
	private Button vendasDia;
	
	
	private ObservableList<Produto> obsList;
	
	
	@FXML
	private AnchorPane telaVenda;
	
	
	
	public void setProdutoService(ProdutoService service) {
		this.service = service;
	}
	
	
	
	@Override
	public void initialize(URL url, ResourceBundle rs) {
		initialize();
		initializeVenda();
	
		
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
		tableCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		tableNome.setCellValueFactory(new PropertyValueFactory<>("nome"));	
		
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
		
		int  qtd, n2, qtdDb = 0;
		
		Produto prod = tableVenda.getSelectionModel().getSelectedItem();
		
				
		double n = prod.getPreco();
	    qtd = Integer.parseInt(tQtd.getText());
	    if(Double.parseDouble(tValorPago.getText()) < n) {
	    	Alerts.showAlert("ERRO", "", "Valor Incorreto", AlertType.INFORMATION);
	    }else {
		    double total = Double.parseDouble(tValorPago.getText()) - (qtd * n);
			lableTroco.setText(String.format("%.2f", total));
					
			n2 = prod.getQtd();			
			qtdDb = n2 - qtd;			
			prod.setQtd(qtdDb);
			
			VendasProduto venda = new VendasProduto(null, new Date(), qtd, prod);
						
			service.insertVenda(venda);
			
			service.insertProd(prod);
			
			tValorPago.setText("");
			tQtd.setText("");
			lableValor.setText("");
	    }
		
		
	}
	
			
	public void loadTelaView(String view) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
			Pane p = loader.load();
			
			telaVenda.getChildren().clear();
			telaVenda.getChildren().setAll(p);
			
			
		}catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public void voltar() {
		loadTelaView("/gui/MainView.fxml");
	}
	
	
	public void vendaQtd(ActionEvent event) {
		Stage stg = Utils.stageAtual(event);
		loaderTotalVenda("/gui/TotalVendido.fxml", stg);
	}	
	
	
	public void loaderTotalVenda(String view, Stage stg) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
			Pane p = loader.load();
			
			TotalVendidoConttroler controlle = loader.getController();
			controlle.setProdutoService(new ProdutoService());
			controlle.comboxProduto();
						
			Stage st = new Stage();
			st.setScene(new Scene(p));
			st.setTitle("Total Vendido");
			st.setResizable(false);
			st.initOwner(stg);
			st.initModality(Modality.WINDOW_MODAL);
			st.showAndWait();
		}
		catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public void onBtnVenda(ActionEvent event) {
		Stage stage = Utils.stageAtual(event);
		
		loaderTotalVenda("/gui/TotalVendido.fxml", stage);
	}
	
	public void initializeVenda() {
		Constraints.setTextFieldInteger(tQtd);
	}
	
	

}
