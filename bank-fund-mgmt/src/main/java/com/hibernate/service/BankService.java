package com.hibernate.service;

import com.hibernate.exception.NotFoundException;
import com.hibernate.model.Fund;
import com.hibernate.model.Manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankService {

    @PersistenceContext // enables entity manager
    private EntityManager em;

    @Transactional
    public void insertManager(Manager manager) {
        em.persist(manager);
    }

    public Manager getManagerById(int managerID) {
        Manager manager = em.find(Manager.class, managerID);
        if(manager == null)
            throw new NotFoundException("Manager not found for the given ID");
        return manager;
    }

    @Transactional
    public void setFund(Fund fund) {
        em.persist(fund);
    }

    public List<?> getAllFundWithManager() { // wildcard
        String jpql = "select f from Fund f"; // hql : from Fund f
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }
}
