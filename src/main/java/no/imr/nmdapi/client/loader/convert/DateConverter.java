package no.imr.nmdapi.client.loader.convert;

import java.sql.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author sjurl
 */
public class DateConverter {

    public static XMLGregorianCalendar convertDate(Date date) {
        XMLGregorianCalendar result = null;
        if (date != null) {

            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);

            try {
                result = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
            } catch (DatatypeConfigurationException ex) {

            }

        }
        return result;

    }
}
