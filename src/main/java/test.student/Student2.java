package test.student;

import connectjar.org.apache.http.HttpEntity;
import connectjar.org.apache.http.client.methods.CloseableHttpResponse;
import connectjar.org.apache.http.client.methods.HttpGet;
import connectjar.org.apache.http.impl.client.CloseableHttpClient;
import connectjar.org.apache.http.impl.client.HttpClients;
import connectjar.org.apache.http.util.EntityUtils;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class Student2 {
    @Setter
    SudentRepo repo;

    @Getter
    @Setter
    private String name;
    private List grades = new ArrayList<>();

    public Student2(String name) {
        this.name = name;
    }

    public List getGrades() {
        return new ArrayList<>(grades);
    }

    @SneakyThrows
    public void addGrade(int grade) {
        SudentRepo.addGradeRepo(grade);
        grades.add(grade);
    }

    @SneakyThrows
    public int raiting() {
        return repo.getRaintingForGradeSum(grades.stream().mapToInt(x -> (int) x).sum());

    }
}

