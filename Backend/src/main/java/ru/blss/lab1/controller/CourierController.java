package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.domain.Orders;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.exception.*;
import ru.blss.lab1.service.DeliveryService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api/1")
public class CourierController extends ApiController {
    private DeliveryService deliveryService;

    public CourierController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("courier")
    public void upgradeToCourierRole(HttpServletRequest request) throws UnauthorizedUserException {
        deliveryService.giveCourierRole(getUser(request));
    }

    @PostMapping("courier/orders/choose")
    public void chooseOrder(@RequestBody Orders orderInfo, HttpServletRequest request) throws UnauthorizedUserException, CourierAlreadyExistException {
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
        private Timestamp begin;
        private Timestamp end;

        public Timestamp getBegin() {
            return begin;
        }

        public void setBegin(Timestamp begin) {
            this.begin = begin;
        }

        public Timestamp getEnd() {
            return end;
        }

        public void setEnd(Timestamp end) {
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
