package com.bupt.account.utils;


import com.bupt.account.VO.ResultVO;
import com.bupt.account.enums.DeviceReturn;

public class ResultVOUtil {
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }
    public static ResultVO success(DeviceReturn deviceReturn) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(deviceReturn.getCode());
        resultVO.setMsg(deviceReturn.getMessage());
        return resultVO;
    }
    public static ResultVO error(DeviceReturn deviceReturn) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(deviceReturn.getCode());
        resultVO.setMsg(deviceReturn.getMessage());
        return resultVO;
    }
}
