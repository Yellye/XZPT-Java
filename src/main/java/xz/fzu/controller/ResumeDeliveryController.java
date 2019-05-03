package xz.fzu.controller;

import org.springframework.web.bind.annotation.*;
import xz.fzu.exception.EvilIntentions;
import xz.fzu.exception.InstanceNotExistException;
import xz.fzu.exception.TokenExpiredException;
import xz.fzu.exception.UserNotFoundException;
import xz.fzu.model.Resume;
import xz.fzu.model.ResumeDelivery;
import xz.fzu.service.*;
import xz.fzu.vo.PageData;
import xz.fzu.vo.ResponseData;
import xz.fzu.vo.ResumeDeliveryRecordVO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 简历投递相关的控制器
 * @author Murphy
 * @date 2019/5/2 14:05
 */
@RestController
public class ResumeDeliveryController {

    //User


    @Resource
    IResumeDeliveryService iResumeDeliveryService;
    @Resource
    IUserService iUserService;
    @Resource
    IResumeService iResumeService;

    /**
     * 投递简历
     *
     * @param token         token
     * @param resumeId      简历id
     * @param recruitmentId 招聘信息id
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/5/2 21:41
     */
    @RequestMapping(value = "/user/deliveryresume", method = RequestMethod.POST)
    public ResponseData deliveryResume(@RequestParam String token, @RequestParam int resumeId, @RequestParam int recruitmentId) throws TokenExpiredException, EvilIntentions {

        ResponseData responseData = new ResponseData();
        String userId = iUserService.verifyToken(token);
        iResumeService.copyResume(resumeId);
        Resume resume = new Resume();
        resume.setResumeId(resumeId);
        resume.setResumeStatus(1);
        iResumeService.updateResume(userId, resume);
        iResumeDeliveryService.deliveryResume(userId, resumeId, recruitmentId);

        return responseData;
    }

    @Resource
    ICompanyService iCompanyService;

    @Resource
    IRecruitmentService iRecruitmentService;

    /**
     * @param token token
     * @param resumeDelivery 简历投递记录
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/5/2 18:28
     * @description 用户更新投递信息
     */
    @RequestMapping(value = "/user/updatedeliveryrecord", method = RequestMethod.POST)
    public ResponseData userUpdateDeliveryRecord(@RequestParam String token, @RequestBody ResumeDelivery resumeDelivery) throws TokenExpiredException {

        ResponseData responseData = new ResponseData();
        iUserService.verifyToken(token);
        resumeDelivery.setDeliveryStatus(0);
        iResumeDeliveryService.updateResumeDeliveryRecord(resumeDelivery);

        return responseData;
    }

    /**
     * @param token token
     * @param resumeDeliveryId 简历投递记录的id
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/5/2 18:28
     * @description 用户根据投递id获得投递信息
     */
    @RequestMapping(value = "/user/getdeliveryrecordbyid", method = RequestMethod.POST)
    public ResponseData userGetDeliveryRecordById(@RequestParam String token, @RequestParam int resumeDeliveryId) throws TokenExpiredException, InstanceNotExistException {

        iUserService.verifyToken(token);
        return getDeliveryRecordById(resumeDeliveryId);
    }


    // Company

    /**
     * @param token token
     * @param resumeId 简历id
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/5/2 18:28
     * @description 用户根据投递id获得投递信息
     */
    @RequestMapping(value = "/user/getdeliveryrecordbyresumeid", method = RequestMethod.POST)
    public ResponseData<ResumeDelivery> userGetDeliveryRecordByResumeId(@RequestParam String token, @RequestParam int resumeId) throws TokenExpiredException, InstanceNotExistException {

        ResponseData<ResumeDelivery> responseData = new ResponseData<>();
        iUserService.verifyToken(token);
        ResumeDelivery resumeDelivery = iResumeDeliveryService.getResumeDeliveryRecordByResume(resumeId);
        responseData.setResultObject(resumeDelivery);

        return responseData;
    }

    /**
     * @param token token
     * @param pageData 页信息
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/5/2 14:47
     * @description 获得自己的所有的投递记录
     */
    @RequestMapping(value = "/user/getlistdeliveryrecord", method = RequestMethod.POST)
    public ResponseData userGetDeliveryRecord(@RequestParam String token, @RequestBody PageData<ResumeDeliveryRecordVO> pageData) throws TokenExpiredException, InstanceNotExistException {

        ResponseData responseData = new ResponseData();
        String userId = iUserService.verifyToken(token);
        List<ResumeDelivery> list = iResumeDeliveryService.userGetResumeDeliveryRecord(userId, pageData);
        pageData.setContentList(listCastToResumeDeliveryRecordVO(list));

        return responseData;
    }

    /**
     * @param token token
     * @param resumeDelivery 简历投递记录
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/5/2 18:19
     * @description 公司更新简历投递记录
     */
    @RequestMapping(value = "/company/updatedeliveryrecord", method = RequestMethod.POST)
    public ResponseData comapnyUpdateDeliveryRecord(@RequestParam String token, @RequestBody ResumeDelivery resumeDelivery) throws TokenExpiredException {

        ResponseData responseData = new ResponseData();
        iCompanyService.verifyToken(token);
        iResumeDeliveryService.updateResumeDeliveryRecord(resumeDelivery);

        return responseData;
    }


