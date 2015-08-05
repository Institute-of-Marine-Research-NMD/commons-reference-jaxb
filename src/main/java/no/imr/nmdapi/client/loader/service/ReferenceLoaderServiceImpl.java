package no.imr.nmdapi.client.loader.service;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import no.imr.nmdapi.client.loader.convert.PlatformXMLWriter;
import no.imr.nmdapi.client.loader.convert.TaxaConverter;
import no.imr.nmdapi.client.loader.dao.PlatformCodeDAO;
import no.imr.nmdapi.client.loader.dao.PlatformDAO;
import no.imr.nmdapi.client.loader.dao.TaxaDAO;
import no.imr.nmdapi.client.loader.pojo.SpesialstadieLists;
import no.imr.nmdapi.generic.nmdreference.domain.v1.KeyValueElementType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.RestrictionElementType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.RestrictionsElementType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.SexEnum;
import no.imr.nmdapi.generic.nmdreference.domain.v1.StockElementType;

import no.imr.nmdapi.generic.nmdreference.domain.v1.TaxaElementListType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.TaxaElementType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.TaxaListElementType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.TaxaListsElementType;
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
    private PlatformCodeDAO platformcodeDAO;

    @Autowired
    private TaxaDAO taxaDAO;

    @Override
    public void loadReferenceToXml() {
//        PlatformXMLWriter xmlWriter = new PlatformXMLWriter();
//
//        xmlWriter.init(platformcodeDAO);
//
//        platformDAO.getAllTaxa(xmlWriter);
//
//        xmlWriter.finish();
//
//        LOGGER.info("FINISHED with platforms!");

        TaxaConverter taxaConverter = new TaxaConverter(taxaDAO);
        TaxaElementListType taxaList = taxaConverter.generateTaxaJaxBData();
        writeToFile(taxaList);

        LOGGER.info("FINISHED with taxa!");
    }

    private void writeToFile(TaxaElementListType taxaList) {
        try {
            JAXBContext ctx = JAXBContext.newInstance("no.imr.nmdapi.generic.nmdreference.domain.v1");
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(taxaList, new File("/tmp/platform/taxa.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(PlatformXMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
