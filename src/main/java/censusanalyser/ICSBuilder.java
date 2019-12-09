package censusanalyser;

import java.io.Reader;

public interface ICSBuilder<T> {

    public Iterable<T> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException, CSVBuilderException;

}
