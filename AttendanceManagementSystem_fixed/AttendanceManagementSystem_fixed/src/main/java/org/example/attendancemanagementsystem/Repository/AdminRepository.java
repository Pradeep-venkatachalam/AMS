package org.example.attendancemanagementsystem.Repository;


import org.example.attendancemanagementsystem.Model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel,Integer> {
    
}
