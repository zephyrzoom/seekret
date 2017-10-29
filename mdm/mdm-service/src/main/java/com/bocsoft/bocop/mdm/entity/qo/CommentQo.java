package com.bocsoft.bocop.mdm.entity.qo;

/**
 * 点赞时的上送字段
 * @author dzg7151
 *
 */
public class CommentQo {

	/**id*/
	private int commentId;
	
	/**评论内容*/
	private String content;
	
	/**经度*/
	private String longitude;
	
	/**纬度*/
	private String latitude;
	
	/**点赞次数*/
	private String praiseNum;
	
	/**提交日期*/
	private String commitDate;

	/**
	 * @return the commentId
	 */
	

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
