package org.example.attendancemanagementsystem.Repository;

import org.example.attendancemanagementsystem.Model.AttendanceModel;
import org.example.attendancemanagementsystem.Model.StudentModel;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Integer> {

//    @Query("select a from AttendanceModel a where a.studentModel.studentId = :studentId")
//    AttendanceModel findByStudentId(@Param("studentId") String studentId);
//    @Query("select s from StudentModel s where s.studentModel.studentId")
}
