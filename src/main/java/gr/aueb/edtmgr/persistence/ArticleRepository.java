package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.Article;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class ArticleRepository implements PanacheRepositoryBase<Article, Integer> {

}
