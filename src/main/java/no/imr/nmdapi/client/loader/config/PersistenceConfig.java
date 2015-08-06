package no.imr.nmdapi.client.loader.config;

import javax.sql.DataSource;
import no.imr.nmdapi.client.loader.dao.AcousticCategoryDAO;
import no.imr.nmdapi.client.loader.dao.EquipmentDAO;
import no.imr.nmdapi.client.loader.dao.InstitutionDAO;
import no.imr.nmdapi.client.loader.dao.PlatformDAO;
import no.imr.nmdapi.client.loader.dao.TaxaDAO;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author sjurl
 */
@Configuration
public class PersistenceConfig {

    @Autowired
    private org.apache.commons.configuration.Configuration config;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(config.getString("jdbc.driver"));
        dataSource.setUrl(config.getString("jdbc.url"));
        dataSource.setUsername(config.getString("jdbc.user"));
        dataSource.setPassword(config.getString("jdbc.password"));
        dataSource.setPassword(config.getString("jdbc.password"));

        return dataSource;
    }

    @Bean
    public PlatformDAO platformDAO() {
        return new PlatformDAO();
    }

    @Bean
    public TaxaDAO taxaDAO() {
        return new TaxaDAO();
    }

    @Bean
    public AcousticCategoryDAO acousticCategoryDAO() {
        return new AcousticCategoryDAO();
    }

    @Bean
    public EquipmentDAO equipmentDAO() {
        return new EquipmentDAO();
    }

    @Bean
    public InstitutionDAO institutionDAO() {
        return new InstitutionDAO();    
    }
}
