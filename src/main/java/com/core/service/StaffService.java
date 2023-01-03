package com.core.service;
import com.core.entity.staff.Staff;
import com.core.view.StaffView;

public interface StaffService {

    void createStaff(Staff staff);

    void updateStaff(Staff staff);

    StaffView getStaffView(Integer staffId);
}
