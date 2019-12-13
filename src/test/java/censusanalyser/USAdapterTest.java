package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USAdapterTest {

    private static final String WRONG_CSV_FILE_PATH
            = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String US_CENSUS_CSV_FILE_PATH
            = "./src/test/resources/USCensusData.csv";
    private static final String INCORRECT_DELIMITER_US_FILE_PATH
            = "./src/test/resources/IncorrectDelimiterUSData.csv";
    private static final String INCORRECT_HEADER_FILE_PATH
            = "./src/test/resources/IncorrectHeaderUSCensusData.csv";
    private static final String NULL_VALUE_FILE_PATH
            = "./src/test/resources/NullUSData.csv";

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords() {
        USCensusAdapter censusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> map = censusAdapter.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, map.size());
        } catch (CensusException e) {
        }
    }

    @Test
    public void givenUSCensusData_WhenIncorrectFile_ShouldReturnException() {
        USCensusAdapter censusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> map = censusAdapter.loadCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenUSCensusData_WhenNoFile_ShouldReturnException() {
        USCensusAdapter censusAdapter = new USCensusAdapter();
        try {
            Map<String, CensusDAO> map = censusAdapter.loadCensusData();
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.FILE_MISSING, e.type);
        }
    }

    @Test
    public void givenUSCensusCSVFile_WhenIncorrectDelimiter_ShouldReturnsException() {
        try {
            USCensusAdapter censusAdapter = new USCensusAdapter();
            Map<String, CensusDAO> map = censusAdapter.loadCensusData(INCORRECT_DELIMITER_US_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenUSCensusCSVFile_WhenIncorrectHeader_ShouldReturnsException() {
        try {
            USCensusAdapter censusAdapter = new USCensusAdapter();
            Map<String, CensusDAO> map = censusAdapter.loadCensusData(INCORRECT_HEADER_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenUSCensusCSVFile_WhenFileHasNullValues_ShouldReturnsException() {
        try {
            USCensusAdapter censusAdapter = new USCensusAdapter();
            Map<String, CensusDAO> map = censusAdapter.loadCensusData(NULL_VALUE_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }
}
