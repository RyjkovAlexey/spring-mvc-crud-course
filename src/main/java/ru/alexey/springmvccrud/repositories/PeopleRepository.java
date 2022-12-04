package ru.alexey.springmvccrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexey.springmvccrud.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
