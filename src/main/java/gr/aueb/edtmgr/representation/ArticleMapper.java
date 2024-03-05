package gr.aueb.edtmgr.representation;

import gr.aueb.edtmgr.domain.Article;
import gr.aueb.edtmgr.domain.Journal;
import gr.aueb.edtmgr.persistence.JournalRepository;
import jakarta.inject.Inject;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.stream.Collectors;

@Mapper(componentModel = "jakarta",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {AuthorMapper.class, ResearcherMapper.class})
public abstract class ArticleMapper {

    @Inject
    JournalRepository journalRepository;

    @Mapping(target = "journalIssn", source = "journal.issn")
    @Mapping(target = "researcher", source = "correspondentAuthor")
    public abstract ArticleRepresentation toRepresentation(Article entity);

    @Mapping(target = "correspondentAuthor", source = "researcher")
    @Mapping(target = "reviewInvitations", ignore = true)
    @Mapping(target = "journal", ignore = true)
    public abstract Article toModel(ArticleRepresentation dto);

    @AfterMapping
    public void resolveJournalByIssn(ArticleRepresentation dto, @MappingTarget Article article){

        Journal journal = null;
        if (dto.journalIssn != null){
            journal = journalRepository.find("issn", dto.journalIssn)
                    .firstResultOptional().orElse(null);
        }
        article.setJournal(journal);
    }
}
