package org.example.attendancemanagementsystem.Service;

import org.example.attendancemanagementsystem.Model.AdminModel;
import org.example.attendancemanagementsystem.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository ar;

    public String addAdmin(AdminModel adminModel) {
        // BUG FIX: new admin has id=0 (not set), so existsById(0) is always false — safe to just save
        ar.save(adminModel);
        return "Admin created";
    }

    public List<AdminModel> showAdmin(AdminModel adminModel) {
        return ar.findAll();
    }

    public AdminModel showAdminbyid(int id) {
        return ar.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin ID not found"));
    }

    public String updateAdmin(AdminModel adminModel) {
        // BUG FIX: was inverted — now correctly checks existence before updating
        if (!ar.existsById(adminModel.getAdminID())) {
            return "Admin not found";
        }
        ar.save(adminModel);
        return "Admin updated";
    }
}
