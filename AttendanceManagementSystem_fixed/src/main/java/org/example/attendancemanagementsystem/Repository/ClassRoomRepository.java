package org.example.attendancemanagementsystem.Repository;

import org.example.attendancemanagementsystem.Model.ClassRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRoomRepository extends CrudRepository<ClassRoom, Integer> {
}