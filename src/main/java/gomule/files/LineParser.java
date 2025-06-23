package gomule.files;

public interface LineParser<T> {

    T parseLine(Line line);
}
