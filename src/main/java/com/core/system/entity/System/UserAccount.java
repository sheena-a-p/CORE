package com.core.system.entity.System;
import lombok.Data;
import javax.persistence.*;

/* Entity for User Account
 * Author Sheena AP
 */
@Entity
@Data
@Table(schema = "system")
public class UserAccount extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private Integer staffId;
    private String email;
    private String password;
    private String passwordToken;
    private String verificationToken;
    private String googleRefreshToken;
    private Integer status;
    private Integer companyId;

    public UserAccount(){}
}
