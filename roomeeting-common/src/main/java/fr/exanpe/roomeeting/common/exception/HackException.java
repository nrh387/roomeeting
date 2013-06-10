/**
 * 
 */
package fr.exanpe.roomeeting.common.exception;

/**
 * Class <code>HackException</code> represents a hack resulting exception
 * 
 * @author jmaupoux
 */
public class HackException extends RuntimeException
{
    /**
     * serialUid
     */
    private static final long serialVersionUID = -8229837820658777788L;

    /**
     * Build a <code>HackException</code>.
     */
    public HackException()
    {
        super();
    }

    /**
     * Build a <code>HackException</code>.
     * 
     * @param message msg
     * @param cause cause
     */
    public HackException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Build a <code>HackException</code>.
     * 
     * @param message msg
     */
    public HackException(String message)
    {
        super(message);
    }

    /**
     * Build a <code>HackException</code>.
     * 
     * @param cause cause
     */
    public HackException(Throwable cause)
    {
        super(cause);
    }
}
