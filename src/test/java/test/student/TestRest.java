package test.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class TestRest {
    private final String baseUrl = "http://localhost:8080/";
    private final String baseUrl_student = baseUrl + "student/";
    private final String baseUrl_topStudent = baseUrl + "topStudent/";
    private final List<Integer> lis = Arrays.asList(3, 5);
    private final List<Integer> lis_max = Arrays.asList(3, 6);
    private final List<Integer> lis_count_max = Arrays.asList(3, 5, 5, 5);
    private final Student st_full = new Student("Виктор1", 1, lis);
    private final Student st_id_name = new Student("Виктор2", 2);
    private final Student st_name = new Student("Виктор3");
    private final Student st_id = new Student(99);
    private final int valid_id = 1;
    private final int invalid_id = 100;


    @Test
    @SneakyThrows
    public void test_00_Create_some_student() {
        ObjectMapper mapper = new ObjectMapper();
        st_full.setId(99);
        st_full.setName("Иосиф");
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(st_full))
                .when().post()
                .then()
                .statusCode(201);
    }


    // 1. get /student/{id} возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе,
    // код 200.
    @Test
    @SneakyThrows
    public void test_01() {
        ObjectMapper mapper = new ObjectMapper();
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(st_full))
                .when().post()
                .then()
                .statusCode(201);
        RestAssured.given()
                .baseUri(baseUrl_student + st_full.getId())
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", Matchers.equalTo(st_full.getId()))
                .body("name", Matchers.equalTo(st_full.getName()));
    }

    // 2. get /student/{id} возвращает код 404, если студента с данным ID в базе нет.
    @Test
    @SneakyThrows
    public void test_02() {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(baseUrl_student + invalid_id);
        response
                .then()
                .statusCode(404);
    }

    // 3. post /student добавляет студента в базу, если студента с таким ID ранее не было, при этом имя заполнено,
    // код 201.
    @Test
    @SneakyThrows
    public void test_03() {
        ObjectMapper mapper = new ObjectMapper();
        st_id_name.setId(10);
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(st_id_name))
                .when().post()
                .then()
                .statusCode(201);
    }

    // 4. post /student обновляет студента в базе, если студент с таким ID ранее был, при этом имя заполнено, код 201.
    @Test
    @SneakyThrows
    public void test_04() {
        ObjectMapper mapper = new ObjectMapper();
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(st_id_name))
                .when().post()
                .then()
                .statusCode(201);
        st_id_name.setName("Коля");
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(st_id_name))
                .when().post()
                .then()
                .statusCode(201);
        RestAssured.given()
                .baseUri(baseUrl_student + st_id_name.getId())
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", Matchers.equalTo("Коля"));
    }

    // 5. post /student добавляет студента в базу, если ID null, то возвращается назначенный ID, код 201.
    @Test
    @SneakyThrows
    public void test_05() {
        ObjectMapper mapper = new ObjectMapper();
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(st_name))
                .when().post()
                .then()
                .statusCode(201);
    }

    // 6. post /student возвращает код 400, если имя не заполнено.
    @Test
    @SneakyThrows
    public void test_06() {
        ObjectMapper mapper = new ObjectMapper();
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(st_id))
                .when().post()
                .then()
                .statusCode(400);
    }

    // 7. delete /student/{id} удаляет студента с указанным ID из базы, код 200.
    @Test
    @SneakyThrows
    public void test_07() {
        ObjectMapper mapper = new ObjectMapper();
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(st_full))
                .when().post()
                .then()
                .statusCode(201);
        RestAssured.given()
                .baseUri(baseUrl_student + st_full.getId())
                .delete()
                .then()
                .statusCode(200);
        RestAssured.given()
                .baseUri(baseUrl_student + st_full.getId())
                .get()
                .then()
                .statusCode(404);
    }

    // 8. delete /student/{id} возвращает код 404, если студента с таким ID в базе нет.
    @Test
    @SneakyThrows
    public void test_08() {
        RestAssured.given()
                .baseUri(baseUrl_student + invalid_id)
                .delete()
                .then()
                .statusCode(404);
    }

    // 9. get /topStudent код 200 и пустое тело, если студентов в базе нет.
    @Test
    @SneakyThrows
    public void test_09() {
        // Берем студентов методом гет
        ObjectMapper mapper = new ObjectMapper();
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(baseUrl_topStudent);
        int status = response.getStatusCode();
        var body = response.getBody();
        String bodyAsString = body.asString();
        List<Integer> id = new ArrayList<>();
        //Если в студентах есть сущности, то удаляем, для создания условий для теста
        if (status == 200 && bodyAsString.length() > 0) {
            List<Map<String, Object>> list = mapper.readValue(bodyAsString, List.class);
            for (Map<String, Object> stringObjectMap : list) {
                id.add((Integer) stringObjectMap.get("id"));
            }
            for (Integer integer : id) {
                httpRequest.delete(baseUrl_student + integer);
            }
        } else
            // Далее сама проверка
            RestAssured.given()
                    .baseUri(baseUrl_topStudent)
                    .get()
                    .then()
                    .statusCode(200);
        Response response2 = httpRequest.get(baseUrl_topStudent);
        assert response2.getBody().asString().length() == 0 :
                "В запросе есть body - " + response2.getBody().asString();
    }

    //10. get /topStudent код 200 и пустое тело, если ни у кого из студентов в базе нет оценок.
    @Test
    @SneakyThrows
    public void test_10() {
        // Берем студентов методом гет
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper mapper2 = new ObjectMapper();
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(baseUrl_topStudent);
        int status = response.getStatusCode();
        var body = response.getBody();
        String bodyAsString = body.asString();
        List<Integer> id = new ArrayList<>();
        //Если в студентах есть сущности, то удаляем, для создания условий для теста
        if (status == 200 && bodyAsString.length() > 0) {
            List<Map<String, Object>> list = mapper.readValue(bodyAsString, List.class);
            for (Map<String, Object> stringObjectMap : list) {
                id.add((Integer) stringObjectMap.get("id"));
            }
            for (Integer integer : id) {
                httpRequest.delete(baseUrl_student + integer);
            }
        } else
            //Далее добавляем тестовые данные
            RestAssured.given()
                    .baseUri(baseUrl_student)
                    .contentType(ContentType.JSON)
                    .body(mapper2.writeValueAsString(st_id_name))
                    .when().post();
        st_id_name.setId(20);
        st_id_name.setName("Брюшко");
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper2.writeValueAsString(st_id_name))
                .when().post();
        // Далее сама проверка
        RestAssured.given()
                .baseUri(baseUrl_topStudent)
                .get()
                .then()
                .statusCode(200);
        Response response2 = httpRequest.get(baseUrl_topStudent);
        assert response2.getBody().asString().length() == 0 :
                "В запросе есть body - " + response2.getBody().asString();
    }

    // 11. get /topStudent код 200 и один студент, если у него максимальная средняя оценка, либо же среди всех студентов
