/**
 * 
 */
package cm.blzcln.stservice.service;

import cm.blzcln.stservice.exception.StudentServiceException;
import cm.blzcln.stservice.model.Student;

/**
 * @author syed.rizvi
 *
 */
public interface StudentService {

	Iterable<Student> findAllStudents();

	Student findById(int id);
	
	Student findByName(String name);

	boolean isStudentExist(Student user);

	void saveStudent(Student user) throws StudentServiceException;

	void updateStudent(Student currentStudent);

	void deleteStudentById(int id);

	void deleteAllStudents();

}
