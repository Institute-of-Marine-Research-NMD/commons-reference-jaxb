package no.imr.nmdapi.client.loader.dao;

import java.util.List;
import javax.sql.DataSource;
import no.imr.nmdapi.client.loader.mapper.AcousticCategoryElementTypeMapper;
import no.imr.nmdapi.generic.nmdreference.domain.v1.AcousticCategoryElementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author sjurl
 */
public class AcousticCategoryDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<AcousticCategoryElementType> getAcousticCategories() {
        return jdbcTemplate.query("SELECT id, acousticcategory, initials, englishinitials, commonname, englishname, pgnapescode FROM nmdreference.acousticcategory", new AcousticCategoryElementTypeMapper());
    }
}
