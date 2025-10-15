package renewal.common.entity;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@lombok.Setter
@NoArgsConstructor
public class PassengerProduct extends PassengerBase{

    @ManyToOne
    @JoinColumn(name = "product_purchase_id", nullable = false)
    private PurchaseProduct productPurchase;

    //요구사항(객실, 투어옵션, 여행자보험, 룸메이트 등등) 추후 enum이나 엔티티로 변경
    @ManyToMany
    @JoinTable(
            name = "product_passenger_special_requests",
            joinColumns = @JoinColumn(name = "product_passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "special_request_id")
    )
    private Set<SpecialRequest> specialRequests = new HashSet<>(); //요구사항

    public PassengerProduct(PurchaseProduct productPurchase, String name, String number, String email, LocalDate birth, Sex sex, CountryCode nationality, String passportNum, String lastName, String firstName, LocalDate expire) {
        super(name, number, email, birth, sex, nationality, passportNum, lastName, firstName, expire);
        this.productPurchase = productPurchase;
    }

    // SpecialRequest 설정
    public void addSpecialRequests(Set<SpecialRequest> specialRequests) {
        if (specialRequests != null) {
            this.specialRequests.addAll(specialRequests);
        }
    }
    
    // setPurchaseProduct 메서드
    public void setPurchaseProduct(PurchaseProduct purchase) {
        this.productPurchase = purchase;
    }
    
    // updateInfo 메서드
    public void updateInfo(renewal.awesome_travel_backoffice.productPurchase.dto.request.ProductPassengerUpdateRequestDto updateRequest, CountryCode newCountry, Object unused) {
        if (updateRequest.getName() != null) this.name = updateRequest.getName();
        if (updateRequest.getNumber() != null) this.number = updateRequest.getNumber();
        if (updateRequest.getEmail() != null) this.email = updateRequest.getEmail();
        if (updateRequest.getBirth() != null) this.birth = updateRequest.getBirth();
        if (updateRequest.getSex() != null) this.sex = Sex.valueOf(updateRequest.getSex().toLowerCase());
        if (newCountry != null) this.nationality = newCountry;
        if (updateRequest.getPassportNum() != null) this.passportNum = updateRequest.getPassportNum();
        if (updateRequest.getLastName() != null) this.lastName = updateRequest.getLastName();
        if (updateRequest.getFirstName() != null) this.firstName = updateRequest.getFirstName();
        if (updateRequest.getExpire() != null) this.expire = updateRequest.getExpire();
    }
}
