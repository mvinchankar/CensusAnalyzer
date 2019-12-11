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
    List<IndiaCensusDTO> censusDAOS;

    public CensusAnalyser() {
        this.censusDAOS = new ArrayList<IndiaCensusDTO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusException, CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusDAO> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusDAO.class);
            while (censusCSVIterator.hasNext()) {
                this.censusDAOS.add(new IndiaCensusDTO(censusCSVIterator.next()));
            }
            return censusDAOS.size();
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
        if (censusDAOS == null || censusDAOS.size() == 0) {
            throw new CensusException("No Census Data", CensusException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDTO> csvComparator = Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensus = new Gson().toJson(censusDAOS);
        return sortedStateCensus;
    }

    public String getPopulationWiseSortedCensusData() throws CensusException {
        if (censusDAOS == null || censusDAOS.size() == 0) {
            throw new CensusException("No Census Data", CensusException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDTO> csvComparator = Comparator.comparing(census -> census.population);
        this.sort(csvComparator);
        String sortedStateCensus = new Gson().toJson(censusDAOS);
        return sortedStateCensus;
    }

    private void sort(Comparator<IndiaCensusDTO> csvComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                IndiaCensusDTO censusCSV = censusDAOS.get(j);
                IndiaCensusDTO censusCSV1 = censusDAOS.get(j + 1);
                if (csvComparator.compare(censusCSV, censusCSV1) > 0) {
                    censusDAOS.set(j, censusCSV1);
                    censusDAOS.set(j + 1, censusCSV);
                }

            }
        }
    }

}