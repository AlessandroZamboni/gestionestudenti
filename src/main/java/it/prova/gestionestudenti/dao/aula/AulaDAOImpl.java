package it.prova.gestionestudenti.dao.aula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.prova.gestionestudenti.model.Aula;

@Component
public class AulaDAOImpl implements AulaDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Aula> list() {
		return entityManager.createQuery("from Aula",Aula.class).getResultList();
	}

	@Override
	public Aula get(Long id) {
		return entityManager.find(Aula.class, id);
	}
	
	@Override
	public Aula findEagerFetch(Long idAula) {
		Query q = entityManager.createQuery("SELECT a FROM Aula a JOIN FETCH a.studenti WHERE a.id = :id");
		q.setParameter("id", idAula);
		return (Aula) q.getSingleResult();
	}


	@Override
	public void update(Aula aulaInstance) {
		aulaInstance = entityManager.merge(aulaInstance);
	}

	@Override
	public void insert(Aula aulaInstance) {
		entityManager.persist(aulaInstance);
	}

	@Override
	public void delete(Aula aulaInstance) {
		entityManager.remove(entityManager.merge(aulaInstance));
	}

	@Override
	public List<Aula> findByExample(Aula example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select a from Aula a where a.id = a.id ");

		if (StringUtils.isNotEmpty(example.getCodice())) {
			whereClauses.add(" a.codice  like :codice ");
			paramaterMap.put("codice", "%" + example.getCodice() + "%");
		}
		if (StringUtils.isNotEmpty(example.getMateria())) {
			whereClauses.add(" a.materia like :materia ");
			paramaterMap.put("materia", "%" + example.getMateria() + "%");
		}
		if (example.getCapienza() != null && example.getCapienza() > 0) {
			whereClauses.add(" a.capienza = :capienza ");
			paramaterMap.put("capienza", example.getCapienza());
		}
	
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Aula> typedQuery = entityManager.createQuery(queryBuilder.toString(), Aula.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

}