// с максимальной средней у него их больше всего.
    @Test
    @SneakyThrows
    public void test_11() {
        // Берем студентов методом гет
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper mapper2 = new ObjectMapper();
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(baseUrl_topStudent);
        int status = response.getStatusCode();
        var body = response.getBody();
        String bodyAsString = body.asString();
        List<Integer> id = new ArrayList<>();
        //Если в студентах есть сущности, то удаляем, для создания условий для теста
        if (status == 200 && bodyAsString.length() > 0) {
            List<Map<String, Object>> list = mapper.readValue(bodyAsString, List.class);
            for (Map<String, Object> stringObjectMap : list) {
                id.add((Integer) stringObjectMap.get("id"));
            }
            for (Integer integer : id) {
                httpRequest.delete(baseUrl_student + integer);
            }
        } else
            //Далее добавляем тестовые данные
            RestAssured.given()
                    .baseUri(baseUrl_student)
                    .contentType(ContentType.JSON)
                    .body(mapper2.writeValueAsString(st_full))
                    .when().post();
        st_full.setMarks(lis_max);
        st_full.setName("Коля");
        st_full.setId(2);
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper2.writeValueAsString(st_full))
                .when().post();
        // Далее проверка - если у него максимальная средняя оценка
        RestAssured.given()
                .baseUri(baseUrl_topStudent)
                .get()
                .then()
                .statusCode(200);
        Response response2 = httpRequest.get(baseUrl_topStudent);
        assert Integer.parseInt(String.valueOf(response2.getBody().asString().charAt(7))) == st_full.getId() :
                "Данные не совпадают гет возвращает id - " + response2.getBody().asString().charAt(7)
                        + " а должен - " + st_full.getId();
        //Далее добавляем еще тестовые данные
        st_full.setMarks(lis_count_max);
        st_full.setName("Ваня");
        st_full.setId(3);
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper2.writeValueAsString(st_full))
                .when().post();
        // Далее проверка - среди всех студентов с максимальной средней оценкой у него их больше всего
        RestAssured.given()
                .baseUri(baseUrl_topStudent)
                .get()
                .then()
                .statusCode(200);
        Response response3 = httpRequest.get(baseUrl_topStudent);
        assert Integer.parseInt(String.valueOf(response3.getBody().asString().charAt(7))) == st_full.getId() :
                "Данные не совпадают гет возвращает id - " + response3.getBody().asString().charAt(7)
                        + " а должен - " + st_full.getId();
    }

    // 12. get /topStudent код 200 и несколько студентов, если у них всех эта оценка максимальная и при этом они равны
