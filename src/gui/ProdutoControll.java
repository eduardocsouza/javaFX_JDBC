package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbException;
import gui.util.Alerts;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Produto;
import model.services.ProdutoService;

public class ProdutoControll implements Initializable {
	
		
	private ProdutoService prodServices;

	@FXML
	private TableView<Produto> tableProduto;
	
	@FXML
	private TableColumn<Produto, Integer> tableColumId;
	
	@FXML
	private TableColumn<Produto, String> tableColumNome;
	
	@FXML
	private TableColumn<Produto, Double> tableColumPreco;
	
	@FXML
	private TableColumn<Produto, Integer> tableColumQtd;
	
	@FXML
	private Button btn_add;
	
	@FXML
	private Button btn_estq;
	
	@FXML
	private Button btn_edit;
	
	@FXML
	private Button btn_excluir;
	
	
		
	private ObservableList<Produto> obsProd;
	
	
	
	private void loadViewr(String view, Stage stagePai) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
			Pane p = loader.load();			
			
			FormController controller = loader.getController();
			controller.setProduto(new ProdutoService());
			
						
			Stage stage = new Stage();
			stage.setScene(new Scene(p));
			stage.setTitle("Adicionar Produto");
			stage.setResizable(false);
			stage.initOwner(stagePai);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.showAndWait();
			
			
		}catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
		
	public void onBtnAction(ActionEvent event) {
		Stage stagePai = Utils.stageAtual(event);
		loadViewr("/gui/ProdutoForm.fxml", stagePai);
	}
	
	
	public void setProdutoServices(ProdutoService services) {
		this.prodServices = services;
	}
	
	
	public void onBtnExcluir() {
		try {	
			Produto prod = new Produto();
			prod = tableProduto.getSelectionModel().getSelectedItem();
			
			if(prod == null) {
				Alerts.showAlert("Erro", null, "Selecione um item na lista", AlertType.ERROR);
			}
			else if(prod != null) {
				Optional<ButtonType> resul = Alerts.showConfirmation("Confirme", "Excluir item ?");
				if(resul.get() == ButtonType.OK) { 
					prodServices.deleteProd(prod);
					updateTableView();
				}
			}
			
		}catch(NullPointerException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public void onBtnEdit(ActionEvent event) {
		try {
			Stage stagePai = Utils.stageAtual(event);
			Produto prod = tableProduto.getSelectionModel().getSelectedItem();
			if(prod == null) {
				Alerts.showAlert("Erro", null, "Selecione um item na lista", AlertType.ERROR);
			}else {
				carregarProd(prod, stagePai);
			}
		}catch(NullPointerException e) {
			throw new DbException(e.getMessage());
		}
	}
		
	
	private void carregarProd(Produto findById, Stage stagePai) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ProdutoForm.fxml"));
			Pane p = loader.load();
			
			FormController controller = loader.getController();
			controller.setProduto(new ProdutoService());
			controller.setProd(findById);
		
						
			
			Stage stage = new Stage();
			stage.setScene(new Scene(p));
			stage.setTitle("Adicionar Produto");
			stage.setResizable(false);
			stage.initOwner(stagePai);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.showAndWait();
			
			
		}
		catch(IOException e) {
			Alerts.showAlert("Erro", null, "Selecione", AlertType.ERROR);
		}
		
	}


	@Override
	public void initialize(URL locaUrl, ResourceBundle resBundle) {
		initializeNode();
	}


	private void initializeNode() {
		tableColumId.setCellValueFactory(new PropertyValueFactory<>("codigo"));
		tableColumNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
		tableColumPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
		
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableProduto.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	public void updateTableView() {
		List<Produto> list = prodServices.findAll();
		obsProd = FXCollections.observableArrayList(list);
		tableProduto.setItems(obsProd);
		
	}
	
	public void fidAll() {
		int n = 0;
		List<Produto> list = prodServices.findAll();
		for(Produto p : list) {
			n += p.getQtd();
		}
		
		System.out.println(n);
	}
	
	
}
