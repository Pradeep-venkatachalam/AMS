package org.example.attendancemanagementsystem.Repository;


import org.example.attendancemanagementsystem.Model.TeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherModel,Integer> {

}
