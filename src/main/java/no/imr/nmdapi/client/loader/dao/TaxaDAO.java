/*
 */
package no.imr.nmdapi.client.loader.dao;

import java.util.List;
import javax.sql.DataSource;
import no.imr.nmdapi.client.loader.convert.RestrictionElementTypeMapper;
import no.imr.nmdapi.client.loader.convert.SpesialstadierMapper;
import no.imr.nmdapi.client.loader.convert.StockElementTypeMapper;
import no.imr.nmdapi.client.loader.convert.TaxaMapper;
import no.imr.nmdapi.client.loader.convert.TaxaSynonymWriter;
import no.imr.nmdapi.client.loader.convert.UDPListMapper;
import no.imr.nmdapi.client.loader.pojo.SpesialstadieLists;
import no.imr.nmdapi.generic.nmdreference.domain.v1.KeyValueElementType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.RestrictionElementType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.StockElementType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.TaxaElementType;
import no.imr.nmdapi.generic.nmdreference.domain.v1.TaxaElementType.TaxaSynonyms.Synonym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author sjurl
 */
public class TaxaDAO {

    private JdbcTemplate jdbcTemplate;
    private static final String GET_ALL_TAXA = "select id, tsn, imr, path, nodc, validstatus, fao, information, pgnapes, aphiaid, ru_code from nmdreference.taxa";
    private static final String GET_TAXA_SYNONYM = "SELECT ts.name, ts.preferred, lan.name as language FROM "
            + "nmdreference.taxa_synonym ts, nmdreference.language lan WHERE ts.id_language = lan.id AND"
            + " ts.id_taxa = ?";
    private static final String GET_TAXA_LISTS = "select pt.sexdependent, udp.name, udp.id from nmdreference.u_u_vobject_property_taxa pt, nmdreference.u_udp udp\n"
            + "where id_taxa = ? and pt.id_u_udp = udp.id";

    private static final String GET_UDP_LIST_ELEMENTS = "select shortname, name, description, validfrom, validto, id from nmdreference.u_udplist where id_u_udp = ?";

    private static final String GET_RESTRICTION = "select rev.double_value, r.name, r.description from nmdreference.u_restriction_value rev, nmdreference.u_restriction r where rev.id_u_restriction = r.id and id_taxa = ?";

        private static final String GET_STOCK = "select description, id, code from nmdreference.stock where id_taxa = ?";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<TaxaElementType> getAllTaxa() {
        return jdbcTemplate.query(GET_ALL_TAXA, new TaxaMapper());
    }

    public List<Synonym> processPlatforms(String id) {
        return jdbcTemplate.query(GET_TAXA_SYNONYM, new TaxaSynonymWriter(), id);
    }

    public List<SpesialstadieLists> getListsForTaxa(String id) {
        return jdbcTemplate.query(GET_TAXA_LISTS, new SpesialstadierMapper(), id);
    }

    public List<KeyValueElementType> getKeyValueElements(String id) {
        return jdbcTemplate.query(GET_UDP_LIST_ELEMENTS, new UDPListMapper(), id);
    }

    public List<RestrictionElementType> getRestrictionsForTaxa(String id) {
        return jdbcTemplate.query(GET_RESTRICTION, new RestrictionElementTypeMapper(), id);
    }

    public List<StockElementType> getStock(String id) {
        return jdbcTemplate.query(GET_STOCK, new StockElementTypeMapper(), id);
    }
}
