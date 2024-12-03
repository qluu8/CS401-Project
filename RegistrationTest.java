package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;

class RegistrationTest {
    private registration reg;
    private PrintWriter out;
    private BufferedReader in;
    private File testUsersFile;

    @BeforeEach
    void setUp() throws IOException {
        reg = new registration();
        testUsersFile = new File("users.txt");
        if (!testUsersFile.exists()) {
            testUsersFile.createNewFile();
        }
        out = new PrintWriter(new OutputStreamWriter(new ByteArrayOutputStream()));
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("".getBytes())));
    }

    @AfterEach
    void tearDown() {
        if (testUsersFile.exists()) {
            testUsersFile.delete();
        }
    }

    @Test
    void testRegisterUser_invalidRole() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("testuser\ntestpass\nINVALID_ROLE\n".getBytes())));
        String result = reg.registerUser(out, in);
        assertEquals("Invalid role! Only PUBLIC or STAFF are allowed.", result);
    }

    @Test
    void testRegisterUser_existingUser() throws IOException {
        // Set up a user in the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testUsersFile))) {
            writer.write("testuser,testpass,PUBLIC");
        }
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("testuser\ntestpass\nPUBLIC\n".getBytes())));
        String result = reg.registerUser(out, in);
        assertEquals("User already exists!", result);
    }

    @Test
    void testRegisterUser_newUser() throws IOException {
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("newuser\nnewpass\nPUBLIC\n".getBytes())));
        String result = reg.registerUser(out, in);
        assertTrue(result.contains("User registered successfully!"));
    }

    @Test
    void testValidateUser_validCredentials() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testUsersFile))) {
            writer.write("testuser,testpass,PUBLIC");
        }
        String result = reg.validateUser("testuser", "testpass");
        assertEquals("PUBLIC", result);
    }

    @Test
    void testValidateUser_invalidCredentials() {
        String result = reg.validateUser("wronguser", "wrongpass");
        assertNull(result);
    }

    @Test
    void testIsUserExists_userExists() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testUsersFile))) {
            writer.write("testuser,testpass,PUBLIC");
        }
        assertTrue(reg.isUserExists("testuser"));
    }

    @Test
    void testIsUserExists_userDoesNotExist() {
        assertFalse(reg.isUserExists("nonexistentuser"));
    }
}
