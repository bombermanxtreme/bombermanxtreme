package edu.eci.arsw.bombermanx.services;

/**
 *
 * @author hcadavid
 */
public class GameCreationException extends Exception {

    public GameCreationException(String message) {
        super(message);
    }

    public GameCreationException(String message, Throwable cause) {
        super(message, cause);
    }

}
