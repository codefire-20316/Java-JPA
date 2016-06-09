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
            System.out.println(post);
        }

        //<editor-fold defaultstate="collapsed" desc="INSERT WITH JPA">
//        Post post= new Post();
//        post.setAuthor("Unknown");
//        post.setTimestamp(new Date());
//        post.setTitle("Simple post.");
//        post.setContent("Text for testing...");
//
//        System.out.println(post.getId());
//        manager.getTransaction().begin();
//        manager.persist(post); // INSERT
//        manager.getTransaction().commit();
//        System.out.println(post.getId());
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="UPDATE WITH JPA">
//        int idx = (int)(Math.random() * resultList.size());
//        Post randomPost = resultList.get(idx);
//
//        randomPost.setAuthor("Random Author");
//
//        manager.getTransaction().begin();
//        manager.merge(randomPost);
//        manager.getTransaction().commit();
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="DELETE WITH JPA">

//        int idx = (int)(Math.random() * resultList.size());
//        Post randomPost = resultList.get(idx);
//
//        manager.getTransaction().begin();
//        manager.remove(randomPost);
//        manager.getTransaction().commit();
//</editor-fold>

        factory.close();

    }

}
