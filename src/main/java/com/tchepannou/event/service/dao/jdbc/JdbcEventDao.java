package com.tchepannou.event.service.dao.jdbc;

import com.tchepannou.event.service.dao.EventDao;
import com.tchepannou.event.service.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class JdbcEventDao implements EventDao {
    @Autowired
    private DataSource ds;

    //-- EventDao overrides
    @Override
    public List<Event> search(Collection<Long> calendarIds, Date startDate, Date endDate, int limit, int offset) {
        if (calendarIds.isEmpty()){
            return Collections.emptyList();
        }

        final String sql = "SELECT * FROM event"
                + " WHERE deleted=? "
                + " AND calendar_id IN (" + JdbcUtils.toParamVars(calendarIds) + ")"
                + " AND start_date BETWEEN ? AND ?"
                + " ORDER BY start_date"
        ;

        final List params = new ArrayList<>();
        params.add(false);
        params.addAll(calendarIds);
        params.add(startDate);
        params.add(endDate);

        return new JdbcTemplate(ds).query(
                sql,
                params.toArray(),
                (rs, i) -> map(rs)
        );
    }


    //-- Private
    private Event map (ResultSet rs) throws SQLException {
        Event event = new Event ();

        event.setId(rs.getLong("id"));
        event.setCalendarId(rs.getLong("calendar_id"));
        event.setRecurrenceId(rs.getString("recurrence_id"));
        event.setLocationId(rs.getLong("place_fk"));
        event.setAddressId(rs.getLong("address_fk"));
        event.setName(rs.getString("name"));
        event.setDescription(rs.getString("description"));
        event.setStartDate(rs.getDate("start_date"));
        event.setStartTime(rs.getTime("start_time"));
        event.setEndTime(rs.getTime("end_time"));
        event.setRequireRsvp(rs.getBoolean("require_rsvp"));
        event.setCreated(rs.getTimestamp("created"));
        event.setUpdated(rs.getTimestamp("updated"));

        Integer type = rs.getInt("type");
        if (type != null){
            event.setType(Event.Type.fromCode(type));
        }

        return event;
    }
}
