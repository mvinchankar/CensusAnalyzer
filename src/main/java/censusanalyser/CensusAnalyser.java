package censusanalyser;

import com.csvbuilder.CSVBuilderException;
import com.csvbuilder.CSVBuilderFactory;
import com.csvbuilder.ICSBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser<T> {
    List<IndiaCensusDTO> censusDAOSList;

    public CensusAnalyser() {
        this.censusDAOSList = new ArrayList<IndiaCensusDTO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusException, CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusDAO> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusDAO.class);
            Iterable<IndiaCensusDAO> censusDAOIterable = () -> censusCSVIterator;
            StreamSupport.stream(censusDAOIterable.spliterator(),false).forEach(censusCSV -> censusDAOSList.add(new IndiaCensusDTO(censusCSV)));
            return censusDAOSList.size();
        } catch (RuntimeException e) {
            throw new CensusException(e.getMessage(), CensusException.ExceptionType.INCORRECT_DELIMITER);
        } catch (IOException e) {
            throw new CensusException(e.getMessage(),
                    CensusException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusException, CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaStateCodeCSV> csvFileListStateCode = csvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            return csvFileListStateCode.size();
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
        if (censusDAOSList == null || censusDAOSList.size() == 0) {
            throw new CensusException("No Census Data", CensusException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDTO> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensus = new Gson().toJson(censusDAOSList);
        return sortedStateCensus;
    }

    public String getPopulationWiseSortedCensusData() throws CensusException {
        if (censusDAOSList == null || censusDAOSList.size() == 0) {
            throw new CensusException("No Census Data", CensusException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDTO> csvComparator = Comparator.comparing(census -> census.population);
        this.sort(csvComparator);
        String sortedStateCensus = new Gson().toJson(censusDAOSList);
        return sortedStateCensus;
    }

    private void sort(Comparator<IndiaCensusDTO> csvComparator) {
        for (int i = 0; i < censusDAOSList.size() - 1; i++) {
            for (int j = 0; j < censusDAOSList.size() - i - 1; j++) {
                IndiaCensusDTO censusCSV = censusDAOSList.get(j);
                IndiaCensusDTO censusCSV1 = censusDAOSList.get(j + 1);
                if (csvComparator.compare(censusCSV, censusCSV1) > 0) {
                    censusDAOSList.set(j, censusCSV1);
                    censusDAOSList.set(j + 1, censusCSV);
                }

            }
        }
    }

}