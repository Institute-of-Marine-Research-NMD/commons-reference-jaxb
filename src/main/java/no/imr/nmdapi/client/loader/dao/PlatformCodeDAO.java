package no.imr.nmdapi.client.loader.dao;

import javax.sql.DataSource;
import no.imr.nmdapi.client.loader.convert.PlatformCodeWriter;
import no.imr.nmdapi.generic.nmdreference.domain.v1.PlatformElementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author sjurl
 */
public class PlatformCodeDAO {

    private JdbcTemplate jdbcTemplate;

    private static final String GET_PLATFORM_CODES_FOR_PLATFORM = "Select pc.firstvaliddate, pc.lastvaliddate, pc.platformcode, pcs.platformcodesys, pcs.platformcodesysname"
            + " from nmdreference.platformcode pc, nmdreference.platformcodesys pcs"
            + " where pc.id_platformcodesys = pcs.id and pc.id_platform = ?";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addPlatformCodes(PlatformElementType platform) {
        PlatformCodeWriter pcw = new PlatformCodeWriter();
        pcw.init(platform);
        jdbcTemplate.query(GET_PLATFORM_CODES_FOR_PLATFORM, pcw, platform.getId());
    }
}
