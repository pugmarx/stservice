package cm.blzcln.stservice.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.cj.jdbc.exceptions.OperationNotSupportedException;

import cm.blzcln.stservice.aws.service.S3Service;
import cm.blzcln.stservice.exception.StudentServiceException;
import cm.blzcln.stservice.model.Student;
import cm.blzcln.stservice.repository.StudentCRUDRepository;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentCRUDRepository studentRepository;

	@Autowired
	private S3Service s3Service;

	private static final AtomicLong counter = new AtomicLong();

	private static List<Student> students;

	static {
		students = populateDummyStudents();
	}

	public Iterable<Student> findAllStudents() {
		return studentRepository.findAll();
	}

	public Student findById(int id) {
		Student student = studentRepository.findOne(id);
		String imgKey = student.getPhoto();
		if(!StringUtils.isEmpty(imgKey)){
			//fetch it from s3
			String localPath = s3Service.downloadFile(imgKey);
			student.setPhoto(localPath);
		}
		return student;
	}

	public Student findByName(String name) {
		List<Student> students = studentRepository.findByName(name);
		if (!students.isEmpty()) {
			return students.get(0);
		}
		return null;
	}
	
	public void saveStudent(Student student) throws StudentServiceException {
		if (!StringUtils.isEmpty(student.getPhoto())) {
			File photo = new File(student.getPhoto());
			if (!photo.exists()) {
				throw new StudentServiceException("Photo path is invalid");
			}
			String keyName = generateFileName(photo);
			s3Service.uploadFile(keyName, photo.getAbsolutePath());
			student.setPhoto(keyName);
		}
		studentRepository.save(student);
	}

	private String generateFileName(File file) {
	    return new Date().getTime() + "-" + file.getName().replace(" ", "_");
	}
	
	public void updateStudent(Student student) {
		studentRepository.save(student);
	}

	public void deleteStudentById(int id) {
		studentRepository.delete(id);
	}

	public boolean isStudentExist(Student student) {
		List<Student> existingStList = studentRepository.findByName(student.getName());
		return existingStList != null && !existingStList.isEmpty();
		//return studentRepository.findByName(student.getName()) != null && student.getName().equa;
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
		throw new RuntimeException("method not supported yet");
		// students.clear();
	}

}