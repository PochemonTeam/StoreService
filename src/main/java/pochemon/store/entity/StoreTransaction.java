package pochemon.store.entity;

import java.util.Date;
import pochemon.enums.Action;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StoreTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	Integer userId;

	Integer cardId;

	Action action;
	
	Date timeSt;
	
}
