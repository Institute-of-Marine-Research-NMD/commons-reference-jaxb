package no.imr.nmdapi.client.loader.service;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import no.imr.nmdapi.client.loader.convert.PlatformConverter;
import no.imr.nmdapi.client.loader.convert.TaxaConverter;
import no.imr.nmdapi.client.loader.dao.PlatformDAO;
import no.imr.nmdapi.client.loader.dao.TaxaDAO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sjurl
 */
@Service(value = "referenceLoaderService")
public class ReferenceLoaderServiceImpl implements ReferenceLoaderServiceInterface {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ReferenceLoaderServiceImpl.class);
    @Autowired
    private PlatformDAO platformDAO;

    @Autowired
    private TaxaDAO taxaDAO;

    @Override
    public void loadReferenceToXml() {

        PlatformConverter pc = new PlatformConverter(platformDAO);

        writeToFile(pc.getPlatformList(), new File("/tmp/platform/platform.xml"));

        LOGGER.info("FINISHED with platforms!");

        TaxaConverter taxaConverter = new TaxaConverter(taxaDAO);
        writeToFile(taxaConverter.generateTaxaJaxBData(), new File("/tmp/platform/taxa.xml"));

        LOGGER.info("FINISHED with taxa!");
    }

    private void writeToFile(Object taxaList, File file) {
        try {
            JAXBContext ctx = JAXBContext.newInstance("no.imr.nmdapi.generic.nmdreference.domain.v1");
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(taxaList, file);
        } catch (JAXBException ex) {
            Logger.getLogger(ReferenceLoaderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
