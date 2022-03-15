package model;

import sun.security.krb5.internal.crypto.Des;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="destination")
public class Destination {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column
    private String description;
    @Column
    private String country;
    @Column
    private Double celsiusTemperature;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<VacationPackage> vacationPackages;

    public Destination() {

    }

    public Destination(String name, String description, String country, Double celsiusTemperature) {
        this.name = name;
        this.description = description;
        this.country = country;
        this.celsiusTemperature = celsiusTemperature;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCountry() {
        return country;
    }

    public Double getCelsiusTemperature() {
        return celsiusTemperature;
    }

    public List<VacationPackage> getVacationPackages() {
        return vacationPackages;
    }
}
