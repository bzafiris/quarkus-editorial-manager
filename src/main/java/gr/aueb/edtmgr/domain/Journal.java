package gr.aueb.edtmgr.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "journals")
public class Journal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="title", length=200, nullable=false, unique = true)
    private String title;

    @Column(name="issn", length=200, nullable=false, unique = true)
    private String issn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "editor_id", nullable = false)
    private Editor editor;

    public Journal() {
    }

    public Journal(String title, String issn) {
        this.title = title;
        this.issn = issn;
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

    public String getIssn() {
        return issn;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }
}
