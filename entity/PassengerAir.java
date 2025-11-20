package renewal.common.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("AIR")
@Getter
@Setter
@NoArgsConstructor
public class PassengerAir extends Passenger {
}

