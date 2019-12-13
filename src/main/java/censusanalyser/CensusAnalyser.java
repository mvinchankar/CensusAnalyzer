package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser<T> {

    public enum Country {INDIA, US}

    Map<String, CensusDAO> censusDAOMap;
    Map<FieldsToSort, Comparator<CensusDAO>> fields = null;
    private Country country;

    public CensusAnalyser(Country country) {
        this.country = country;
        this.fields = new HashMap<>();
        this.fields.put(FieldsToSort.state, Comparator.comparing(census -> census.state));
        this.fields.put(FieldsToSort.population, Comparator.comparing(census ->
                census.population, Comparator.reverseOrder()));
        this.fields.put(FieldsToSort.areaInSqKm, Comparator.comparing(census ->
                census.totalArea, Comparator.reverseOrder()));
        this.fields.put(FieldsToSort.densityPerSqKm, Comparator.comparing(census ->
                census.populationDensity, Comparator.reverseOrder()));
    }

    public CensusAnalyser() {
        this.censusDAOMap = new HashMap();
    }

    public int loadCensusData(String... csvFilePath) throws CensusException {
        CensusAdapter censusFactory = CensusFactory.CountryObject(country);
        censusDAOMap = censusFactory.loadCensusData(csvFilePath);
        return censusDAOMap.size();
    }

    public String getFieldWiseSortedCensusData(FieldsToSort fieldsToSort) throws CensusException {
        if (censusDAOMap == null || censusDAOMap.size() == 0) {
            throw new CensusException("No Census Data", CensusException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList arrayList = censusDAOMap.values().stream()
                .sorted(this.fields.get(fieldsToSort))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(toCollection(ArrayList::new));
        String sortedStateCensus = new Gson().toJson(arrayList);
        return sortedStateCensus;
    }
}