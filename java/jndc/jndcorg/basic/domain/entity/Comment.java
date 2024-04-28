package jndc.jndcorg.basic.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "T_COMMENT")
@Data
public class Comment {

    @Id
    @Column(name = "COMMENT_KEYNO")
    private String commentId;

    @ManyToOne
    @JoinColumn(name = "COMMENT_DU_KEYNO")
    private User commentUser;

    @Column(name = "COMMENT_CONTENT")
    private String commentContent;

    @Column(name = "COMMENT_DATE")
    private String commentDate;

    @Column(name = "COMMENT_CATEGORY")
    private String commentCategory;

    @Column(name = "COMMENT_YN")
    private String commentYN;

    public Comment() {
    }

    public Comment(String commentId, User commentUser, String commentContent, String commentDate, String commentCategory, String commentYN) {
        this.commentId = commentId;
        this.commentUser = commentUser;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
        this.commentCategory = commentCategory;
        this.commentYN = commentYN;
    }
}
