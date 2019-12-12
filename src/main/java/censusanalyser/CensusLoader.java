package censusanalyser;

import com.csvbuilder.CSVBuilderException;
import com.csvbuilder.CSVBuilderFactory;
import com.csvbuilder.ICSBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {
    Map<String, CensusDAO> censusDAOMap = new HashMap<>();

    public <T> Map loadCensusData(String csvFilePath, Class<T> censusCSVClass) throws CensusException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<T> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<T> censusDAOIterable = () -> censusCSVIterator;
            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(censusDAOIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(censusDAOIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return censusDAOMap;
        } catch (RuntimeException e) {
            throw new CensusException(e.getMessage(), CensusException.ExceptionType.INCORRECT_DELIMITER);
        } catch (IOException | CSVBuilderException e) {
            throw new CensusException(e.getMessage(),
                    CensusException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
