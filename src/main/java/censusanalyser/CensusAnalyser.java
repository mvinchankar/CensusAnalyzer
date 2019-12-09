package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser<T> {
    List<IndiaCensusCSV> csvFileList;

    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            csvFileList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public int loadIndiaStateCode(String csvFilePath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaStateCodeCSV> csvFileListStateCode = csvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            return csvFileListStateCode.size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    private <T> int getCount(Iterator<T> iterator) {
        Iterable<T> csvIterator = () -> iterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterator.spliterator(), false).
                count();
        return numOfEnteries;
    }


    public String getStateWiseSortedCensusData(String csvFilePath) throws CSVBuilderException {
        if(csvFileList == null || csvFileList.size() == 0){
            throw new CSVBuilderException("No Census Data",CSVBuilderException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV>csvComparator=Comparator.comparing(census -> census.state);
        this.sort(csvComparator);
        String sortedStateCensus = new Gson().toJson(csvFileList);
        return sortedStateCensus;
    }

    private void sort(Comparator<IndiaCensusCSV> csvComparator) {
        for (int i = 0; i < csvFileList.size() - 1; i++) {
            for (int j = 0; j < csvFileList.size() - i - 1; j++) {
                IndiaCensusCSV censusCSV = csvFileList.get(j);
                IndiaCensusCSV censusCSV1 = csvFileList.get(j + 1);
                if (csvComparator.compare(censusCSV, censusCSV1) > 0) {
                    csvFileList.set(j, censusCSV1);
                    csvFileList.set(j + 1, censusCSV);
                }

            }
        }
    }
}