package com.auction.springrestapi.Repo;

import com.auction.springrestapi.DTO.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<UserDTO> getUserForSA() {

        String jpql = "SELECT u FROM User u WHERE u.sentiment IS NOT NULL AND u.userName IS NOT NULL";

        TypedQuery<UserDTO> query = entityManager.createQuery(jpql, UserDTO.class);
        return query.getResultList();
    }
}
