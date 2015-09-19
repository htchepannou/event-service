package com.tchepannou.event.service.dao.jdbc;

import com.tchepannou.event.service.dao.PlaceDao;
import com.tchepannou.event.service.domain.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JdbcPlaceDao implements PlaceDao {
    @Autowired
    private DataSource ds;

    @Override
    public List<Place> findByIds(Collection<Long> ids) {
        if (ids.isEmpty()){
            return Collections.emptyList();
        }

        final String sql = "SELECT * FROM place WHERE id IN (" + JdbcUtils.toParamVars(ids) + ")";
        return new JdbcTemplate(ds).query(
                sql,
                ids.toArray(),
                (rs, i) -> map(rs)
        );
    }

    private Place map(ResultSet rs) throws SQLException{
        final Place place = new Place();
        place.setAddressId(rs.getLong("address_fk"));
        place.setId(rs.getLong("id"));
        place.setName(rs.getString("name"));
        place.setWebsite(rs.getString("website"));
        return place;
    }
}
