package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }

    public void addPartner(DeliveryPartner deliveryPartner){
        orderRepository.addPartner(deliveryPartner);
    }

    public void addOrderPartnerPair(String oId,String pId){
        orderRepository.addOrderPartnerPair(oId, pId);
    }

    public Order getOrderById(String oId){
       return orderRepository.getOrderById(oId);
    }

    public DeliveryPartner getPartnerById(String pId){
        return orderRepository.getPartnerById(pId);
    }

    public int getOrderCountByPartnerId(String pId){
        return orderRepository.getOrderCountByPartnerId(pId);
    }

    public List<Order> getOrdersByPartnerId(String pId){
        return orderRepository.getOrdersByPartnerId(pId);
    }

    public List<Order>getAllOrders(){
        return orderRepository.getAllOrders();
    }

    public int getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String pId){
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,pId);
    }

    public String getLastDeliveryTimeByPartnerId (String pId){
        return orderRepository.getLastDeliveryTimeByPartnerId(pId);
    }

    public void deletePartnerById(String pId){
         orderRepository.deletePartnerById(pId);
    }

    public void deleteOrderById(String oId){
        orderRepository.deleteOrderById(oId);
    }
}
