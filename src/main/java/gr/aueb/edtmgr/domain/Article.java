package gr.aueb.edtmgr.domain;

import gr.aueb.edtmgr.util.SystemDate;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="title", length=200, nullable=false)
    private String title;

    @Column(name="summary", length=4000, nullable=false)
    private String summary;

    @Column(name="keywords", length=500, nullable=false)
    private String keywords;

    @Column(name="created_at")
    private LocalDate createdAt = SystemDate.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "journal_id", nullable = false)
    private Journal journal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "correspondent_author_id", nullable = false)
    private Researcher correspondentAuthor;

    @ManyToMany(cascade = { CascadeType.ALL},
            fetch=FetchType.LAZY)
    @JoinTable(name="article_authors",
            joinColumns = {@JoinColumn(name="article_id")},
            inverseJoinColumns = {@JoinColumn(name="author_id")}
    )
    private Set<Author> authors = new HashSet<Author>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ReviewInvitation> reviewInvitations = new HashSet<ReviewInvitation>();

    public Article() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Researcher getCorrespondentAuthor() {
        return correspondentAuthor;
    }

    public void setCorrespondentAuthor(Researcher correspondentAuthor) {
        this.correspondentAuthor = correspondentAuthor;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void addAuthor(Author author) {
        if (author == null) return;
        if (authors.contains(author)){
            throw new DomainException("Author already exists");
        }
        authors.add(author);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
    }

    public Set<ReviewInvitation> getReviewInvitations() {
        return new HashSet<>(reviewInvitations);
    }

    public void clearReviewInvitations(){
        reviewInvitations.clear();
    }

    public ReviewInvitation inviteReviewer(Researcher invitedReviewer) {

        for(ReviewInvitation invitation: reviewInvitations){
            if (invitation.getReviewer().equals(invitedReviewer)){
                throw new DomainException("Reviewer already invited");
            }
        }
        ReviewInvitation newInvitation = new ReviewInvitation();
        newInvitation.setArticle(this);
        newInvitation.setReviewer(invitedReviewer);
        reviewInvitations.add(newInvitation);
        return newInvitation;
    }

    public Review createReview(Researcher researcher, int score, String authorComments,
                               String editorComments, Recommendation recommendation) {

        // search review invitation
        ReviewInvitation reviewInvitation = null;
        for(ReviewInvitation invitation: reviewInvitations){
            if (invitation.getReviewer().equals(researcher)){
                reviewInvitation = invitation;
                break;
            }
        }

        if (reviewInvitation == null){
            throw new DomainException("Review creation without invitation is not allowed");
        }

        if (!reviewInvitation.getAccepted()){
            throw new DomainException("Review creation for rejected invitation is not allowed");
        }

        if (reviewInvitation.getReview() != null){
            throw new DomainException("Review already created for given researcher");
        }

        Review review = new Review(score, authorComments, editorComments, recommendation);
        review.setInvitation(reviewInvitation);

        return review;
    }
}