    /**
     * @param token token
     * @param resumeDeliveryId 简历投递记录id
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/5/2 18:28
     * @description 企业根据投递id获得投递信息
     */
    @RequestMapping(value = "/company/getdeliveryrecordbyid", method = RequestMethod.POST)
    public ResponseData companyGetDeliveryRecordById(@RequestParam String token, @RequestParam int resumeDeliveryId) throws TokenExpiredException, InstanceNotExistException {

        iCompanyService.verifyToken(token);
        return getDeliveryRecordById(resumeDeliveryId);
    }

    /**
     * @param token    token
     * @param pageData 页信息
     * @return xz.fzu.vo.ResponseData
     * @author Murphy
     * @date 2019/5/2 14:47
     * @description 公司获得自己招聘信息所有的投递记录
     */
    @RequestMapping(value = "/company/getlistdeliveryrecord", method = RequestMethod.POST)
    public ResponseData companyGetDeliveryRecord(@RequestParam String token, @RequestBody PageData<ResumeDeliveryRecordVO> pageData) throws TokenExpiredException, InstanceNotExistException, UserNotFoundException {

        ResponseData<PageData> responseData = new ResponseData<>();
        iCompanyService.verifyToken(token);
        String companyId = iCompanyService.getInfoByToken(token).getCompanyId();
        List<ResumeDelivery> list = iResumeDeliveryService.companyGetResumeDeliveryRecord(companyId, pageData);
        pageData.setContentList(listCastToResumeDeliveryRecordVO(list));
        responseData.setResultObject(pageData);

        return responseData;
    }

    /**
     * @param resumeDeliveryId 简历投递记录id
     * @return xz.fzu.vo.ResponseData<xz.fzu.model.ResumeDelivery>
     * @author Murphy
     * @date 2019/5/2 18:37
     * @description 根据投递id获得投递信息
     */
    private ResponseData<ResumeDelivery> getDeliveryRecordById(int resumeDeliveryId) throws InstanceNotExistException {

        ResponseData<ResumeDelivery> responseData = new ResponseData<>();
        ResumeDelivery resumeDelivery = iResumeDeliveryService.getResumeDeliveryRecordById(resumeDeliveryId);
        responseData.setResultObject(castResumeDeliveryRecordVO(resumeDelivery));

        return responseData;
    }

    /**
     * 通过简历id 得到简历
     * @author Murphy
     * @date 2019/5/3 15:36
     * @param token token
     * @param resumeId 简历id
     * @return xz.fzu.vo.ResponseData
     */
    @RequestMapping(value = "/company/getresumebyid", method = RequestMethod.POST)
    public ResponseData companyGetResumeByResumeId(@RequestParam String token, @RequestParam int resumeId) throws TokenExpiredException, InstanceNotExistException {

        ResponseData<Resume> responseData = new ResponseData<>();
        iCompanyService.verifyToken(token);
        Resume resume = iResumeService.getResume(null, resumeId);
        responseData.setResultObject(resume);

        return responseData;
    }

    /**
     * 将投递记录添加投递者的用户名
     *
     * @param resumeDeliveries 投递记录的list
     * @return java.util.List<xz.fzu.vo.ResumeDeliveryRecordVO>
     * @author Murphy
     * @date 2019/5/3 15:36
     */
    public List<ResumeDeliveryRecordVO> listCastToResumeDeliveryRecordVO(List<ResumeDelivery> resumeDeliveries) throws InstanceNotExistException {

        List<ResumeDeliveryRecordVO> list = new ArrayList<>();
        for (ResumeDelivery resumeDelivery : resumeDeliveries) {
            list.add(castResumeDeliveryRecordVO(resumeDelivery));
        }

        return list;
    }

    /**
     * 单条投递记录添加投递用户名
     *
     * @param resumeDelivery 投递记录
     * @return xz.fzu.vo.ResumeDeliveryRecordVO
     * @author Murphy
     * @date 2019/5/3 15:38
     */
    private ResumeDeliveryRecordVO castResumeDeliveryRecordVO(ResumeDelivery resumeDelivery) throws InstanceNotExistException {

        String userId = resumeDelivery.getUserId();
        ResumeDeliveryRecordVO resumeDeliveryRecordVO = new ResumeDeliveryRecordVO(resumeDelivery);
        resumeDeliveryRecordVO.setUserName(iUserService.selectByUserId(userId).getUserName());
        resumeDeliveryRecordVO.setRecruitmentName(iRecruitmentService.getRecruitmentById(resumeDelivery.getRecruitmentId()).getJobName());
        resumeDeliveryRecordVO.setSchool(iResumeService.getResume(null, (int) resumeDelivery.getResumeId()).getSchool());
        resumeDeliveryRecordVO.setSpeciality(iResumeService.getResume(null, (int) resumeDelivery.getResumeId()).getSpeciality());

        return resumeDeliveryRecordVO;
    }
}
