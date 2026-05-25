package activity;

import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        String workingDir = System.getProperty("user.dir");
        String serverLog = workingDir + "/src/resources/server.log";
        String line;

        List<String> levels = List.of("INFO", "WARN", "ERROR");

        Map<String, Integer> logLevelCount = new HashMap<>();
        logLevelCount.put("INFO", 0);
        logLevelCount.put("WARN", 0);
        logLevelCount.put("ERROR", 0);

        List<String> errorMsg = new ArrayList<>();
        List<String> lines = new ArrayList<>();

        LogComparator comparator = new LogComparator(); 
        
        boolean hasError = false;

        try (BufferedReader br = new BufferedReader(new FileReader(serverLog))) {
            while ((line = br.readLine()) != null) {

                boolean isValidFormat = line.matches(
                    "^\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\] (INFO|WARN|ERROR): .+"
                );

                if (!isValidFormat) {
                    hasError = true; 
                    throw new MalformedLogEntryException("Malformed line detected: " + line);
                }

                Optional<String> matchedLevel = levels.stream()
                                                      .filter(line::contains)
                                                      .findFirst();

                String level = matchedLevel.get();
                logLevelCount.put(level, logLevelCount.get(level) + 1);

                if (level.equals("ERROR")) { 
                    String result = getTextAfterWord(line, "ERROR: ");
                    errorMsg.add(result);
                }

                lines.add(line); 
            }

        } catch (FileNotFoundException e) {
            hasError = true;
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            hasError = true;
            System.err.println("I/O error: " + e.getMessage());
        } catch (MalformedLogEntryException e) {
            hasError = true;
            System.err.println("Malformed log entry: " + e.getMessage());
        }

        if (hasError) {
            System.err.println("Processing stopped due to errors. No file written.");
            return;
        }

        String earliestLine = lines.stream().min(comparator).orElse(null);
        String latestLine   = lines.stream().max(comparator).orElse(null);

        System.out.println("Total Entries: " + lines.size());
        System.out.println("INFO: " + logLevelCount.get("INFO"));
        System.out.println("WARN: " + logLevelCount.get("WARN"));
        System.out.println("ERROR: " + logLevelCount.get("ERROR"));

        System.out.println("Error Messages:");
        for (String msg : errorMsg) {
            System.out.println(msg);
        }

        System.out.println("Earliest: " + earliestLine);
        System.out.println("Latest: " + latestLine);
        
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(workingDir + "/src/resources/summary.txt"))) {

            bw.write("Log Summary Report\n");
            bw.write("--------------------\n");

            bw.write("Total Entries: " + lines.size() + "\n");
            bw.write("INFO: " + logLevelCount.get("INFO") + "\n");
            bw.write("WARN: " + logLevelCount.get("WARN") + "\n");
            bw.write("ERROR: " + logLevelCount.get("ERROR") + "\n");

            bw.write("Error Messages:\n");
            for (String msg : errorMsg) {
                bw.write("- " + msg + "\n");
            }

            bw.write("Earliest Timestamp: " + earliestLine + "\n");
            bw.write("Latest Timestamp: " + latestLine + "\n");

        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }
    }

    public static String getTextAfterWord(String text, String keyword) {
        if (text == null || keyword == null || keyword.isEmpty()) {
            return null;
        }

        int startIndex = text.indexOf(keyword);
        if (startIndex == -1) {
            return null;
        }

        startIndex += keyword.length();
        return text.substring(startIndex).trim();
    }
}