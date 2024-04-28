package jndc.jndcorg.basic.domain.entity;

import jndc.jndcorg.basic.domain.entity.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "T_LOG")
@Data
public class Log {

    @Id
    @Column(name = "LOG_KEYNO")
    private String logId;

    @ManyToOne
    @JoinColumn(name = "LOG_DU_KEYNO")
    private User logUser;

    @Column(name = "LOG_IP")
    private String logIP;

    @Column(name = "LOG_DATE")
    private String logDate;

    @Column(name = "LOG_WORK")
    private String logWork;

    @ManyToOne
    @JoinColumn(name = "LOG_TARGET_KEY_NO")
    private User logTargetUser;

    @Column(name = "LOG_ERROR_STATUS")
    private String logErrorStatus;

    public Log() {
    }

    public Log(String logId, User logUser, String logIP, String logDate, String logWork, User logTargetUser, String logErrorStatus) {
        this.logId = logId;
        this.logUser = logUser;
        this.logIP = logIP;
        this.logDate = logDate;
        this.logWork = logWork;
        this.logTargetUser = logTargetUser;
        this.logErrorStatus = logErrorStatus;
    }
}
