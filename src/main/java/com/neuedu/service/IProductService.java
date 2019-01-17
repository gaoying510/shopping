package com.neuedu.service;

import com.neuedu.pojo.Product;
import com.neuedu.common.ServerResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface IProductService {

    /*
    * 新增或跟新商品
    * */
    ServerResponse saveOrUpdate(Product product);

    /*
    *
    * 产品上下架
    * */

    ServerResponse set_sale_status(Integer productId,Integer status);
    /*
    *
    * 查看商品详情
    * */

    ServerResponse detail(Integer productId);

    /*
    *
    * 查询商品列表，分页
    * */
    ServerResponse list(Integer pageNum,Integer pageSize);

    /**
     *
     * 商品搜索
     *
     */
    ServerResponse search(Integer productId,String productName,Integer pageNum,Integer pageSize);


    /*
    *
    * 上传图片
    * */
    ServerResponse upload(MultipartFile file ,String path);
/*
*
* 前台查看商品
* */
    ServerResponse detail_portal(Integer productId);

    /*
    * 前台商品搜索
    * */

    ServerResponse list_portal(Integer categoryId,String keyword,Integer pageNum,Integer pageSize,String orderBy);

}
