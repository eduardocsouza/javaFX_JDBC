package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.services.ProdutoService;

public class ViewController implements Initializable {

	@FXML
	private Button btAdd;

	@FXML
	private Button btList;
	
	@FXML
	private Button btnVenda;
	
	@FXML
	private AnchorPane telaInicial;
	
	
	// métodos/funções
	public void onMenuAdd(ActionEvent event) {
		Stage stage = Utils.stageAtual(event);
		loadView("/gui/Produto.fxml", stage);
	}

	public void onAddBtn(ActionEvent event) {
		Stage stage = Utils.stageAtual(event);
		loadViewForm("/gui/ProdutoForm.fxml", stage);
		
	}

	@Override
	public void initialize(URL locaUrl, ResourceBundle resBundle) {
		
	}

	private synchronized void loadView(String viwer, Stage stagePai) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(viwer));
			VBox newHbox = loader.load();

			ProdutoControll controller = loader.getController();
			controller.setProdutoServices(new ProdutoService());
			controller.updateTableView();

			Scene mainScene = new Scene(newHbox);
			Stage stage = new Stage();

			stage.setScene(mainScene);
			stage.setTitle("Lista de Produtos");
			stage.initOwner(stagePai);
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO exception", "Erro", e.getMessage(), AlertType.ERROR);
		}
	}

	private synchronized void loadViewForm(String viwer, Stage stagePai) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(viwer));
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

		} catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	private void loadViewVenda(String view) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
			Pane p = loader.load();	
			
			VendasController controller = loader.getController();
			controller.setProdutoService(new ProdutoService());
			controller.updateList();
			
			telaInicial.getChildren().clear();
			telaInicial.getChildren().setAll(p);			
		}
		catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public void onVendaProd() {
		loadViewVenda("/gui/Venda.fxml");
	}

}
