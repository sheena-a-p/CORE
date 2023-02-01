package com.core.system.entity.System;
import lombok.Data;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


/* Audit filed class
 * Author Sheena AP
 */
@Data
@MappedSuperclass
public class AuditableModel {
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    private Integer staffCreated;
    private Integer staffModified;
}
