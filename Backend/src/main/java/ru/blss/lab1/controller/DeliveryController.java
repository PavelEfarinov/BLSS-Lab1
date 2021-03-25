package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.domain.Orders;
import ru.blss.lab1.exception.CartItemNotFoundException;
import ru.blss.lab1.exception.OrderNotFoundException;
import ru.blss.lab1.exception.UnauthorizedUserException;
import ru.blss.lab1.exception.ValidationException;
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
    public void addNewOrder(@RequestBody Orders orderInfo, HttpServletRequest request) throws CartItemNotFoundException {
        if(orderInfo.getAddress() == null || orderInfo.getAddress().isEmpty())
        {
            throw new ValidationException("Order address should be provided");
        }
        if(orderInfo.getPaymentStatus() == null || orderInfo.getPaymentStatus().isEmpty())
        {
            throw new ValidationException("Order payment status should be provided");
        }
        deliveryService.addNewOrder(getUser(request), orderInfo);
    }

    @PostMapping("orders/update/status")
    public void updateOrderStatus(@RequestBody Orders orderUpdate, HttpServletRequest request) throws UnauthorizedUserException, OrderNotFoundException {
        if(getUser(request) == null)
        {
            throw new UnauthorizedUserException();
        }
        if(orderUpdate.getOrderStatus() == null)
        {
            throw new ValidationException("New order status should be provided");
        }
        deliveryService.updateOrderStatus(orderUpdate.getId(), orderUpdate.getOrderStatus());
    }

}
