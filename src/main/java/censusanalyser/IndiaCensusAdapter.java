package censusanalyser;

import com.csvbuilder.CSVBuilderException;
import com.csvbuilder.CSVBuilderFactory;
import com.csvbuilder.ICSBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusException {
        try {
            Map<String, CensusDAO> censusStateMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
            this.loadIndiaStateCode(censusStateMap, csvFilePath[1]);
            return censusStateMap;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CensusException(e.getMessage(), CensusException.ExceptionType.FILE_MISSING);
        }
    }

    public int loadIndiaStateCode(Map<String, CensusDAO> censusDAOMap, String csvFilePath) throws CensusException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> csvStateCode = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> stateCodeCSVS = () -> csvStateCode;
            StreamSupport.stream(stateCodeCSVS.spliterator(), false)
                    .filter(csvState -> censusDAOMap.get(csvState.state) != null)
                    .forEach(csvState -> censusDAOMap.get(csvState.state).stateCode = csvState.stateCode);
            return censusDAOMap.size();
        } catch (IOException | CSVBuilderException e) {
            throw new CensusException(e.getMessage(),
                    CensusException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusException(e.getMessage(), CensusException.ExceptionType.INCORRECT_DELIMITER);
        }
    }
}
