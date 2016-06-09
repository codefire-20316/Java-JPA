/*
 * CodeFireUA public license.
 */
package javajpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author CodeFireUA <edu@codefire.com.ua>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JavaJPAPU");

        EntityManager manager = factory.createEntityManager();
        
        TypedQuery<Author> query = manager.createNamedQuery("Author.findByName", Author.class);
        query.setParameter("author_name", "Unknown");
//        List<Author> resultList = query.getResultList();
//        
//        for (Author author : resultList) {
//            System.out.println(author.getName());
//        }
        try {
            Author author = query.getSingleResult();
            
            System.out.println(author.getName() + " posts:");
            
            for (Post post : author.getPosts()) {
                System.out.println("    " + post.getTitle());
            }
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } catch (NonUniqueResultException ex) {
            ex.printStackTrace();
        }

        factory.close();

    }

}
