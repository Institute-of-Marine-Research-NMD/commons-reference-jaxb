package no.imr.nmdapi.client.loader.convert;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import no.imr.nmdapi.generic.nmdreference.domain.v1.PlatformElementType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.PlatformElementType.PlatformCodes.PlatformCode;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 *
 * @author sjurl
 */
public class PlatformCodeWriter implements RowCallbackHandler {

    private PlatformElementType platform;

    @Override
    public void processRow(ResultSet rs) throws SQLException {
        PlatformCode platformcode = new PlatformCode();
        platformcode.setPlatformCode(rs.getString("platformcode"));
        platformcode.setSysCode(rs.getInt("platformcodesys"));
        platformcode.setSysName(rs.getString("platformcodesysname"));
        platformcode.setValidFrom(DateConverter.convertDate(rs.getDate("firstvaliddate")));
        platformcode.setValidTo(DateConverter.convertDate(rs.getDate("lastvaliddate")));
        platform.getPlatformCodes().getPlatformCode().add(platformcode);
    }

    public void init(PlatformElementType platform) {
        platform.setPlatformCodes(new PlatformElementType.PlatformCodes());
        this.platform = platform;
    }


}
