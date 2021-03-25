package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.domain.Orders;
import ru.blss.lab1.exception.UnauthorizedUserException;
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
    public void chooseOrder(@RequestBody Orders orderInfo, HttpServletRequest request) {
        deliveryService.setOrderCourier(getUser(request).getId(), orderInfo.getId());
    }

    @PostMapping("courier/flight/new")
    public void addNewCarFlight(@RequestBody FlightInfoDTO flightInfo, HttpServletRequest request) {
        deliveryService.addDeliveryCarFlight(flightInfo.getBegin(), flightInfo.getEnd(), getUser(request).getId(), flightInfo.getAddress());
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
