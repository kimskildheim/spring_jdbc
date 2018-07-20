package com.pluralsight.repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.RideRepository;
import com.pluralsight.repository.util.RideRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

//@Repository("rideRepository")
public class RideRepositoryImplReturnKeyHolder {// implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Ride> getRides() {
	    List<Ride> rides = jdbcTemplate.query("select * from ride", new RideRowMapper());
	    return rides;
	}

	public Ride createRide(Ride ride) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement ps =  connection.prepareStatement("insert into ride (name, duration) values (?,?)", new String [] {"id"});
                ps.setString(1, ride.getName());
                ps.setInt(2, ride.getDuration());
                return ps;
            }
        }, keyHolder);

        Number id = keyHolder.getKey();

        return getRide(id.intValue());
	}

	private Ride getRide(Integer id) {
	    Ride ride = jdbcTemplate.queryForObject("select * from ride where id = ?", new RideRowMapper(), id);

	    return ride;
    }
}
