package it.polito.tdp.PremierLeague.model;

public class Mese {
	
	private Integer numeroMese;
	private String nomeMese;
	
	public Mese(Integer numeroMese, String nomeMese) {
		super();
		this.numeroMese = numeroMese;
		this.nomeMese = nomeMese;
	}

	public Integer getNumeroMese() {
		return numeroMese;
	}

	public String getNomeMese() {
		return nomeMese;
	}

	@Override
	public String toString() {
		return nomeMese;
	}
	
	
	
}
