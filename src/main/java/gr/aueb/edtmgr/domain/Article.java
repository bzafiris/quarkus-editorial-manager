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

    @ManyToMany(cascade = { CascadeType.ALL},
            fetch=FetchType.LAZY)
    @JoinTable(name="article_authors",
            joinColumns = {@JoinColumn(name="article_id")},
            inverseJoinColumns = {@JoinColumn(name="author_id")}
    )
    private Set<Author> authors = new HashSet<Author>();

    public Article() {
    }

    public Integer getId() {
        return id;
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

    public Set<Author> getAuthors() {
        return new HashSet<>(authors);
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

}
