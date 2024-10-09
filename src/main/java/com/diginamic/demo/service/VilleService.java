package com.diginamic.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.demo.dao.VilleDao;
import com.diginamic.demo.entite.Ville;

import jakarta.transaction.Transactional;


@Service
public class VilleService {

    @Autowired
    private VilleDao villeDao;

    @Transactional
    public List<Ville> findAll() {
        return villeDao.findAll();
    }

    @Transactional
    public Ville findById(int id) {
        return villeDao.findById(id);
    }

    @Transactional
    public Ville save(Ville ville) {
        return villeDao.save(ville);
    }

    @Transactional
    public void delete(int id) {
        villeDao.delete(id);
    }

    @Transactional
    public Optional<Ville> findByNom(String nom) {
        return villeDao.findByNom(nom);
    }
    
    @Transactional
    public Ville update(Ville ville) {
        return villeDao.update(ville);
    }
    @Transactional
    public List<Ville> findTopNLargestCitiesByDepartment(int departmentId, int n) {
        return villeDao.findTopNLargestCitiesByDepartment(departmentId, n);
    }

    @Transactional
    public List<Ville> findCitiesByPopulationRangeAndDepartment(int minPopulation, int maxPopulation, int departmentId) {
        return villeDao.findCitiesByPopulationRangeAndDepartment(minPopulation, maxPopulation, departmentId);
    }
}