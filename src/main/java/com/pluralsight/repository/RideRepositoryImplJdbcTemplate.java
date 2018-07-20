package com.pluralsight.repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Repository("rideRepository")
public class RideRepositoryImplJdbcTemplate { //implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

//	@Override
	public List<Ride> getRides() {
	    List<Ride> rides = jdbcTemplate.query("select * from ride", new RowMapper<Ride>() {
            @Override
            public Ride mapRow(ResultSet resultSet, int i) throws SQLException {
                Ride ride = new Ride();
                ride.setId(resultSet.getInt("id"));
                ride.setName(resultSet.getString("name"));
                ride.setDuration(resultSet.getInt("duration"));
                return ride;
            }
        });
	    return rides;
	}

//	@Override
	public Ride createRide(Ride ride) {

//	     With jdbcTemplate
		 jdbcTemplate.update("insert into ride (name, duration) values (?,?)", ride.getName(), ride.getDuration());

        return null;
	}

	public Ride getRide(Integer id) {
	    Ride ride = jdbcTemplate.queryForObject("select * from ride where id = ?", new RideRowMapper(), id);

	    return ride;
    }
}
