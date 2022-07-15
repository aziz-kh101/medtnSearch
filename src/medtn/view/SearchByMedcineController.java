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

/**
 * FXML Controller class
 *
 * @author aziz
 */
public class SearchByMedcineController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField adress;

    @FXML
    private TextField phone;

    @FXML
    private TextField sepcialite;

    @FXML
    private TextField gouv;

    @FXML
    private TableView<Medcine> table;

    @FXML
    private TableColumn<Medcine, String> prenom_col;

    @FXML
    private TableColumn<Medcine, String> nom_col;

    @FXML
    private TableColumn<Medcine, String> adress_col;

    @FXML
    private TableColumn<Medcine, String> phone_col;

    @FXML
    private TableColumn<Medcine, String> phone_2_col;

    @FXML
    private TableColumn<Medcine, String> specialite_col;

    @FXML
    private TableColumn<Medcine, String> gouv_col;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        prenom_col.setCellValueFactory(new PropertyValueFactory<Medcine, String>("prenom"));
        nom_col.setCellValueFactory(new PropertyValueFactory<Medcine, String>("nome"));
        adress_col.setCellValueFactory(new PropertyValueFactory<Medcine, String>("adress"));
        phone_col.setCellValueFactory(new PropertyValueFactory<Medcine, String>("phone"));
        phone_2_col.setCellValueFactory(new PropertyValueFactory<Medcine, String>("phone2"));
        specialite_col.setCellValueFactory(new PropertyValueFactory<Medcine, String>("specialite"));
        gouv_col.setCellValueFactory(new PropertyValueFactory<Medcine, String>("gouv"));

    }

    @FXML
    void returnToPrevious(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("selectSearch.fxml"));
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    @FXML
    void search(ActionEvent event) {
        List<Medcine> l = DBConnection.getInstance().getMedicines(adress.getText(), phone.getText(), gouv.getText(), sepcialite.getText());
        table.getItems().clear();
        table.getItems().addAll(l);
    }
}
