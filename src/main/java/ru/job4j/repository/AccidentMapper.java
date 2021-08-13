package ru.job4j.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.job4j.model.Accident;
import ru.job4j.model.AccidentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

@Service
public class AccidentMapper implements RowMapper<Accident> {

    private final AccidentJdbcTemplate accidentJdbcTemplate;

    public AccidentMapper(AccidentJdbcTemplate accidentJdbcTemplate) {
        this.accidentJdbcTemplate = accidentJdbcTemplate;
    }

    @Override
    public Accident mapRow(ResultSet resultSet, int i) throws SQLException {
        Accident accident = new Accident();
        AccidentType accidentType = new AccidentType();
        accident.setId(resultSet.getInt("accident_id"));
        accident.setName(resultSet.getString("accident_name"));
        accidentType.setId(resultSet.getInt("types_id"));
        accidentType.setName(resultSet.getString("types_name"));
        accident.setType(accidentType);
        accident.setRules(new HashSet<>(accidentJdbcTemplate.findRulesById(accident.getId())));
        return accident;
    }
}
