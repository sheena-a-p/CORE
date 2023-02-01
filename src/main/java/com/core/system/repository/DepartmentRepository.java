package com.core.system.repository;
import com.core.system.entity.org.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/* Repository interface for Company
 * Author Sheena AP
 */
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    @Override
    Optional<Department> findById(Integer departmentId);
}
