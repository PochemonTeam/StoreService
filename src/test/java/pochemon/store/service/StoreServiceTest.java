package pochemon.store.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import pochemon.dto.UserDTO;
import pochemon.service.CardWebService;
import pochemon.service.UserWebService;
import pochemon.store.entity.StoreOrder;
import pochemon.store.entity.StoreTransaction;
import pochemon.store.mapper.StoreOrderMapper;
import pochemon.store.mapper.StoreTransactionMapper;
import pochemon.store.repository.StoreOrderRepository;
import pochemon.store.repository.StoreTransactionRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
class StoreServiceTest {

    @MockBean
    private UserWebService userWebService;

    @MockBean
    private CardWebService cardWebService;

    @MockBean
    private StoreTransactionRepository storeTransactionRepository;

    @MockBean
    private StoreOrderRepository storeOrderRepository;
    
    @MockBean
    private StoreOrderMapper storeOrderMapper;
    
    @MockBean
    private StoreTransactionMapper storeTransactionMapper;

    @Autowired
    private StoreService storeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        storeService = new StoreService(storeOrderRepository, storeTransactionRepository);
    }

    @Test
    void testGetAllStoreTransactions() {
        // Mock the behavior of StoreTransactionRepository
        List<StoreTransaction> expectedTransactions = new ArrayList<>();
        when(storeTransactionRepository.findAll()).thenReturn(expectedTransactions);

        // Call the method to get all store transactions
        List<StoreTransaction> transactions = storeService.getAllStoreTransactions();

        // Verify the result
        Assertions.assertEquals(expectedTransactions, transactions);
        verify(storeTransactionRepository).findAll();
    }

    @Test
    void testGetAllStoreOrders() {
        // Mock the behavior of StoreOrderRepository
        List<StoreOrder> expectedOrders = new ArrayList<>();
        when(storeOrderRepository.findAll()).thenReturn(expectedOrders);

        // Call the method to get all store orders
        List<StoreOrder> orders = storeService.getAllStoreOrders();

        // Verify the result
        Assertions.assertEquals(expectedOrders, orders);
        verify(storeOrderRepository).findAll();
    }

    @Test
    void testSellCard_WithPositivePrice() {
        // Mock the behavior of StoreOrderRepository
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setPrice(10f);

        // Call the sellCard method
        boolean result = storeService.sellCard(storeOrder);

        // Verify the result
        Assertions.assertTrue(result);
        verify(storeOrderRepository).save(storeOrder);
    }

    @Test
    void testSellCard_WithNegativePrice() {
        // Mock the behavior of StoreOrderRepository
        StoreOrder storeOrder = new StoreOrder();
        storeOrder.setPrice(-10f);

        // Call the sellCard method
        boolean result = storeService.sellCard(storeOrder);

        // Verify the result
        Assertions.assertFalse(result);
        verifyNoInteractions(storeOrderRepository);
    }

    @Test
    void testBuyCard() {
        // Mock the behavior of StoreOrderRepository
        int cardId = 123;
        float price = 50f;
        int sellerUserId = 1;

        StoreOrder storeOrderToBuy = new StoreOrder();
        storeOrderToBuy.setCardId(cardId);
        storeOrderToBuy.setPrice(price);
        storeOrderToBuy.setUserId(sellerUserId);

        when(storeOrderRepository.findByCardId(cardId)).thenReturn(storeOrderToBuy);

        // Mock the behavior of UserWebService
        UserDTO sellerUser = new UserDTO();
        sellerUser.setId(sellerUserId);
    }
    
}

       

