package com.driver;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String,Order>orderMap= new HashMap<>();
    HashMap<String,String>orderDeliveryPair= new HashMap<>();
    HashMap<String,DeliveryPartner>deliveryPartnerMap= new HashMap<>();
    HashMap<String, List<Order>>deliveryListMap= new HashMap<>();

    public void addOrder(Order order){
        orderMap.put(order.getId(),order);
    }

    public void addPartner(DeliveryPartner deliveryPartner){
        deliveryPartnerMap.put(deliveryPartner.getId(),deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId ,String partnerId){
        orderDeliveryPair.put(orderId,partnerId);
        if(!deliveryListMap.containsKey(partnerId)){
            List<Order>temp = new ArrayList<>();
            temp.add(orderMap.get(orderId));
            deliveryListMap.put(partnerId,temp);
            deliveryPartnerMap.get(partnerId).setNumberOfOrders(temp.size());
        }
        else{
            List<Order>temp = deliveryListMap.get(partnerId);
            temp.add(orderMap.get(orderId));
            deliveryListMap.put(partnerId,temp);
            deliveryPartnerMap.get(partnerId).setNumberOfOrders(temp.size());
        }

    }

    public Order getOrderById(String orderId){
        return orderMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return deliveryPartnerMap.get(partnerId);
    }

    public int getOrderCountByPartnerId(String partnerId){
        List<Order>temp = deliveryListMap.get(partnerId);
        return temp.size();
    }

    public List<Order> getOrdersByPartnerId(String partnerId){
       return deliveryListMap.get(partnerId);
    }

    public List<Order>getAllOrders(){
        List<Order>temp = new ArrayList<>();
        for(String id :orderMap.keySet()){
            temp.add(orderMap.get(id));
        }
        return temp;
    }

    public int getCountOfUnassignedOrders(){
        int count=0;
        for(String id:orderMap.keySet()){
            if(!orderDeliveryPair.containsKey(id)){
                count++;
            }
        }
        return count;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        int HH = Integer.parseInt(time.substring(0,2));
        int MM = Integer.parseInt(time.substring(3,5));
        int delTime  = HH*60 + MM;

        int count =0;
        List<Order>temp = deliveryListMap.get(partnerId);
        for(Order order :temp){
            if(order.getDeliveryTime()>delTime){
                count++;
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        int time =0;
        List<Order>temp = deliveryListMap.get(partnerId);
        for(Order order :temp){
            if(order.getDeliveryTime()>time){
                time=order.getDeliveryTime();
            }
        }
        int hh = time/60;
        String HH ="";
        if(hh<10)HH = '0'+Integer.toString(hh);
        else HH = Integer.toString(hh);

        int mm = time%60;
        String MM = ":";
        if(mm<10)MM=MM+'0'+Integer.toString(mm);
        else MM=MM+Integer.toString(mm);

        String LastTime =HH+MM;
        return LastTime;
    }

    public void deletePartnerById(String partnerId){
        List<Order>temp = deliveryListMap.get(partnerId);
        deliveryListMap.remove(partnerId);
        deliveryPartnerMap.remove(partnerId);
        for(Order order : temp){
            if(orderDeliveryPair.containsKey(order.getId())){
                orderDeliveryPair.remove(order.getId());
            }
        }
    }

    public void deleteOrderById(String orderId){
        orderMap.remove(orderId);
        String tempId = orderDeliveryPair.get(orderId);
        List<Order>orders = deliveryListMap.get(tempId);
        for(Order order :orders){
            if(order.getId().equals(orderId)){
                orders.remove(order);
            }
        }
        deliveryListMap.put(tempId,orders);
        orderDeliveryPair.remove(orderId);
    }




}
