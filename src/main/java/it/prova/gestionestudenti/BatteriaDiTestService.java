package it.prova.gestionestudenti;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.gestionestudenti.model.Aula;
import it.prova.gestionestudenti.model.Studente;
import it.prova.gestionestudenti.service.aula.AulaService;
import it.prova.gestionestudenti.service.studente.StudenteService;

@Service
public class BatteriaDiTestService {

	@Autowired
	private AulaService aulaService;

	@Autowired
	private StudenteService studenteService;

	// casi di test (usare valorizzando la variabile casoDaTestare nel main)
	public static final String INSERISCI_NUOVA_AULA = "INSERISCI_NUOVA_AULA";
	public static final String INSERISCI_STUDENTI_DATA_AULA = "INSERISCI_STUDENTI_DATA_AULA";
	public static final String RIMUOVI_AULA = "RIMUOVI_AULA";
	public static final String ELENCA_TUTTE_LE_AULE = "ELENCA_TUTTE_LE_AULE";
	public static final String FIND_BY_EXAMPLE_BY_MATERIA = "FIND_BY_EXAMPLE_BY_MATERIA";
	public static final String UPDATE_STUDENTE_SET_COGNOME = "UPDATE_STUDENTE_SET_COGNOME";
	public static final String CARICA_AULA_EAGER = "CARICA_AULA_EAGER";
	public static final String UPDATE_AULA_SET_MATERIA = "UPDATE_AULA_SET_MATERIA";
	public static final String ELENCA_TUTTI_GLI_STUDENTI = "ELENCA_TUTTI_I_STUDENTI";
	public static final String FIND_BY_EXAMPLE_BY_COGNOME = "FIND_BY_EXAMPLE_BY_COGNOME";
	public static final String RIMUOVI_STUDENTE = "RIMUOVI_STUDENTE";

	public void eseguiBatteriaDiTest(String casoDaTestare) {
		try {
			switch (casoDaTestare) {
			case INSERISCI_NUOVA_AULA:
				Aula nuovaAula = new Aula("A1","Italiano",20);
				aulaService.inserisciNuovo(nuovaAula);
				System.out.println("Aula appena inserita: " + nuovaAula);
				break;

			case INSERISCI_STUDENTI_DATA_AULA:
				Studente nuovoStudente = new Studente("Alessandro","Zamboni",new Date());
				nuovoStudente.setAula(aulaService.caricaSingolaAulaEagerStuenti(1L));
				studenteService.inserisciNuovo(nuovoStudente);
				System.out.println("Studente appena inserito: " + nuovoStudente);
				break;
				
			case RIMUOVI_AULA:
				aulaService.rimuovi(aulaService.caricaSingolaAula(1L));
				break;
				
			case ELENCA_TUTTE_LE_AULE:
				System.out.println("Elenco delle aule:");
				for (Aula aulaItem : aulaService.listAllAule()) {
					System.out.println(aulaItem);
				}
				
				break;
			
			case FIND_BY_EXAMPLE_BY_MATERIA:
				System.out.println("########### EXAMPLE ########################");
				Aula aulaExample = new Aula();
				aulaExample.setMateria("Italiano");
				for (Aula aulaItem : aulaService.findByExample(aulaExample)) {
					System.out.println(aulaItem);
				}
				break;
				
			case UPDATE_STUDENTE_SET_COGNOME:
				Studente studenteEsistente = studenteService.caricaSingoloStudente(6L);
				studenteEsistente.setAula(aulaService.caricaSingolaAulaEagerStuenti(1L));
				if (studenteEsistente != null) {
					studenteEsistente.setCognome("cognome update");
					studenteService.aggiorna(studenteEsistente);
				}
				break;	
				
				
			case CARICA_AULA_EAGER:
				Aula aulaACaso = aulaService.caricaSingolaAulaEagerStuenti(1L);
				if (aulaACaso != null) {
					for (Studente studenteItem : aulaACaso.getStudenti()) {
						System.out.println(studenteItem);
					}
				}
				break;	
				
				
			case UPDATE_AULA_SET_MATERIA:
				Aula aulaEsistente = aulaService.caricaSingolaAula(1L);
				if (aulaEsistente != null) {
					aulaEsistente.setMateria("materia update");
					aulaService.aggiorna(aulaEsistente);
				}
				break;	
				
				
			case ELENCA_TUTTI_GLI_STUDENTI:
				System.out.println("Elenco tutti gli studenti:");
				for (Studente studenteItem : studenteService.listAllStudenti()) {
					System.out.println(studenteItem);
				}
				
				break;	
			case FIND_BY_EXAMPLE_BY_COGNOME:
				System.out.println("########### EXAMPLE ########################");
				Studente studenteExample = new Studente();
				studenteExample.setCognome("cognome update");
				for (Studente studenteItem : studenteService.findByExample(studenteExample)) {
					System.out.println(studenteItem);
				}
				break;	
				
			case RIMUOVI_STUDENTE:
				studenteService.rimuovi(studenteService.caricaSingoloStudente(1L));
				break;	
				
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