// по количеству оценок.
    @Test
    @SneakyThrows
    public void test_12() {
        // Берем студентов методом гет
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper mapper2 = new ObjectMapper();
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(baseUrl_topStudent);
        int status = response.getStatusCode();
        var body = response.getBody();
        String bodyAsString = body.asString();
        List<Integer> id = new ArrayList<>();
        //Если в студентах есть сущности, то удаляем, для создания условий для теста
        if (status == 200 && bodyAsString.length() > 0) {
            List<Map<String, Object>> list = mapper.readValue(bodyAsString, List.class);
            for (Map<String, Object> stringObjectMap : list) {
                id.add((Integer) stringObjectMap.get("id"));
            }
            for (Integer integer : id) {
                httpRequest.delete(baseUrl_student + integer);
            }
        } else
            //Далее добавляем тестовые данные
            RestAssured.given()
                    .baseUri(baseUrl_student)
                    .contentType(ContentType.JSON)
                    .body(mapper2.writeValueAsString(st_full))
                    .when().post();
        st_full.setMarks(lis_max);
        st_full.setName("Коля");
        st_full.setId(2);
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper2.writeValueAsString(st_full))
                .when().post();
        st_full.setMarks(lis_max);
        st_full.setName("Ваня");
        st_full.setId(3);
        RestAssured.given()
                .baseUri(baseUrl_student)
                .contentType(ContentType.JSON)
                .body(mapper2.writeValueAsString(st_full))
                .when().post();
        // Далее проверка - несколько студентов, если у них всех эта оценка максимальная
        // и при этом они равны по количеству оценок.
        RestAssured.given()
                .baseUri(baseUrl_topStudent)
                .get()
                .then()
                .statusCode(200);
        Response response2 = httpRequest.get(baseUrl_topStudent);
        String body_test = response2.getBody().asString();
        List<Map<String, Object>> list = mapper.readValue(body_test, List.class);
        assert list.size() == 2 : "В json иное кол-во студентов отличное от 2. а именно - " + list.size();
        assert list.get(0).get("marks").equals(st_full.getMarks()) && list.get(1).get("marks").
                equals(st_full.getMarks()) : "В json иное оценки студентов - " + list.get(0).get("marks")
                + " и " + list.get(1).get("marks") + " отличаются от валидной - " + st_full.getMarks();
    }
}

