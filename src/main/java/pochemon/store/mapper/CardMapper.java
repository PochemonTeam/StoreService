package pochemon.store.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.CardDTO;
import com.entity.Card;
import com.entity.User;
import com.service.UserService;

@Mapper(componentModel = "spring", uses = { UserMapper.class, UserService.class })
public interface CardMapper {

	@Mapping(target = "userId", source = "user.id")
	public CardDTO toCardDTO(Card card);
	
	public Card toCard(CardDTO cardDTO);
	
	@Mapping(target = "userId", source = "user.id")
	public List<CardDTO> toCardDTO(List<Card> card);
	
	public List<Card> toCard(List<CardDTO> cardDTO);
	
	default User map(Integer userId, @Context UserService userService, @Context UserMapper userMapper) {
	       return userMapper.toUser(userService.getUserById(userId));
	}
	
}
