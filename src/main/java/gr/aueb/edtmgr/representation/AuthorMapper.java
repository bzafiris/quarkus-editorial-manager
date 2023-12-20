package gr.aueb.edtmgr.representation;

import gr.aueb.edtmgr.domain.Author;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class AuthorMapper {

    public abstract AuthorRepresentation toRepresentation(Author entity);

    public abstract Author toModel(AuthorRepresentation dto);


}
