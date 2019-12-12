package censusanalyser;

import com.csvbuilder.CSVBuilderException;
import com.csvbuilder.CSVBuilderFactory;
import com.csvbuilder.ICSBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser<T> {
    Map<String, CensusDAO> censusDAOMap;
    Map<FieldsToSort, Comparator<CensusDAO>> fields = null;

    public CensusAnalyser() {
        this.censusDAOMap = new HashMap();
        this.fields = new HashMap<>();
        this.fields.put(FieldsToSort.state, Comparator.comparing(census -> census.state));
        this.fields.put(FieldsToSort.population, Comparator.comparing(census -> census.population, Comparator.reverseOrder()));
        this.fields.put(FieldsToSort.areaInSqKm, Comparator.comparing(census -> census.totalArea, Comparator.reverseOrder()));
        this.fields.put(FieldsToSort.densityPerSqKm, Comparator.comparing(census -> census.populationDensity, Comparator.reverseOrder()));
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> censusDAOIterable = () -> censusCSVIterator;
            StreamSupport.stream(censusDAOIterable.spliterator(), false)
                    .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            return censusDAOMap.size();
        } catch (RuntimeException e) {
            throw new CensusException(e.getMessage(), CensusException.ExceptionType.INCORRECT_DELIMITER);
        } catch (IOException | CSVBuilderException e) {
            throw new CensusException(e.getMessage(),
                    CensusException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusException {
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

    public int loadUSCensusData(String csvFilePath) throws CensusException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, USCensusCSV.class);
            Iterable<USCensusCSV> censusDAOIterable = () -> censusCSVIterator;
            StreamSupport.stream(censusDAOIterable.spliterator(), false)
                    .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            return censusDAOMap.size();
        } catch (RuntimeException e) {
            throw new CensusException(e.getMessage(), CensusException.ExceptionType.INCORRECT_DELIMITER);
        } catch (IOException | CSVBuilderException e) {
            throw new CensusException(e.getMessage(),
                    CensusException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public String getFieldWiseSortedCensusData(FieldsToSort fieldName) throws CensusException {
        if (censusDAOMap == null || censusDAOMap.size() == 0) {
            throw new CensusException("No Census Data", CensusException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> daos = censusDAOMap.values().stream().collect(Collectors.toList());
        this.sort(daos, this.fields.get(fieldName));
        String sortedStateCensus = new Gson().toJson(daos);
        return sortedStateCensus;
    }

    private void sort(List<CensusDAO> daoList, Comparator<CensusDAO> csvComparator) {
        for (int i = 0; i < daoList.size() - 1; i++) {
            for (int j = 0; j < daoList.size() - i - 1; j++) {
                CensusDAO censusCSV = daoList.get(j);
                CensusDAO censusCSV1 = daoList.get(j + 1);
                if (csvComparator.compare(censusCSV, censusCSV1) > 0) {
                    daoList.set(j, censusCSV1);
                    daoList.set(j + 1, censusCSV);
                }
            }
        }
    }


}