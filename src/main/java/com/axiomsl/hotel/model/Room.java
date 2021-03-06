package com.axiomsl.hotel.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "rooms")
@NamedQueries({
        @NamedQuery(name = "Room.findAllByCriteriaAndCity",
                query = "select r from Room r where " +
                        "(r.hotel.city = :city) and (r.number = :number) and (r.type = :type) and (r.direction = :direction)")
})
public class Room {
    private Long id;
    private int version;
    private String number;
    private RoomType type;
    private RoomDirection direction;
    private String price;
    private Integer numBeds;
    private Hotel hotel;
    private Set<Reservation> reservations = new HashSet<Reservation>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "room_version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @NotEmpty(message="{not_empty_text}")
    @Size(max = 4,
        message = "{max_room_number}")
    @Column(name = "room_number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name = "room_type")
    @Enumerated(EnumType.STRING)
    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    @Column(name = "room_direction")
    @Enumerated(EnumType.STRING)
    public RoomDirection getDirection() {
        return direction;
    }

    public void setDirection(RoomDirection direction) {
        this.direction = direction;
    }
    
    @NotEmpty(message="{not_empty_text}")
    @Column(name = "room_price")
    public String getPrice() {
    	return price;
    }
    
    public void setPrice(String price) {
    	this.price = price;
    }
    
    @NotNull
    @Column(name = "room_num_beds")
    public Integer getNumBeds() {
    	return numBeds;
    }
    
    public void setNumBeds(Integer numBeds) {
    	this.numBeds = numBeds;
    }

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @OneToMany(mappedBy = "room",
            cascade = CascadeType.ALL)
    @OrderBy("from ASC, to ASC")
    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", version=" + version +
                ", number='" + number + '\'' +
                ", type=" + type +
                ", direction=" + direction +
                ", price=" + price +
                ", hotel=" + hotel +
                ", reservations=" + reservations +
                '}';
    }
}
