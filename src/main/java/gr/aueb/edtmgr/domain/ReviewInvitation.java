package gr.aueb.edtmgr.domain;

import gr.aueb.edtmgr.util.SystemDate;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "review_invitations")
public class ReviewInvitation {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private Researcher reviewer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name="created_at", nullable = false)
    private LocalDate createdAt = SystemDate.now();

    @Column(name = "accepted")
    private Boolean accepted = null;

    @OneToOne(mappedBy = "invitation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Review review;

    protected ReviewInvitation() {
    }

    public Researcher getReviewer() {
        return reviewer;
    }

    public void setReviewer(Researcher reviewer) {
        this.reviewer = reviewer;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void accept(){
        accepted = true;
    }

    public void reject(){
        accepted = false;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}