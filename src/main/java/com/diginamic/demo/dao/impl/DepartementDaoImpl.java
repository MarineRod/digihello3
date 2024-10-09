package com.diginamic.demo.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.diginamic.demo.dao.DepartementDao;
import com.diginamic.demo.entite.Departement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class DepartementDaoImpl implements DepartementDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Departement> findAll() {
        TypedQuery<Departement> query = entityManager.createQuery("SELECT d FROM Departement d", Departement.class);
        return query.getResultList();
    }

    @Override
    public Departement findById(int id) {
        return entityManager.find(Departement.class, id);
    }

    @Override
    public Optional<Departement> findByNom(String nom) {
        TypedQuery<Departement> query = entityManager.createQuery("SELECT d FROM Departement d WHERE d.nom = :nom", Departement.class);
        query.setParameter("nom", nom);
        return query.getResultStream().findFirst();
    }

    @Override
    public Departement save(Departement departement) {
        if (departement.getId() == 0) {
            entityManager.persist(departement);
            return departement;
        } else {
            return entityManager.merge(departement);
        }
    }
    
    @Override
    public Departement update(Departement departement) {
        return entityManager.merge(departement);
    }

    @Override
    public void delete(int id) {
        Departement departement = findById(id);
        if (departement != null) {
            entityManager.remove(departement);
        }
    }
}