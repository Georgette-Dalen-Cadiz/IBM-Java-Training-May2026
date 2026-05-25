package activity;

import java.util.Comparator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class LogComparator implements Comparator<String> {

    private DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public int compare(String l1, String l2) {
        LocalDateTime t1 = extractTime(l1);
        LocalDateTime t2 = extractTime(l2);
        return t1.compareTo(t2);
    }

    private LocalDateTime extractTime(String line) {
        int start = line.indexOf("[") + 1;
        int end = line.indexOf("]");
        String timestamp = line.substring(start, end);
        return LocalDateTime.parse(timestamp, formatter);
    }
}