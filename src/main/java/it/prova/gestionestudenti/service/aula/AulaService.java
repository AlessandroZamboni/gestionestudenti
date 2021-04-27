package it.prova.gestionestudenti.service.aula;

import java.util.List;

import it.prova.gestionestudenti.model.Aula;

public interface AulaService {

	public List<Aula> listAllAule();

	public Aula caricaSingolaAula(Long id);

	public void aggiorna(Aula aulaInstance);

	public void inserisciNuovo(Aula aulaInstance);

	public void rimuovi(Aula aulaInstance);

	public List<Aula> findByExample(Aula example);
	
	public Aula caricaSingolaAulaEagerStuenti(Long idAula);
}
