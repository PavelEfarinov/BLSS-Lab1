package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.*;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.domain.order.Order;
import ru.blss.lab1.exception.*;
import ru.blss.lab1.service.DeliveryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DeliveryController extends ApiController {
    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("order/free")
    public List<Order> getFreeOrders(@ModelAttribute("user") User user) throws UnauthorizedUserException, NoPermissionException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        return deliveryService.getFreeOrders(user.getId());
    }

    @GetMapping("order/assigned")
    public List<Order> getAssignedOrders(@ModelAttribute("user") User user) throws UnauthorizedUserException, NoPermissionException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        return deliveryService.getAssignedOrders(user.getId());
    }

    @PostMapping("order/new")
    public void addNewOrder(@RequestBody Order orderInfo, @ModelAttribute("user") User user) throws CartItemNotFoundException, UnauthorizedUserException {
        if(orderInfo.getAddress() == null || orderInfo.getAddress().isEmpty())
        {
            throw new ValidationException("Order address should be provided");
        }
        if (orderInfo.getPaymentStatus() == null) {
            throw new ValidationException("Order payment status should be provided");
        }
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        deliveryService.addNewOrder(user, orderInfo);
    }

    @PostMapping("order/update/status")
    public void updateOrderStatus(@RequestBody Order orderUpdate, @ModelAttribute("user") User user) throws UnauthorizedUserException, OrderNotFoundException {
        if(user == null)
        {
            throw new UnauthorizedUserException();
        }
        if (orderUpdate.getOrderStatus() == null) {
            throw new ValidationException("New order status should be provided");
        }
        deliveryService.updateOrderStatus(orderUpdate.getId(), orderUpdate.getOrderStatus().name());
    }

}
