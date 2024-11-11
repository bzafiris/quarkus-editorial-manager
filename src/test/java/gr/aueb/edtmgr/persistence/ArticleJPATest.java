package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArticleJPATest extends JPATest {

    @Test
    public void listArticles(){
        List<Article> result = em.createQuery("select a from Article a").getResultList();
        assertEquals(2, result.size());

        Article article = result.get(0);
        assertNotNull(article.getJournal());
        assertNotNull(article.getCorrespondentAuthor());

        Set<Author> authors = article.getAuthors();
        assertEquals(2, authors.size());
    }

    @Test
    public void listArticlesByCorrespondentAuthor(){
        Query query = em.createQuery("select a from Article a where a.correspondentAuthor.personalInfo.email=:email");
        query.setParameter("email", "ndia@aueb.gr");
        List<Article> result = query.getResultList();
        assertEquals(1, result.size());
    }

    @Test
    public void fetchArticleWithAuthorsAndCorrespondentAuthor(){
        Query query = em.createQuery("select a from Article a " +
                "join fetch a.authors " +
                "join fetch a.correspondentAuthor r " +
                "where r.personalInfo.email=:email");
        query.setParameter("email", "mgia@aueb.gr");
        List<Article> result = query.getResultList();
        assertEquals(1, result.size());

        em.close();

        List<Author> authors = new ArrayList<>(result.get(0).getAuthors());
        Author a = authors.get(0);
        assertEquals("University of Zurich", a.getAffiliation());

    }

    @Test
    public void persistArticleWithReviewInvitation(){

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        String researcherEmail = "ndia@aueb.gr";
        Article article = fetchArticleWithReviewInvitations(researcherEmail);

        String otherResearcherEmail = "mgia@aueb.gr";
        Researcher otherResearcher = fetchResearcherByEmail(otherResearcherEmail);

        ReviewInvitation invitation = article.inviteReviewer(otherResearcher);

        tx.commit();

        // invitation id should be initialized due to persist cascade
        assertNotNull(invitation.getId());

        em.close();

        em = JPAUtil.getCurrentEntityManager();
        // assert saved review invitations
        ReviewInvitation savedInvitation = em.find(ReviewInvitation.class, invitation.getId());
        assertNotNull(savedInvitation);

    }

    private Researcher fetchResearcherByEmail(String researcherEmail) {
        Query query;
        query = em.createQuery("select r from Researcher r " +
                "where r.personalInfo.email=:email");
        query.setParameter("email", researcherEmail);
        Researcher researcher = (Researcher)query.getSingleResult();
        return researcher;
    }

    private Article fetchArticleWithReviewInvitations(String researcherEmail) {
        // fetch article with correspondent author and review invitations
        // Notice left join fetch on review invitations, it is required
        // since review invitations collection may be empty
        Query query = em.createQuery("select a from Article a " +
                "left join fetch a.reviewInvitations inv " +
                "join fetch a.correspondentAuthor r " +
                "where r.personalInfo.email=:email");

        query.setParameter("email", researcherEmail);
        List<Article> result = query.getResultList();
        assertEquals(1, result.size());
        Article article = result.get(0);
        return article;
    }

    private Article fetchArticleWithReviewInvitationsAndReviewers(String researcherEmail) {
        // fetch article with correspondent author and review invitations
        // Notice left join fetch on review invitations, it is required
        // since review invitations collection may be empty
        Query query = em.createQuery("select a from Article a " +
                "left join fetch a.reviewInvitations inv " +
                "join fetch inv.reviewer " +
                "join fetch a.correspondentAuthor r " +
                "where r.personalInfo.email=:email");

        query.setParameter("email", researcherEmail);
        List<Article> result = query.getResultList();
        assertEquals(1, result.size());
        Article article = result.get(0);
        return article;
    }

    @Test
    public void persistArticleReview(){

        createAndPersistReviewInvitationForUser("mgia@aueb.gr");

        // start new session
        em = JPAUtil.getCurrentEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Article article = fetchArticleWithReviewInvitationsAndReviewers("ndia@aueb.gr");
        Researcher researcher = fetchResearcherByEmail("mgia@aueb.gr");

        Review review = article.createReview(researcher, 70, "Author comments",
                "editor comments", Recommendation.ACCEPT);

        em.persist(review);
        assertNotNull(review.getId());
        tx.commit();
        // clear cache, all results will be requested from db
        em.clear();

        Review savedReview = (Review) em.createQuery("select r from Review r").getSingleResult();
        assertNotNull(savedReview);

        assertEquals(researcher.getId(), savedReview.getInvitation().getReviewer().getId());

    }

    private void createAndPersistReviewInvitationForUser(String email) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Article article = fetchArticleWithReviewInvitations("ndia@aueb.gr");
        Researcher researcher = fetchResearcherByEmail(email);

        ReviewInvitation invitation = article.inviteReviewer(researcher);
        invitation.accept();
        tx.commit();

        assertNotNull(invitation.getId());
        em.close();
    }

}
