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

    @Test
    public void givenIndiaCensusData_ShouldReturnCorrectRecords() {
        IndiaCensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> map = censusAdapter.loadCensusData(
                    INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV_PATH );
            Assert.assertEquals(29, map.size());
        } catch (CensusException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_ShouldReturnException() {
        IndiaCensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> map = censusAdapter.loadCensusData(
                    INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, map.size());
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.FILE_MISSING,e.type);

        }
    }

    @Test
    public void givenIndiaFile_WithSecondWrongFile_ShouldThrowException() {
        try {
            IndiaCensusAdapter censusAnalyser = new IndiaCensusAdapter();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH,INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithOneWrongFile_ShouldThrowException() {
        try {
            IndiaCensusAdapter censusAnalyser = new IndiaCensusAdapter();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAnalyser.loadCensusData(INDIA_STATE_CSV_PATH,INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithBothFileAsIndiaStateCode_ShouldThrowException() {
        try {
            IndiaCensusAdapter censusAnalyser = new IndiaCensusAdapter();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAnalyser.loadCensusData(INDIA_STATE_CSV_PATH,INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithOneWrongFileAsUSCensus_ShouldThrowException() {
        try {
            IndiaCensusAdapter censusAnalyser = new IndiaCensusAdapter();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithSecondWrongFileAsUSCensus_ShouldThrowException() {
        try {
            IndiaCensusAdapter censusAnalyser = new IndiaCensusAdapter();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, US_CENSUS_CSV_FILE_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndiaCensus_WithOneFileHasIncorrectDelimiter_ShouldThrowException() {
        try {
            IndiaCensusAdapter censusAnalyser = new IndiaCensusAdapter();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusException.class);
            censusAnalyser.loadCensusData(INCORRECT_DELIMITER_FILE_PATH, INDIA_STATE_CSV_PATH);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }




}
