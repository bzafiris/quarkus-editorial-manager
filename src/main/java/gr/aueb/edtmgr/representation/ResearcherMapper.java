package gr.aueb.edtmgr.representation;

import gr.aueb.edtmgr.domain.Article;
import gr.aueb.edtmgr.domain.Researcher;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ResearcherMapper {

    public abstract ResearcherRepresentation toRepresentation(Researcher entity);

    public abstract Researcher toModel(ResearcherRepresentation dto);


}
