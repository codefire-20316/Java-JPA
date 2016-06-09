/*
 * CodeFireUA public license.
 */
package javajpa;

import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

        Post post = new Post();
        post.setAuthor(new Author("New Author"));

        manager.getTransaction().begin();
        manager.persist(post);
        manager.getTransaction().commit();

        Category[] categories = {
            new Category("Education"),
            new Category("Western"),
            new Category("Horror")
        };

        manager.getTransaction().begin();
        for (Category cat : categories) {
            manager.persist(cat);
        }
        manager.getTransaction().commit();
       
        post.getCategories().addAll(Arrays.asList(categories));
        
        manager.getTransaction().begin();
        manager.merge(post);
        manager.getTransaction().commit();
        
        for (Category category : categories) {
            manager.refresh(category);
            
            System.out.println(category.getName() + ": " + category.getPosts());
        }

        factory.close();

    }

}
