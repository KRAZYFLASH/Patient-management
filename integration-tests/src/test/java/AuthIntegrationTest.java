import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthIntegrationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
        // opsional: kalau sering butuh ini
        // RestAssured.basePath = "/";
    }

    @Test
    void shouldReturnOKWithValidToken() {
        String loginPayload = """
                {
                  "email": "testuser@test.com",
                  "password": "password123"
                }
                """;

        Response response =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(loginPayload)
                        .when()
                        .post("/auth/login")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("token", notNullValue())
                        .body("token", is(not(emptyOrNullString())))
                        .extract()
                        .response();

        String token = response.jsonPath().getString("token");
        System.out.println("Generated Token: " + token);
    }

    /**
     * Ini test untuk request yang VALID formatnya,
     * tapi kredensial SALAH => harusnya 401.
     *
     * Pastikan password panjang >= aturan validasi kamu (misal min 8).
     */
    @Test
    void shouldReturn401WhenWrongPasswordButValidFormat() {
        String loginPayload = """
                {
                  "email": "testuser@test.com",
                  "password": "passwordSALAH123"
                }
                """;

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(400);
    }

    /**
     * Ini test untuk request yang TIDAK valid (contoh password terlalu pendek).
     * Kalau backend kamu pakai Bean Validation, biasanya balikin 400.
     */
    @Test
    void shouldReturn400WhenPayloadInvalid() {
        String loginPayload = """
                {
                  "email": "testuser@test.com",
                  "password": "short"
                }
                """;

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(400);
    }

    /**
     * Ini test untuk email format salah => 400
     */
    @Test
    void shouldReturn400WhenEmailFormatInvalid() {
        String loginPayload = """
                {
                  "email": "invalid-email-format",
                  "password": "password123"
                }
                """;

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(400);
    }
}
