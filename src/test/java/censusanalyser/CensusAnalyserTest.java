package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH
            = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH
            = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_PATH
            = "./src/test/resources/IndiaStateCode.csv";
    private static final String INCORRECT_INDIA_CENSUS_CSV_FILE_PATH
            = "./src/test/resources/IndiaStateCode.csv";
    private static final String INCORRECT_DELIMITER_FILE_PATH
            = "./src/test/resources/InCorrectDelimiter.csv";
    private static final String INCORRECT_HEADER_FILE_PATH
            = "./src/test/resources/InCorrectHeader.csv";
    private static final String INCORRECT_INDIA_STATE_CODE_CSV_FILE_PATH
            = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String US_CENSUS_CSV_FILE_PATH
            = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusData_whenSortedOnState_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.state);
            CensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnState_ShouldReturnUnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.state);
            CensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, CensusDAO[].class);
            Assert.assertNotEquals("Uttar Pradesh", censusCSV[0].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulation_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.population);
            CensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulation_ShouldReturnUnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.population);
            CensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, CensusDAO[].class);
            Assert.assertNotEquals("Uttar Pradesh", censusCSV[25].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectFilePath_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenIncorrectDelimiter_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_DELIMITER_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFile_WhenHeaderNotFound_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_HEADER_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenStateCodeCSVFile_WhenIncorrectFilePath_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCSVFile_WhenIncorrectDelimiter_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_DELIMITER_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCSVFile_WhenHeaderNotFound_ShouldReturnsException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INCORRECT_HEADER_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_hasNULLValues_ShouldThrowException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String stateWiseSortedCensusData = null;
        try {
            stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.state);
            CensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, CensusDAO[].class);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnAreaInSqMs_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.areaInSqKm);
            CensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Rajasthan", censusCSV[0].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnDensityInSqMs_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.densityPerSqKm);
            CensusDAO[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, CensusDAO[].class);
            Assert.assertEquals("Bihar", censusCSV[0].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenUSCENSUSDATA_ShouldReturnCorrectRecords() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int data = 0;
        try {
            data = censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, data);
        } catch (CensusException e) {
        }
    }
}
