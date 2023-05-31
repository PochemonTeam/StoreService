package pochemon.store.mapper;

import pochemon.dto.StoreOrderDTO;

import java.util.List;

import org.mapstruct.Mapper;

import pochemon.store.entity.StoreOrder;

@Mapper(componentModel = "spring")
public interface StoreOrderMapper {
	
	public StoreOrderDTO toStoreOrderDTO(StoreOrder storeOrder);
	
	public StoreOrder toStoreOrder(StoreOrderDTO storeOrderDTO);
	
	public List<StoreOrderDTO> toListStoreOrderDTO(List<StoreOrder> storeOrder);

}	
