package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class IndiaCensusAdapterTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH
            = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH
            = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_PATH
            = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH
            = "./src/test/resources/USCensusData.csv";
    private static final String INCORRECT_DELIMITER_FILE_PATH
            = "./src/test/resources/InCorrectDelimiter.csv";
    private static final String INCORRECT_HEADER_FILE_PATH
            = "./src/test/resources/InCorrectHeader.csv";
    private static final String INCORRECT_INDIA_STATE_CODE_CSV_FILE_PATH
            = "./src/test/resources/IndiaStateCensusData.csv";

    CensusAdapter censusAdapter = new IndiaCensusAdapter();


    @Test
    public void givenIndiaCensusData_ShouldReturnCorrectRecords() {
        try {
            Map<String, CensusDAO> map = censusAdapter.loadCensusData(
                    INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_PATH);
            Assert.assertEquals(29, map.size());
        } catch (CensusException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_ShouldReturnException() {
        try {
            Map<String, CensusDAO> map = censusAdapter.loadCensusData(
                    INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, map.size());
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);

        }
    }

    @Test
    public void givenIndiaFile_WithSecondWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAdapter.loadCensusData(WRONG_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithOneWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAdapter.loadCensusData(INDIA_STATE_CSV_PATH, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithBothFileAsIndiaStateCode_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAdapter.loadCensusData(INDIA_STATE_CSV_PATH, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithOneWrongFileAsUSCensus_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAdapter.loadCensusData(US_CENSUS_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithSecondWrongFileAsUSCensus_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, US_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithOneFileHasIncorrectDelimiter_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAdapter.loadCensusData(INCORRECT_DELIMITER_FILE_PATH, INDIA_STATE_CSV_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithNoFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAdapter.loadCensusData();
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.FILE_MISSING, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithOneFileHasIncorrectHeader_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAdapter.loadCensusData(INCORRECT_HEADER_FILE_PATH, INDIA_STATE_CSV_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

}
