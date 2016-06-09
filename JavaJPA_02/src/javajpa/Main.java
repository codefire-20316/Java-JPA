/*
 * CodeFireUA public license.
 */
package javajpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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

        // JPQL
        TypedQuery<Post> query = manager.createQuery("SELECT p FROM Post p", Post.class);
        List<Post> resultList = query.getResultList();

        for (Post post : resultList) {
            Author author = post.getAuthor();
            System.out.printf("%s [%s]\n", post.getTitle(), author.getName());
            
            System.out.println("Also wrote:");
            for (Post wpost : author.getPosts()) {
                System.out.println("    " + wpost.getTitle());
            }
        }

        factory.close();

    }

}
