package mypackage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogAnalyzerTest {
	
	private LogAnalyzer log;
	
	@BeforeAll
	static void initAll() {
	    System.out.println("Start tests");
	}
	
	
	@BeforeEach
	void setUp() {
		log = new LogAnalyzer();
	}
	
	@AfterEach
	void tearDown() {
		log = null;
	}
	
	@AfterAll
	static void tearDownAll() {
	    System.out.println("End tests");
	}
	
	@Test
	void shouldPrintAnalysisComplete_whenServerLogExists() {
	    // Arrange
	    String workingDir = System.getProperty("user.dir");
	    String filename = workingDir + "/src/resources/server.log";

	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    PrintStream originalOut = System.out;
	    System.setOut(new PrintStream(out));

	    try {
	        // Act
	        LogAnalyzer.main(new String[]{filename});

	        // Assert
	        String output = out.toString().trim();
	        assertTrue(output.contains("Analysis complete. Summary written to summary.txt"));
	    } finally {
	        System.setOut(originalOut);
	    }
	}

    @Test
    void shouldPrintFileNotFoundMessage_whenFileDoesNotExist() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            // Act
            LogAnalyzer.main(new String[]{"definitely_does_not_exist.log"});

            // Assert
            String output = out.toString().trim();
            assertTrue(output.contains("Log file not found."));
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    void shouldPrintSkippingMessage_whenMalformedLogLineExists() throws Exception {
        // Arrange: create temp file with invalid log line
        File tempFile = File.createTempFile("test-log", ".log");

        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write("INVALID LOG LINE\n"); // malformed
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            // Act
            LogAnalyzer.main(new String[]{tempFile.getAbsolutePath()});

            // Assert
            String output = out.toString().trim();

            assertTrue(output.contains("Skipping malformed line: INVALID LOG LINE"));
        } finally {
            System.setOut(originalOut);
            tempFile.delete();
        }
    }
    
    @Test
    void shouldPrintMissingMessage_whenMalformedLogNoMessage() throws Exception {
        File tempFile = File.createTempFile("test-log", ".log");

        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write("[2024-01-01 10:00:00] INFO\n"); // triggers "Missing message"
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            // Act
            LogAnalyzer.main(new String[]{tempFile.getAbsolutePath()});

            // Assert
            String output = out.toString().trim();

            assertTrue(output.contains("Skipping malformed line"));
        } finally {
            System.setOut(originalOut);
            tempFile.delete();
        }
    }
    
    @Test
    void shouldPrintSkippingMessage_whenInvalidLogLevel() throws Exception {
        File tempFile = File.createTempFile("test-log", ".log");

        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write("[2024-01-01 10:00:00] GO: Backup completed successfully\n");
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            // Act
            LogAnalyzer.main(new String[]{tempFile.getAbsolutePath()});

            // Assert
            String output = out.toString().trim();

            assertTrue(output.contains("Skipping malformed line"));
        } finally {
            System.setOut(originalOut);
            tempFile.delete();
        }
    }
    
    @Test
    void shouldPrintErrorWritingSummary_whenIOExceptionOccurs() throws Exception {
        // Arrange: create a valid temp log file (so reading succeeds)
        File tempFile = File.createTempFile("test-log", ".log");

        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write("[2024-01-01 10:00:00] INFO: Test\n");
        }

        // Force invalid path for writing
        String originalDir = System.getProperty("user.dir");
        System.setProperty("user.dir", "/invalid/path/does/not/exist");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            // Act
            LogAnalyzer.main(new String[]{tempFile.getAbsolutePath()});

            // Assert
            String output = out.toString().trim();
            assertTrue(output.contains("Error writing summary file."));
        } finally {
            // cleanup
            System.setOut(originalOut);
            System.setProperty("user.dir", originalDir);
            tempFile.delete();
        }
    }
    
   
    
}