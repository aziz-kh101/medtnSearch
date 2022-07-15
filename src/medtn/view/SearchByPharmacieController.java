/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medtn.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import medtn.database.DBConnection;
import medtn.models.Medcine;
import medtn.models.Pharmacie;

/**
 * FXML Controller class
 *
 * @author aziz
 */
public class SearchByPharmacieController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private TextField gov;

    @FXML
    private TextField phone;

    @FXML
    private TextField adress;

    @FXML
    private TableView<Pharmacie> table;

    @FXML
    private TableColumn<Pharmacie, String> nom_col;

    @FXML
    private TableColumn<Pharmacie, String> adress_col;

    @FXML
    private TableColumn<Pharmacie, String> phone_col;

    @FXML
    private TableColumn<Pharmacie, String> phone_2_col;

    @FXML
    private TableColumn<Pharmacie, String> gouv_col;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nom_col.setCellValueFactory(new PropertyValueFactory<Pharmacie, String>("nom"));        
        adress_col.setCellValueFactory(new PropertyValueFactory<Pharmacie, String>("adress"));
        phone_col.setCellValueFactory(new PropertyValueFactory<Pharmacie, String>("phone"));
        phone_2_col.setCellValueFactory(new PropertyValueFactory<Pharmacie, String>("phone_2"));
        gouv_col.setCellValueFactory(new PropertyValueFactory<Pharmacie, String>("gouv"));

    }    
    
    @FXML
    void returnToPrevious(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("selectSearch.fxml"));
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
    }
    
    @FXML
    void search(ActionEvent event) {
        List<Pharmacie> l = DBConnection.getInstance().getPharmacies(adress.getText(), phone.getText(),gov.getText());
        table.getItems().clear();
        table.getItems().addAll(l);
    }
}
