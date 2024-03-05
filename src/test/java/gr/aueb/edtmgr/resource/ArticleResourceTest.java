package gr.aueb.edtmgr.resource;

import gr.aueb.edtmgr.representation.ArticleRepresentation;
import gr.aueb.edtmgr.util.Fixture;
import gr.aueb.edtmgr.util.SystemDateStub;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ArticleResourceTest {

    @AfterEach
    public void tearDown(){
        SystemDateStub.setStub(null);
    }

    @Test
    void submitArticle() {

        SystemDateStub.setStub(LocalDate.of(2022, 12, 1));
        ArticleRepresentation articleRepresentation = Fixture.getArticleRepresentation();
        articleRepresentation.createdAt = null;

        ArticleRepresentation savedArticle = given()
                .contentType(ContentType.JSON)
                .body(articleRepresentation)
                .when()
                .post(Fixture.API_ROOT + "/articles")
                .then().statusCode(201)
                .extract().as(ArticleRepresentation.class);

        assertEquals(1000, savedArticle.researcher.id);
        assertEquals(1, savedArticle.authors.size());
        assertNotNull(savedArticle.authors.get(0).id);
        assertEquals("2022-12-01", savedArticle.createdAt);

    }

    /**
     * TBD
     */
    //@Test
    @TestTransaction
    void removeExistingArticle(){

        when()
                .delete(Fixture.API_ROOT + "/articles/4001")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @TestTransaction
    void removeNonExistingArticle(){

        when()
                .delete(Fixture.API_ROOT + "/articles/4002")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void inviteNewReviewer(){
        when()
                .post(Fixture.API_ROOT + "/articles/4000/invite/1002")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("Location", Matchers.matchesPattern(".*/invitations/[0-9]+"));
    }

    @Test
    public void denyInvitationToExistingReviewer(){
        when()
                .post(Fixture.API_ROOT + "/articles/4000/invite/2000")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}