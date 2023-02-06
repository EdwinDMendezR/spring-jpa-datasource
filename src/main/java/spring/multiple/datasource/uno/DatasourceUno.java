package spring.multiple.datasource.uno;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = {CityRepository.class},
        entityManagerFactoryRef = "db1EntityManagerFactory",
        transactionManagerRef = "db1TransactionManager"
)
public class DatasourceUno {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "app.datasource.db1")
    public DataSourceProperties db1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.jpa.db1")
    public JpaProperties db1JpaProperties() {
        return new JpaProperties();
    }

    @Bean(name = "db1DataSource")
    @Primary
    @ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
    @ConfigurationProperties("app.datasource.db1.tomcat")
    public DataSource db1DataSource() {
        return db1DataSourceProperties().initializeDataSourceBuilder().build();
    }


    @Bean(name = "db1EntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
            final EntityManagerFactoryBuilder builder,
            @Qualifier("db1DataSource") final DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages(City.class)
                .persistenceUnit("db1")
                .properties(db1JpaProperties().getProperties())
                .build();
    }

    @Bean(name = "db1TransactionManager")
    @Primary
    public PlatformTransactionManager db1TransactionManager(
            @Qualifier("db1EntityManagerFactory") final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}
