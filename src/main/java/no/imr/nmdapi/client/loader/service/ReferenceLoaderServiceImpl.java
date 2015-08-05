package no.imr.nmdapi.client.loader.service;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import no.imr.nmdapi.client.loader.convert.PlatformXMLWriter;
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

        TaxaElementListType taxaList = new TaxaElementListType();
        List<TaxaElementType> taxas = taxaDAO.getAllTaxa();
        for (TaxaElementType taxaElementType : taxas) {
            List<TaxaElementType.TaxaSynonyms.Synonym> synonyms = taxaDAO.processPlatforms(taxaElementType.getId());
            TaxaElementType.TaxaSynonyms tsyn = new TaxaElementType.TaxaSynonyms();
            tsyn.getSynonym().addAll(synonyms);
            taxaElementType.setTaxaSynonyms(tsyn);

            List<SpesialstadieLists> spesialstadier = taxaDAO.getListsForTaxa(taxaElementType.getId());
            TaxaListsElementType lists = new TaxaListsElementType();
            for (SpesialstadieLists spesialstadieLists : spesialstadier) {
                TaxaListElementType tlet = new TaxaListElementType();
                tlet.setName(spesialstadieLists.getName());
                switch (spesialstadieLists.getSexdependent()) {
                    case 0:
                        tlet.setSex(SexEnum.BOTH);
                        break;
                    case 1:
                        tlet.setSex(SexEnum.FEMALE);
                        break;
                    case 2:
                        tlet.setSex(SexEnum.MALE);
                        break;
                }

                List<KeyValueElementType> keyvalues = taxaDAO.getKeyValueElements(spesialstadieLists.getId());
                tlet.getElement().addAll(keyvalues);
                lists.getList().add(tlet);
            }
            if (!lists.getList().isEmpty()) {
                taxaElementType.setLists(lists);
            }

            RestrictionsElementType ret = new RestrictionsElementType();
            List<RestrictionElementType> restrictions = taxaDAO.getRestrictionsForTaxa(taxaElementType.getId());
            ret.getRestriction().addAll(restrictions);
            if (!ret.getRestriction().isEmpty()) {
                taxaElementType.setRestrictions(ret);
            }

            TaxaElementType.Stocks st = new TaxaElementType.Stocks();
            List<StockElementType> stocks = taxaDAO.getStock(taxaElementType.getId());
            st.getStock().addAll(stocks);
            if(!st.getStock().isEmpty()){
                taxaElementType.setStocks(st);
            }
            
            taxaList.getTaxa().add(taxaElementType);
        }

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
