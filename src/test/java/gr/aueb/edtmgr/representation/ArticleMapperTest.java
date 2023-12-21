package gr.aueb.edtmgr.representation;

import gr.aueb.edtmgr.domain.Article;
import gr.aueb.edtmgr.domain.Author;
import gr.aueb.edtmgr.domain.Journal;
import gr.aueb.edtmgr.domain.Researcher;
import gr.aueb.edtmgr.persistence.ArticleRepository;
import gr.aueb.edtmgr.persistence.JPATest;
import gr.aueb.edtmgr.util.Fixture;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class ArticleMapperTest extends JPATest {

    @Inject
    private ArticleRepository articleRepository;

    @Inject
    ArticleMapper articleMapper;

    private Author findAuthor(List<Author> authors, String email){
        return authors.stream().filter(a -> a.getEmail().contains(email))
                .findFirst().orElse(null);
    }


    @Transactional
    @Test
    void testToModel(){

        ArticleRepresentation articleRepresentation = Fixture.getArticleRepresentation();

        Article entity = articleMapper.toModel(articleRepresentation);

        assertEquals(entity.getTitle(), articleRepresentation.title);
        assertEquals(entity.getKeywords(), articleRepresentation.keywords);
        assertEquals(entity.getSummary(), articleRepresentation.summary);
        assertEquals(entity.getCreatedAt(), LocalDate.of(2022, 12, 1));

        AuthorRepresentation authorDto = articleRepresentation.authors.get(0);
        assertEquals(1, entity.getAuthors().size());
        Author author = entity.getAuthors().stream().findFirst().get();
        assertEquals(author.getId(), authorDto.id);
        assertEquals(author.getEmail(), authorDto.email);
        assertEquals(author.getFirstName(), authorDto.firstName);
        assertEquals(author.getLastName(), authorDto.lastName);
        assertEquals(author.getAffiliation(), authorDto.affiliation);

        Journal journal = entity.getJournal();
        assertNotNull(journal);
        assertEquals("Journal of Systems and Software", journal.getTitle());

        assertNotNull(entity.getCorrespondentAuthor());

    }

    @Transactional
    @Test
    void testToRepresentation(){

        Article article = articleRepository.findById(4000);

        ArticleRepresentation articleRepresentation = articleMapper.toRepresentation(article);
        assertEquals(article.getTitle(), articleRepresentation.title);
        assertEquals(article.getId(), articleRepresentation.id);
        assertEquals(article.getSummary(), articleRepresentation.summary);
        assertEquals(article.getKeywords(), articleRepresentation.keywords);
        assertEquals(article.getCreatedAt().toString(), articleRepresentation.createdAt);
        assertEquals(article.getJournal().getIssn(), articleRepresentation.journalIssn);

        Researcher researcher = article.getCorrespondentAuthor();

        assertEquals(researcher.getFirstName(), articleRepresentation.researcher.firstName);
        assertEquals(researcher.getLastName(), articleRepresentation.researcher.lastName);
        assertEquals(researcher.getAffiliation(), articleRepresentation.researcher.affiliation);
        assertEquals(researcher.getEmail(), articleRepresentation.researcher.email);

        List<Author> authors = new ArrayList<>(article.getAuthors());
        assertEquals(2, articleRepresentation.authors.size());
        for(AuthorRepresentation r: articleRepresentation.authors){
            Author d = findAuthor(authors, r.email);
            assertEquals(d.getFirstName(), r.firstName);
            assertEquals(d.getLastName(), r.lastName);
            assertEquals(d.getAffiliation(), r.affiliation);
        }

    }



}