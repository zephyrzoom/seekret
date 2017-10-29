package com.bocsoft.bocop.mdm.entity;

import java.util.List;

/**
 * @author dzg7151
 *
 */
public class CommentNearbyInfo {

	/**评论内容*/
	private String content;
	
	/**id*/
	private String commentId;
	
	/**本条评论的回应*/
	private List<String> reply;
	
	/** 经度*/
	private String longitude;
	
	/**纬度*/
	private String latitude;

	/**点赞次数*/
	private String praiseNum;
	
	/**和用户之间的距离*/
	private String distance;
	
	
	/**评论提交日期*/
	private String commitDate;
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the reply
	 */
	public List<String> getReply() {
		return reply;
	}

	/**
	 * @param reply the reply to set
	 */
	public void setReply(List<String> reply) {
		this.reply = reply;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the commentId
	 */
	public String getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	/**
	 * @return the praiseNum
	 */
	public String getPraiseNum() {
		return praiseNum;
	}

	/**
	 * @param praiseNum the praiseNum to set
	 */
	public void setPraiseNum(String praiseNum) {
		this.praiseNum = praiseNum;
	}

	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}

	/**
	 * @return the commitDate
	 */
	public String getCommitDate() {
		return commitDate;
	}

	/**
	 * @param commitDate the commitDate to set
	 */
	public void setCommitDate(String commitDate) {
		this.commitDate = commitDate;
	}
}
