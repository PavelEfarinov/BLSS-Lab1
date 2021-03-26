package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.domain.order.Order;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.exception.*;
import ru.blss.lab1.service.DeliveryService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class CourierController extends ApiController {
    private DeliveryService deliveryService;

    public CourierController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("courier")
    public void upgradeToCourierRole(HttpServletRequest request) throws UnauthorizedUserException, CourierAlreadyExistException {
        deliveryService.giveCourierRole(getUser(request));
    }

    @PostMapping("courier/order/choose")
    public void chooseOrder(@RequestBody Order orderInfo, HttpServletRequest request) throws UnauthorizedUserException, CourierAlreadyExistException {
        User user = getUser(request);
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        deliveryService.setOrderCourier(user.getId(), orderInfo.getId());
    }

    @PostMapping("courier/flight/new")
    public void addNewCarFlight(@RequestBody FlightInfoDTO flightInfo, HttpServletRequest request) throws UnauthorizedUserException, NoPermissionException, OrderNotFoundException {

        if (flightInfo.getAddress() == null || flightInfo.getAddress().isEmpty()) {
            throw new ValidationException("Delivery address should be provided");
        }
        if (flightInfo.getBegin() == null) {
            throw new ValidationException("Delivery departure status should be provided");
        }
        if (flightInfo.getEnd() == null) {
            throw new ValidationException("Delivery arrival status should be provided");
        }
        User user = getUser(request);
        if (user == null) {
            throw new UnauthorizedUserException();
        }

        deliveryService.addDeliveryCarFlight(flightInfo.getBegin(), flightInfo.getEnd(), user.getId(), flightInfo.getAddress());
    }

    static class FlightInfoDTO {
        private String address;
        private LocalDateTime begin;
        private LocalDateTime end;

        public LocalDateTime getBegin() {
            return begin;
        }

        public void setBegin(LocalDateTime begin) {
            this.begin = begin;
        }

        public LocalDateTime getEnd() {
            return end;
        }

        public void setEnd(LocalDateTime end) {
            this.end = end;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
