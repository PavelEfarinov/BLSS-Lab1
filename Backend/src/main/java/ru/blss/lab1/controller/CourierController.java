package ru.blss.lab1.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.domain.order.Order;
import ru.blss.lab1.exception.*;
import ru.blss.lab1.service.DeliveryService;
import ru.blss.lab1.service.UserService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/courier")
public class CourierController extends ApiController {
    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private UserService userService;

    @PostMapping("order/choose")
    public void chooseOrder(@RequestBody Order orderInfo, @ModelAttribute("user") User user) throws UnauthorizedUserException, CourierAlreadyExistException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        deliveryService.setOrderCourier(user.getId(), orderInfo.getId());
    }

    @PostMapping("flight/new")
    public void addNewCarFlight(@RequestBody FlightInfoDTO flightInfo, @ModelAttribute("user") User user) throws UnauthorizedUserException, NoPermissionException, OrderNotFoundException {

        if (flightInfo.getAddress() == null || flightInfo.getAddress().isEmpty()) {
            throw new ValidationException("Delivery address should be provided");
        }
        if (flightInfo.getBegin() == null) {
            throw new ValidationException("Delivery departure status should be provided");
        }
        if (flightInfo.getEnd() == null) {
            throw new ValidationException("Delivery arrival status should be provided");
        }
        if (user == null) {
            throw new UnauthorizedUserException();
        }

        deliveryService.addDeliveryCarFlight(flightInfo.getBegin(), flightInfo.getEnd(), user.getId(), flightInfo.getAddress());
    }

    @Data
    static class FlightInfoDTO {
        private String address;
        private LocalDateTime begin;
        private LocalDateTime end;
    }
}
