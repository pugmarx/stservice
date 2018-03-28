package cm.blzcln.stservice.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.appstream.model.ResourceNotAvailableException;

import cm.blzcln.stservice.model.Photo;
import cm.blzcln.stservice.model.Student;

/* FIXME Make tests independent */
/* FIXME Mock the s3 service */
@RunWith(SpringRunner.class)
public class StudentServiceTest {
	private static final String REST_SERVICE_URI = "http://localhost:8080/StudentRestApi";
	private static File resourcesDir;
	private static List<Student> toRemove = new ArrayList<Student>();

	@Before
	public void setUp() {
		resourcesDir = new File("src/test/resources");
	}

	@After
	public void tearDown() {
		for (Student s : toRemove) {
			removeStudent(s.getId());
		}
	}

	@Test
	public void testCreate() {
		Student student = createStudent(true);
		assertNotNull(student);
		assertNotEquals(0, student.getId());
	}

	@Test @Ignore
	public void testFetch() {
		Student student = createStudent(true);
		RestTemplate restTemplate = new RestTemplate();
		Student fetchedStudent = restTemplate.getForObject(REST_SERVICE_URI + "/student/" + student.getId(),
				Student.class);
		assertEquals(student, fetchedStudent);
	}

	@Test(expected = ResourceNotAvailableException.class)
	@Ignore
	public void testDelete() {
		Student student = createStudent(true);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/student/" + student.getId());

	}

	private static boolean removeStudent(int studentId) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/student/" + studentId);
		return true;
	}

	private static Student createStudent(boolean markForAutoRemoval) {
		RestTemplate restTemplate = new RestTemplate();
		Photo photo = new Photo();
		photo.setLocalPath(new File(resourcesDir, "test.png").getAbsolutePath());
		Student student = new Student(UUID.randomUUID().toString(), 33, photo, 3);
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/student/", student, Student.class);
		System.out.println("Location : " + uri.toASCIIString());
		if (markForAutoRemoval) {
			toRemove.add(student);
		}
		return student;
	}

	private static void generateStudents() {
		createStudent("Amy", 12, null, 4, 4, 14);
		createStudent("Ben", 13, null, 3, 2, 12);
		createStudent("Cathy", 11, null, 2, 5, 15);
		createStudent("Diana", 15, null, 1, 2, 12);
		createStudent("Emily", 12, null, 4, 4, 14);
		createStudent("Frank", 14, null, 3, 5, 15);
		createStudent("Gunther", 13, null, 3, 6, 16);
		createStudent("Heather", 11, null, 5, 3, 13);
		createStudent("Isaac", 12, null, 4, 3, 13);
		createStudent("Joanna", 15, null, 5, 5, 15);

	}

	private static void createStudent(String n, int a, Photo p, int r, int c, int t) {
		System.out.println("Testing create Student API----------");
		RestTemplate restTemplate = new RestTemplate();
		Student student = new Student(n, a, p, r, c, t);
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/student/", student, Student.class);
		System.out.println("Location : " + uri.toASCIIString());
	}
}
