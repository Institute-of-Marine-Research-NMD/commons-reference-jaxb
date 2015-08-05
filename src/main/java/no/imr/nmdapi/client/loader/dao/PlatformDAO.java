package no.imr.nmdapi.client.loader.dao;

import javax.sql.DataSource;
import no.imr.nmdapi.client.loader.convert.PlatformXMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author sjurl
 */
public class PlatformDAO {

    private JdbcTemplate jdbcTemplate;

    private static final String QUERY = "select p.id, p.platform, pt.platformtype, na.nation, na.nationname, ty.platformtype, ty.platformsubtype, ty.platformtypename, ty.platformsubtypename\n"
            + "from nmdreference.platform p, nmdreference.platformtype pt, nmdreference.nation na, nmdreference.platformtype ty\n"
            + "where p.id_platformtype = pt.id and p.id_nation = na.id and p.id_platformtype = ty.id";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void processPlatforms(PlatformXMLWriter writer) {
        jdbcTemplate.query(QUERY, writer);
    }

}
