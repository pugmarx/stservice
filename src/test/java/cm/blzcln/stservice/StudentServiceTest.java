package cm.blzcln.stservice;

import java.net.URI;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import cm.blzcln.stservice.model.Photo;
import cm.blzcln.stservice.model.Student;
import cm.blzcln.stservice.service.StudentService;

public class StudentServiceTest {
	public static final String REST_SERVICE_URI = "http://localhost:8080/StudentRestApi";
	
	@Autowired
	StudentService studentService;
	
	@Test
	@Ignore
	public void testCreate() {
		//generateStudents();
		createStudent();
	}
	
	@Test
	public void testFetch() {
		Student student = studentService.findById(11);
		System.out.println(student.getPhoto().getLocalPath());
	}
	
	private static void createStudent() {
		System.out.println("Testing create Student API----------");
		RestTemplate restTemplate = new RestTemplate();
		//Student student = new Student("Yasmin", 12, "/Users/Hobbes/Pictures/8bit/2086526.png", 5);
		Photo photo = new Photo();
		photo.setLocalPath("C:\\temp\\1.png");
		Student student = new Student("Kamakazi", 33, photo , 3);
		
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/student/",
				student, Student.class);
		System.out.println("Location : " + uri.toASCIIString());
	}
	
	private static void generateStudents(){
		createStudent("Amy", 12, null, 4, 4, 14);
		createStudent("Ben", 13, null, 3, 2, 12);
		createStudent("Cathy", 11, null, 2, 5, 15);
		createStudent("Diana", 15, null, 1,2, 12);
		createStudent("Emily", 12, null, 4,4, 14);
		createStudent("Frank", 14, null, 3, 5, 15);
		createStudent("Gunther", 13, null, 3, 6, 16);
		createStudent("Heather", 11, null, 5, 3, 13);
		createStudent("Isaac", 12, null, 4, 3, 13);
		createStudent("Joanna", 15, null, 5, 5, 15);
		
	}
	
	private static void createStudent(String n, int a, Photo p, int r, int c,
			int t) {
		System.out.println("Testing create Student API----------");
		RestTemplate restTemplate = new RestTemplate();
		Student student = new Student(n, a, p, r, c, t);
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/student/",
				student, Student.class);
		System.out.println("Location : " + uri.toASCIIString());
	}
}
