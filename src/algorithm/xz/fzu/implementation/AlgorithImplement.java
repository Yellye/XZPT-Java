package xz.fzu.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

import xz.fzu.needobject.*;

/**
 * ����Ϊ������㷨ʵ����
 * @author LITM
 *
 */
public class AlgorithImplement {
	/**
	 * ����Ӳ��ָ�����Ƹ��Ϣ���й���
	 * @author LITM
	 * @param UserProfile,List<RecruimentProfile>
	 * @return List<RecruimentProfile>
	 */
	private double[] degreeQuanValue = {0.3,0.5,0.8,1.0};
	private double[] salaryQuanValue = {0.1,0.15,0.25,0.35,0.5,0.7,1.0};
	private double[] uWeight = {0.6,0.4};//�����û���ѧ����нˮ�����ӳ̶ȷֱ�����Ȩ��Ϊ0.4,��0.6
	//��ѧ��ӳ���int�������Ƚ�
	private Map<String,Integer> typeOfDegree = new HashMap<String,Integer>(){
		private static final long serialVersionUID = 1L;

	{put("ѧ������",1);put("��ר������",2);put("���Ƽ�����",3);put("˶ʿ������",4);put("��ʿ������",5);}
	
	};
	/**
	 * ����Ӳ��ָ�����Ƹ��Ϣ���г�ɸ����ɸѡ���
	 * Ӳ��ָ�� ��ҵ��ǩ int������ʱ��int�������ص�String����������int
	 * @param upf
	 * @param rps
	 * @return
	 */
	public List<RecruitmentProfile> directFiltration(UserProfile upf,List<RecruitmentProfile> rps) {
		
		Iterator<RecruitmentProfile> iterator = rps.iterator();
		while (iterator.hasNext()) {
			
			RecruitmentProfile rp = iterator.next();
			if(upf.getJobCategory()!=rp.getJobCategory()) {
				iterator.remove();
			}
			else if(upf.getWorkTime()!=rp.getWorkTime()) {
				iterator.remove();
			}
			else if(!upf.getLocation().equals(rp.getLocation())) {
				iterator.remove();
			}
			else if(upf.getJobNature()!=rp.getJobNature()) {
				iterator.remove();
			}
		}
		return rps; 
		
	}
	/**
	 * ��������ȡ��нˮֵת��Ϊ���ַ�������Ƚ�
	 * @param salary
	 * @return
	 */
	public int[] regExSalary(String salary) {
		
		int[] salaryRange = new int[2];
		String regex1 = "^\\d*(?=k)";
		String regex2 = "(?<=~)\\d*";
		Pattern p1 = Pattern.compile(regex1);
		Pattern p2 = Pattern.compile(regex2);
		Matcher m1 = p1.matcher(salary);
		Matcher m2 = p2.matcher(salary);
		m1.find();
		m2.find();
		salaryRange[0] = Integer.parseInt(m1.group());
		salaryRange[1] = Integer.parseInt(m2.group());
		return salaryRange;
		
	}
	/**
	 * ��нˮ��������
	 * @param salaryRange
	 * @return
	 */
	public double salaryQuan(int[] salaryRange) {
		
		if (salaryRange[0]>=20) {
			return salaryRange[6];
		}
		if (salaryRange[0]>=15) {
			return salaryQuanValue[5];
		}
		else if (salaryRange[0]>=10) {
			return salaryQuanValue[4];
		}
		else if (salaryRange[0]>=7) {
			return salaryQuanValue[3];
		}
		else if (salaryRange[0]>=5) {
			return salaryQuanValue[2];
		}
		else if (salaryRange[0]>=3)
			return salaryQuanValue[1];
		else 
			return salaryQuanValue[0];
		
	}
	/**
	 * ��ѧ����������
	 * @param upf
	 * @param rp
	 * @return
	 */
	public double degreeQuan(UserProfile upf,RecruitmentProfile rp) {
		
		int uDegree = upf.getDegree();
		String rDegreeRequire = rp.getDegreeRequire();
		int rdegreeRequire = typeOfDegree.get(rDegreeRequire);
		if (uDegree==rdegreeRequire) {
			return degreeQuanValue[3];
		}
		else if (1==rdegreeRequire) {
			return degreeQuanValue[2];
		}
		else if (uDegree>rdegreeRequire){
			return degreeQuanValue[1];
		}
		else {
			return degreeQuanValue[0];
		}
		
	}
	/**
	 * ����Ƹ��Ϣ�ؼ��ֶν���������Ȩ
	 * Ŀǰֻ��ѧ����нˮ����������Ȩ
	 * @param upf
	 * @param trps
	 * @return
	 */
	
	public Map<String,double[]> quantization(UserProfile upf,List<RecruitmentProfile> preScreeningResults) {
		
		Map<String,double[]> weightResults = new HashMap<String,double[]>();
		Iterator<RecruitmentProfile> iterator = preScreeningResults.iterator();
		while(iterator.hasNext()) {
			RecruitmentProfile rp = iterator.next();
			double[] quanValue = new double[2];
			int[] sarlaryRange = regExSalary(rp.getSalary());
			quanValue[0] = degreeQuan(upf,rp)*uWeight[0];//ѧ��������Ȩ
			quanValue[1] = salaryQuan(sarlaryRange)*uWeight[1];//нˮ������Ȩ
			weightResults.put(rp.getRecruitmentId(), quanValue);
		}
		
		return weightResults;
	}
	
	/**
	 * ����Ƹ��Ϣ�������ƶȼ���
	 * @param uWeight
	 * @param weightedResults
	 */
	public FiltrationResult computationalSimilarity(String userId,Map<String,double[]> weightedResults) {
		
		FiltrationResult fr =new FiltrationResult();
		List<EnterpriseSimilarityResult> esrs = new ArrayList<EnterpriseSimilarityResult>();
		fr.setUserId(userId);
		double result = 0;
		double r1 = 0;
		double r2 = 0;
		double r3 = 0;
		Iterator<Map.Entry<String,double[]>> iterator = weightedResults.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, double[]> entry = iterator.next();
			String str = entry.getKey();
			double[] dou = entry.getValue();
			for (int i=0;i<dou.length;i++) {
				r1+=dou[i]*uWeight[i];
				r2+=dou[i]*dou[i];
				r3+=uWeight[i]*uWeight[i];
			}
			result = Double.parseDouble(String.format("%.6f", r1/Math.sqrt(r2*r3)));
			EnterpriseSimilarityResult esr = new EnterpriseSimilarityResult();
			esr.setRecruitmentId(str);
			esr.setSimilarityResult(result);
			esrs.add(esr);
		}
		fr.setEnterpriseSimilarityResults(esrs);
		
		return fr;
 	}
	/**
	 * ��ȡ���ƶ���ߵ�Top-N
	 * @param fr
	 * @param n
	 */
	public FiltrationResult getTopN(FiltrationResult fr,int n) {
		
		List<EnterpriseSimilarityResult> esrs = fr.getEnterpriseSimilarityResults();
		Collections.sort(esrs);
		if (n<esrs.size()) {
			fr.setEnterpriseSimilarityResults( esrs.subList(0, n-1));
		}
		
		return fr;
	}
}
