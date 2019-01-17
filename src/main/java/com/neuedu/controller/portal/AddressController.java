package com.neuedu.controller.portal;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IAddressSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value ="/shipping")
public class AddressController {
    /*
     * 添加收货地址
     * */
    @Autowired
    IAddressSerive iAddressSerive;

    @RequestMapping(value = "/add.do")
    public ServerResponse add(HttpSession session, Shipping shipping){
        UserInfo userInfo =(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户未登录");
        }
        return  iAddressSerive.add(userInfo.getId(),shipping);
    }

    /*
     * 删除收货地址
     * */

    @RequestMapping(value = "/del.do")
    public ServerResponse del(HttpSession session, Integer shippingId){
        UserInfo userInfo =(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户未登录");
        }
        return  iAddressSerive.del(userInfo.getId(),shippingId);
    }

    /*
    * 登录状态更新地址
    * */
    @RequestMapping(value = "/update.do")
    public ServerResponse update(HttpSession session, Shipping shipping){
        UserInfo userInfo =(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户未登录");
        }
        shipping.setUserId(userInfo.getId());
        return  iAddressSerive.update(shipping);
    }
    /*
     * 选中查看具体地址
     * */
    @RequestMapping(value = "/select.do")
    public ServerResponse select(HttpSession session, Integer shippingId){
        UserInfo userInfo =(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户未登录");
        }

        return  iAddressSerive.select(shippingId);
    }
    /*
     * 地址列表
     * */
    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session, @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize   ){
        UserInfo userInfo =(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null)
        {
            return ServerResponse.createServerRespnseByError("用户未登录");
        }

        return  iAddressSerive.list(pageNum,pageSize);
    }
}
