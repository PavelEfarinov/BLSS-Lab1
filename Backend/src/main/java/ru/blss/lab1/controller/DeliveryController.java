package ru.blss.lab1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.domain.order.Order;
import ru.blss.lab1.exception.*;
import ru.blss.lab1.service.DeliveryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class DeliveryController extends ApiController {
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("free")
    public List<Order> getFreeOrders(@ModelAttribute("user") User user) throws UnauthorizedUserException, NoPermissionException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        return deliveryService.getFreeOrders(user.getId());
    }

    @GetMapping("assigned")
    public List<Order> getAssignedOrders(@ModelAttribute("user") User user) throws UnauthorizedUserException, NoPermissionException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        return deliveryService.getAssignedOrders(user.getId());
    }



    @PostMapping("update/status")
    public void updateOrderStatus(@RequestBody Order orderUpdate, @ModelAttribute("user") User user) throws UnauthorizedUserException, OrderNotFoundException {
        if(user == null)
        {
            throw new UnauthorizedUserException();
        }
        if (orderUpdate.getOrderStatus() == null) {
            throw new ValidationException("New order status should be provided");
        }
        deliveryService.updateOrderStatus(orderUpdate.getId(), orderUpdate.getOrderStatus());
    }

}
