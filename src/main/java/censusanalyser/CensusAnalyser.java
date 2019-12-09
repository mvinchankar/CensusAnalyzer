package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser<T> {

    public int loadIndiaCensusData(String csvFilePath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List csvFileList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public int loadIndiaStateCode(String csvFilePath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
           List<IndiaStateCodeCSV> csvFileListStateCode=csvBuilder.getCSVFileList(reader,IndiaStateCodeCSV.class);
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


}