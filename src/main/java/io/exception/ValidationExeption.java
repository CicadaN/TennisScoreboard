package io.exception;

public class ValidationExeption extends IllegalArgumentException {

    private static final String PROBLEM_IN_DAO = "Exception occurred in %s. %s. ";

    public ValidationExeption(String daoClassName, String problemDescription, Throwable cause) {
        super(PROBLEM_IN_DAO.formatted(daoClassName, problemDescription), cause);
    }

}
