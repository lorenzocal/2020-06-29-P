package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private SimpleWeightedGraph<Match, DefaultWeightedEdge> grafo;
	private List<DefaultWeightedEdge> listaConnessioniMax;
	private List<Match> bestPath;
	private double bestScore;
	
	public Model() {
		super();
		this.dao = new PremierLeagueDAO();
	}

	public PremierLeagueDAO getDao() {
		return dao;
	}

	public SimpleWeightedGraph<Match, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public List<Mese> getAllMesi(){
		List<Mese> result = new LinkedList<>();
		result.add(new Mese(1, "Gennaio"));
		result.add(new Mese(2, "Febbraio"));
		result.add(new Mese(3, "Marzo"));
		result.add(new Mese(4, "Aprile"));
		result.add(new Mese(5, "Maggio"));
		result.add(new Mese(6, "Giugno"));
		result.add(new Mese(7, "Luglio"));
		result.add(new Mese(8, "Agosto"));
		result.add(new Mese(9, "Settembre"));
		result.add(new Mese(10, "Ottobre"));
		result.add(new Mese(11, "Novembre"));
		result.add(new Mese(12, "Dicembre"));
		return result;
	}
	
	public void creaGrafo(Mese mese, Integer min) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.dao.listAllMatchesMonth(mese));
		for (Match m1 : this.grafo.vertexSet()) {
			for (Match m2 : this.grafo.vertexSet()) {
				if (m1.getMatchID() < m2.getMatchID()) {
					Integer weight = this.dao.getEdgeWeight(m1, m2, min);
					if (weight > 0) {
						Graphs.addEdge(this.grafo, m1, m2, weight);
					}
				}
			}
		}
		for (DefaultWeightedEdge dfe : this.grafo.edgeSet()) {
			System.out.println(dfe.toString() + " " + this.grafo.getEdgeWeight(dfe));
		}
	}
	
	public void connessioneMax() {
		this.listaConnessioniMax = new ArrayList<>();
		double maxWeight = 0;
		
		for (DefaultWeightedEdge dfe : this.grafo.edgeSet()) {
			if (this.grafo.getEdgeWeight(dfe) > maxWeight) {
				maxWeight = this.grafo.getEdgeWeight(dfe);
			}
		}
		
		for (DefaultWeightedEdge dfe : this.grafo.edgeSet()) {
			if (this.grafo.getEdgeWeight(dfe) == maxWeight) {
				this.listaConnessioniMax.add(dfe);
			}
		}
	}

	public List<DefaultWeightedEdge> getListaConnessioniMax() {
		return listaConnessioniMax;
	}
	
	public void avviaRicorsione(Match source, Match target) {
		this.bestPath = new ArrayList<Match>();
		this.bestScore = 0;
		List<Match> parziale = new ArrayList<Match>();
		parziale.add(source);
		double score = 0;
		cerca(parziale, target, score);
	}
	
	public void cerca(List<Match> parziale, Match target, double score) {
		
		if (parziale.get(parziale.size()-1).getMatchID() == target.getMatchID()) {
			System.out.println(parziale);
			System.out.println(score);
			if (score > this.bestScore) {
				this.bestScore = score;
				this.bestPath = new ArrayList<>(parziale);
			}

		}
		
		else {
			List<Match> listaVicini = Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1));
			for (Match vicino : listaVicini) {
				if (this.isAggiungibile(parziale, vicino)) {
					parziale.add(vicino);
					score += this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(parziale.size()-2), vicino));
					this.cerca(parziale, target, score);
					parziale.remove(parziale.size()-1);
				}
			}
		}
	}
	
	public boolean isAggiungibile(List<Match> parziale, Match match) {
		for (Match m : parziale) {
			if (m.getTeamHomeID().compareTo(match.teamHomeID) == 0 && m.getTeamAwayID().compareTo(match.teamAwayID) == 0) {
				return false;
			}
			if (m.getTeamHomeID().compareTo(match.teamAwayID) == 0 && m.getTeamAwayID().compareTo(match.teamHomeID) == 0) {
				return false;
			}
		}
		return true;
	}

	public List<Match> getBestPath() {
		return bestPath;
	}

	public double getBestScore() {
		return bestScore;
	}
	
}
