package test.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    private String name;
    private int id;
    private List<Integer> marks = new ArrayList<>();

    public Student(String name, int id, List<Integer> arrayList) {
        this.name = name;
        this.id = id;
        this.marks = arrayList;
    }

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Student(int id) {
        this.id = id;
    }

    public Student(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMarks(List<Integer> marks) {

        this.marks = marks;
    }

    public List<Integer> getMarks() {

        return marks;
    }

    public void addGrade(int grade) {
        if (grade < 2 || grade > 5) {
            throw new IllegalArgumentException(grade + " is wrong grade");
        }
        marks.add(grade);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.marks);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.marks, other.marks);
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", id = " + id + ", marks=" + marks + '}';
    }
}