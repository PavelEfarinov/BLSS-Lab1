package ru.blss.lab1.controller;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.domain.*;
import ru.blss.lab1.service.DeliveryService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api/1")
public class DeliveryController extends ApiController{
    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("courier")
    public void upgradeToCourierRole(HttpServletRequest request) {
        deliveryService.giveCourierRole(getUser(request));
    }

    @PostMapping("courier/orders/choose")
    public void chooseOrder(long courierId, long orderId) {
        deliveryService.setOrderCourier(courierId, orderId);
    }

    @PostMapping("orders/new")
    public void addNewOrder(@RequestBody OrderInfoDTO orderInfo) {
        deliveryService.addNewOrder(orderInfo.getAddress(), orderInfo.getPaymentStatus());
    }

    @PostMapping("orders/update/status")
    public void updateOrderStatus(long id, String status) {
        deliveryService.updateOrderStatus(id, status);
    }

    @PostMapping("courier/flight/new")
    public void addNewCarFlight(@RequestBody FlightInfoDTO flightInfo) {
        deliveryService.addDeliveryCarFlight(flightInfo.getBegin(), flightInfo.getEnd(), flightInfo.getCourierId());
    }
}

class OrderInfoDTO{
    private String address;
    private String orderStatus;
    private String paymentStatus;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}

class FlightInfoDTO {
    private long courierId;
    private Timestamp begin;
    private Timestamp end;

    public long getCourierId() {
        return courierId;
    }

    public void setCourierId(long courierId) {
        this.courierId = courierId;
    }

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
}
