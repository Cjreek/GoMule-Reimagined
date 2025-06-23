package gomule.files;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static gomule.files.FileReaderUtils.readTsv;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileReaderUtilsTest {

    @Test
    public void testReadTsv() throws IOException {
        File tempFile = File.createTempFile("fileReaderTest", null);
        tempFile.deleteOnExit();
        Path path = tempFile.toPath();
        Files.write(path, "header1\theader2\nval1\tval2\nval3\tval4\nignore\tignore".getBytes(UTF_8));
        List<List<String>> actual = readTsv(Files.newInputStream(path), new TestLineParser());
        List<List<String>> expected = asList(asList("val2", "val1"), asList("val4", "val3"));
        assertEquals(expected, actual);
    }

    private static class TestLineParser implements LineParser<List<String>> {
        @Override
        public List<String> parseLine(Line line) {
            if (line.get(0).equals("ignore")) {
                return null;
            } else {
                return asList(line.get(1), line.get(0));
            }
        }
    }
}