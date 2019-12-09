package censusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSBuilder<T> {
    public Iterator<T> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException;
    public List<T> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException;

}
