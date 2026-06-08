package org.example.attendancemanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attendanceId;

    private int totalhour;

    private int presenthour;

    // MANY ATTENDANCE → ONE STUDENT
    @ManyToOne
    @JoinColumn(name = "student_id")
   @JsonBackReference
    private StudentModel studentModel;

    public AttendanceModel( int totalhour, int presenthour) {

        this.totalhour = totalhour;
        this.presenthour = presenthour;

    }

    // MANY ATTENDANCE → ONE CLASSROOM
//    @ManyToOne
//    @JoinColumn(name = "classroom_id")
//    private ClassRoom classroom;

    @Transient
    public int getAbsenthour() {
        return totalhour - presenthour;
    }

    @Transient
    public double getAttendancePercentage() {
        if (totalhour <= 0) return 0;
        return ((double) presenthour / totalhour) * 100;
    }
}