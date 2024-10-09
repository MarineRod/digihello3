package com.diginamic.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.diginamic.demo.dao.VilleDao;
import com.diginamic.demo.entite.Ville;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class VilleService implements VilleDao {

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

}
