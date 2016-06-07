/*
 * CodeFireUA public license.
 */

package javajpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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
        
        // JPQL
        Query query = manager.createQuery("SELECT p FROM Post p");
        List resultList = query.getResultList();
        
        for (Object post : resultList) {
            System.out.println(post);
        }
        
        factory.close();
        
    }

}
