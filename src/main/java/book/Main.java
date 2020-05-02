package book;

import book.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (int i = 0; i < 1000; i++) {
                em.persist(BookGenerator.randomBook());
            }
            em.getTransaction().commit();
            listBooks();
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void listBooks() {
        EntityManager em = emf.createEntityManager();
        try{
            em.createQuery("SELECT b FROM Book b ORDER BY b.id", Book.class).getResultStream().forEach(System.out::println);
        }
        finally {
            em.close();
        }
    }
}
