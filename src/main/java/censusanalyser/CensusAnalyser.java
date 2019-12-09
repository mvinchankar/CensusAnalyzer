package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser<T> {

    public int loadIndiaCensusData(String csvFilePath, Class<T> csvClass) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<T> stateCodeCSVIterator = getCSVFileIterator(reader, csvClass).iterator();
            return this.getCount(stateCodeCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    private int getCount(Iterator<T> stateCodeCSVIterator) {

        Iterable<T> csvIterator = () -> stateCodeCSVIterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterator.spliterator(), false).count();
        return numOfEnteries;
    }

    private <T> Iterable<T> getCSVFileIterator(Reader reader, Class<T> csvClass) throws CensusAnalyserException {

        try {
            CsvToBeanBuilder<T> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<T> csvToBean = csvToBeanBuilder.build();
            return csvToBean;
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}