package com.neuedu.schedule;

import com.neuedu.service.IOrderService;
import com.neuedu.utils.PropertiesUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
* 定时关闭订单
* */
@Component
public class CloseOrder {

    @Autowired
    IOrderService iOrderService;
    @Scheduled(cron = "0 */1 * * * *")
    public void choseOrder(){
        System.out.println("----关闭订单----");
        Integer hour = Integer.parseInt(PropertiesUtils.readByKey("close.order.time"));
        //拿到当前时间，
        String date= com.neuedu.utils.DateUtils.dateToStr(DateUtils.addHours(new Date(),-hour));
        iOrderService.choseOrder(date);

    }
}
