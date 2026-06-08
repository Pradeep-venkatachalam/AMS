package org.example.attendancemanagementsystem.Service;

import org.example.attendancemanagementsystem.Model.ClassRoom;
import org.example.attendancemanagementsystem.Repository.ClassRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassRoomService {

    @Autowired
    ClassRoomRepository classRoomRepository;

    public String createClassroom(ClassRoom classroom){

        classRoomRepository.save(classroom);

        return "Classroom Created Successfully";
    }
}