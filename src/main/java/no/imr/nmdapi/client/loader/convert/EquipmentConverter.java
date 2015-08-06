package no.imr.nmdapi.client.loader.convert;

import no.imr.nmdapi.client.loader.dao.EquipmentDAO;
import no.imr.nmdapi.generic.nmdreference.domain.v1.EquipmentElementListType;

/**
 *
 * @author sjurl
 */
public class EquipmentConverter {

    private final EquipmentDAO equipmentDAO;

    public EquipmentConverter(EquipmentDAO equipmentDAO) {
        this.equipmentDAO = equipmentDAO;
    }

    public EquipmentElementListType generateEquipmentElementListType() {
        EquipmentElementListType equipmentList = new EquipmentElementListType();
        equipmentList.getElement().addAll(equipmentDAO.getEquipments());
        return equipmentList;
    }
}
