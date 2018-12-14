package com.stylefeng.guns.modular.api.apiparam;



public class ResponseData<T> {
    /**
     * 响应信息
     */
    private String resultMessage = StaticVariable.OPERATION_SUCCESS;
    /**
     * 响应状态
     */
    private String resultCode = StaticVariable.REQUEST_SUCCESS;
    /**
     * 响应数据
     */
    private T dataCollection;


    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }


    public T getDataCollection() {
        return dataCollection;
    }

    public void setDataCollection(T dataCollection) {
        this.dataCollection = dataCollection;
    }



    @Override
    public String toString() {
        return "ResponseData{" +
                "resultMessage='" + resultMessage + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", dataCollection='" + dataCollection + '\'' +
                '}';
    }

}
