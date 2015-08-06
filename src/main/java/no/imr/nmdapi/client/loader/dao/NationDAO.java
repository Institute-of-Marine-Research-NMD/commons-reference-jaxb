package no.imr.nmdapi.client.loader.dao;

import java.util.List;
import javax.sql.DataSource;
import no.imr.nmdapi.client.loader.mapper.NationElementTypeMapper;
import no.imr.nmdapi.generic.nmdreference.domain.v1.NationElementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author sjurl
 */
public class NationDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public List<NationElementType> getNations(){
        return jdbcTemplate.query("select id, nation, nationioc, nationname, pgnapescode FROM nmdreference.nation", new NationElementTypeMapper());
    }
}
