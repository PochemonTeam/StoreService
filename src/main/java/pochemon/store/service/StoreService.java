package pochemon.store.service;

import java.time.Instant;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pochemon.dto.CardDTO;
import pochemon.dto.UserDTO;
import pochemon.service.CardWebService;
import pochemon.service.UserWebService;
import pochemon.store.repository.StoreTransactionRepository;
import pochemon.store.repository.StoreOrderRepository;

import pochemon.store.entity.StoreTransaction;
import pochemon.store.entity.StoreOrder;
import pochemon.enums.Action;

@Service
public class StoreService {

	@Autowired
	UserWebService userWebService;

	@Autowired
	CardWebService cardWebService;

	@Autowired
	StoreTransactionRepository storeTransactionRepository;
	
	@Autowired
	StoreOrderRepository storeOrderRepository;
	
	public List<StoreTransaction> getAllStoreTransactions() {
		return storeTransactionRepository.findAll();
	}

	public List<StoreOrder> getAllStoreOrders() {
		return storeOrderRepository.findAll();
	}

	public Boolean sellCard(StoreOrder storeOrder) {
		if(storeOrder.getPrice() < 0f) {
			return false;
		}
		storeOrderRepository.save(storeOrder);
		return true;
	}
	
	public Boolean buyCard(StoreOrder storeOrder) {
		// Récupération de la carte à acheter
		StoreOrder storeOrderToBuy = storeOrderRepository.findByCardId(storeOrder.getCardId());

		// Utilisateur qui vend et achète la carte
		UserDTO sellerUser = userWebService.getUser(storeOrderToBuy.getUserId());
		UserDTO buyerUser = userWebService.getUser(storeOrder.getUserId());

		// Si l'utilisateur a assez d'argent pour acheter la carte
		if(userWebService.changeMoney(buyerUser, storeOrderToBuy.getPrice() * (-1))) {

			CardDTO cardToSell = cardWebService.getCard(storeOrderToBuy.getCardId());

			userWebService.changeMoney(sellerUser, storeOrderToBuy.getPrice());

			// Création des transactions
			StoreTransaction sellerStoreTransaction = createStoreTransaction(sellerUser.getId(),storeOrder.getCardId(),Action.SELL);
			StoreTransaction buyerStoreTransaction = createStoreTransaction(storeOrder.getUserId(), cardToSell.getId(), Action.BUY);

			cardToSell.setUserId(buyerUser.getId());
			//cardService.addCard(cardToSell);

			storeTransactionRepository.save(sellerStoreTransaction);
			storeTransactionRepository.save(buyerStoreTransaction);

			storeOrderRepository.delete(storeOrderToBuy);
		}
		return false;
		
	}
	
	private StoreTransaction createStoreTransaction(Integer userId, Integer cardId, Action action) {
		StoreTransaction newStoreTransaction = new StoreTransaction();
		
		newStoreTransaction.setCardId(cardId);
		newStoreTransaction.setTimeSt(Date.from(Instant.now()));
		newStoreTransaction.setUserId(userId);
		newStoreTransaction.setAction(action);
		
		return newStoreTransaction;
	}
	
}
