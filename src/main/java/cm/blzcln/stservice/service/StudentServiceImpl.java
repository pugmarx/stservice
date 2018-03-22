package cm.blzcln.stservice.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import cm.blzcln.stservice.model.Student;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	private static final AtomicLong counter = new AtomicLong();

	private static List<Student> students;

	static {
		students = populateDummyStudents();
	}

	public List<Student> findAllStudents() {
		return students;
	}

	public Student findById(long id) {
		for (Student student : students) {
			if (student.getId() == id) {
				return student;
			}
		}
		return null;
	}

	public Student findByName(String name) {
		for (Student student : students) {
			if (student.getName().equalsIgnoreCase(name)) {
				return student;
			}
		}
		return null;
	}

	public void saveStudent(Student student) {
		counter.incrementAndGet();
		student.setId(counter.intValue());
		students.add(student);
	}

	public void updateStudent(Student student) {
		int index = students.indexOf(student);
		students.set(index, student);
	}

	public void deleteStudentById(long id) {

		for (Iterator<Student> iterator = students.iterator(); iterator.hasNext();) {
			Student student = iterator.next();
			if (student.getId() == id) {
				iterator.remove();
			}
		}
	}

	public boolean isStudentExist(Student student) {
		return findByName(student.getName()) != null;
	}

	// this(id, name, age, photo, rating);
	private static List<Student> populateDummyStudents() {
		List<Student> students = new ArrayList<Student>();

		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Amy", 15, "", 4));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Ben", 16, "", 3));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Cathy", 16, "", 2));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Dan", 16, "", 4));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Emily", 15, "", 5));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Fraser", 15, "", 3));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Gunther", 17, "", 3));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Haley", 17, "", 1));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Ian", 15, "", 5));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Joe", 16, "", 5));

		return students;
	}

	public void deleteAllStudents() {
		students.clear();
	}

}