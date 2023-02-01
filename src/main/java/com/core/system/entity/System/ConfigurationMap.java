package com.core.system.entity.System;
import lombok.Data;
import javax.persistence.*;

/* Entity for system configuration map
 * Author Sheena AP
 */
@Entity
@Data
@Table(schema = "system")
public class ConfigurationMap extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ConfigMapId;
    private Integer configId;
    private String notes;
    private String value1;
    private String value2;
    private String value3;
    private Integer companyId;
}
