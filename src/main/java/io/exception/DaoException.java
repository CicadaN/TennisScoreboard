package io.exception;

public class DaoException extends RuntimeException {

    private static final String PROBLEM_IN_DAO = "Exception occurred in %s. %s. ";

    public DaoException(String daoClassName, String problemDescription, Throwable cause) {
        super(PROBLEM_IN_DAO.formatted(daoClassName, problemDescription), cause);
    }



}
