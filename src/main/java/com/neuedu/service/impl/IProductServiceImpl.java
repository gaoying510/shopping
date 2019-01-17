package com.neuedu.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.neuedu.common.Const;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.common.ServerResponse;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import com.neuedu.utils.DateUtils;
import com.neuedu.utils.FTPUtil;
import com.neuedu.utils.PropertiesUtils;
import com.neuedu.vo.ProductDetailVO;
import com.neuedu.vo.ProdutListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


@Service
public class IProductServiceImpl implements IProductService
{

    @Autowired
    ProductMapper productMapper;
    @Autowired

    CategoryMapper categoryMapper;
    @Autowired
    ICategoryService iCategoryService;

    /*
     * 新增或更新商品
     * */
    @Override
    public ServerResponse saveOrUpdate(Product product) {

        //非空判断
       if(product==null)
       {
           return ServerResponse.createServerRespnseByError("商品不能为空");
       }
        //设置商品主图
        //拿到子图
         String subImages= product.getSubImages();
       //判断
       if(subImages!=null&&!subImages.equals(""))
       {
           //逗号隔开
           String[] split = subImages.split(",");
           if (split.length>0)
           {
               //把子图第一张图传到主图
               product.setMainImage(split[0]);
           }
       }

        //商品更新和修改

        if(product.getId()==null)
        {
            //添加
            int insert = productMapper.insert(product);
            if (insert>0)
            {
                return ServerResponse.createServerRespnseBySucces("添加成功");
            }else
            {
                return ServerResponse.createServerRespnseByError("添加失败");
            }
        }else
        {
            //跟新
            int i = productMapper.updateByPrimaryKey(product);
            if(i>0)
            {
                return ServerResponse.createServerRespnseBySucces("更新成功");
            }else
            {
                return ServerResponse.createServerRespnseByError("更新失败");
            }
        }

        //返回结果

    }
    /*
    *
    * 商品上下架
    * */

    @Override
    public ServerResponse set_sale_status(Integer productId, Integer status) {

        //非空判断
        if(productId==null)
        {
            return ServerResponse.createServerRespnseByError("商品ID不能为空");
        }
        if(status==null)
        {
            return ServerResponse.createServerRespnseByError("状态不能为空");
        }
        //更新商品状态
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int i = productMapper.updateProductKeySelective(product);


        //返回结果
        if(i>0)
        {
            return ServerResponse.createServerRespnseBySucces("更新成功");
        }else
        {
            return ServerResponse.createServerRespnseByError("更新失败");
        }
    }
/*
*
* 查看商品详情
* */

    @Override

    public ServerResponse detail(Integer productId) {

        //非空判断
        if(productId==null)
        {
            return ServerResponse.createServerRespnseByError("商品ID不能为空");
        }

        //根据商品Id查询
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null)
        {
            return ServerResponse.createServerRespnseByError("商品不存在");
        }

        //product转换成ProductDetailVO
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);

        //返回结果

        return ServerResponse.createServerRespnseBySucces(productDetailVO);
    }



    private ProductDetailVO assembleProductDetailVO(Product product) {

        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setImageHost(PropertiesUtils.readByKey("imageHost"));
        productDetailVO.setName(product.getName());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setId(product.getId());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
         if(category!=null)
         {
              productDetailVO.setParentCategoryId(category.getParentId());
         }else
         {
             productDetailVO.setParentCategoryId(0);
         }
        //设置商品的父类ID




        return productDetailVO;
    }

/*
*
* 查询商品列表，分页
* */
    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        //商品查询数据
        List<Product> products = productMapper.selectAll();

        List<ProdutListVO> produtListVOS = Lists.newArrayList();
        if(products!=null&&products.size()>0)

        {
            for (Product product:products) {
                ProdutListVO produtListVO= assembleProutListVO(product);
                produtListVOS.add(produtListVO);
            }
        }
        PageInfo pageInfo=new PageInfo(produtListVOS);
        return ServerResponse.createServerRespnseBySucces(pageInfo);
    }


    private ProdutListVO assembleProutListVO(Product product)
    {
        ProdutListVO produtListVO = new ProdutListVO();
        produtListVO.setId(product.getId());
        produtListVO.setCategoryId(product.getCategoryId());
        produtListVO.setMainImage(product.getMainImage());
        produtListVO.setName(product.getName());
        produtListVO.setPrice(product.getPrice());
        produtListVO.setStatus(product.getStatus());
        produtListVO.setSubtitle(product.getSubtitle());

        return produtListVO;
    }
