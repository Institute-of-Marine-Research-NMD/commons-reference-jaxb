package no.imr.nmdapi.client.loader.convert;

import no.imr.nmdapi.client.loader.dao.InstitutionDAO;
import no.imr.nmdapi.generic.nmdreference.domain.v1.InstitutionElementListType;

/**
 *
 * @author sjurl
 */
public class InstitutionConverter {

    private final InstitutionDAO institutionDAO;

    public InstitutionConverter(InstitutionDAO institutionDAO) {
        this.institutionDAO = institutionDAO;
    }

    public InstitutionElementListType getInstitutionElementListType() {
        InstitutionElementListType institutionList = new InstitutionElementListType();
        institutionList.getElement().addAll(institutionDAO.getInstitutions());
        return institutionList;
    }
}
