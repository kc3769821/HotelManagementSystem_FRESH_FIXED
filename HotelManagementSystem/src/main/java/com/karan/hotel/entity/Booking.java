package com.karan.hotel.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name="bookings")
public class Booking {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.EAGER) private Room room;
 @JsonIgnore @ManyToOne(fetch=FetchType.LAZY) private User user;
 @Column(nullable=false) private String guestName;
 @Column(nullable=false) private String email;
 @Column(nullable=false) private String phone;
 @Column(nullable=false) private LocalDate checkIn;
 @Column(nullable=false) private LocalDate checkOut;
 private Integer guests;
 @Column(nullable=false,precision=10,scale=2) private BigDecimal totalAmount;
 @Enumerated(EnumType.STRING) private Status status=Status.CONFIRMED;
 private LocalDateTime createdAt=LocalDateTime.now();
 public enum Status { CONFIRMED, CANCELLED }
 public Booking(){}
 public Long getId(){return id;} public User getUser(){return user;} public void setUser(User u){user=u;} public Room getRoom(){return room;} public void setRoom(Room r){room=r;} public String getGuestName(){return guestName;} public void setGuestName(String x){guestName=x;} public String getEmail(){return email;} public void setEmail(String x){email=x;} public String getPhone(){return phone;} public void setPhone(String x){phone=x;} public LocalDate getCheckIn(){return checkIn;} public void setCheckIn(LocalDate x){checkIn=x;} public LocalDate getCheckOut(){return checkOut;} public void setCheckOut(LocalDate x){checkOut=x;} public Integer getGuests(){return guests;} public void setGuests(Integer x){guests=x;} public BigDecimal getTotalAmount(){return totalAmount;} public void setTotalAmount(BigDecimal x){totalAmount=x;} public Status getStatus(){return status;} public void setStatus(Status x){status=x;} public LocalDateTime getCreatedAt(){return createdAt;}
}
