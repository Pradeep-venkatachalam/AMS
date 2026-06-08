package org.example.attendancemanagementsystem.Service;

import org.example.attendancemanagementsystem.Model.AttendanceModel;
import org.example.attendancemanagementsystem.Repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    public String stuattend(AttendanceModel attendanceModel) {

        Integer studentId = attendanceModel.getStudentModel().getStudentId();

        List<AttendanceModel> list =
                attendanceRepository.findByStudentId(studentId);

        // FIRST TIME ENTRY
        if (list.isEmpty()) {

            attendanceRepository.save(attendanceModel);

            return "Student Attendance Added";
        }

        // UPDATE FIRST RECORD ONLY
        AttendanceModel existing = list.get(0);

        existing.setPresenthour(
                existing.getPresenthour() + attendanceModel.getPresenthour()
        );

        existing.setTotalhour(
                existing.getTotalhour() + attendanceModel.getTotalhour()
        );

        attendanceRepository.save(existing);

        return "Student Attendance Updated";
    }

    public String teacherattend(AttendanceModel attendanceModel) {

        attendanceRepository.save(attendanceModel);

        return "Teacher Attendance Added";
    }

    public AttendanceModel getAttendanceById(int id) {

        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance ID Not Found"));
    }

    public String updateAttendance(AttendanceModel attendanceModel) {

        attendanceRepository.save(attendanceModel);

        return "Attendance Updated Successfully";
    }
}