package cm.blzcln.stservice.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "student")
@Getter @Setter @NoArgsConstructor @ToString 
@EqualsAndHashCode
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
	private int rating;
	@Column
	private int klass;
	@Column
	private int classTeacher; //FIXME separate table
	
	@OneToOne(cascade = CascadeType.ALL)
	private Photo photo;

	public Student(String name, int age, Photo photo, int rating,
			int klass, int classTeacher) {
		this(name, age, photo, rating);
		this.klass = klass;
		this.classTeacher = classTeacher;
	}
	
	public Student(String name, int age, Photo photo, int rating) {
		super();
		this.name = name;
		this.age = age;
		this.photo = photo;
		this.rating = rating;
	}

	public Student(int id, String name, int age, Photo photo, int rating) {
		super();
		this.name = name;
		this.id = id;
		this.age = age;
		this.photo = photo;
		this.rating = rating;
	}

	public Student(int id, String name, int age, Photo photo, int rating,
			int klass, int classTeacher) {
		this(id, name, age, photo, rating);
		this.klass = klass;
		this.classTeacher = classTeacher;
	}
}
