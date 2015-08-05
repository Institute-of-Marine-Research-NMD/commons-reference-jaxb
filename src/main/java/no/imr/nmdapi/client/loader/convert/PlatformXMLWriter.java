package no.imr.nmdapi.client.loader.convert;

import java.io.File;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import no.imr.nmdapi.client.loader.dao.PlatformCodeDAO;
import no.imr.nmdapi.generic.nmdreference.domain.v1.NationType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.PlatformElementListType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.PlatformElementType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.PlatformType;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 *
 * @author sjurl
 */
public class PlatformXMLWriter implements RowCallbackHandler {

    private PlatformCodeDAO platformCodeDAO;

    private PlatformElementListType platformList;

    public void init(PlatformCodeDAO platformcodeDAO) {
        platformCodeDAO = platformcodeDAO;
        platformList = new PlatformElementListType();
    }

    public void finish() {
        try {
            JAXBContext ctx = JAXBContext.newInstance("no.imr.nmdapi.generic.nmdreference.domain.v1");
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(platformList, new File("/tmp/platform/test.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(PlatformXMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void processRow(ResultSet rs) throws SQLException {
        PlatformElementType platform = new PlatformElementType();
        platform.setId(rs.getString("id"));
        platform.setPlatformNumber(rs.getInt("platform"));
        PlatformType pt = new PlatformType();
        pt.setValue(new BigInteger(rs.getString("platformtype").concat(rs.getString("platformsubtype"))));
        pt.setDescription(rs.getString("platformtypename").concat(": ").concat(rs.getString("platformsubtypename")));
        platform.setPlatformType(pt);
        platform.setDeprecated(Boolean.FALSE);
        NationType nation = new NationType();
        nation.setValue(BigInteger.valueOf(rs.getInt("nation")));
        nation.setName(rs.getString("nationname").trim());
        platform.setNation(nation);
        PlatformType platformType = new PlatformType();
        platformType.setValue(BigInteger.valueOf(rs.getInt("platformtype")));
        

        platformCodeDAO.addPlatformCodes(platform);

        platformList.getPlatform().add(platform);
    }
}
