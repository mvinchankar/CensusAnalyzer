package censusanalyser;

public class IndiaCensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public int areaInSqKms;
    public int densityPerSqKms;

    public IndiaCensusDAO(IndiaCensusCSV next) {
        state = next.state;
        population = next.population;
        areaInSqKms = next.areaInSqKm;
        densityPerSqKms = next.densityPerSqKm;
    }
}
