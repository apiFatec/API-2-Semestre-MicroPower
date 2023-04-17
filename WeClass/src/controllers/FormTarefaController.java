/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dao.TarefaDAO;
import dao.TurmaDao;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.Turma;
import models.Tarefa;

/**
 * FXML Controller class
 *
 * @author Mateus
 */
public class FormTarefaController implements Initializable {

    /**
     * Initializes the controller class.
     */ 
    @FXML
    private AnchorPane btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private TableView<Turma> tableTurma;
    
    @FXML
    private TableColumn<Turma, String> escolaCol;

    @FXML
    private TableColumn<Turma, CheckBox> selectCol;
    
    @FXML
    private TableColumn<Turma, String> nomeCol;

    @FXML
    private DatePicker pickerEnt;

    @FXML
    private TextArea txtDesc;

    @FXML
    private TextField txtNome;

    @FXML
    private Spinner<Integer> spNota;
    
    @FXML
    void btnCancel(MouseEvent event) {

    }

    @FXML
    void btnSalvar(ActionEvent event) throws SQLException {
        for(Turma a : tableTurma.getItems()){
            if(a.getSelect().isSelected()){
                Tarefa tarefa = new Tarefa();
                tarefa.setNomeTarefa(txtNome.getText());
                tarefa.setDescricao(txtDesc.getText());
                tarefa.setNota(spNota.getValue());
                tarefa.setDataFim(java.sql.Date.valueOf(pickerEnt.getValue()));
                tarefa.setDataInicio(java.sql.Date.valueOf(LocalDate.now()));
                tarefa.setIdTurma(a.getIdTurma());
                
                TarefaDAO dao = new TarefaDAO();
                dao.inserirTarefa(tarefa);
                
            }}
        txtDesc.setText("");
        txtNome.setText("");
        pickerEnt.setValue(LocalDate.now());        
   
    }
    ObservableList<Turma> turmas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        listaDeTurma();
        for(int i = 0; i<turmas.size();i++){
        CheckBox ch = new CheckBox("");
        turmas.get(i).setSelect(ch);
        }
        
        tableTurma.setItems(turmas);
        
        selectCol.setCellValueFactory(new PropertyValueFactory<Turma, CheckBox>("select"));
        
        nomeCol.setCellValueFactory(new PropertyValueFactory<Turma, String>("Nome"));
        
        escolaCol.setCellValueFactory(new PropertyValueFactory<Turma, String>("Escola"));
        
         SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10,1);
         spNota.setValueFactory(valueFactory);
        
    }
        
        
        
        private void listaDeTurma() {
            ArrayList<Turma> list = new ArrayList<>();       
            TurmaDao dao = new TurmaDao();
            list = dao.listTurma();
            this.turmas = FXCollections.observableArrayList(list);    
    }
}