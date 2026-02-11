package net.ponec.jdbc.dao;

import net.ponec.jdbc.entity.Country;

import java.sql.Connection;
import java.sql.SQLException;

public class CountryDao  extends GenericCrudManager<Country, Long> {

    final Connection dbConnection;

    public CountryDao(Connection dbConnection) throws SQLException {
        super(dbConnection, Country.class, Country::setId);
        this.dbConnection = dbConnection;
    }

}
