package it.prova.gestionestudenti.dao.aula;

import it.prova.gestionestudenti.dao.IBaseDAO;
import it.prova.gestionestudenti.model.Aula;

public interface AulaDAO extends IBaseDAO<Aula>{

	public Aula findEagerFetch(Long idAula);
}
