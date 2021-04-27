package it.prova.gestionestudenti.service.aula;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionestudenti.dao.aula.AulaDAO;
import it.prova.gestionestudenti.model.Aula;

@Service
public class AulaServiceImpl implements AulaService {
	
	@Autowired
	private AulaDAO aulaDAO;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	@Transactional(readOnly = true)
	public List<Aula> listAllAule() {
		return aulaDAO.list();
	}

	@Override
	@Transactional(readOnly = true)
	public Aula caricaSingolaAula(Long id) {
		return aulaDAO.get(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Aula caricaSingolaAulaEagerStuenti(Long idAula) {
		return aulaDAO.findEagerFetch(idAula);
	}
	
	@Override
	@Transactional
	public void aggiorna(Aula aulaInstance) {
		aulaDAO.update(aulaInstance);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Aula aulaInstance) {
		aulaDAO.insert(aulaInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Aula aulaInstance) {
		aulaInstance = entityManager.merge(aulaInstance);
		if(aulaInstance.getStudenti().isEmpty())
			aulaDAO.delete(aulaInstance);
		else
			throw new RuntimeException("Non posso rimuovere l'aula.");
	}

	@Override
	@Transactional(readOnly = true)
	public List<Aula> findByExample(Aula example) {
		return aulaDAO.findByExample(example);
	}

	
}
