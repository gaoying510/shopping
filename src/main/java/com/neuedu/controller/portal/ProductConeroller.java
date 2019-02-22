package com.neuedu.controller.portal;


import com.neuedu.common.ServerResponse;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.awt.SunHints;

@RestController
@RequestMapping(value = "/product")
/*
*
* 商品详情
* */
public class ProductConeroller {


@Autowired
IProductService productService;


    @RequestMapping(value = "/detail.do/productId/{productId}")
    public ServerResponse detail(@PathVariable("productId") Integer productId)
    {
        return productService.detail_portal(productId);
    }


    /**
     *前台搜索商品并排序
     */

    @RequestMapping(value = "/list.do/categoryId/{categoryId}/{pageNum}/{pageSize}/{orderBy}")
    public ServerResponse listCategoryId(@PathVariable("categoryId") Integer categoryId,

                                        @PathVariable("pageNum") Integer pageNum,
                                         @PathVariable("pageSize")Integer pageSize,
                                         @PathVariable("orderBy")String orderBy)
        {

            return productService.list_portal(categoryId,null,pageNum,pageSize,orderBy);
    }
    @RequestMapping(value = "/list.do/keyword/{keyword}/{pageNum}/{pageSize}/{orderBy}")
    public ServerResponse listKeyword(@PathVariable("keyword") String keyword,

                                      @PathVariable("pageNum") Integer pageNum,
                                      @PathVariable("pageSize")Integer pageSize,
                                      @PathVariable("orderBy")String orderBy) {
        {

            return productService.list_portal(null, keyword, pageNum, pageSize, orderBy);
        }
    }
}

