package xz.fzu.exception;

import xz.fzu.util.Constants;

/**
 * @author Murphy
 * @date 2019/4/27 11:28
 */
public class EvilIntentions extends AbstractException {
    public EvilIntentions() {
        super("恶意操作！");
        setErrorCode(Constants.EVIL_INTENTIONS);
    }
}
