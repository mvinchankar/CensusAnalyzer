package censusanalyser;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusException {
        try {
            Map<String, CensusDAO> censusStateMap = super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
            return censusStateMap;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CensusException(e.getMessage(), CensusException.ExceptionType.FILE_MISSING);
        }
    }
}
