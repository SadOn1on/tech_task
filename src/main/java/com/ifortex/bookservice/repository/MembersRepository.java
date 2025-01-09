package com.ifortex.bookservice.repository;

import com.ifortex.bookservice.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MembersRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public MembersRepository(EntityManagerFactory factory) {
        this.entityManagerFactory = factory.unwrap(SessionFactory.class);
    }

    public List<Member> findMembersByRegistrationYear(int year) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("SELECT m.name FROM Member m " +
                "WHERE YEAR(m.membershipDate) = :year " +
                "AND m.borrowedBooks IS EMPTY");
        query.setParameter("year", year);

        return (List<Member>) query.getResultList();
    }

    public List<Member> findMembersByBookGenreOrderByRegistration(String genre) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query q = entityManager.createNativeQuery(
                "SELECT m.* " +
                "FROM members m " +
                "JOIN member_books mb ON m.id = mb.member_id " +
                "JOIN books b ON mb.book_id = b.id " +
                "WHERE m.membership_date BETWEEN '2023-01-01' AND '2023-12-31' " +
                "  AND ? = ANY(b.genre) " +
                "ORDER BY ( " +
                "    SELECT MIN(b1.publication_date) " +
                "    FROM books b1 " +
                "    JOIN member_books mb1 ON b1.id = mb1.book_id " +
                "    WHERE mb1.member_id = m.id AND ? = ANY(b1.genre) " +
                "), m.membership_date;",
                Member.class
        );
        q.setParameter(1, genre);
        q.setParameter(2, genre);

        return (List<Member>) q.getResultList();
    }
}
