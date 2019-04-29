package xz.fzu.algorithmentry;

/**
 * ��ҵ��ǩ
 *  1	����|����|��ά��
	2	��Ʒ|����|��Ŀ��
	3	��Ӫ|�༭|�ͷ���
	4	�г�|������
	5	������
	6	�ۺ�ְ��|�߼�������
	7	������
	8	����|��ý|����|������
	9	����|��ѵ��
	10	��ҵ����|רҵ������
	11	ó��|����|����|����ҵ��
	12	��ͨ|����|����|�ִ���
	13	���ز�|����|��ҵ��
	14	����|�ӹ�|������
	15	��Դ���|ũ��������
	16	����|����|��ҩ|ҽ����
	17	����Ա|������
 *
 */
/**
 * ѧ��
 * 1   ѧ������
 * 2   ��ר������
 * 3   ���Ƽ�����
 * 4   ˶ʿ������
 * 5   ��ʿ������
 */
/**
 * ����ʱ��
 * 1   955
 * 2   965
 * 3   956
 * 4   996
 */
/**
 * ��������
 * 1   ʵϰ
 * 2   ��ְ
 * 3   ȫְ
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xz.fzu.implementation.*;
import xz.fzu.needobject.*;

public class RecomAlgorithm {
	
	/**
	 * �Ƽ��㷨
	 * @param upf
	 * @param rps
	 */
	public  FiltrationResult recomAlgorithm(UserProfile upf,List<RecruitmentProfile> rps,int n) {
		
		AlgorithImplement algorithImplement = new AlgorithImplement();
		List<RecruitmentProfile> preScreeningResults  = algorithImplement.directFiltration(upf,rps);
		Map<String,double[]> weightResults = algorithImplement.quantization(upf,preScreeningResults);
		FiltrationResult filtrationResult = algorithImplement.computationalSimilarity(upf.getUserId(),weightResults);
		filtrationResult = algorithImplement.getTopN(filtrationResult,n);
		
		return filtrationResult;
	}
}
