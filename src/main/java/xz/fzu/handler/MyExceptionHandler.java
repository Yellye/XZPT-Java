package xz.fzu.handler;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.apache.commons.mail.EmailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import xz.fzu.exception.AbstractException;
import xz.fzu.util.Constants;
import xz.fzu.vo.ResponseData;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Murphy
 * @date 2019/4/26 20:49
 */
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * @param httpServletResponse httpServletResponse
     * @param e 异常实例
     * @return xz.fzu.vo.ResponseData<java.lang.String>
     * @author Murphy
     * @date 2019/4/26 22:12
     * @description 默认的异常处理器
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData<String> defaultHandler(HttpServletResponse httpServletResponse, Exception e) {
        ResponseData<String> responseObject = new ResponseData<>();
        responseObject.setResultCode(Constants.INTERNAL_ERROR);
        responseObject.setResultObject("如果你看到这条消息，说明我还未处理此异常，" +
                "请先保存ResultMsg中的内容，并请向我反馈！谢谢！");
        if (httpServletResponse.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            responseObject.setResultCode(Constants.URL_NOT_FOUND);
            responseObject.setResultObject(null);
        } else if (httpServletResponse.getStatus() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            responseObject.setResultCode(Constants.INTERNAL_ERROR);
        }
        responseObject.setResultMsg(e.getClass() + ":" + e.getMessage());
        printStack(e);

        return responseObject;
    }

    /**
     * @param e 异常实例
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/4/26 22:12
     * @description 处理Toke异常
     */
    @ExceptionHandler(value = SignatureVerificationException.class)
    @ResponseBody
    public ResponseData defaultHandler(SignatureVerificationException e) {

        ResponseData responseObject = new ResponseData();
        responseObject.setResultCode(Constants.TOKEN_ERROR);
        responseObject.setResultMsg(e.getMessage());
        printStack(e);

        return responseObject;
    }

    /**
     * @param e 异常实例
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/4/26 22:12
     * @description 处理Toke异常
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseData defaultHandler(NoHandlerFoundException e) {

        ResponseData responseObject = new ResponseData();
        responseObject.setResultCode(Constants.URL_NOT_FOUND);
        responseObject.setResultMsg("找不到Handler，请检查URL");
        printStack(e);

        return responseObject;
    }

    /**
     * @param e 异常实例
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/4/26 22:11
     * @description 处理MyException
     */
    @ExceptionHandler(value = AbstractException.class)
    @ResponseBody
    public ResponseData myExceptionHandle(AbstractException e) {

        ResponseData<String> responseObject = new ResponseData<>();
        responseObject.putData(e.getErrorCode(), e.getMessage(), null);
        printStack(e);

        return responseObject;
    }

    /**
     * @param e 异常实例
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/5/2 19:44
     * @description 邮件发送错误
     */
    @ExceptionHandler(value = EmailException.class)
    @ResponseBody
    public ResponseData emailExceptionHandle(EmailException e) {

        ResponseData<String> responseObject = new ResponseData<>();
        responseObject.putData(Constants.SEND_EMAIL_ERROR, e.getMessage(), null);
        printStack(e);

        return responseObject;
    }

    private void printStack(Exception e) {
        e.printStackTrace();
    }
}
