package com.neuedu.service.impl;

import com.google.common.collect.Lists;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CartMapper;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICartService;
import com.neuedu.service.ICategoryService;
import com.neuedu.utils.BigDecimaUtils;
import com.neuedu.vo.CartProductVO;
import com.neuedu.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ICartServiceImpl implements ICartService
{

    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    /*
    * 购物车添加商品
    * */
    @Override
    public ServerResponse add(Integer userId,Integer productId, Integer count) {

        //参数校验
        if(productId==null||count==null)
        {
            return ServerResponse.createServerRespnseByError("参数不能为空");
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null)
        {
            return ServerResponse.createServerRespnseByError("要添加的商品不存在");
        }
        //根据productId和userId查询购物信息
        Cart cart = cartMapper.selectCarByuseridAndProductId(userId, productId);
        if(cart == null)
        {
            // 有业务需求的话，可以加逻辑判断商品是否存在
            /*Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null){*/
                //添加
                Cart cart1 = new Cart();
                cart1.setUserId(userId);
                cart1.setProductId(productId);
                cart1.setQuantity(count);
                cart1.setChecked(Const.CartCheckedEnum.PRODUCT_CHECKED.getCode());
                cartMapper.insert(cart1);
            //}

        }else
        {
            // 更新
            Cart cart1 = new Cart();
            cart1.setId(cart.getId());
            cart1.setQuantity(count);
            cart1.setProductId(productId);
            System.out.println(cart1.getProductId());
            cart1.setUserId(userId);
            cart1.setChecked(cart.getChecked());
            cartMapper.updateByPrimaryKey(cart1);

        }
        CartVO cartVOLimit = getCartVOLimit(userId);
        return ServerResponse.createServerRespnseBySucces(cartVOLimit);

    }

    private CartVO getCartVOLimit(Integer userId){
       CartVO cartVO = new CartVO();
       //根据用户ID查询购物信息
       List<Cart> carts = cartMapper.selectCarByuserid(userId);
       //cart转换cartvo
       List<CartProductVO> cartProductVOList  = Lists.newArrayList();

       //购物车总价格
       BigDecimal carttoalprice = new BigDecimal("0");

       if(carts!=null&&carts.size()>0)
       {
           for (Cart cart:carts) {
               CartProductVO cartProductVO = new CartProductVO();
               cartProductVO.setId(cart.getId());
               cartProductVO.setQuantity(cart.getQuantity());
               cartProductVO.setUserId(cart.getUserId());
               cartProductVO.setProductChecked(cart.getChecked());
               //查询商品
               Product product = productMapper.selectByPrimaryKey(cart.getProductId());
               if(product!=null) {
                   cartProductVO.setProductId(cart.getProductId());
                   cartProductVO.setProductMainImage(product.getMainImage());
                   cartProductVO.setProductName(product.getName());
                   cartProductVO.setProductPrice(product.getPrice());
                   cartProductVO.setProductStatus(product.getStatus());
                   cartProductVO.setProductStock(product.getStock());
                   cartProductVO.setProductSubtitle(product.getSubtitle());
                   int stock = product.getStock();
                   int limiProductCount=0;
                   if(stock>=cart.getQuantity()) {
                       limiProductCount=cart.getQuantity();
                       cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");
                   } else {
                       //s商品库存不足
                       limiProductCount=stock;
                       //跟新购物车中的数量
                       Cart cart1 = new Cart();
                       cart1.setId(cart.getId());
                       cart1.setQuantity(stock);
                       cart1.setChecked(cart.getChecked());
                       cart1.setUserId(cart.getUserId());
                       cart1.setProductId(cart.getProductId());
                       cartMapper.updateByPrimaryKey(cart1);

                    cartProductVO.setLimitQuantity("LIMIT_NUM_FAIL");
                   }
                   cartProductVO.setQuantity(limiProductCount);
                   cartProductVO.setProductTotalPrice(BigDecimaUtils.mul(product.getPrice().doubleValue(),Double.valueOf(cartProductVO.getQuantity())));

               }
               if (cartProductVO.getProductChecked()==Const.CartCheckedEnum.PRODUCT_CHECKED.getCode()) {
                   carttoalprice = BigDecimaUtils.add(carttoalprice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
               }
               cartProductVOList.add(cartProductVO);
           }

       }
       cartVO.setCartProductVOList(cartProductVOList);
       //计算总价格
       cartVO.setCarttotalprice(carttoalprice);
       //判断购物车是否全选
       int checkedAll = cartMapper.isCheckedAll(userId);
       if(checkedAll>0)
       {
           cartVO.setIsallchecked(false);
       }else
       {
           cartVO.setIsallchecked(true);
       }

       return cartVO;
    }
/**
 *
 * 购物车列表
 *
 */
    @Override
    public ServerResponse list(Integer userId) {
        CartVO cartVOLimit = getCartVOLimit(userId);

        return ServerResponse.createServerRespnseBySucces(cartVOLimit);
    }
/*
* 更新购物车数量
* */
    @Override
    public ServerResponse update(Integer userId,Integer productId, Integer count) {

        //非空判断
        if (productId==null||productId.equals(""))
        {
            return ServerResponse.createServerRespnseByError("参数不能为空");
        }
        //查询购物车中的商品
        Cart cart = cartMapper.selectCarByuseridAndProductId(userId, productId);
        if(cart!=null)
        {  //跟新数量
            cart.setQuantity(count);
            //更新购物车
            cartMapper.updateByPrimaryKey(cart);
        }

        //返回结果
        return ServerResponse.createServerRespnseBySucces(getCartVOLimit(userId ));
    }
/*
* 移除购物车商品
* */
    @Override
    public ServerResponse delete_product(Integer userId, String productIds) {

        //非空判断
        if(productIds==null||productIds.equals(""))
        {
            return ServerResponse.createServerRespnseByError("参数不能为空");
        }
        //字符串productIds转成集合
        List<Integer> productIdList=Lists.newArrayList();
        String[] productIdsArr = productIds.split(",");
        if(productIdsArr!=null&&productIdsArr.length>0)
        {
            for (String productIdsarr: productIdsArr) {
                int i = Integer.parseInt(productIdsarr);
                productIdList.add(i);
            }
        }
        //删除
       cartMapper.deleteByUseridAndProductId(userId, productIdList);
        //返回结果
        return ServerResponse.createServerRespnseBySucces(getCartVOLimit(userId));
    }
/*
*
* 购物车选中某个商品
* */
    @Override
    public ServerResponse select(Integer userId, Integer productId,Integer check) {
        //非空校验
       /* if(productId==null)
        {
            return ServerResponse.createServerRespnseByError("参数不能为空");
        }*/
        //Dao接口
        cartMapper.selectOrUnselectProduct(userId,productId,check);
        return ServerResponse.createServerRespnseBySucces(getCartVOLimit(userId));
    }
/*
*
* 查询购物车中产品数量
* */
    @Override
    public ServerResponse get_cart_product_count(Integer userId) {
        int cart_product_count = cartMapper.get_cart_product_count(userId);
        return ServerResponse.createServerRespnseBySucces(cart_product_count);
    }

}
