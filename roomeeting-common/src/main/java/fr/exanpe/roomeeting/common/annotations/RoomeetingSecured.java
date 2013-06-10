package fr.exanpe.roomeeting.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marker for secured method
 * 
 * @author jmaupoux
 */
@Target(
{ ElementType.METHOD, ElementType.TYPE })
public @interface RoomeetingSecured
{

}
