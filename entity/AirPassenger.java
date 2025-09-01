package renewal.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class AirPassenger extends BasePassenger {

    @ManyToOne
    @JoinColumn(name = "air_purchase_id", nullable = false)
    private AirPurchase airPurchase;

    //요구사항(기내식, 좌석, 수하물 등등) 추후 enum이나 엔티티로 변경
    @ManyToMany
    @JoinTable(
            name = "air_passenger_special_requests",
            joinColumns = @JoinColumn(name = "air_passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "special_request_id")
    )
    private Set<SpecialRequest> specialRequests = new HashSet<>(); //요구사항

    public AirPassenger(AirPurchase airPurchase, String name, String number, String email, LocalDate birth, Sex sex, CountryCode nationality, String passportNum, String lastName, String firstName, LocalDate expire) {
        super(name, number, email, birth, sex, nationality, passportNum, lastName, firstName, expire);
        this.airPurchase = airPurchase;
    }

    // SpecialRequest 설정
    public void addSpecialRequests(Set<SpecialRequest> specialRequests) {
        if (specialRequests != null) {
            this.specialRequests.addAll(specialRequests);
        }
    }

    public void updateAirPurchase(){

    }

}
