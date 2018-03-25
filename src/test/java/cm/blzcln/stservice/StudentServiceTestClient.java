package cm.blzcln.stservice;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import cm.blzcln.stservice.model.Student;

public class StudentServiceTestClient {

	public static final String REST_SERVICE_URI = "http://localhost:8080/StudentRestApi";

	/* GET */
	@SuppressWarnings("unchecked")
	private static void listAllStudents() {
		System.out.println("Testing listAllStudents API-----------");

		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap<String, Object>> studentsMap = restTemplate
				.getForObject(REST_SERVICE_URI + "/student/", List.class);

		if (studentsMap != null) {
			for (LinkedHashMap<String, Object> map : studentsMap) {
				System.out.println("Student : id=" + map.get("id") + ", Name="
						+ map.get("name") + ", Age=" + map.get("age")
						+ ", Rating=" + map.get("rating"));
			}
		} else {
			System.out.println("No student exist----------");
		}
	}

	private static void listStudents() {
		System.out.println("Testing listStudents API-----------");

		RestTemplate restTemplate = new RestTemplate();
		// List<LinkedHashMap<String, Object>> studentsMap =
		// restTemplate.getForObject(REST_SERVICE_URI+"/student/all",
		// List.class);
		Student[] students = restTemplate.getForObject(REST_SERVICE_URI
				+ "/student/", Student[].class);
		if (students != null) {
			for (Student student : students) {
				System.out.println(student);
			}
		} else {
			System.out.println("No student exist----------");
		}
	}

	/* GET */
	private static void getStudent() {
		System.out.println("Testing getStudent API----------");
		RestTemplate restTemplate = new RestTemplate();
		Student student = restTemplate.getForObject(REST_SERVICE_URI
				+ "/student/1", Student.class);
		System.out.println(student);
	}

	/* POST */
	private static void createStudent() {
		System.out.println("Testing create Student API----------");
		RestTemplate restTemplate = new RestTemplate();
		Student student = new Student("Zaba", 17, "/Users/Hobbes/Dev/file.txt", 7);
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/student/",
				student, Student.class);
		System.out.println("Location : " + uri.toASCIIString());
	}

	private static void createStudent(String n, int a, String p, int r, int c,
			int t) {
		System.out.println("Testing create Student API----------");
		RestTemplate restTemplate = new RestTemplate();
		Student student = new Student(n, a, p, r, c, t);
		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/student/",
				student, Student.class);
		System.out.println("Location : " + uri.toASCIIString());
	}

	/* PUT */
	private static void updateStudent() {
		System.out.println("Testing update Student API----------");
		RestTemplate restTemplate = new RestTemplate();
		Student student = new Student(1, "Amy", 45, "", 3);
		restTemplate.put(REST_SERVICE_URI + "/student/1", student);
		System.out.println(student);
	}

	/* DELETE */
	private static void deleteStudent() {
		System.out.println("Testing delete Student API----------");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/student/10");
	}

	/* DELETE */
	private static void deleteAllStudents() {
		System.out.println("Testing all delete Students API----------");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(REST_SERVICE_URI + "/student/");
	}

	private static void generateStudents(){
//		createStudent("Amy", 12, "", 4, 4, 14);
//		createStudent("Ben", 13, "", 3, 2, 12);
//		createStudent("Cathy", 11, "", 2, 5, 15);
//		createStudent("Diana", 15, "", 1,2, 12);
//		createStudent("Emily", 12, "", 4,4, 14);
//		createStudent("Frank", 14, "", 3, 5, 15);
//		createStudent("Gunther", 13, "", 3, 6, 16);
//		createStudent("Heather", 11, "", 5, 3, 13);
//		createStudent("Isaac", 12, "", 4, 3, 13);
//		createStudent("Joanna", 15, "", 5, 5, 15);
		
		
	}
	
	public static void main(String args[]) {
		createStudent();
		
		//generateStudents();
		//listStudents();
		listAllStudents();
		//getStudent();
		// createStudent();
		// listAllStudents();
		//updateStudent();
		// listAllStudents();
		//deleteStudent();
		//listAllStudents();
		// deleteAllStudents();
		// listAllStudents();
	}
}