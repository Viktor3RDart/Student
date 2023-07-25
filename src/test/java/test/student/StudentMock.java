package test.student;

public class StudentMock implements SudentRepo{

    @Override
    public int getRaintingForGradeSum(int sum) {
        return 10;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Student entity) {

    }

    @Override
    public void deleteAll(Iterable<Student> entities) {

    }

    @Override
    public Iterable<Student> findAll() {
        return null;
    }

    @Override
    public Student save(Student entity) {
        return null;
    }

    @Override
    public Iterable<Student> saveAll(Iterable<Student> entities) {
        return null;
    }
}
