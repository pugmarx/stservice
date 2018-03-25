package cm.blzcln.stservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private String name;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column
	private int age;
	@Column
	private String photo; //FIXME separate table
	@Column
	private int rating;
	@Column
	private int klass;
	@Column
	private int classTeacher; //FIXME separate table

	public Student() {
	}

	
	public Student(String name, int age, String photo, int rating,
			int klass, int classTeacher) {
		this(name, age, photo, rating);
		this.klass = klass;
		this.classTeacher = classTeacher;
	}
	
	public Student(String name, int age, String photo, int rating) {
		super();
		this.name = name;
		//this.id = id;
		this.age = age;
		this.photo = photo;
		this.rating = rating;
	}

	public Student(int id, String name, int age, String photo, int rating) {
		super();
		this.name = name;
		this.id = id;
		this.age = age;
		this.photo = photo;
		this.rating = rating;
	}

	public Student(int id, String name, int age, String photo, int rating,
			int klass, int classTeacher) {
		// super();
		this(id, name, age, photo, rating);
		this.klass = klass;
		this.classTeacher = classTeacher;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getKlass() {
		return klass;
	}

	public void setKlass(int klass) {
		this.klass = klass;
	}

	public int getClassTeacher() {
		return classTeacher;
	}

	public void setClassTeacher(int classTeacher) {
		this.classTeacher = classTeacher;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;

	}

	public int getAge() {
		return this.age;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", id=" + id + ", age=" + age
				+ ", photo=" + photo + ", rating=" + rating + ", klass="
				+ klass + ", classTeacher=" + classTeacher + "]";
	}

}
