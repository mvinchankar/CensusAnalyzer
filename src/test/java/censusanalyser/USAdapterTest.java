package censusanalyser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class USAdapterTest {

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords() {
        IndiaCensusAdapter censusAdapter = new IndiaCensusAdapter();
        try {
            Map<String, CensusDAO> map = censusAdapter.loadCensusData("resources/IndiaStateCensusData.csv");
            Assert.assertEquals(51, map.size());
        } catch (CensusException e) {
        }

    }
}
