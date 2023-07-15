package ba.unsa.etf.rpr.exceptions;

/**
 * Custom Exception created for the purposes of this application
 * @author Minela Sultanović
 */
public class MyBookListException extends Exception{

    public MyBookListException(String message){super(message);}
    public MyBookListException(String message, Exception e){super(message, e);}

}
