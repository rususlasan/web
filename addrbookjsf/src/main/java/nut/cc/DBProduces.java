package nut.cc;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by ruslansh on 12.02.17.
 * позволяент inject EntityManager в классе ContactEJB
 */

public class DBProduces {

    @Produces
    @PersistenceContext(unitName = "addrbook-web"/*"addrbook-db"*/)
    private EntityManager em;

}
