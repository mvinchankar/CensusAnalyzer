package censusanalyser;

import java.io.Reader;
import java.util.List;

public interface ICSBuilder<T> {
    public Iterable<T> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException;
    public List<T> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException;

}
