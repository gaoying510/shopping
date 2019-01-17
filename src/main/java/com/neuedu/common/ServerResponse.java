package com.neuedu.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/*
*
* 响应前端的高复用对象
* */
@JsonSerialize(include =JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> {

    private Integer status; //状态码，成功=0，不成功等于非0
    private T data; //当状态码=0时，data对应接口显示的数据
    private String msg; //显示信息


    private ServerResponse() {

    }
    private ServerResponse(Integer status) {
        this.status=status;
    }
    private ServerResponse(Integer status,T data)
    {
        this.status=status;
        this.data=data;
    }
    private ServerResponse(Integer status,T data,String msg)
    {
        this.status=status;
        this.data=data;
        this.msg=msg;
    }

/*
* 成功
* */

/*
* 判断接口是否连接成功
* */

@JsonIgnore
public boolean isSuccess(){

    return this.status == ResponseCode.SUCCESS;
}
    /*
    * { status =0}
    * */
   public static ServerResponse createServerRespnseBySucces(){
        return new ServerResponse(ResponseCode.SUCCESS);
   }

   /*
   *
   * {status=0,mag="asdfasdf"}
   * */
  public static ServerResponse createServerRespnseBySucces(String msg){
       return new ServerResponse(ResponseCode.SUCCESS,msg);
  }

    /*
     *
     * {status=0,mag="asdfasdf",data[]}
     * */
  public static <T> ServerResponse createServerRespnseBySucces(T data,String mag)
  {
      return  new ServerResponse(ResponseCode.SUCCESS,data,mag);
  }

  public static <T> ServerResponse createServerRespnseBySucces(T data)
  {
      return new ServerResponse(ResponseCode.SUCCESS,data);
  }
    /*
     * { status =1}
     * */

    public static ServerResponse createServerRespnseByError(){
        return new ServerResponse(ResponseCode.ERROR);
    }

    /*
    * 自定义的状态码
    * { status ：= custom }
    * */

    public static ServerResponse createServerRespnseByError(Integer status)
    {
        return new ServerResponse(status);
    }
    /*
     * 自定义的状态码和返回信息
     * { status ：custom ，msg : "错误"}
     * */
    public static ServerResponse createServerRespnseByError(Integer status,String msg)
    {
        return new ServerResponse(status,msg);
    }
    /*
     * 状态码和返回信息
     * { status ：1 ，msg : "错误"}
     * */
    public static ServerResponse createServerRespnseByError(String msg){
        return new ServerResponse(ResponseCode.ERROR,msg);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
