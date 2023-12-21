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
}
