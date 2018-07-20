package com.pluralsight.repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RideRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Repository("rideRepository")
public class RideRepositoryImplNamedParameters implements RideRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Ride> getRides() {
	    List<Ride> rides = jdbcTemplate.query("select * from ride", new RideRowMapper());
	    return rides;
	}

	@Override
	public Ride createRide(Ride ride) {

        // With SimpleJdbcTemplate
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);

        List<String> columns = new ArrayList<>();
        columns.add("name");
        columns.add("duration");

        insert.setTableName("ride");
        insert.setColumnNames(columns);

        Map<String, Object> data = new HashMap<>();
        data.put("name", ride.getName());
        data.put("duration", ride.getDuration());

        insert.setGeneratedKeyName("id");

        Number id = insert.executeAndReturnKey(data);

        return getRide(id.intValue());
	}

	@Override
	public Ride getRide(Integer id) {
	    Ride ride = jdbcTemplate.queryForObject("select * from ride where id = ?", new RideRowMapper(), id);

	    return ride;
    }

    @Override
    public Ride updateRide(Ride ride) {

	    jdbcTemplate.update("update ride set name = ?, duration = ? where id = ?",
                ride.getName(), ride.getDuration(), ride.getId());

        return ride;
    }

    @Override
    public void updateRides(List<Object[]> pairs) {
        jdbcTemplate.batchUpdate("update ride set ride_date = ? where id = ?", pairs);
    }

    @Override

    //Works best with many parameters (like in a search) so you dont need to fix the order of the ?-marks
    public void deleteRide(Integer id) {
        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);

        namedTemplate.update("delete from ride where id = :id", paramMap);
    }
}
