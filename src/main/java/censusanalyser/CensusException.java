package censusanalyser;

public class CensusException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM, NO_CENSUS_DATA, INCORRECT_DELIMITER, INVALID_COUNTRY,FILE_MISSING
    }

    ExceptionType type;

    public CensusException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
