package jndc.jndcorg.basic.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jndc.jndcorg.basic.domain.entity.Log;
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
public class LogRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public LogRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void saveLog(Log newLog) {
        em.persist(newLog);
    }

    public Optional<Log> findLastLogById(){
        String query = "SELECT t FROM Log t ORDER BY t.logId DESC";

        try {
            Log result = em.createQuery(query, Log.class)
                    .setMaxResults(1)
                    .getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            // 결과가 없는 경우
            return Optional.empty();
        }
    }



    public Page<Log> findLogs(String searchCon, String searchInput, String startDate, String endDate, int page, int size) {
        QLog logValue = QLog.log;
        Pageable pageable = PageRequest.of(page,size);

        List<Log> content = queryFactory
                .select(logValue)
                .from(logValue)
                .where(likeSearchCon(searchCon), likeSearchInput(searchInput), dateCheck(startDate, endDate))
                .orderBy(logValue.logId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = queryFactory
                .selectFrom(logValue)
                .where(likeSearchCon(searchCon), likeSearchInput(searchInput), dateCheck(startDate, endDate))
                .fetchCount();

        return new PageImpl<>(content, pageable, totalCount);
    }


    private BooleanExpression likeSearchCon(String searchCon) {
        if (!searchCon.equals("전체") && StringUtils.hasText(searchCon)) {
            return QLog.log.logWork.like("%" + searchCon + "%");
        }
        return null;
    }

    private BooleanExpression likeSearchInput(String searchInput) {
        if (StringUtils.hasText(searchInput)) {
            return QLog.log.logTargetUser.name.like("%" + searchInput + "%");
        }
        return null;
    }

    private BooleanExpression dateCheck(String startDate,String endDate) {
        if (StringUtils.hasText(startDate) && StringUtils.hasText(endDate) ) {
            return QLog.log.logDate.between(startDate,endDate);
        }
        return null;
    }




}
