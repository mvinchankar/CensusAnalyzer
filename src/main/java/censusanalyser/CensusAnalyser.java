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
    Map<String, IndiaCensusDAO> censusDAOMap = null;


    public CensusAnalyser() {
        this.censusDAOMap = new HashMap();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusException, CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> censusDAOIterable = () -> censusCSVIterator;
            StreamSupport.stream(censusDAOIterable.spliterator(), false)
                    .forEach(censusCSV -> censusDAOMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            return censusDAOMap.size();
        } catch (RuntimeException e) {
            throw new CensusException(e.getMessage(), CensusException.ExceptionType.INCORRECT_DELIMITER);
        } catch (IOException e) {
            throw new CensusException(e.getMessage(),
                    CensusException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusException, CSVBuilderException {
        int counter=0;
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> csvStateCode = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            while (csvStateCode.hasNext()) {
                counter++;
                IndiaStateCodeCSV stateCodeCSV = csvStateCode.next();
                IndiaCensusDAO censusIndiaDAO = censusDAOMap.get(stateCodeCSV.state);
                if (censusIndiaDAO == null) continue;
                censusIndiaDAO.stateCode = stateCodeCSV.stateCode;

            }
            return counter;
        } catch (IOException e) {
            throw new CensusException(e.getMessage(),
                    CensusException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    private <T> int getCount(Iterator<T> iterator) {
        Iterable<T> csvIterator = () -> iterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterator.spliterator(), false).
                count();
        return numOfEnteries;
    }


    public String getStateWiseSortedCensusData() throws CensusException {
        if (censusDAOMap == null || censusDAOMap.size() == 0) {
            throw new CensusException("No Census Data", CensusException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> csvComparator = Comparator.comparing(census -> census.state);
        List<IndiaCensusDAO> daos = censusDAOMap.values().stream().collect(Collectors.toList());
        this.sort(daos, csvComparator);
        String sortedStateCensus = new Gson().toJson(daos);
        return sortedStateCensus;
    }

    public String getPopulationWiseSortedCensusData() throws CensusException {
        if (censusDAOMap == null || censusDAOMap.size() == 0) {
            throw new CensusException("No Census Data", CensusException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> csvComparator = Comparator.comparing(census -> census.population);
        List<IndiaCensusDAO> daos = censusDAOMap.values().stream().collect(Collectors.toList());
        this.sort(daos, csvComparator);
        String sortedStateCensus = new Gson().toJson(daos);
        return sortedStateCensus;
    }

    private void sort(List<IndiaCensusDAO> daoList, Comparator<IndiaCensusDAO> csvComparator) {
        for (int i = 0; i < daoList.size() - 1; i++) {
            for (int j = 0; j < daoList.size() - i - 1; j++) {
                IndiaCensusDAO censusCSV = daoList.get(j);
                IndiaCensusDAO censusCSV1 = daoList.get(j + 1);
                if (csvComparator.compare(censusCSV, censusCSV1) > 0) {
                    daoList.set(j, censusCSV1);
                    daoList.set(j + 1, censusCSV);
                }

            }
        }
    }

}