package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.Article;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@RequestScoped
public class ArticleRepository implements PanacheRepositoryBase<Article, Integer> {


    @Transactional
    public void delete(Integer id){
        Article article = findById(id);
        article.clearReviewInvitations();
        delete(article);
    }

    @Transactional
    public Article fetchWithReviewInvitations(Integer articleId) {

        Query query = getEntityManager().createQuery(
                " select a from Article a" +
                " left join fetch a.reviewInvitations r" +
                " join fetch r.reviewer" +
                " where a.id = :id");
        query.setParameter("id", articleId);

        Article article = (Article) query.getSingleResult();

        return article;
    }

    public Article fetchWithAllDependencies(String email){

        Article article = find("join fetch authors " +
                "    join fetch journal j " +
                "    join fetch correspondentAuthor r " +
                "    join fetch reviewInvitations i" +
                "    join fetch i.review " +
                "    where r.personalInfo.email=?1", email)
                .firstResult();

        return article;
        // "select a from Article a " +
        //                "join fetch a.authors " +
        //                "join fetch a.correspondentAuthor r " +
        //                "where r.personalInfo.email=:email");
    }
}
