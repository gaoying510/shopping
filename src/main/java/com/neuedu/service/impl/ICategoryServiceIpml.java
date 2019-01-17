package com.neuedu.service.impl;

import com.google.common.collect.Sets;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class ICategoryServiceIpml implements ICategoryService{
    /*
    *     获取品类子节点（平级）
     *
    * */

 @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ServerResponse get_category(Integer categoryId) {

        //非空校验
        if(categoryId==null){

            return ServerResponse.createServerRespnseByError("类别ID不能为空");
        }
        //根据CATEGORYID查询类别
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category==null)
        {
            return ServerResponse.createServerRespnseByError("查询的类别不存在");
        }
        //查询子类别
        List<Category> childCategory = categoryMapper.findChildCategory(categoryId);
        //返回结果
        return ServerResponse.createServerRespnseBySucces(childCategory);
    }
    /*
    *
    *
    * 增加节点
    * */
    @Override
    public ServerResponse add_category(Integer parentId, String categoryName) {

        //参数校验
        if(categoryName==null||categoryName.equals(""))
        {
            return ServerResponse.createServerRespnseByError("类别名称不能为空");
        }
        //添加节点
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(1);
        int insert = categoryMapper.insert(category);
        //返回结果
        if(insert>0)
        {
            return ServerResponse.createServerRespnseBySucces();
        }
        return ServerResponse.createServerRespnseByError("添加失败");
    }
/*
*
* 修改节点
* */
    @Override
    public ServerResponse set_category_name(Integer categoryId, String categoryName) {
//  参数非空校验
        if(categoryId==null||categoryId.equals(""))
        {
            return ServerResponse.createServerRespnseByError("类别ID不能为空");
        }
        if(categoryName==null||categoryName.equals(""))
        {
            return ServerResponse.createServerRespnseByError("类别名称不能为空");
        }
        //查询Categoryid
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category==null)
        {
            return ServerResponse.createServerRespnseByError("要修改的类别不存在");
        }
        //修改
        category.setName(categoryName);
        int i = categoryMapper.updateByPrimaryKey(category);
        if(i>0)
        {
            return ServerResponse.createServerRespnseBySucces();
        }
        //返回结果
        return ServerResponse.createServerRespnseByError("修改失败");
    }

 /*
 *
 * 获取当前分类id及递归子节点categoryId
 * */
    @Override
    @RequestMapping(value = "get_deep_category.do")
    public ServerResponse get_deep_category(Integer categoryId) {

        //参数非空校验
        if(categoryId==null)
        {
            return ServerResponse.createServerRespnseByError("类别ID不能为空");
        }
        //查询
        Set<Category> categorySet= Sets.newHashSet();
        categorySet = findAllChidCategory(categorySet, categoryId);

        Set<Integer> integerSet=Sets.newHashSet();

        Iterator<Category> categoryIterator=categorySet.iterator();
        while (categoryIterator.hasNext()) {
            Category category = categoryIterator.next();
            integerSet.add(category.getId());
        }
        return ServerResponse.createServerRespnseBySucces(integerSet);
    }

    private Set<Category> findAllChidCategory(Set<Category> categorySet,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category!=null)
        {
            categorySet.add(category);//id
        }
        //查找categoryId下的子节点（平级）
        List<Category> childCategory = categoryMapper.findChildCategory(categoryId);
        if(childCategory!=null&&childCategory.size()>0)
        {
            for (Category category1:childCategory) {
                findAllChidCategory(categorySet,category1.getId());
            }
        }

        return categorySet;
    }
}
