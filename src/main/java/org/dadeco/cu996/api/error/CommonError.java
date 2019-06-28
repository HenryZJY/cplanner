package org.dadeco.cu996.api.error;

public interface CommonError {
    public int getErrCode();

    public String getErrMsg();

    public CommonError setErrMsg(String errMsg);
}
