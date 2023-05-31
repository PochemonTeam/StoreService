package pochemon.store.service;

import java.time.Instant;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pochemon.store.repository.StoreTransactionRepository;
import pochemon.store.repository.StoreOrderRepository;

import pochemon.store.entity.StoreTransaction;
import pochemon.store.entity.StoreOrder;
import pochemon.enums.Action;

@Service
public class StoreService {

	@Autowired
	StoreTransactionRepository storeTransactionRepository;
	
	@Autowired
	StoreOrderRepository storeOrderRepository;
	
	public List<StoreTransaction> getAllStoreTransaction() {
		return storeTransactionRepository.findAll();
	}
	
	public Boolean sellCard(StoreOrder storeOrder) {
		if(storeOrder.getPrice() < 0f) {
			return false;
		}
		storeOrderRepository.save(storeOrder);
		return true;
	}
	
	public Boolean buyCard(StoreOrder storeOrder) {
		//TO DO REPRENDRE AVEC RPC ET LA LIB
		
		/*
		// Récupération de la carte à acheter
		StoreOrder storeOrderToBuy = storeOrderRepository.findByCardId(storeOrder.getCard().getId());

		// Utilisateur qui vend et achète la carte
		User sellerUser = storeOrderToBuy.getUser();
		User buyerUser = storeOrder.getUser();

		// Si l'utilisateur a assez d'argent pour acheter la carte
		if(userService.changeMoney(buyerUser, storeOrderToBuy.getPrice() * (-1))) {

			Card cardToSell = storeOrderToBuy.getCard();

			userService.changeMoney(sellerUser, storeOrderToBuy.getPrice());

			// Création des transactions
			StoreTransaction sellerStoreTransaction = createStoreTransaction(sellerUser,storeOrder.getCard(),Action.SELL);
			StoreTransaction buyerStoreTransaction = createStoreTransaction(storeOrder.getUser(), cardToSell, Action.BUY);

			cardToSell.setUser(buyerUser);
			//cardService.addCard(cardToSell);

			storeTransactionRepository.save(sellerStoreTransaction);
			storeTransactionRepository.save(buyerStoreTransaction);

			storeOrderRepository.delete(storeOrderToBuy);
		}
		return false;*/
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
