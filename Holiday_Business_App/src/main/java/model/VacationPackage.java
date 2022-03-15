package model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="package")
public class VacationPackage {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Date start;
    @Column(nullable = false)
    private Date end;
    @Column
    private String extraDetails;

    @Column(nullable = false)
    private Integer maximumBookings;

    @Column(nullable = false)
    private Integer bookings;

    @ManyToOne
    @JoinColumn(name="destination_id")
    private Destination destination;

    @ManyToMany(mappedBy = "bookedPackages", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> user;

    public VacationPackage() {

    }


    public VacationPackage(String name, Double price, Date start, Date end,
                           String extraDetails, Integer maximumBookings, Destination destination) {
        this.name = name;
        this.price = price;
        this.start = start;
        this.end = end;
        this.extraDetails = extraDetails;
        this.maximumBookings = maximumBookings;
        this.destination = destination;
        this.bookings = 0;
    }

    public Integer getId() {
        return id;
    }

    public List<User> getUser() { return user; }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public Integer getMaximumBookings() {
        return maximumBookings;
    }

    public Integer getBookings() {
        return bookings;
    }

    public Destination getDestination() { return destination; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
    }

    public void setMaximumBookings(Integer maximumBookings) {
        this.maximumBookings = maximumBookings;
    }

    public void setBookings(Integer bookings) {
        this.bookings = bookings;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
