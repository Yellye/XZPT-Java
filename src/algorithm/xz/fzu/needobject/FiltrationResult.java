package xz.fzu.needobject;

import java.util.ArrayList;
import java.util.List;

/**
 * ���������洢�㷨ɸѡ�Ľ��
 * @author LITM
 *
 */
public class FiltrationResult {
	
	private String userId;
	private List<EnterpriseSimilarityResult> enterpriseSimilarityResults = new ArrayList<EnterpriseSimilarityResult>();
	public String getUserId() {
		
		return userId;
	}
	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	public List<EnterpriseSimilarityResult> getEnterpriseSimilarityResults() {
		
		return enterpriseSimilarityResults;
	}
	public void setEnterpriseSimilarityResults(List<EnterpriseSimilarityResult> enterpriseSimilarityResults) {
		
		this.enterpriseSimilarityResults = enterpriseSimilarityResults;
	}
}
