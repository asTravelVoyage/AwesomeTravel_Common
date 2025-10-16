// package renewal.common.entity;

// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Column;

// import lombok.Getter;
// import lombok.RequiredArgsConstructor;
// import lombok.Setter;

// @Entity
// @Getter
// @Setter
// @RequiredArgsConstructor
// public class SpecialRequest {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(nullable = false, unique = true)
//     private String requestType; //요청유형(휠체어, 추가수하물, 유아용좌석, 채식주의자, 의료지원 등등)

//     @Column(nullable = true)
//     private String description; //요청설명

//     public SpecialRequest(String newRequestType, String newDescription) {
//         requestType = newRequestType;
//         description = newDescription;
//     }

//     public void update(String newRequestType, String newDescription) {
//         requestType = newRequestType;
//         description = newDescription;
//     }
// }
