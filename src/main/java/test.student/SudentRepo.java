package test.student;

public interface SudentRepo {
        int getRaintingForGradeSum(int sum);
        long count();
        void delete(Student entity);
        void deleteAll(Iterable<Student> entities);
        Iterable<Student> findAll();
        Student save(Student entity);
        Iterable<Student> saveAll(Iterable<Student> entities);

       static void addGradeRepo(int grade) {
                if (grade < 2 || grade > 5) {
                        throw new IllegalArgumentException(grade + " is wrong grade");
                }
        }

    }


