package no.imr.nmdapi.client.loader.convert;

import no.imr.nmdapi.client.loader.dao.LanguageDAO;
import no.imr.nmdapi.generic.nmdreference.domain.v1.LanguageElementListType;

/**
 *
 * @author sjurl
 */
public class LanguageConverter {

    private final LanguageDAO languageDAO;

    public LanguageConverter(LanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
    }

    public LanguageElementListType getLanguageElementList() {
        LanguageElementListType langList = new LanguageElementListType();
        langList.getElement().addAll(languageDAO.getLanguageElementType());
        return langList;
    }
}
