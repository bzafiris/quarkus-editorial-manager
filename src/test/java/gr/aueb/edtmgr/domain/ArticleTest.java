package gr.aueb.edtmgr.domain;

import gr.aueb.edtmgr.util.SystemDate;
import gr.aueb.edtmgr.util.SystemDateStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    LocalDate now = LocalDate.of(2022, 11, 11);
    LocalDate invitationDate = LocalDate.of(2022, 11, 20);
    Article article;
    Researcher researcher;
    ReviewInvitation invitation;

    @BeforeEach
    public void setup(){
        SystemDateStub.setStub(invitationDate);
        article = new Article();

        Author author = new Author("Manolis", "Giakoumakis", "AUEB", "mgia@aueb.gr");
        article.addAuthor(author);

        researcher = new Researcher();
        researcher.setId(1);
        invitation = article.inviteReviewer(researcher);
    }

    @Test
    public void denyDuplicateAuthors(){

        Author a1 = new Author("Mary", "Jones", "AUEB", "mary@aueb.gr");
        Author a2 = new Author("John", "Doe", "AUEB", "john1@aueb.gr");
        Author a3 = new Author("John", "Doe", "AUEB", "john2@aueb.gr");

        article.addAuthor(a1);
        article.addAuthor(a2);
        Assertions.assertThrows(DomainException.class, () -> {
            article.addAuthor(a3);
        });
    }

    @Test
    void inviteNewReviewer() throws Exception {

        assertNotNull(invitation);
        assertEquals(invitationDate, invitation.getCreatedAt());
        assertEquals(researcher, invitation.getReviewer());
        assertEquals(article, invitation.getArticle());
        assertNull(invitation.getAccepted());

    }

    @Test
    void inviteDifferentReviewers() throws Exception {

        Researcher mary = new Researcher();
        mary.setId(2);

        ReviewInvitation invitation = article.inviteReviewer(mary);
        assertNotNull(invitation);

        Set<ReviewInvitation> invitations = article.getReviewInvitations();
        assertEquals(2, invitations.size());

    }

    @Test()
    void denyInvitationOfInvitedReviewer(){

        Researcher researcher = new Researcher();
        researcher.setId(1);
        DomainException exception = assertThrows(DomainException.class, () ->{
            // invite the same reviewer twice
            article.inviteReviewer(researcher);
        });

        assertEquals(1, article.getReviewInvitations().size());
        assertEquals("Reviewer already invited", exception.getMessage());

    }

    // TBD
    @Test
    void createReviewByInvitedReviewer(){

    }

    @Test
    void denyReviewForRejectedInvitation(){

    }

    @Test
    void denyReviewWithoutInvitation(){

    }

    @Test
    void denyDuplicateReviewCreation(){

    }
}