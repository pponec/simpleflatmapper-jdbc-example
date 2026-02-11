package net.ponec.jdbc.dao;

import net.ponec.jdbc.entity.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class CountryDaoTest extends AbstractDaoTest {

    /** Test the creation and retrieval of a Country entity. */
    @Test
    void insertCountry() throws SQLException {
        var country = Country.of(null, "test");
        var countryDao = new CountryDao(connection);

        countryDao.create(country);
        Assertions.assertNotNull(country.getId());

        var restoredCountry = countryDao.read(country.getId());
        Assertions.assertEquals(country, restoredCountry);

        var missingCountry = countryDao.read(-100L);
        Assertions.assertNull(missingCountry);
    }

}