package gr.aueb.edtmgr.representation;

import gr.aueb.edtmgr.domain.Article;
import gr.aueb.edtmgr.domain.Journal;
import gr.aueb.edtmgr.persistence.JournalRepository;
import jakarta.inject.Inject;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.stream.Collectors;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
imports = {HashSet.class, Collectors.class})
public abstract class ArticleMapper {

    @Inject
    JournalRepository journalRepository;

    @Inject
    AuthorMapper authorMapper;

    @Mapping(target = "journalIssn", source = "journal.issn")
    @Mapping(target = "researcher", source = "correspondentAuthor")
    public abstract ArticleRepresentation toRepresentation(Article entity);

    @Mapping(target = "correspondentAuthor", source = "researcher")
    @Mapping(target = "reviewInvitations", ignore = true)
    @Mapping(target = "journal", ignore = true)
    @Mapping(target = "authors", expression = "java(dto.authors.stream().map(a -> authorMapper.toModel(a)).collect(Collectors.toSet()))")
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
