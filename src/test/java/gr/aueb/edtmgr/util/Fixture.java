package gr.aueb.edtmgr.util;


import gr.aueb.edtmgr.domain.Article;
import gr.aueb.edtmgr.domain.Researcher;
import gr.aueb.edtmgr.domain.ReviewInvitation;
import gr.aueb.edtmgr.representation.ArticleRepresentation;
import gr.aueb.edtmgr.representation.AuthorRepresentation;
import gr.aueb.edtmgr.representation.ResearcherRepresentation;

import java.time.LocalDate;
import java.util.ArrayList;

public class Fixture {

    public static String API_ROOT  = "http://localhost:8081";

    public static ArticleRepresentation getArticleRepresentation(){
        ArticleRepresentation dto = new ArticleRepresentation();
        dto.title = "Article title";
        dto.summary = "Article summary";
        dto.keywords = "Article keywords";
        dto.createdAt = "2022-12-01";
        dto.journalIssn = "0164-1212";

        dto.researcher = getResearcherRepresentation();
        dto.authors = new ArrayList<>();
        dto.authors.add(getAuthorRepresentation());

        return dto;
    }

    /**
     * Representation for researcher Nikos Diamantidis
     * @return
     */
    public static ResearcherRepresentation getResearcherRepresentation(){
        ResearcherRepresentation dto = new ResearcherRepresentation();
        dto.id = 1000;
        dto.firstName = "Nikos";
        dto.lastName = "Diamantidis";
        dto.affiliation = "AUEB";
        dto.email = "ndia@aueb.gr";
        return dto;
    }

    public static AuthorRepresentation getAuthorRepresentation(){
        AuthorRepresentation dto = new AuthorRepresentation();
        dto.affiliation = "AUEB";
        dto.firstName = "Manolis";
        dto.lastName = "Giakoumakis";
        dto.email = "mgia@aueb.gr";
        return dto;
    }
}