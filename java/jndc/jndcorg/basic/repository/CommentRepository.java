package jndc.jndcorg.basic.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jndc.jndcorg.basic.domain.entity.Comment;
import jndc.jndcorg.basic.domain.entity.Log;
import jndc.jndcorg.basic.domain.entity.QComment;
import jndc.jndcorg.basic.domain.entity.QLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
public class CommentRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CommentRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    public void saveComment(Comment comment) {
        em.persist(comment);
    }


    public Optional<Comment> findLastCommentById(){
        String query = "SELECT c FROM Comment c ORDER BY c.commentId DESC";

        try {
            Comment result = em.createQuery(query, Comment.class)
                    .setMaxResults(1)
                    .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            // 결과가 없는 경우
            return Optional.empty();
        }
    }



    public List<Comment> findComments(String searchCon, String searchInput, String startDate, String endDate) {

        QComment comment = QComment.comment;
        return queryFactory
                .select(comment)
                .from(comment)
                .where(likeSearchCon(searchCon), likeSearchInput(searchInput), dateCheck(startDate, endDate))
                .orderBy(comment.commentId.desc())
                .fetch();
    }


    private BooleanExpression likeSearchCon(String searchCon) {
        if (!searchCon.equals("전체") && StringUtils.hasText(searchCon)) {
            return QComment.comment.commentCategory.like("%" + searchCon + "%");
        }
        return null;
    }

    private BooleanExpression likeSearchInput(String searchInput) {
        if (StringUtils.hasText(searchInput)) {
            return QComment.comment.commentUser.name.like("%" + searchInput + "%");
        }
        return null;
    }

    private BooleanExpression dateCheck(String startDate,String endDate) {
        if (StringUtils.hasText(startDate) && StringUtils.hasText(endDate) ) {
            return QComment.comment.commentDate.between(startDate,endDate);
        }
        return null;
    }


    public void updateYN(String commentId) {
        Comment comment = em.find(Comment.class, commentId);
        comment.setCommentYN("Y");
    }
}
