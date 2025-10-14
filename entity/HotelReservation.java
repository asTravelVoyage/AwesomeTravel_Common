// package renewal.common.entity;

// import java.time.LocalDate;

// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.JoinColumn;

// import lombok.Getter;
// import lombok.Setter;
// import lombok.NoArgsConstructor;
// import lombok.NonNull;
// import lombok.RequiredArgsConstructor;

// @Entity
// @Getter
// @Setter
// @NoArgsConstructor
// @RequiredArgsConstructor
// public class HotelReservation extends AuditingFields {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
    
//     @NonNull
//     @ManyToOne
//     @JoinColumn(name = "hotel_id")
//     private Hotel hotel;
    
//     @NonNull
//     private Long tourId;

//     @NonNull
//     private Long roomCount;

//     @NonNull
//     private LocalDate startDate;
    
//     @NonNull
//     private LocalDate endDate;

//     // @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//     // private LocalDateTime createdAt;

//     @Enumerated(EnumType.STRING)
//     @NonNull
//     private HotelReservationStatus status;

//     public enum HotelReservationStatus {
//         BOOKED,
//         CANCELLED,
//         COMPLETED
//     }
    
// }
