package censusanalyser;

public class CSVBuilderFactory {

    public static ICSBuilder createCSVBuilder() {

        return new OpenCSVBuilder<>();
    }
}
