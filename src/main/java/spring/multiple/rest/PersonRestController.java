package spring.multiple.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.multiple.datasource.dos.Person;
import spring.multiple.datasource.dos.PersonRepository;
import spring.multiple.datasource.uno.City;
import spring.multiple.datasource.uno.CityRepository;

import java.util.UUID;

@RestController
public class PersonRestController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/post")
    public String postPerson() {
        Person person = new Person();
        person.setName(UUID.randomUUID().toString());
        person.setLastName(UUID.randomUUID().toString());
        personRepository.save(person);

        City city = new City();
        city.setName(UUID.randomUUID().toString());
        cityRepository.save(city);

        return "post";
    }


}
