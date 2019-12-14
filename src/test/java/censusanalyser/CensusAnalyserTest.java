package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH
            = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_PATH
            = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH
            = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusData_whenSortedOnState_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.state);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenIndianCensusData_whenOnlyOneFileForSortingState_ShouldReturnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.state);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.FILE_MISSING, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenBothFileForSortingStateButSwapped_ShouldReturnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        String stateWiseSortedCensusData = null;
        try {
            censusAnalyser.loadCensusData(INDIA_STATE_CSV_PATH, INDIA_CENSUS_CSV_FILE_PATH);
            stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.state);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulation_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.population);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Maharashtra", censusCSV[0].state);
        } catch (CensusException e) {
        }
    }

    @Test
    public void givenIndianCensusData_whenFileSameSortedOnPopulation_ShouldReturnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(INDIA_STATE_CSV_PATH, INDIA_STATE_CSV_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.population);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[28].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.INCORRECT_DELIMITER, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnAreaInSqMs_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.areaInSqKm);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[0].state);
        } catch (CensusException e) {
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnDensityInSqMs_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.densityPerSqKm);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[0].state);
        } catch (CensusException e) {
        }
    }

    @Test
    public void givenIndianCensusData_whenNoFileToSortByDensityInSqMs_ShouldReturnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData();
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.densityPerSqKm);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[0].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.FILE_MISSING, e.type);
        }
    }

    @Test
    public void givenUSCensusData_whenSortedOnState_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.state);
            USCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alabama", censusCSV[0].state);
        } catch (CensusException e) {
        }
    }

    @Test
    public void givenUSCensusData_whenSortedOnPopulation_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.population);
            USCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", censusCSV[0].state);
        } catch (CensusException e) {
        }
    }

    @Test
    public void givenUSCensusData_whenSortedOnArea_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.areaInSqKm);
            USCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCSV[0].state);
        } catch (CensusException e) {
        }
    }

    @Test
    public void givenUSCensusData_whenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.densityPerSqKm);
            USCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", censusCSV[0].state);
        } catch (CensusException e) {
        }
    }

    @Test
    public void givenUSCensusData_whenNoValueInMap_ShouldReturnException() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.densityPerSqKm);
            USCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", censusCSV[0].state);
        } catch (CensusException e) {
            Assert.assertEquals(CensusException.ExceptionType.NO_CENSUS_DATA, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulationButSameValuesOfStates_ThenSortByDensity() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_PATH);
            String stateWiseSortedCensusData = censusAnalyser.getFieldWiseSortedCensusData(FieldsToSort.population_density);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(stateWiseSortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
        } catch (CensusException e) {
            e.printStackTrace();
        }
    }
}
