package com.tchepannou.event.service.dao.jdbc;

import com.tchepannou.event.service.dao.AddressDao;
import com.tchepannou.event.service.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JdbcAddressDao implements AddressDao {
    @Autowired
    private DataSource ds;

    @Override
    public Address findById(long id) {
        final String sql = "SELECT * FROM address WHERE id=?";
        return new JdbcTemplate(ds).queryForObject(
                sql,
                new Object[] {id},
                (rs, i) -> map(rs)
        );
    }

    @Override
    public List<Address> findByIds(Collection<Long> ids) {
        if (ids.isEmpty()){
            return Collections.emptyList();
        }

        final String sql = "SELECT * FROM address WHERE id IN (" + JdbcUtils.toParamVars(ids) + ")";
        return new JdbcTemplate(ds).query(
                sql,
                ids.toArray(),
                (rs, i) -> map(rs)
        );
    }

    private Address map(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setId(rs.getLong("id"));
        address.setCity(rs.getString("city"));
        address.setCountry(rs.getString("country_code"));
        address.setState(rs.getString("state"));
        address.setStreet(rs.getString("street"));
        address.setZipCode(rs.getString("zip_code"));
        return address;
    }
}
