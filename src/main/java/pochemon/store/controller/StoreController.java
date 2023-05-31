package pochemon.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pochemon.dto.StoreOrderDTO;
import pochemon.dto.StoreTransactionDTO;
import pochemon.store.mapper.StoreOrderMapper;
import pochemon.store.mapper.StoreTransactionMapper;
import pochemon.store.service.StoreService;

import java.util.List;

@RestController
@RequestMapping("/stores")
@CrossOrigin
public class StoreController {

    @Autowired
    StoreService storeService;

    @Autowired
    StoreOrderMapper storeOrderMapper;

    @Autowired
    StoreTransactionMapper storeTransactionMapper;

    @PostMapping("/sell")
    public Boolean sellCard(@RequestBody StoreOrderDTO storeOrderDTO) {
        return storeService.sellCard(storeOrderMapper.toStoreOrder(storeOrderDTO));
    }

    @PostMapping("/buy")
    public boolean buyCard(@RequestBody StoreOrderDTO storeOrderDTO) {
        return storeService.buyCard(storeOrderMapper.toStoreOrder(storeOrderDTO));
    }

    @GetMapping("/transactions")
    public List<StoreTransactionDTO> getAllTransactions() {
        return storeTransactionMapper.toListStoreTransactionDTO(storeService.getAllStoreTransactions());
    }

    @GetMapping("/orders")
    public List<StoreOrderDTO> getAllStoreOrders() {
        return storeOrderMapper.toListStoreOrderDTO(storeService.getAllStoreOrders());
    }
}
