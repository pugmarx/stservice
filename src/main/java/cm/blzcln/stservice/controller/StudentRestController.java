package cm.blzcln.stservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import cm.blzcln.stservice.model.Student;
import cm.blzcln.stservice.service.StudentService;
 
@RestController
public class StudentRestController {
 
    @Autowired
    StudentService studentService;  //Service which will do all data retrieval/manipulation work
 
     
    //-------------------Retrieve All Students--------------------------------------------------------
     
    @RequestMapping(value = "/student/", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> listAllStudents() {
        Iterable<Student> studentIter = studentService.findAllStudents();
        List<Student> students = new ArrayList<Student>();
        studentIter.forEach(students :: add);
        if(students.isEmpty()){
            return new ResponseEntity<List<Student>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
    }
 
    
//    @RequestMapping(value = "/student/", method = RequestMethod.GET)
//    public ResponseEntity<Student[]> getAllStudents() {
//        List<Student> students = studentService.findAllStudents();
//        if(students.isEmpty()){
//            return new ResponseEntity<Student[]>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
//        }
//        Student[] stArr = students.toArray(new Student[students.size()]);
//        return new ResponseEntity<Student[]>(stArr, HttpStatus.OK);
//    }
 
    //-------------------Retrieve Single Student--------------------------------------------------------
     
    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudent(@PathVariable("id") int id) {
        System.out.println("Fetching Student with id " + id);
        Student student = studentService.findById(id);
        if (student == null) {
            System.out.println("Student with id " + id + " not found");
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }
    
    //-------------------Retrieve Single Student By Name --------------------------------------------------------
    
    @RequestMapping(value = "/student/n/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudent(@PathVariable("name") String name) {
        System.out.println("Fetching Student with name " + name);
        Student student = studentService.findByName(name);
        if (student == null) {
            System.out.println("Student with name " + name + " not found");
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }
 
     
     
    //-------------------Create a Student--------------------------------------------------------
     
    @RequestMapping(value = "/student/", method = RequestMethod.POST)
    public ResponseEntity<Void> createStudent(@RequestBody Student student,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Student " + student.getName());
 
        if (studentService.isStudentExist(student)) {
            System.out.println("A Student with name " + student.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        try{
        	studentService.saveStudent(student);
        }catch(Exception e){
        	return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/student/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
     
    //------------------- Update a Student --------------------------------------------------------
     
    @RequestMapping(value = "/student/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Student> updateStudent(@PathVariable("id") int id, @RequestBody Student student) {
        System.out.println("Updating Student " + id);
         
        Student currentStudent = studentService.findById(id);
         
        if (currentStudent==null) {
            System.out.println("Student with id " + id + " not found");
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
 
        currentStudent.setName(student.getName());
        currentStudent.setAge(student.getAge());
        currentStudent.setRating(student.getRating());
         
        studentService.updateStudent(currentStudent);
        return new ResponseEntity<Student>(currentStudent, HttpStatus.OK);
    }
 
    //------------------- Delete a Student --------------------------------------------------------
     
    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Student with id " + id);
 
        Student student = studentService.findById(id);
        if (student == null) {
            System.out.println("Unable to delete. Student with id " + id + " not found");
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
 
        studentService.deleteStudentById(id);
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }
 
     
    //------------------- Delete All Students --------------------------------------------------------
     
    @RequestMapping(value = "/student/", method = RequestMethod.DELETE)
    public ResponseEntity<Student> deleteAllStudents() {
        System.out.println("Deleting All Students");
 
        studentService.deleteAllStudents();
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }
 
}
