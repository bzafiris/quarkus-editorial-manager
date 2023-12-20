package gr.aueb.edtmgr.representation;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ResearcherRepresentation {

    public Integer id;
    public String firstName;
    public String lastName;
    public String affiliation;
    public String email;
}