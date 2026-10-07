package com.karan.hotel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity @Table(name="rooms")
public class Room {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(unique=true,nullable=false) private String roomNumber;
 @Column(nullable=false) private String name;
 @Column(nullable=false) private String type;
 private String description;
 @Column(nullable=false,precision=10,scale=2) private BigDecimal price;
 private Integer capacity;
 private String bedType;
 private String imageUrl;
 private String amenities;
 @Enumerated(EnumType.STRING) private Status status=Status.AVAILABLE;
 public enum Status { AVAILABLE, MAINTENANCE }
 public Room(){}
 public Room(String n,String name,String type,String desc,double price,int cap,String bed,String img,String amenities){this.roomNumber=n;this.name=name;this.type=type;this.description=desc;this.price=BigDecimal.valueOf(price);this.capacity=cap;this.bedType=bed;this.imageUrl=img;this.amenities=amenities;}
 public Long getId(){return id;} public String getRoomNumber(){return roomNumber;} public String getName(){return name;} public String getType(){return type;} public String getDescription(){return description;} public BigDecimal getPrice(){return price;} public Integer getCapacity(){return capacity;} public String getBedType(){return bedType;} public String getImageUrl(){return imageUrl;} public String getAmenities(){return amenities;} public Status getStatus(){return status;}
 public void setStatus(Status s){this.status=s;}
}
