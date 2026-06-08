package org.example.attendancemanagementsystem.Repository;

import org.example.attendancemanagementsystem.Model.AttendanceModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends CrudRepository<AttendanceModel, Integer> {

    @Query("select a from AttendanceModel a where a.studentModel.studentId = :studentId")
    List<AttendanceModel> findByStudentId(@Param("studentId") Integer studentId);
}