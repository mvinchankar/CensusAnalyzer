package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder<T> implements ICSBuilder {
    @Override
    public Iterator<T> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader, csvClass).iterator();
    }

    @Override
    public List<T> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader, csvClass).parse();
    }

    private CsvToBean<T> getCSVBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
