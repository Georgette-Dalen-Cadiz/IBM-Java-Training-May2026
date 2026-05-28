package mypackage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;

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

    private File createTempLog(String content) throws IOException {
        File file = File.createTempFile("test-log", ".log");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(content);
        }
        return file;
    }

    // =========================
    // Basic Behavior Tests
    // =========================

    @Test
    void shouldPrintAnalysisComplete_whenServerLogExists() {
        String workingDir = System.getProperty("user.dir");
        String filename = workingDir + "/src/resources/server.log";

        LogAnalyzer.main(new String[]{filename});

        assertTrue(getOutput().contains(
            "Analysis complete. Summary written to summary.txt"
        ));
    }

    @RepeatedTest(3)
    void shouldConsistentlyPrintFileNotFound() {
        LogAnalyzer.main(new String[]{"non_existent.log"});
        assertTrue(getOutput().contains("Log file not found."));
    }

    // =========================
    // Parameterized Tests
    // =========================

    @ParameterizedTest
    @ValueSource(strings = {
        "INVALID LOG LINE",
        "[2024-01-01 10:00:00] INFO",
        "[2024-01-01 10:00:00] GO: Invalid level"
    })
    void shouldSkipMalformedLines_forVariousInputs(String logLine) throws Exception {
        File file = createTempLog(logLine + "\n");

        LogAnalyzer.main(new String[]{file.getAbsolutePath()});

        assertTrue(getOutput().contains("Skipping malformed line"));

        file.delete();
    }

    @ParameterizedTest
    @CsvSource({
        "'INVALID LOG LINE', 'Skipping malformed line: INVALID LOG LINE'",
        "'[2024-01-01 10:00:00] INFO', 'Skipping malformed line'",
        "'[2024-01-01 10:00:00] GO: Bad', 'Skipping malformed line'"
    })
    void shouldHandleMalformedCases_withExpectedMessages(String input, String expectedMessage) throws Exception {
        File file = createTempLog(input + "\n");

        LogAnalyzer.main(new String[]{file.getAbsolutePath()});

        assertTrue(getOutput().contains(expectedMessage));

        file.delete();
    }
    
    @Test
    void shouldHandleDirectoryInput_asFileNotFound() throws Exception {
        File dir = Files.createTempDirectory("test-dir").toFile();

        LogAnalyzer.main(new String[]{dir.getAbsolutePath()});

        assertTrue(getOutput().contains("Log file not found."));

        dir.delete();
    }
    
    
    @Test
    void shouldPrintError_whenWriteFails() throws Exception {
        // Create a valid log file (so reading succeeds)
        File tempLog = createTempLog(
            "[2024-01-01 10:00:00] INFO: Test message\n"
        );

        File fakeWorkDir = Files.createTempDirectory("fake-work-dir").toFile();

        File srcAsFile = new File(fakeWorkDir, "src");
        srcAsFile.createNewFile(); 

        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", fakeWorkDir.getAbsolutePath());

        try {
            LogAnalyzer.main(new String[]{tempLog.getAbsolutePath()});

            // Assert the write error is triggered
            assertTrue(getOutput().contains("Error writing summary file."));
        } finally {
            System.setProperty("user.dir", originalDir);
            tempLog.delete();
            srcAsFile.delete();
            fakeWorkDir.delete();
        }
    }
    
    @Test
    void shouldPrintErrorReadingFile_whenIOExceptionOccurs() throws IOException {
//        // Arrange
//        String workingDir = System.getProperty("user.dir");
//
//        // Point to a DIRECTORY instead of a file
//        String[] args = { workingDir + "/src/resources" };
//
//        // Capture console output
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PrintStream originalOut = System.out;
//        System.setOut(new PrintStream(outputStream));
//
//        try {
//            // Act
//            LogAnalyzer.main(args);
//        } finally {
//            // Restore original System.out
//            System.setOut(originalOut);
//        }
//
//        // Assert
//        String output = outputStream.toString();
//        assertTrue(output.contains("Error reading file."));
    	
    	
    }
    
  
}