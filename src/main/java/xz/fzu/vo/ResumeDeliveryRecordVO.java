package xz.fzu.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import xz.fzu.model.ResumeDelivery;

/**
 * @author Murphy
 * @date 2019/5/315:26
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeDeliveryRecordVO extends ResumeDelivery {

    private String userName;
    private String recruitmentName;
    private String school;
    private String speciality;

    public ResumeDeliveryRecordVO() {
        super();
    }

    public ResumeDeliveryRecordVO(ResumeDelivery resumeDelivery) {
        setResumeId(resumeDelivery.getResumeId());
        setDeliveryStatus(resumeDelivery.getDeliveryStatus());
        setRecruitmentId(resumeDelivery.getRecruitmentId());
        setUserId(resumeDelivery.getUserId());
        setRemark(resumeDelivery.getRemark());
        setResumeDeliveryId(resumeDelivery.getResumeDeliveryId());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRecruitmentName() {
        return recruitmentName;
    }

    public void setRecruitmentName(String recruitmentName) {
        this.recruitmentName = recruitmentName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
