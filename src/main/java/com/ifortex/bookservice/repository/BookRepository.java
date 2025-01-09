package com.ifortex.bookservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class BookRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public BookRepository(EntityManagerFactory factory) {
        this.entityManagerFactory = factory.unwrap(SessionFactory.class);
    }

    public List<Set<String>> getBooksGenres() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Set<String>> genres = entityManager.createQuery("SELECT b.genres FROM Book b").getResultList();

        entityManager.getTransaction().commit();
        return genres;
    }
}
