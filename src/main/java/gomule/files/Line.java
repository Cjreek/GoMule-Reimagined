package gomule.files;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Line {

    private final List<String> line;
    private final Map<String, Integer> header;

    public Line(List<String> line, Map<String, Integer> header) {
        this.line = line;
        this.header = header;
    }

    public String get(int i) {
        return line.get(i);
    }

    public String get(String s) {
        Integer index = header.get(s);
        if (index == null) throw new IllegalArgumentException("Failed to find " + s + " in header " + header);
        return line.get(index);
    }

    public String coalesce(String... fieldNames) {
        return Arrays.stream(fieldNames)
                .map(header::get)
                .filter(Objects::nonNull)
                .map(this::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Failed to find " + Arrays.toString(fieldNames) + " in header " + header));
    }
}