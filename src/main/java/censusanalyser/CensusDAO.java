package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public double totalArea;
    public double populationDensity;

    public CensusDAO(IndiaCensusCSV next) {
        state = next.state;
        population = next.population;
        totalArea = next.areaInSqKm;
        populationDensity = next.densityPerSqKm;
    }

    public CensusDAO(USCensusCSV next) {
        state = next.state;
        population = next.population;
        totalArea = next.totalArea;
        populationDensity = next.populationDensity;
        stateCode = next.stateId;
    }

    public Object getCensusDTO(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.US)) {
            return new USCensusCSV(state, stateCode, population, populationDensity, totalArea);
        }
        return new IndiaCensusCSV(state, population, (int) populationDensity, (int) totalArea);
    }
}
