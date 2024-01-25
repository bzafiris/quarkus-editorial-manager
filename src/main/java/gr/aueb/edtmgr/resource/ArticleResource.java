package gr.aueb.edtmgr.resource;

import gr.aueb.edtmgr.domain.Article;
import gr.aueb.edtmgr.domain.Researcher;
import gr.aueb.edtmgr.domain.ReviewInvitation;
import gr.aueb.edtmgr.persistence.ArticleRepository;
import gr.aueb.edtmgr.persistence.ResearcherRepository;
import gr.aueb.edtmgr.representation.ArticleMapper;
import gr.aueb.edtmgr.representation.ArticleRepresentation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("articles")
@RequestScoped
public class ArticleResource {

    @Inject
    private Logger log;

    @Inject
    private ArticleRepository articleRepository;

    @Inject
    private ArticleMapper articleMapper;

    @Inject
    ResearcherRepository researcherRepository;

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ArticleRepresentation> listArticles(){
        log.info("List articles");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return articleRepository.listAll()
                .stream().map(a -> articleMapper.toRepresentation(a))
                .collect(Collectors.toList());
    }

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


    @DELETE
    @Path("{id:[0-9]+}")
    @Transactional
    public Response removeArticle(@PathParam("id") Integer articleId){
        Article article = articleRepository
                .find("id", articleId)
                .firstResult();

        if (article == null){
            return Response.status(404).build();
        }
        articleRepository.delete(articleId);
        return Response.noContent().build();
    }


    @POST
    @Path("{articleId:[0-9]+}/invite/{reviewerId:[0-9]+}")
    public Response inviteReviewer(@PathParam("articleId") Integer articleId,
                                   @PathParam("reviewerId") Integer reviewerId){

        ReviewInvitation invitation = createInvitation(articleId, reviewerId);
        if (invitation == null){
            return Response.status(404).build();
        }
        URI reviewerInvitationUri = uriInfo.getBaseUriBuilder()
                .path("invitations")
                .path(Integer.toString(invitation.getId()))
                .build();

        return Response.created(reviewerInvitationUri).build();
    }

    /**
     * FIXME: Should throw appropriate application exceptions to discriminate
     * between different error conditions
     * @param articleId
     * @param reviewerId
     * @return
     */
    @Transactional
    protected ReviewInvitation createInvitation(Integer articleId, Integer reviewerId){
        Article article = articleRepository.fetchWithReviewInvitations(articleId);
        if (article == null) {
            return null;
        }
        // fetch article with review invitations and reviewers for each
        Optional<Researcher> researcher = researcherRepository.find("id", reviewerId).firstResultOptional();
        if (researcher.isEmpty()){
            return null;
        }
        ReviewInvitation invitation = article.inviteReviewer(researcher.get());
        return invitation;
    }

}
