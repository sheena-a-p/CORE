package com.core.system.entity.System;
import lombok.Data;
import javax.persistence.*;


/* Entity for company
 * Author Sheena AP
 */
@Entity
@Data
@Table(schema = "org")
public class Company extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;
    private String companyCode;
    private String companyName;
    private Integer status;
}
