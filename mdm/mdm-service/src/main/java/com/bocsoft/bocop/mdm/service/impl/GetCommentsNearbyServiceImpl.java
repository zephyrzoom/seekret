package com.bocsoft.bocop.mdm.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bocsoft.bocop.mdm.entity.CommentNearbyInfo;
import com.bocsoft.bocop.mdm.entity.qo.LocationQo;
import com.bocsoft.bocop.mdm.entity.qo.ResponseQo;
import com.bocsoft.bocop.mdm.mapper.GetCommentsNearbyMapper;
import com.bocsoft.bocop.mdm.service.GetCommentsNearbyService;
import com.bocsoft.bocop.mdm.utils.CommonConstants;

/**
 * @author dzg7151
 * 返回距离location5公里内的20条评论,如果5公里内评论数量大于20条
 * 则用轮盘赌算法进行筛选，以点赞数量为适应度。
 * 若有些评论的距离太近，则进行聚类
 */
@Service
public class GetCommentsNearbyServiceImpl implements GetCommentsNearbyService{

	@Autowired
	private GetCommentsNearbyMapper cnMapper;
	@Override
	public ResponseQo getComments(LocationQo cnq) {
		
		ResponseQo resp = new ResponseQo();
		List<CommentNearbyInfo> returnList = new LinkedList<>();
		
		List<CommentNearbyInfo> queryResult = cnMapper.getComments(cnq);
		if(queryResult == null) {
			resp.setDesc(CommonConstants.LC_FAILURE);
		}else{
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
			java.util.Date today = new java.util.Date();
			
			for(CommentNearbyInfo each: queryResult) {
				String distStr = each.getDistance();
				double distDouble = Double.parseDouble(distStr);
				
				//距离限制
//				if(Math.abs(distDouble - CommonConstants.DISTANCETOSHOW) >= 10) {
//					continue;
//				}
				if(distDouble > CommonConstants.DISTANCETOSHOW) {
					continue;
				}
				
				
				/**时间筛选*/
				String commitDate = each.getCommitDate();
				Date cdate;
				try {
					cdate = format.parse(commitDate);
					long diffDays = TimeUnit.DAYS.convert(today.getTime()-cdate.getTime(), TimeUnit.MILLISECONDS);
					if(diffDays <= CommonConstants.DAYSDIFF) {//时间限制
						returnList.add(each);
//						if(returnList.size() >= CommonConstants.ITEMTOSHOW) {
//							break;
//						}
					}else {
						continue;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}					
			}//for
			
			//轮盘赌筛选
			if(returnList.size() > CommonConstants.ITEMTOSHOW) {
				
				roulettleWheelSelection(returnList);
			}
		}
		
		//调用聚类函数
		
//		resp.setResult(classify(returnList));
		resp.setResult(classifyByMerge(returnList));
		
		resp.setDesc(CommonConstants.LC_SUCCESS);
		
//		for(CommentNearbyInfo each: returnList) {
//			System.out.println("内容会"+each.getContent());
//		}
		return resp;
	}
	
	/**
	 * 以轮盘赌选择算法筛选20条，以点赞次数作为适应度
	 */
	private void roulettleWheelSelection(List<CommentNearbyInfo> commentList) {
		//如果全部的评论点赞次数都为0
		if(allPraiseNumZero(commentList)) {
			int listSize = commentList.size();
			for(int i=listSize-1; i>=CommonConstants.ITEMTOSHOW; i-- ) {
				commentList.remove(i);
			}
			return;
		}
		
		addPraiseBy1(commentList);
		
		List<Float> probobilityForeach = new ArrayList<>();
		List<Float> probobilitySum = new ArrayList<>();
		calcProbForeach(probobilityForeach,commentList);
		calcProbSum(probobilityForeach,probobilitySum);
		
		List<CommentNearbyInfo> resultList = new  ArrayList<>();

		Random random = new Random();
		boolean flag = true;
		while(flag) {					
			float rand = random.nextFloat();
			for(int i=0; i<probobilitySum.size(); i++) {				
				if(rand < probobilitySum.get(i)) {
					if(resultList.contains(commentList.get(i))) {
//						continue;
						break;
					}
					resultList.add(commentList.get(i));
					if(resultList.size() >= CommonConstants.ITEMTOSHOW){
						commentList.clear();
						for(CommentNearbyInfo each: resultList) {
							commentList.add(each);
						}
						minusPraiseBy1(commentList);
						return ;
					}
					break;
				}
			}
		}//while
		
		minusPraiseBy1(commentList);
		
	}
	
	/**
	 * 之前加过1，这里减掉1
	 * @param commentList
	 */
	private void minusPraiseBy1(List<CommentNearbyInfo> commentList) {
		for(int i=0; i<commentList.size();i++) {
			CommentNearbyInfo each = commentList.get(i);
			String praiseNum = each.getPraiseNum();
			int pNum = Integer.parseInt(praiseNum);
			each.setPraiseNum( String.valueOf(--pNum) );
		}
	}
	
	/**
	 * 计算累加概率
	 */
	private void calcProbSum(List<Float> probobilityForeach, List<Float> probobilitySum) {
		probobilitySum.add(0, probobilityForeach.get(0));
		for(int i=1; i<probobilityForeach.size(); i++) {
			probobilitySum.add(i, probobilitySum.get(i-1) + probobilityForeach.get(i));
		}
		return ;
	}
	/**
	 * 计算每个适应度
	 */
	private void calcProbForeach(List<Float> probobilityForeach, List<CommentNearbyInfo> commentList) {
		int praiseSum = 0;
		for(int i=0; i<commentList.size();i++) {
			CommentNearbyInfo each = commentList.get(i);
			String praiseNum = each.getPraiseNum();
			int pNum = Integer.parseInt(praiseNum);
			praiseSum += pNum;
		}
		
		for(int i=0; i<commentList.size(); i++) {
			CommentNearbyInfo each = commentList.get(i);
			String praiseNum = each.getPraiseNum();
			int pNum = Integer.parseInt(praiseNum);
			float prob = ((float)pNum)/praiseSum;
			probobilityForeach.add(i,prob);
		}		
	}
	
	/**所有评论数加1*/
	private void addPraiseBy1(List<CommentNearbyInfo> commentList) {
		for(int i=0; i<commentList.size();i++) {
			CommentNearbyInfo each = commentList.get(i);
			String praiseNum = each.getPraiseNum();
			int pNum = Integer.parseInt(praiseNum);
			each.setPraiseNum( String.valueOf(++pNum) );
		}
	}
	
	/**如果全部的评论点赞次数都为0*/
	private boolean allPraiseNumZero(List<CommentNearbyInfo> commentList) {
		String praiseNum ;
		for(CommentNearbyInfo each: commentList) {
			praiseNum = each.getPraiseNum();
			if(!"0".equals(praiseNum)){
				return false;
			}
		}
		return true;
	}
	private List<  List<CommentNearbyInfo> > classifyByMerge(List<CommentNearbyInfo> commentList) {
		List< List<CommentNearbyInfo> > returnList = new LinkedList< List<CommentNearbyInfo> >();
		for(CommentNearbyInfo each: commentList ) {
			List<CommentNearbyInfo> tempList = new LinkedList<> ();
			tempList.add(each);
			returnList.add(tempList);
		}
		mergeList(returnList);
		return returnList;
	}
	/**
	 * 将结果集按距离分类，将距离小于阈值的放在一个集合中,返回元素为集合的集合
	 * @param commentList
	 */
	private List< List<CommentNearbyInfo> > classify(List<CommentNearbyInfo> commentList) {
		List< List<CommentNearbyInfo> > returnList = new LinkedList< List<CommentNearbyInfo> >();
		HashSet<CommentNearbyInfo> isAdded = new HashSet<CommentNearbyInfo>();
		
		List<CommentNearbyInfo> itemList = new LinkedList<CommentNearbyInfo>();
		itemList.add(commentList.get(0));
		isAdded.add(commentList.get(0));
		returnList.add(itemList);
		
		//递归分类
		classifyRecursively(returnList, isAdded, commentList);
		mergeList(returnList);
		return returnList;
	}
	
	/**
	 * 递归聚类
	 * @param returnList 分类结果
	 * @param isAdded 已经分类的元素
	 * @param commentList 未分类的
	 */
	private void classifyRecursively(List< List<CommentNearbyInfo> > returnList,/**分类结果*/
			HashSet<CommentNearbyInfo> isAdded,/**已经分类的元素*/
			List<CommentNearbyInfo> commentList/**未分类的*/) {
		
		for(CommentNearbyInfo each: commentList) {
			if(isAdded.contains(each) ) {
				continue;
			}
			
			boolean flag_itemAdded = false;//该元素是否已被聚类
			boolean flag_out = true;//标识是否要进行外层循环
			for(List<CommentNearbyInfo> listItem: returnList) {
				if(flag_out==false) {
					break;
				}
				for(int i=0; i<listItem.size(); i++){
					CommentNearbyInfo commItem = listItem.get(i);
					
					if(commItem == each) {//同一个引用
						continue;
					}
					
					if(mergeAble(each, commItem)) {
						//退出两层循环
						listItem.add(each);
						isAdded.add(each);
						flag_itemAdded = true;
						flag_out = false;
						break;
					}					
				}//inner for						
			}//outer for
			
			//创建一个新聚合
			if(flag_itemAdded == false) {
				List<CommentNearbyInfo> newList = new LinkedList<>();
				newList.add(each);
				isAdded.add(each);
				returnList.add(newList);
				classifyRecursively(returnList, isAdded, commentList);
			}
		}
	}
	
	/**
	 * 判断两个评论是否处于可合并范围内
	 * @param comm1
	 * @param comm2
	 * @return
	 */
	private boolean mergeAble(CommentNearbyInfo comm1, CommentNearbyInfo comm2) {
		double latitude1 = Double.parseDouble(comm1.getLatitude());
		double latitude2 = Double.parseDouble(comm2.getLatitude());
		if(Math.abs(latitude2 - latitude1) > CommonConstants.CLASSIFYCRITERIA_LATITUDE) {
			return false;
		}else{
			double longitude1 = Double.parseDouble(comm1.getLongitude());
			double longitude2 = Double.parseDouble(comm2.getLongitude());			
			int charNum = 0;
			if(longitude1 > longitude2) {
				charNum = comm2.getContent().length();
				if( (longitude1-longitude2) < (CommonConstants.CLASSIFYCRITERIA_LANGITUDE-charNum*CommonConstants.LANGITUDE_FACTOR) ){
					return true;
				}
			}else{
				charNum = comm1.getContent().length();
				if( (longitude2-longitude1) < (CommonConstants.CLASSIFYCRITERIA_LANGITUDE-charNum*CommonConstants.LANGITUDE_FACTOR) ){
					return true;
				}
			}
		}
//		double distance = calcDistance(comm1, comm2);
//		if(distance < CommonConstants.CLASSIFYCRITERIA) {
//			return true;
//		}
		return false;
	}
	
	/**
	 * 将结果集两两聚合
	 * @param returnList
	 */
	private void mergeList(List< List<CommentNearbyInfo> > returnList){
		List< List<CommentNearbyInfo> > tempList = new ArrayList< List<CommentNearbyInfo> > ();
		tempList.addAll(returnList);
		
		for(int iter_out =0; iter_out<tempList.size(); iter_out++) {
			boolean flag_merger_out = false; //此次遍历是否进行了合并操作
			for(int iter_inner=iter_out+1; iter_inner<tempList.size(); iter_inner++) {
				List<CommentNearbyInfo> itemList_out = tempList.get(iter_out);
				List<CommentNearbyInfo> itemList_inner = tempList.get(iter_inner);
				if(merge0(itemList_out, itemList_inner) ) {
					//若发生合并，则删掉内层遍历到的元素，内外层的遍历指针都要回到原来的位置
					tempList.remove(itemList_inner);
					flag_merger_out = true;
					--iter_inner;								
				}
			}
			//发生合并，外层指针回到原来位置
			if(flag_merger_out == true) {
				--iter_out;	
			}
		}
		if(tempList.size() != 0) {
			returnList.clear();
			returnList.addAll(tempList);
		}
	}
	
	/**
	 * 合并两个集合，若发生合并，list1为合并后的新集合
	 * @param list1
	 * @param list2
	 * @return 若发生合并，返回TRUE，否则FALSE
	 */
	private boolean merge0(List<CommentNearbyInfo> list1, List<CommentNearbyInfo> list2){
		for(int i=0; i<list1.size();i++) {
			for(int j=0; j<list2.size(); j++){
				CommentNearbyInfo item1 = list1.get(i);
				CommentNearbyInfo item2 = list2.get(j);

				if(mergeAble(item1, item2))  {
					list1.addAll(list2);
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 以经纬度计算距离
	 * @param comm1
	 * @param comm2
	 * @return
	 */
	private double calcDistance(CommentNearbyInfo comm1, CommentNearbyInfo comm2) /*throws Exception*/{
		double distance = 0;
		//TODO
		
		double longitude1 = Double.parseDouble(comm1.getLongitude());
		double latitude1  = Double.parseDouble(comm1.getLatitude());
		double longitude2 = Double.parseDouble(comm2.getLongitude());
		double latitude2  = Double.parseDouble(comm2.getLatitude());
		
		distance = Math.acos((1-((1-Math.cos(Math.toRadians(latitude2)-Math.toRadians(latitude1)))/2+
	             Math.cos(Math.toRadians(latitude1))*Math.cos(Math.toRadians(latitude2))*
	             (1-Math.cos(Math.toRadians(longitude2)-Math.toRadians(longitude1)))/2)*2))*6378137;
		return distance;
	}
}
