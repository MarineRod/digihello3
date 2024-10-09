package com.diginamic.demo.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.diginamic.demo.dao.VilleDao;
import com.diginamic.demo.entite.Ville;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class VilleDaoImpl implements VilleDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Ville> findAll() {
		TypedQuery<Ville> query = entityManager.createQuery("SELECT v FROM Ville v", Ville.class);
		return query.getResultList();
	}

	@Override
	public Ville findById(int id) {
		return entityManager.find(Ville.class, id);
	}

	@Override
	public Optional<Ville> findByNom(String nom) {
		TypedQuery<Ville> query = entityManager.createQuery("SELECT v FROM Ville v WHERE v.nom = :nom", Ville.class);
		query.setParameter("nom", nom);
		return query.getResultStream().findFirst();
	}

	@Override
	public Ville save(Ville ville) {
		if (ville.getId() == 0) {
			entityManager.persist(ville);
			return ville;
		} else {
			return entityManager.merge(ville);
		}
	}

	@Override
	public void delete(int id) {
		Ville ville = findById(id);
		if (ville != null) {
			entityManager.remove(ville);
		}
	}
	
	 @Override
	    public Ville update(Ville ville) {
	        return entityManager.merge(ville);
	    }
	
	 @Override
	    public List<Ville> findTopNLargestCitiesByDepartment(int departmentId, int n) {
	        TypedQuery<Ville> query = entityManager.createQuery(
	            "SELECT v FROM Ville v WHERE v.departement.id = :departmentId ORDER BY v.nbHabitants DESC", Ville.class);
	        query.setParameter("departmentId", departmentId);
	        query.setMaxResults(n);
	        return query.getResultList();
	    }

	    @Override
	    public List<Ville> findCitiesByPopulationRangeAndDepartment(int minPopulation, int maxPopulation, int departmentId) {
	        TypedQuery<Ville> query = entityManager.createQuery(
	            "SELECT v FROM Ville v WHERE v.nbHabitants BETWEEN :minPopulation AND :maxPopulation AND v.departement.id = :departmentId", Ville.class);
	        query.setParameter("minPopulation", minPopulation);
	        query.setParameter("maxPopulation", maxPopulation);
	        query.setParameter("departmentId", departmentId);
	        return query.getResultList();
	    }

		

}