/*
*
* 商品搜索
* */

    @Override
    public ServerResponse search(Integer productId, String productName, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        if (productName != null && !productName.equals(""))
        {
            productName="%"+productName+"%";
        }
        List<Product> productByProductIdAnductName = productMapper.findProductByProductIdAnductName(productId, productName);
        List<ProdutListVO> produtListVOS = Lists.newArrayList();
        if(productByProductIdAnductName!=null&&productByProductIdAnductName.size()>0)

        {
            for (Product product:productByProductIdAnductName) {
                ProdutListVO produtListVO= assembleProutListVO(product);
                produtListVOS.add(produtListVO);
            }
        }
        PageInfo pageInfo=new  PageInfo(produtListVOS);

        return ServerResponse.createServerRespnseBySucces(pageInfo);
    }
    /*
     * 上传图片
     * */
    @Override
    public ServerResponse upload(MultipartFile file, String path) {

        //非空判断
        if(file==null)
        {
            return ServerResponse.createServerRespnseByError();
        }
        //获取图片名称
        String originalFilename = file.getOriginalFilename();
        //如果不等于空修改图片名字
        String substr = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成唯一名字
        String NewFileName = UUID.randomUUID().toString() + substr;
        File file1 = new File(path);
        if(!file1.exists())
        {
            file1.setWritable(true);
            file1.mkdirs();
        }

        File file2 = new File(path,NewFileName);
        try {
            // 上传成功
            file.transferTo(file2);
            boolean b = FTPUtil.uploadFile(Lists.newArrayList(file2));
            if (b){
                Map<String,String> map= Maps.newHashMap();
                map.put("uri",NewFileName);
                map.put("url",PropertiesUtils.readByKey("imageHost")+"/"+NewFileName);
                //从本地删除应用服务器上的图片
                file2.delete();
                return ServerResponse.createServerRespnseBySucces(map);
            }else {
                return ServerResponse.createServerRespnseByError("文件上传失败");
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return ServerResponse.createServerRespnseByError("上传失败") ;
    }
/*
*
* 前台查看商品详情
* */
    @Override
    public ServerResponse detail_portal(Integer productId) {
        //参数校验
        if(productId==null)
        {
            return ServerResponse.createServerRespnseByError("商品ID不能为空");
        }

        //根据商品ID查询商品
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null)
        {
            return ServerResponse.createServerRespnseByError("商品不存在");
        }

        //校验商品状态
        if(product.getStatus()!= Const.ProductStatusEnum.PRODUCT_ONLINE.getCode())
        {
            return ServerResponse.createServerRespnseByError("商品已下架或删除");
        }

        //获取productDetailVO
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);

        //返回结果

        return ServerResponse.createServerRespnseBySucces(productDetailVO);
    }


    /*
    *
    * 前台搜索商品
    * */
    @Override
    public ServerResponse list_portal(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
       //参数校验
        if(categoryId==null&&(keyword==null||keyword.equals(""))){
            return ServerResponse.createServerRespnseByError("参数错误");
        }
       //根据categoryId查询
        Set<Integer> integerSet = Sets.newHashSet();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && (keyword == null || keyword.equals(""))) {
                //说明商品没有数据
                PageHelper.startPage(pageNum, pageSize);
                List<ProdutListVO> produtListVOS = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(produtListVOS);
                return ServerResponse.createServerRespnseBySucces(pageInfo);
            }
            ServerResponse deep_category = iCategoryService.get_deep_category(categoryId);

            if (deep_category.isSuccess()) {
                integerSet = (Set<Integer>) deep_category.getData();
            }
        }

            if (keyword!=null&&!keyword.equals(""))
            {
                keyword="%"+keyword+"%";
            }else{
                keyword=null;
            }
            if(orderBy.equals(""))
            {
                PageHelper.startPage(pageNum,pageSize);
            }else
            {
                //orderby = 字段_排序方式
                String[] s = orderBy.split("_");
                //s[0]=字段 s[1]=排序方式
                if(s.length>1)
                {
                    // PageHelper.startPage(pageNum,pageSize);
                    // PageHelper.orderBy();//price desc 传过来的值是price_desc
                    PageHelper.startPage(pageNum,pageSize,s[0]+" "+s[1]);
                }else{
                    PageHelper.startPage(pageNum,pageSize);
                }

            }

            List<Product> products = productMapper.searchProudct(integerSet, keyword);
            List<ProdutListVO> produtListVOS = Lists.newArrayList();
            if(products!=null&&products.size()>0)
            {
                for (Product product:products) {
                    ProdutListVO produtListVO = assembleProutListVO(product);
                    produtListVOS.add(produtListVO);
                }
            }
            PageInfo pageInfo = new PageInfo();
            pageInfo.setList(produtListVOS);


        return ServerResponse.createServerRespnseBySucces(pageInfo);
    }

}
