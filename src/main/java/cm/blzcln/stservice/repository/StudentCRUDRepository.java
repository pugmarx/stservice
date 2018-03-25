package cm.blzcln.stservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cm.blzcln.stservice.model.Student;

public interface StudentCRUDRepository extends CrudRepository<Student, Integer>{
	
	List<Student> findByName(String name);
}
