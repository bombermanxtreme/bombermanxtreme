package edu.eci.arsw.bombermanx.services;

/**
 *
 * @author hcadavid
 */
public class GameServicesException extends Exception {

    public GameServicesException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameServicesException(String cause) {
        super(cause);
    }

}
