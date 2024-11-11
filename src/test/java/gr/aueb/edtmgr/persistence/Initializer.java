package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class Initializer {

    private EntityManager em;

    public Initializer() {
        em = JPAUtil.getCurrentEntityManager();
    }

    private void eraseData() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.createNativeQuery("delete from article_authors").executeUpdate();
        em.createNativeQuery("delete from authors").executeUpdate();
        em.createNativeQuery("delete from articles").executeUpdate();
        em.createNativeQuery("delete from journals").executeUpdate();
        em.createNativeQuery("delete from users").executeUpdate();

        tx.commit();
    }

    public void prepareData() {

        eraseData();

        Researcher r1 = new Researcher("Nikos", "Diamantidis", "AUEB", "ndia@aueb.gr");
        Researcher r2 = new Researcher("Manolis", "Giakoumakis", "AUEB", "mgia@aueb.gr");

        Editor e1 = new Editor("Paris", "Avgeriou",
                "University of Groningen", "avgeriou@gmail.com");

        Journal j1 = new Journal("Journal of Systems and Software", "0164-1212");
        j1.setEditor(e1);

        Author a11 = new Author("Pooja", "Rani", "University of Bern", "pooja.rani@unibe.ch");
        Author a12 = new Author("Ariana", "Blasi", "Università della Svizzera italiana", "arianna.blasi@usi.ch");

        Author a21 = new Author("Enrico", "Fregnan", "University of Zurich", "fregnan@ifi.uzh.ch");
        Author a22 = new Author("Josua", "Fröhlich", "University of Zurich", "josua.froehlich@uzh.ch");

        Article article1 = new Article();
        article1.setTitle("A decade of code comment quality assessment: A systematic literature review");
        article1.setSummary("Code comments are important artifacts in software systems and play" +
                " a paramount role in many software engineering (SE) tasks...");
        article1.setKeywords("Code comments\n" +
                "Documentation quality\n" +
                "Systematic literature review");
        article1.setJournal(j1);
        article1.setCorrespondentAuthor(r1);
        article1.addAuthor(a11);
        article1.addAuthor(a12);


        Article article2 = new Article();
        article2.setTitle("Graph-based visualization of merge requests for code review");
        article2.setSummary("Code review is a software development practice aimed at assessing " +
                "code quality, finding defects, and sharing knowledge among developers ...");
        article2.setKeywords("Modern code review\n" +
                "Software visualization\n" +
                "Empirical software engineering");
        article2.setJournal(j1);
        article2.setCorrespondentAuthor(r2);
        article2.addAuthor(a21);
        article2.addAuthor(a22);

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(r1);
        em.persist(r2);
        em.persist(e1);
        em.persist(j1);
        em.persist(article1);
        em.persist(article2);

        tx.commit();

        em.close();

    }

}

