/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Mese;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Mese> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	if (this.model.getGrafo() != null) {
    		this.model.connessioneMax();
        	this.txtResult.appendText("Coppie aventi massima connessione:\n\n");
        	for (DefaultWeightedEdge dfe : this.model.getListaConnessioniMax()) {
        		this.txtResult.appendText(this.model.getGrafo().getEdgeSource(dfe).toString() + "\n");
        		this.txtResult.appendText(this.model.getGrafo().getEdgeTarget(dfe).toString() + "\n\n");
        	}
    	}
    	else {
    		this.txtResult.appendText("Occorre prima creare un grafo.\n");
    	}	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	try {
    		Mese mese = this.cmbMese.getValue();
    		Integer min = Integer.parseInt(this.txtMinuti.getText());
    		if (mese == null || min == null) {
    			this.txtResult.appendText("Uno o più campi inseriti sono vuoti.\n");
    		}
    		else {
    			this.model.creaGrafo(mese, min);
    			this.txtResult.appendText("Grafo creato correttamente.\n");
    			this.txtResult.appendText("Vertici: " + this.model.getGrafo().vertexSet().size() + "\n");
    			this.txtResult.appendText("Archi: " + this.model.getGrafo().edgeSet().size() + "\n");
    			this.cmbM1.getItems().setAll(this.model.getGrafo().vertexSet());
    			this.cmbM2.getItems().setAll(this.model.getGrafo().vertexSet());
    		}
    	} catch (NumberFormatException nfe) {
    		this.txtResult.appendText("Inserire un minimo di minuti in un formato valido.\n");
    	}
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	Match source = this.cmbM1.getValue();
    	Match target = this.cmbM2.getValue();
    	if (source == null || target == null) {
    		this.txtResult.appendText("I campi M1 ed M2 non possono essere vuoti.");
    	}
    	else {
    		this.model.avviaRicorsione(source, target);
        	this.txtResult.appendText("Miglior cammino trovato:\n");
        	for (Match m : this.model.getBestPath()) {
        		this.txtResult.appendText(m.toString() + "\n");
        	}
        	this.txtResult.appendText("Score: " + this.model.getBestScore() + "\n");
    	}	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbMese.getItems().setAll(this.model.getAllMesi());
    }
    
    
}
