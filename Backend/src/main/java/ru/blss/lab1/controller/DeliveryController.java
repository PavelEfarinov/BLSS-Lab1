package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.domain.Orders;
import ru.blss.lab1.service.DeliveryService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/1")
public class DeliveryController extends ApiController {
    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("orders/new")
    public void addNewOrder(@RequestBody Orders orderInfo, HttpServletRequest request) {
        deliveryService.addNewOrder(getUser(request), orderInfo.getAddress(), orderInfo.getPaymentStatus());
    }

    @PostMapping("orders/update/status")
    public void updateOrderStatus(@RequestBody Orders orderUpdate) {
        deliveryService.updateOrderStatus(orderUpdate.getId(), orderUpdate.getOrderStatus());
    }

}
