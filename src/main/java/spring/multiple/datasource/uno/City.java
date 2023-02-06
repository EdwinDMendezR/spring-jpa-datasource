package spring.multiple.datasource.uno;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class City {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}
