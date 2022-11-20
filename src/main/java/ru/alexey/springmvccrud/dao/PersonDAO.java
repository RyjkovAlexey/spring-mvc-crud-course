package ru.alexey.springmvccrud.dao;

import org.springframework.stereotype.Component;
import ru.alexey.springmvccrud.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final List<Person> people;
    private static int PEOPLE_COUNT = 0;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Ivan"));
        people.add(new Person(++PEOPLE_COUNT, "Petya"));
        people.add(new Person(++PEOPLE_COUNT, "Katya"));
        people.add(new Person(++PEOPLE_COUNT, "Alexey"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people
                .stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);

        people.add(person);
    }

    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(updatedPerson.getName());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }
}
