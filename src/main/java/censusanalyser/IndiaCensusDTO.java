package censusanalyser;

public class IndiaCensusDTO {
    public String state;
    public String stateCode;
    public int population;
    public int areaInSqKms;
    public int densityPerSqKms;

    public IndiaCensusDTO(IndiaCensusDAO next) {
        state = next.state;
        population = next.population;
        areaInSqKms = next.areaInSqKm;
        densityPerSqKms = next.densityPerSqKm;
    }
}
