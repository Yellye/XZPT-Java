package xz.fzu.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Auto-generator
 *
 * @author Murphy
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "recruitment")
public class Recruitment implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    private long recruitmentId;
    private java.sql.Timestamp publishTime;
    private long validate;
    private String companyId;
    private String jobName;
    private String description;
    private String contact;
    private String location;
    private String deliveryRequest;
    private String salary;
    private String degree;
    private long workTime;
    private long industryLabel;
    private String stationLabel;
    private long jobType;
    private Integer headCount;

    @Transient
    /*
     * 以下字段不对应表中的具体字段，所以全部使用@Transient
     * */
    private String companyName;
    @Transient
    private String station;
    @Transient
    private String industry;

    public long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }


    public java.sql.Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(java.sql.Timestamp publishTime) {
        this.publishTime = publishTime;
    }


    public long getValidate() {
        return validate;
    }

    public void setValidate(long validate) {
        this.validate = validate;
    }


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getDeliveryRequest() {
        return deliveryRequest;
    }

    public void setDeliveryRequest(String deliveryRequest) {
        this.deliveryRequest = deliveryRequest;
    }


    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }


    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }


    public long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime = workTime;
    }


    public long getIndustryLabel() {
        return industryLabel;
    }

    public void setIndustryLabel(long industryLabel) {
        this.industryLabel = industryLabel;
    }


    public String getStationLabel() {
        return stationLabel;
    }

    public void setStationLabel(String stationLabel) {
        this.stationLabel = stationLabel;
    }


    public long getJobType() {
        return jobType;
    }

    public void setJobType(long jobType) {
        this.jobType = jobType;
    }

    public Integer getHeadCount() {
        return headCount;
    }

    public void setHeadCount(Integer headCount) {
        this.headCount = headCount;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
