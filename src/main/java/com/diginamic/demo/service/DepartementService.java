package com.diginamic.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.demo.dao.DepartementDao;
import com.diginamic.demo.entite.Departement;

import jakarta.transaction.Transactional;

@Service
public class DepartementService {

    @Autowired
    private DepartementDao departementDao;

    @Transactional
    public List<Departement> findAll() {
        return departementDao.findAll();
    }

    @Transactional
    public Departement findById(int id) {
        return departementDao.findById(id);
    }

    @Transactional
    public Departement save(Departement departement) {
        return departementDao.save(departement);
    }
    
    @Transactional
    public Departement update(Departement departement) {
        return departementDao.update(departement);
    }

    @Transactional
    public void delete(int id) {
        departementDao.delete(id);
    }

    @Transactional
    public Optional<Departement> findByNom(String nom) {
        return departementDao.findByNom(nom);
    }
}