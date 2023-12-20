package gr.aueb.edtmgr.resource;

import gr.aueb.edtmgr.domain.Article;
import gr.aueb.edtmgr.persistence.ArticleRepository;
import gr.aueb.edtmgr.representation.ArticleMapper;
import gr.aueb.edtmgr.representation.ArticleRepresentation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("articles")
@RequestScoped
public class ArticleResource {

    @Inject
    private ArticleRepository articleRepository;

    @Inject
    private ArticleMapper articleMapper;

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response submitArticle(ArticleRepresentation articleDto){

        Article entity = articleMapper.toModel(articleDto);
        articleRepository.persist(entity);

        URI location = uriInfo.getAbsolutePathBuilder().path(
                Integer.toString(entity.getId())).build();
        return Response
                .created(location)
                .entity(articleMapper.toRepresentation(entity))
                .build();

    }



}

