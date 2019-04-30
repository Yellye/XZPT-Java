package xz.fzu.service;

import xz.fzu.exception.AccountUsedException;
import xz.fzu.exception.PasswordErrorException;
import xz.fzu.exception.TokenExpiredException;
import xz.fzu.model.User;


public interface IUserService {
    String register(User user) throws AccountUsedException;

    User selectByEmail(String email);

    User selectByUserId(String userId);

    String verifyUser(User user) throws PasswordErrorException;

    String verifyToken(String token) throws TokenExpiredException;

    void updateToken(String token, String userId);

    User selectUserByToken(String token) throws TokenExpiredException;

    String updatePasswd(String token, String oldPasswd, String newPasswd) throws PasswordErrorException, TokenExpiredException;

    void updateInfo(User user, String token) throws TokenExpiredException;

    void resetPasswd(String email, String passwd);
}
