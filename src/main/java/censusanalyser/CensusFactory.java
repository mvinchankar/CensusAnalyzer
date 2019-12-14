package censusanalyser;

public class CensusFactory {

    public static CensusAdapter CountryObject(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter();
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter();
        return null;
    }
}
