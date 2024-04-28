package jndc.jndcorg.basic.domain.webDTO;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class UserUpdateForm {

    private String userId;
    private String phoneNumber;
    private String email;
}
