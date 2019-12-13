package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser<T> {

    public enum Country {INDIA, US}

    Map<String, CensusDAO> censusDAOMap;
    Map<FieldsToSort, Comparator<CensusDAO>> fields = null;

    public CensusAnalyser() {
        this.censusDAOMap = new HashMap();
        this.fields = new HashMap<>();
        this.fields.put(FieldsToSort.state, Comparator.comparing(census -> census.state));
        this.fields.put(FieldsToSort.population, Comparator.comparing(census ->
                census.population, Comparator.reverseOrder()));
        this.fields.put(FieldsToSort.areaInSqKm, Comparator.comparing(census ->
                census.totalArea, Comparator.reverseOrder()));
        this.fields.put(FieldsToSort.densityPerSqKm, Comparator.comparing(census ->
                census.populationDensity, Comparator.reverseOrder()));
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusException {
        CensusAdapter censusFactory = CensusFactory.CountryObject(country);
         censusDAOMap = censusFactory.loadCensusData(csvFilePath);
        return censusDAOMap.size();
    }

    public String getFieldWiseSortedCensusData(FieldsToSort fieldName) throws CensusException {
        if (censusDAOMap == null || censusDAOMap.size() == 0) {
            throw new CensusException("No Census Data", CensusException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> daos = censusDAOMap.values().stream().collect(Collectors.toList());
        this.sort(daos, this.fields.get(fieldName));
        String sortedStateCensus = new Gson().toJson(daos);
        return sortedStateCensus;
    }

    private void sort(List<CensusDAO> daoList, Comparator<CensusDAO> csvComparator) {
        for (int i = 0; i < daoList.size() - 1; i++) {
            for (int j = 0; j < daoList.size() - i - 1; j++) {
                CensusDAO censusCSV = daoList.get(j);
                CensusDAO censusCSV1 = daoList.get(j + 1);
                if (csvComparator.compare(censusCSV, censusCSV1) > 0) {
                    daoList.set(j, censusCSV1);
                    daoList.set(j + 1, censusCSV);
                }
            }
        }
    }
}