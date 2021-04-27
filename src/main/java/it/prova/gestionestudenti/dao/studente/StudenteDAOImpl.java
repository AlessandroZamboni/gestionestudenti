package it.prova.gestionestudenti.dao.studente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.prova.gestionestudenti.model.Studente;

@Component
public class StudenteDAOImpl implements StudenteDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Studente> list() {
		return entityManager.createQuery("from Studente",Studente.class).getResultList();
	}

	@Override
	public Studente get(Long id) {
		return entityManager.find(Studente.class, id);
	}

	@Override
	public void update(Studente studenteInstance) {
		studenteInstance = entityManager.merge(studenteInstance);
	}

	@Override
	public void insert(Studente studenteInstance) {
		entityManager.persist(studenteInstance);
	}

	@Override
	public void delete(Studente studenteInstance) {
		entityManager.remove(entityManager.merge(studenteInstance));
	}

	@Override
	public List<Studente> findByExample(Studente example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select s from Studente s where s.id = s.id ");

		if (StringUtils.isNotEmpty(example.getNome())) {
			whereClauses.add(" s.nome  like :nome ");
			paramaterMap.put("nome", "%" + example.getNome() + "%");
		}
		if (StringUtils.isNotEmpty(example.getCognome())) {
			whereClauses.add(" s.cognome like :cognome ");
			paramaterMap.put("cognome", "%" + example.getCognome() + "%");
		}
		if (example.getDataNascita() != null) {
			whereClauses.add(" s.dataNascita = :dataNascita ");
			paramaterMap.put("dataNascita", new java.sql.Date(example.getDataNascita().getTime()));
		}
		
		if (example.getAula() != null) {
			whereClauses.add(" s.aula = :aula ");
			paramaterMap.put("aula", example.getAula());
		}
	
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Studente> typedQuery = entityManager.createQuery(queryBuilder.toString(), Studente.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

}
