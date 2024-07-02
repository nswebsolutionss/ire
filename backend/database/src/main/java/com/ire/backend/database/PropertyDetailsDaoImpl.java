package com.ire.backend.database;

import com.generated.organizationplatform.protocol.domain.PropertyDetails;
import com.ire.backend.database.dao.PropertyDetailsDao;
import com.organizationplatform.protocol.domain.types.Address;
import com.organizationplatform.protocol.domain.types.Price;
import com.organizationplatform.protocol.domain.types.PropertyType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static com.ire.backend.database.DataSourceFactory.closeConnection;
import static java.sql.Types.OTHER;

public class PropertyDetailsDaoImpl implements PropertyDetailsDao {

    private static final Logger LOGGER = LogManager.getLogger();
    private final String[] idColumn = new String[]{"id"};
    private final DataSource dataSource;

    public PropertyDetailsDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Received exception when attempting to get dataSource connection: " + e);
            throw new RuntimeException(e);
        }
        return connection;
    }

    @Override
    public String insertPropertyDetails(final PropertyDetails propertyDetails) {

        String sql = """
                   WITH details_insert as (
                    INSERT into property_details (
                     date_added,
                     last_updated,
                     property_type,
                     beds,
                     bathrooms,
                     price,
                     currency,
                     organization_id)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    RETURNING id
                   )
                   INSERT into property_address(
                    building_identifier,
                    street_name,
                    city,
                    county,
                    postcode,
                    country,
                    property_details_id,
                    organization_id)
                    VALUES(?, ?, ?, ?, ?, ?, (select id from details_insert), ?);
                """;

        // TODO: FIX PROPERTY DETAILS ID TO BE LONG AND NOT STRING
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql, idColumn);

            ps.setLong(1, propertyDetails.getDateAdded());
            ps.setLong(2, propertyDetails.getLastUpdated());
            ps.setString(3, propertyDetails.getPropertyType().representation());
            ps.setInt(4, (int) propertyDetails.getBeds());
            ps.setInt(5, (int) propertyDetails.getBathrooms());
            ps.setString(6, String.valueOf(propertyDetails.getPrice().value()));
            ps.setString(7, propertyDetails.getPrice().currency().getCurrencyCode());
            ps.setObject(8, propertyDetails.getOrganizationId(), OTHER);

            ps.setString(9, propertyDetails.getAddress().buildingIdentifier());
            ps.setString(10, propertyDetails.getAddress().streetName());
            ps.setString(11, propertyDetails.getAddress().city());
            ps.setString(12, propertyDetails.getAddress().county());
            ps.setString(13, propertyDetails.getAddress().postcode());
            ps.setString(14, propertyDetails.getAddress().country());
            ps.setObject(15, propertyDetails.getOrganizationId(), OTHER);

            ps.execute();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                String key = generatedKeys.getString(1);
                return key;
            } else {
                LOGGER.warn("Unable to INSERT propertyDetails: " + propertyDetails);
                return null;
            }
        } catch (final SQLException e) {
            LOGGER.error("Received SqlException when attempting to write to database: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public PropertyDetails getPropertyDetails(final String uuid) {

        final String sql = """
                SELECT
                    property_details.id,
                    property_details.date_added,
                    property_details.last_updated,
                    property_details.property_type,
                    property_details.beds,
                    property_details.bathrooms,
                    property_details.price,
                    property_details.currency,
                    property_details.organization_id,
                    property_address.building_identifier,
                    property_address.street_name,
                    property_address.city,
                    property_address.county,
                    property_address.postcode,
                    property_address.country
                FROM
                    property_details
                    INNER JOIN property_address ON property_address.property_details_id = property_details.id
                WHERE property_details.id = ?
                """;

        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, Long.parseLong(uuid));
            ResultSet rs = ps.executeQuery();
            PropertyDetails organizationInformation = null;
            if (rs.next()) {
                final Address address = new Address(
                        rs.getString("building_identifier"),
                        rs.getString("street_name"),
                        rs.getString("city"),
                        rs.getString("county"),
                        rs.getString("postcode"),
                        rs.getString("country")
                );
                organizationInformation = new PropertyDetails(
                        rs.getString("id"),
                        address,
                        rs.getLong("date_added"),
                        rs.getLong("last_updated"),
                        PropertyType.valueOf(rs.getString("property_type")),
                        rs.getDouble("beds"),
                        rs.getDouble("bathrooms"),
                        new Price(rs.getDouble("price"), Currency.getInstance(rs.getString("currency"))),
                        List.of(),
                        rs.getString("organization_id")
                );
            }
            rs.close();
            ps.close();
            return organizationInformation;
        } catch (SQLException e) {
            LOGGER.error("Received SqlException when attempting to write to database: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<PropertyDetails> getAllPropertiesDetailsForOrganizationId(String organizationId) {
        final String sql = """
                SELECT
                    property_details.id,
                    property_details.date_added,
                    property_details.last_updated,
                    property_details.property_type,
                    property_details.beds,
                    property_details.bathrooms,
                    property_details.price,
                    property_details.currency,
                    property_details.organization_id,
                    property_address.building_identifier,
                    property_address.street_name,
                    property_address.city,
                    property_address.county,
                    property_address.postcode,
                    property_address.country
                FROM
                    property_details
                    INNER JOIN property_address ON property_address.property_details_id = property_details.id
                WHERE property_details.organization_id = ?
                """;

        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, organizationId, OTHER);
            ResultSet rs = ps.executeQuery();
            List<PropertyDetails> organizationInformationList = new ArrayList<>();
            while (rs.next()) {
                final Address address = new Address(
                        rs.getString("building_identifier"),
                        rs.getString("street_name"),
                        rs.getString("city"),
                        rs.getString("county"),
                        rs.getString("postcode"),
                        rs.getString("country")
                );
                organizationInformationList.add(
                        new PropertyDetails(
                                rs.getString("id"),
                                address,
                                rs.getLong("date_added"),
                                rs.getLong("last_updated"),
                                PropertyType.valueOf(rs.getString("property_type")),
                                rs.getDouble("beds"),
                                rs.getDouble("bathrooms"),
                                new Price(rs.getDouble("price"), Currency.getInstance(rs.getString("currency"))),
                                List.of(),
                                rs.getString("organization_id")
                        )
                );
            }
            rs.close();
            ps.close();
            return organizationInformationList;
        } catch (SQLException e) {
            LOGGER.error("Received SqlException when attempting to write to database: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public List<PropertyDetails> getAllProperties() {
        final String sql = """
                SELECT
                    property_details.id,
                    property_details.date_added,
                    property_details.last_updated,
                    property_details.property_type,
                    property_details.beds,
                    property_details.bathrooms,
                    property_details.price,
                    property_details.currency,
                    property_details.organization_id,
                    property_address.building_identifier,
                    property_address.street_name,
                    property_address.city,
                    property_address.county,
                    property_address.postcode,
                    property_address.country
                FROM
                    property_details
                    INNER JOIN property_address ON property_address.property_details_id = property_details.id
                """;

        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<PropertyDetails> organizationInformationList = new ArrayList<>();
            while (rs.next()) {
                final Address address = new Address(
                        rs.getString("building_identifier"),
                        rs.getString("street_name"),
                        rs.getString("city"),
                        rs.getString("county"),
                        rs.getString("postcode"),
                        rs.getString("country")
                );
                organizationInformationList.add(
                        new PropertyDetails(
                                rs.getString("id"),
                                address,
                                rs.getLong("date_added"),
                                rs.getLong("last_updated"),
                                PropertyType.valueOf(rs.getString("property_type")),
                                rs.getDouble("beds"),
                                rs.getDouble("bathrooms"),
                                new Price(rs.getDouble("price"), Currency.getInstance(rs.getString("currency"))),
                                List.of(),
                                rs.getString("organization_id")
                        )
                );
            }
            rs.close();
            ps.close();
            return organizationInformationList;
        } catch (SQLException e) {
            LOGGER.error("Received SqlException when attempting to write to database: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public String deletePropertyDetails(final String uuid) {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM property_details WHERE id = ?", idColumn);
            String generatedKey = null;
            ps.setLong(1, Long.parseLong(uuid));

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getString(1);
            }
            ps.close();
            rs.close();
            return generatedKey;
        } catch (SQLException e) {
            LOGGER.error("Received SqlException when attempting to write to database: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * ONLY TEMPORARY FOR USE IN TESTS
     *
     * @return
     */
    public void deleteAll() {
        Connection connection = getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM property_details");
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public String updatePropertyDetails(final PropertyDetails propertyDetails) {

        String sql = """
                   WITH property_insert as (
                       UPDATE property_details set
                        date_added = ?,
                        last_updated = ?,
                        property_type = ?,
                        beds = ?,
                        bathrooms = ?,
                        price = ?,
                        currency = ?
                        WHERE id = ?
                        RETURNING id 
                    )
                    UPDATE property_address set
                     building_identifier = ?,
                     street_name = ?,
                     city = ?,
                     county = ?,
                     postcode = ?,
                     country = ?
                     WHERE id = (select id from property_insert);
                """;


        Connection connection = getConnection();
        try {

            String generatedKey = null;
            PreparedStatement ps = connection.prepareStatement(sql, 1);

            ps.setLong(1, propertyDetails.getDateAdded());
            ps.setLong(2, propertyDetails.getLastUpdated());
            ps.setString(3, propertyDetails.getPropertyType().representation());
            ps.setInt(4, (int) propertyDetails.getBeds());
            ps.setInt(5, (int) propertyDetails.getBathrooms());
            ps.setString(6, String.valueOf(propertyDetails.getPrice().value()));
            ps.setString(7, propertyDetails.getPrice().currency().getCurrencyCode());


            ps.setLong(8, Long.parseLong(propertyDetails.getId()));

            ps.setString(9, propertyDetails.getAddress().buildingIdentifier());
            ps.setString(10, propertyDetails.getAddress().streetName());
            ps.setString(11, propertyDetails.getAddress().city());
            ps.setString(12, propertyDetails.getAddress().county());
            ps.setString(13, propertyDetails.getAddress().postcode());
            ps.setString(14, propertyDetails.getAddress().country());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getString(1);
            }
            ps.close();
            rs.close();
            return generatedKey;
        } catch (SQLException e) {
            LOGGER.error("Received SqlException when attempting to write to database: ", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
        }
    }

}
