/**
 * 
 */
package cm.blzcln.stservice.service;

import java.util.List;

import cm.blzcln.stservice.model.Student;

/**
 * @author syed.rizvi
 *
 */
public interface StudentService {

	List<Student> findAllStudents();

	Student findById(long id);

	boolean isStudentExist(Student user);

	void saveStudent(Student user);

	void updateStudent(Student currentStudent);

	void deleteStudentById(long id);

	void deleteAllStudents();

}
