package cm.blzcln.stservice.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cm.blzcln.stservice.aws.service.S3Service;
import cm.blzcln.stservice.exception.StudentServiceException;
import cm.blzcln.stservice.model.Photo;
import cm.blzcln.stservice.model.Student;
import cm.blzcln.stservice.repository.StudentCRUDRepository;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

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
		Photo photo = student.getPhoto();
		if (photo != null) {
			String remoteKey = student.getPhoto().getRemoteKey();
			if (!StringUtils.isEmpty(remoteKey)) {
				// fetch it from s3
				String localPath = s3Service.downloadFile(remoteKey);
				photo.setLocalPath(localPath);
			}
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
		Photo photo = student.getPhoto();
		if (photo != null) {
			String localPath = photo.getLocalPath();
			if (!StringUtils.isEmpty(localPath)) {
				File photoLocalPath = new File(localPath);
				if (!photoLocalPath.exists()) {
					throw new StudentServiceException("Photo path is invalid");
				}
				String keyName = generateFileName(photoLocalPath);
				s3Service.uploadFile(keyName, photoLocalPath.getAbsolutePath());
				student.getPhoto().setRemoteKey(keyName);
			}
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
		Student student = studentRepository.findOne(id);
		String imgKey = student.getPhoto().getRemoteKey();
		if (!StringUtils.isEmpty(imgKey)) {
			logger.debug(String.format("Removing file with key '%s' from S3...", imgKey));
			// delete it from s3
			s3Service.deleteFile(imgKey);
		}
		// FIXME
		// studentRepository.delete(id);
	}

	public boolean isStudentExist(Student student) {
		List<Student> existingStList = studentRepository.findByName(student.getName());
		return existingStList != null && !existingStList.isEmpty();
		// return studentRepository.findByName(student.getName()) != null &&
		// student.getName().equa;
	}

	// this(id, name, age, photo, rating);
	private static List<Student> populateDummyStudents() {
		List<Student> students = new ArrayList<Student>();

		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Amy", 15, null, 4));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Ben", 16, null, 3));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Cathy", 16, null, 2));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Dan", 16, null, 4));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Emily", 15, null, 5));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Fraser", 15, null, 3));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Gunther", 17, null, 3));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Haley", 17, null, 1));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Ian", 15, null, 5));
		counter.incrementAndGet();
		students.add(new Student(counter.intValue(), "Joe", 16, null, 5));

		return students;
	}

	public void deleteAllStudents() {
		throw new RuntimeException("method not supported yet");
		// students.clear();
	}

}