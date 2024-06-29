package com.ire.backend.database.dao;

import com.generated.organizationplatform.protocol.domain.PropertyDetails;
import com.ire.backend.database.DataSourceFactory;
import com.ire.backend.database.PropertyDetailsDaoImpl;
import com.organizationplatform.protocol.domain.types.Address;
import com.organizationplatform.protocol.domain.types.Price;
import com.organizationplatform.protocol.domain.types.PropertyType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Tag("DaoTest")
public class PropertyDetailsDaoTest {

    private String calculateUUID() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void shouldGetConnection() {
        Connection connection = DataSourceFactory.ownerDataSource();
        DataSourceFactory.closeConnection(connection);
    }

    @Test
    public void shouldInsertPropertyDetailsAndReturnUUID() {

        String uuid = calculateUUID();

        PropertyDetails propertyDetails = new PropertyDetails(
                "12",
                new Address(
                        "12",
                        "york house",
                        "london",
                        "greater london",
                        "w148rl",
                        "united kingdom"
                ),
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                PropertyType.Apartment,
                12,
                2,
                new Price(12000.0, Currency.getInstance("GBP")),
                List.of(),
                uuid

        );


        PropertyDetailsDaoImpl propertyDetailsDao = new PropertyDetailsDaoImpl();

        String result = propertyDetailsDao.insert(propertyDetails);

        Assertions.assertNotNull(result);
    }

    @Test
    public void shouldGetPropertyDetails() {

        String uuid = calculateUUID();

        PropertyDetails expected = new PropertyDetails(
                "12",
                new Address(
                        "12",
                        "york house",
                        "london",
                        "greater london",
                        "w148rl",
                        "united kingdom"
                ),
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                PropertyType.Apartment,
                12,
                2,
                new Price(12000.0, Currency.getInstance("GBP")),
                List.of(),
                uuid

        );

        PropertyDetailsDaoImpl propertyDetailsDao = new PropertyDetailsDaoImpl();

        String result = propertyDetailsDao.insert(expected);
        Assertions.assertNotNull(result);

        PropertyDetails actual = propertyDetailsDao.get(result);

        deepAssert(expected, actual);
    }

    @Test
    public void shouldDeletePropertyDetails() {

        String uuid = calculateUUID();

        PropertyDetails expected = new PropertyDetails(
                "12",
                new Address(
                        "12",
                        "york house",
                        "london",
                        "greater london",
                        "w148rl",
                        "united kingdom"
                ),
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                PropertyType.Apartment,
                12,
                2,
                new Price(12000.0, Currency.getInstance("GBP")),
                List.of(),
                uuid

        );

        PropertyDetailsDaoImpl propertyDetailsDao = new PropertyDetailsDaoImpl();

        String result = propertyDetailsDao.insert(expected);
        PropertyDetails actual = propertyDetailsDao.get(result);

        deepAssert(expected, actual);

        String deletedId = propertyDetailsDao.delete(result);
        Assertions.assertFalse(deletedId.isEmpty());

        PropertyDetails actualDeleted = propertyDetailsDao.get(result);

        Assertions.assertNull(actualDeleted);
    }

    @Test
    public void shouldUpdatePropertyDetails() {

        String uuid = calculateUUID();

        PropertyDetails expected = new PropertyDetails(
                "12",
                new Address(
                        "12",
                        "york house",
                        "london",
                        "greater london",
                        "w148rl",
                        "united kingdom"
                ),
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                PropertyType.Apartment,
                12,
                2,
                new Price(12000.0, Currency.getInstance("GBP")),
                List.of(),
                uuid

        );

        PropertyDetailsDaoImpl propertyDetailsDao = new PropertyDetailsDaoImpl();

        String result = propertyDetailsDao.insert(expected);
        Assertions.assertNotNull(result);

        PropertyDetails actual = propertyDetailsDao.get(result);

        deepAssert(expected, actual);


        PropertyDetails expectedUpdate = new PropertyDetails(
                result,
                new Address(
                        "18",
                        "hazel way",
                        "crawley",
                        "west sussex",
                        "RH104JR",
                        "united kingdom"
                ),
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                PropertyType.House,
                3,
                2,
                new Price(10000000.0, Currency.getInstance("GBP")),
                List.of(),
                uuid

        );

        String updatedId = propertyDetailsDao.update(expectedUpdate);
        Assertions.assertFalse(updatedId.isEmpty());

        PropertyDetails actualUpdated = propertyDetailsDao.get(result);

        deepAssert(expectedUpdate, actualUpdated);

    }

    private static void deepAssert(final PropertyDetails expected, final PropertyDetails actual)
    {
        Assertions.assertEquals(expected.getAddress(), actual.getAddress());
        Assertions.assertEquals(expected.getDateAdded(), actual.getDateAdded());
        Assertions.assertEquals(expected.getLastUpdated(), actual.getLastUpdated());
        Assertions.assertEquals(expected.getPropertyType(), actual.getPropertyType());
        Assertions.assertEquals(expected.getBeds(), actual.getBeds());
        Assertions.assertEquals(expected.getBathrooms(), actual.getBathrooms());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
        Assertions.assertEquals(expected.getOrganizationInformationId(), actual.getOrganizationInformationId());
    }

}
