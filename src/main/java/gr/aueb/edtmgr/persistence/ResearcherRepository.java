package gr.aueb.edtmgr.persistence;

import gr.aueb.edtmgr.domain.Journal;
import gr.aueb.edtmgr.domain.Researcher;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class ResearcherRepository implements PanacheRepositoryBase<Researcher, Integer> {

}
