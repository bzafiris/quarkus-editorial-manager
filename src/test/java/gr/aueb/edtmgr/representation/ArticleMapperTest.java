package gr.aueb.edtmgr.representation;

import gr.aueb.edtmgr.domain.Article;
import gr.aueb.edtmgr.domain.Author;
import gr.aueb.edtmgr.domain.Journal;
import gr.aueb.edtmgr.domain.Researcher;
import gr.aueb.edtmgr.util.Fixture;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class ArticleMapperTest {

    @Inject
    private ArticleMapper articleMapper;

    private List<Author> getAuthors(){
        return List.of(new Author("Nikos", "Diamantidis", "AUEB", "nad@aueb.gr"),
                new Author("Manolis", "Giakoumakis", "AUEB", "mgia@aueb.gr"));
    }

    private Journal getJournal(){
        return new Journal("Journal of Systems and Software", "0164-1212");
    }

    private Researcher getResearcher(){
        return new Researcher("John", "Doe", "AUEB", "doe@aueb.gr");
    }

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


}