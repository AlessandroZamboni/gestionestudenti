package it.prova.gestionestudenti.service.studente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionestudenti.dao.studente.StudenteDAO;
import it.prova.gestionestudenti.model.Studente;

@Service
public class StudenteServiceImpl implements StudenteService {

	@Autowired
	private StudenteDAO studenteDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Studente> listAllStudenti() {
		return studenteDAO.list();
	}

	@Override
	@Transactional(readOnly = true)
	public Studente caricaSingoloStudente(Long id) {
		return studenteDAO.get(id);
	}

	@Override
	@Transactional
	public void aggiorna(Studente studenteInstance) {
		
		if(studenteInstance.getAula() != null) {
			
			if(studenteInstance.getAula().getCapienza() - studenteInstance.getAula().getStudenti().size() >= 1)
				studenteDAO.update(studenteInstance);
			else
				throw new RuntimeException("Non posso effettuare questa operazione.");
			
		} else
			studenteDAO.update(studenteInstance);
		
	}

	@Override
	@Transactional
	public void inserisciNuovo(Studente studenteInstance) {
		
		if(studenteInstance.getAula().getCapienza() - studenteInstance.getAula().getStudenti().size() >= 1)
			studenteDAO.insert(studenteInstance);
		else
			throw new RuntimeException("Non posso effettuare questa operazione.");
	}

	@Override
	@Transactional
	public void rimuovi(Studente studenteInstance) {
		studenteDAO.delete(studenteInstance);
	}

	@Override
	@Transactional
	public List<Studente> findByExample(Studente example) {
		return studenteDAO.findByExample(example);
	}

}
