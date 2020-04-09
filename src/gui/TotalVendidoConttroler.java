package gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.Produto;
import model.entities.VendasProduto;
import model.services.ProdutoService;

public class TotalVendidoConttroler implements Initializable {

	private ProdutoService service;

	@FXML
	private Label lResNome;

	@FXML
	private Label lResQtd;

	@FXML
	private Label lResTotal;

	@FXML
	private Button btnLoja, btnVendas;

	@FXML
	private DatePicker dateCal;

	@FXML
	private Button btnBuscar;

	@FXML
	private ComboBox<Produto> comboBox;

	private ObservableList<Produto> obsList;

	public void setProdutoService(ProdutoService service) {
		this.service = service;
	}

	public void comboxProduto() {
		List<Produto> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBox.setItems(obsList);
	}

	private void initializeComboBoxDepartment() {
		Callback<ListView<Produto>, ListCell<Produto>> factory = lv -> new ListCell<Produto>() {
			@Override
			protected void updateItem(Produto item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBox.setCellFactory(factory);
		comboBox.setButtonCell(factory.call(null));
	}

	public void onBuscar() {
		int qtd = 0;
		double total = 0;
		LocalDate lDate = dateCal.getValue();
		List<VendasProduto> list = service.findAllVenda(lDate.toString());
		int cod = comboBox.getValue().getCodigo();
		
		for(VendasProduto v : list) {
			
			if(v.getCodigo() == cod) {
				
				int n = v.getQtd();
				qtd += n;
				total = comboBox.getValue().getPreco() * qtd;
				
				lResQtd.setText(String.valueOf(qtd));
				lResTotal.setText(String.valueOf(total));
			}
		}
			

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeComboBoxDepartment();
	}

}
