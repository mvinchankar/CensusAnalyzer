package censusanalyser;

import com.csvbuilder.CSVBuilderException;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String INCORRECT_INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String INCORRECT_DELIMITER_FILE_PATH = "./src/test/resources/InCorrectDelimiter.csv";
    private static final String INCORRECT_HEADER_FILE_PATH = "./src/test/resources/InCorrectHeader.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenIndiaStateCsv_ShouldReturn_ExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            int numOfStateCode = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CSV_PATH);
            Assert.assertEquals(37, numOfStateCode);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnState_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnState_ShouldReturnUnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusDAO[].class);
            Assert.assertNotEquals("Uttar Pradesh", censusCSV[0].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulation_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[28].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulation_ShouldReturnUnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusDAO[].class);
            Assert.assertNotEquals("Uttar Pradesh", censusCSV[25].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectFilePath_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INCORRECT_INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectDelimiter_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INCORRECT_DELIMITER_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenHeaderNotFound_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INCORRECT_HEADER_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        } catch (CSVBuilderException e) {
            e.printStackTrace();
        }
    }
}
