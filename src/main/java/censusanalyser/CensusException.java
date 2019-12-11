package censusanalyser;

public class CensusException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,NO_CENSUS_DATA,INCORRECT_DELIMITER
    }

    ExceptionType type;

    public CensusException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
