package org.example.demo.controller;

import org.example.demo.service.PurchaseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @RequestMapping(path = "/reserve/{userId}/{productId}")
    public boolean reserve(@PathVariable final String userId,
                           @PathVariable final String productId) {
        return purchaseService.reserved(userId, productId);
    }

    @RequestMapping(path = "/cancel/{userId}")
    public boolean cancelReserve(@PathVariable final String userId) {
        return purchaseService.cancelReserve(userId);
    }

    @RequestMapping(path = "/buy/{userId}")
    public boolean buyReserve(@PathVariable final String userId) {
        return purchaseService.buy(userId);
    }

}
