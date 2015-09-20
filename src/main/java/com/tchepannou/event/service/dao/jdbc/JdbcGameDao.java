package com.tchepannou.event.service.dao.jdbc;

import com.google.common.base.Strings;
import com.tchepannou.event.service.dao.GameDao;
import com.tchepannou.event.service.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JdbcGameDao implements GameDao {
    @Autowired
    private DataSource ds;

    @Override
    public List<Game> findByIds(Collection<Long> ids) {
        if (ids.isEmpty()){
            return Collections.emptyList();
        }

        final String sql = "SELECT * FROM game WHERE id IN (" + JdbcUtils.toParamVars(ids) + ")";
        return new JdbcTemplate(ds).query(
                sql,
                ids.toArray(),
                (rs, i) -> map(rs)
        );
    }

    private Game map(ResultSet rs) throws SQLException {
        Game game = new Game ();
        game.setId(rs.getLong("id"));
        game.setDuration((Integer)rs.getObject("duration"));
        game.setHome((Boolean)rs.getObject("home"));
        game.setJerseyColor(rs.getString("jersey_color"));
        game.setOpponent(rs.getString("opponent"));
        game.setOvertime((Boolean)rs.getObject("overtime"));
        game.setScore1((Integer) rs.getObject("score1"));
        game.setScore2((Integer)rs.getObject("score2"));

        String outcome = rs.getString("outcome");
        if (!Strings.isNullOrEmpty(outcome)) {
            game.setOutcome(Game.Outcome.fromCode(outcome.charAt(0)));
        }

        return game;
    }
}
