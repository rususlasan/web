package nut.cc;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Created by ruslansh on 18.02.17.
 */
@Singleton
@Startup
@DataSourceDefinition(
        name = "java:global/jdbc/addrbookDS",
        className = "org.postgresql.Driver",
        url = "jdbc:postgresql://127.0.0.1:5432/db_for_prod",
        user = "prod_user",
        password = "prod"
)
public class DBPopulator {
    
}
