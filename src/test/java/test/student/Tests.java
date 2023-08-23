//package test.student;
//
//import org.junit.jupiter.api.Test;
//import org.testng.Assert;
//
//import java.util.List;
//
//public class Tests {
//    @Test
//    public void marksInRange() {
//        List<Integer> lst = List.of(2, 3, 4, 5);
//        Student stud = new Student("vasia");
//        stud.addGrade(lst.get(0));
//        stud.addGrade(lst.get(1));
//        stud.addGrade(lst.get(2));
//        stud.addGrade(lst.get(3));
//        Assert.assertEquals(stud.getGrades(), lst);
//    }
//
//    @Test
//    public void marksNotInRange() {
//        List<Integer> lst = List.of(0, 1, 6, 7);
//        Student stud = new Student("vasia");
//        Assert.assertThrows(IllegalArgumentException.class, () -> stud.addGrade(lst.get(0)));
//        Assert.assertThrows(IllegalArgumentException.class, () -> stud.addGrade(lst.get(1)));
//        Assert.assertThrows(IllegalArgumentException.class, () -> stud.addGrade(lst.get(2)));
//        Assert.assertThrows(IllegalArgumentException.class, () -> stud.addGrade(lst.get(3)));
//    }
//    @Test
//    public void testRating() {
//        Student2 stud = new Student2("vasia");
//        stud.addGrade(4);
//        stud.setRepo(new StudentMock());
//        Assert.assertEquals(stud.raiting(), 10);
//    }
//    @Test
//    public void addGrade() {
//        List<Integer> lst = List.of(0, 1, 6, 7);
//        Student2 stud = new Student2("vasia");
//        Assert.assertThrows(IllegalArgumentException.class, () -> stud.addGrade(lst.get(0)));
//        Assert.assertThrows(IllegalArgumentException.class, () -> stud.addGrade(lst.get(1)));
//        Assert.assertThrows(IllegalArgumentException.class, () -> stud.addGrade(lst.get(2)));
//        Assert.assertThrows(IllegalArgumentException.class, () -> stud.addGrade(lst.get(3)));
//    }
//}
//
