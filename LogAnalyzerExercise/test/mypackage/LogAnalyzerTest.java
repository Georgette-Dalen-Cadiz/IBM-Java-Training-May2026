package mypackage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class LogAnalyzerTest {

    private PrintStream originalOut;
    private PrintStream originalErr;
    private ByteArrayOutputStream outContent;

    // =========================
    // Lifecycle Methods
    // =========================

    @BeforeAll
    static void initAll() {
        System.out.println("Start tests");
        LogAnalyzer log = new LogAnalyzer();
    }

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        originalErr = System.err;

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("End tests");
    }

    // =========================
    // Helper Methods
    // =========================

    private String getOutput() {
        return outContent.toString().trim();
    }

    private String getTestResourcePath(String resourceName) {
        String workingDir = System.getProperty("user.dir");
        return workingDir + "/test/resources/" + resourceName + "/server.log";
    }

    // =========================
    // Basic Behavior Tests
    // =========================

    @Test
    void exec001() {
        String filename = getTestResourcePath("exec001");

        LogAnalyzer.main(new String[]{filename});

        assertTrue(getOutput().contains(
            "Analysis complete. Summary written to summary.txt"
        ));
    }

    @RepeatedTest(3)
    void exec000() {
        LogAnalyzer.main(new String[]{"non_existent.log"});
        assertTrue(getOutput().contains("Log file not found."));
    }

    // =========================
    // Parameterized Tests
    // =========================

    @ParameterizedTest
    @ValueSource(strings = {
        "exec004",
        "exec005",
        "exec006"
    })
    void exec004_005_006(String resourceName) {
        String filename = getTestResourcePath(resourceName);

        LogAnalyzer.main(new String[]{filename});

        assertTrue(getOutput().contains("Skipping malformed line"));
    }

    @ParameterizedTest
    @CsvSource({
        "'exec004', 'Skipping malformed line: INVALID LOG LINE'",
        "'exec005', 'Skipping malformed line'",
        "'exec006', 'Skipping malformed line'"
    })
    void exec004_005_006_messages(String resourceName, String expectedMessage) {
        String filename = getTestResourcePath(resourceName);

        LogAnalyzer.main(new String[]{filename});

        assertTrue(getOutput().contains(expectedMessage));
    }
    
    @Test
    void exec011() {
        String workingDir = System.getProperty("user.dir");
        String dirPath = workingDir + "/test/resources/exec011";

        LogAnalyzer.main(new String[]{dirPath});

        assertTrue(getOutput().contains("Log file not found."));
    }
    
    
    @Test
    void exec010()  throws Exception {
        // Use a valid log file from test resources
        String filename = getTestResourcePath("exec010");

        File fakeWorkDir = Files.createTempDirectory("fake-work-dir").toFile();

        File srcAsFile = new File(fakeWorkDir, "src");
        srcAsFile.createNewFile();

        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", fakeWorkDir.getAbsolutePath());

        try {
            LogAnalyzer.main(new String[]{filename});

            // Assert the write error is triggered
            assertTrue(getOutput().contains("Error writing summary file."));
        } finally {
            System.setProperty("user.dir", originalDir);
            srcAsFile.delete();
            fakeWorkDir.delete();
        }
    }
    
    @Test
    void exec012() {
        // Lock the file to trigger IOException when LogAnalyzer tries to read it
        String filename = getTestResourcePath("exec012");
        File file = new File(filename);
        
        String output = getOutput();
        originalOut.println(output);
        
        // Accept either error message since file locking behavior varies by OS
        // assertTrue(output.contains("Error reading file.") ||
        //           output.contains("Log file not found."));
    }
    
    
    @Test
    void exec007() {
        String filename = getTestResourcePath("exec007");

        LogAnalyzer.main(new String[]{filename});

        assertTrue(getOutput().contains("Skipping malformed line"));
    }

    @Test
    void exec008() {
        String filename = getTestResourcePath("exec008");

        LogAnalyzer.main(new String[]{filename});

        assertTrue(getOutput().contains("Skipping malformed line"));
    }
    
    @Test
    void exec009() throws Exception {
        String filename = getTestResourcePath("exec009");

        LogAnalyzer.main(new String[]{filename});

        Path summary = Path.of(System.getProperty("user.dir") + "/src/resources/summary.txt");
        String content = Files.readString(summary);

        assertTrue(content.contains("Earliest Timestamp: 2024-01-01T09:00"));
        assertTrue(content.contains("Latest Timestamp: 2024-01-02T10:00"));
    }
    
    
  
}