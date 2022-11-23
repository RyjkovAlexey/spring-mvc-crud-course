package ru.alexey.springmvccrud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alexey.springmvccrud.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query(
                        "SELECT * FROM person WHERE id=?",
                        new BeanPropertyRowMapper<>(Person.class),
                        new Object[]{id}
                )
                .stream()
                .findAny()
                .orElse(null);
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query(
                        "SELECT * FROM person WHERE email=?",
                        new BeanPropertyRowMapper<>(Person.class), new Object[]{email}
                )
                .stream()
                .findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update(
                "INSERT INTO person(name, age, email,address) VALUES (?, ?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress()
        );
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update(
                "UPDATE person SET name=?, age=?, email=?, address=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(), id
        );
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public void testMultipleUpdate() {
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        people.forEach(person -> {
            jdbcTemplate.update(
                    "INSERT INTO person(name, age, email) VALUES (?, ?, ?)",
                    person.getId(), person.getName(), person.getAge(), person.getEmail()
            );
        });

        long after = System.currentTimeMillis();

        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name" + i, 30, "test" + i + "@mail.ru", "some address"));
        }

        return people;
    }

    public void testBatchUpdate() {
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO person(name, age, email) VALUES (?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Person person = people.get(i);
                        ps.setString(1, person.getName());
                        ps.setInt(2, person.getAge());
                        ps.setString(3, person.getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long after = System.currentTimeMillis();

        System.out.println("Time: " + (after - before));
    }
}
