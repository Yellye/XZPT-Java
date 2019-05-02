package xz.fzu.exception;

import xz.fzu.util.Constants;

/**
 * @author Murphy
 * @date 2019/4/24 15:11
 */
public class NoVerfcationCodeException extends AbstractException {
    public NoVerfcationCodeException() {
        super("没有获取验证码！");
        setErrorCode(Constants.NO_VERFCATION_CODE);
    }
}
