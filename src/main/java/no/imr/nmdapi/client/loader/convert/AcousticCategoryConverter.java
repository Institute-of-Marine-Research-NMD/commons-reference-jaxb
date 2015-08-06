package no.imr.nmdapi.client.loader.convert;

import java.util.List;
import no.imr.nmdapi.client.loader.dao.AcousticCategoryDAO;
import no.imr.nmdapi.generic.nmdreference.domain.v1.AcousticCategoryElementListType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.AcousticCategoryElementType;

/**
 *
 * @author sjurl
 */
public class AcousticCategoryConverter {

    private final AcousticCategoryDAO acousticDAO;

    public AcousticCategoryConverter(AcousticCategoryDAO acousticCategoryDAO) {
        this.acousticDAO = acousticCategoryDAO;
    }

    public AcousticCategoryElementListType generateAcousticCategoryListType() {
        AcousticCategoryElementListType acListType = new AcousticCategoryElementListType();
        List<AcousticCategoryElementType> acList = acousticDAO.getAcousticCategories();
        acListType.getElement().addAll(acList);
        return acListType;
    }

}
