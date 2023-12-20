package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.*;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class ArticleJPATest extends JPATest {

    @Test
    @Transactional
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
    @Transactional
    public void listArticlesByCorrespondentAuthor(){
        Query query = em.createQuery("select a from Article a where a.correspondentAuthor.personalInfo.email=:email");
        query.setParameter("email", "fregnan@ifi.uzh.ch");
        List<Article> result = query.getResultList();
        assertEquals(1, result.size());
    }

    @Test
    @Transactional
    public void fetchArticleWithAuthorsAndCorrespondentAuthor(){
        Query query = em.createQuery("select a from Article a " +
                "join fetch a.authors " +
                "join fetch a.correspondentAuthor r " +
                "where r.personalInfo.email=:email");
        query.setParameter("email", "fregnan@ifi.uzh.ch");
        List<Article> result = query.getResultList();
        assertEquals(1, result.size());

        em.detach(result.get(0));
        List<Author> authors = new ArrayList<>(result.get(0).getAuthors());
        Author a = authors.get(0);
        assertEquals("University of Zurich", a.getAffiliation());

    }

    @Test
    @Transactional
    public void persistArticleWithReviewInvitation(){

        String researcherEmail = "pooja.rani@unibe.ch";
        Article article = fetchArticleWithReviewInvitations(researcherEmail);

        String userEmail = "fregnan@ifi.uzh.ch";
        Researcher user = fetchResearcherByEmail(userEmail);

        ReviewInvitation invitation = article.inviteReviewer(user);

        em.persist(invitation);
        // invitation id should be initialized due to persist cascade
        assertNotNull(invitation.getId());

    }

    private Researcher fetchResearcherByEmail(String mgiaEmail) {
        Query query;
        // fetch researcher mgia
        query = em.createQuery("select r from Researcher r " +
                "where r.personalInfo.email=:email");
        query.setParameter("email", mgiaEmail);
        Researcher mgia = (Researcher)query.getSingleResult();
        return mgia;
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

    //@Test
    @TestTransaction
    public void persistArticleReview(){

        createAndPersistReviewInvitationForUser("fregnan@ifi.uzh.ch");

        Article article = fetchArticleWithReviewInvitationsAndReviewers("pooja.rani@unibe.ch");
        Researcher researcher = fetchResearcherByEmail("fregnan@ifi.uzh.ch");

        Review review = article.createReview(researcher, 70, "Author comments",
                "editor comments", Recommendation.ACCEPT);

        em.persist(review);
        assertNotNull(review.getId());

        Review savedReview = (Review) em.createQuery("select r from Review r").getSingleResult();
        assertNotNull(savedReview);

        assertEquals(researcher.getId(), savedReview.getInvitation().getReviewer().getId());

    }

    private void createAndPersistReviewInvitationForUser(String email) {
        Article article = fetchArticleWithReviewInvitations(email);
        Researcher researcher = fetchResearcherByEmail(email);

        ReviewInvitation invitation = article.inviteReviewer(researcher);
        invitation.accept();
    }

}
