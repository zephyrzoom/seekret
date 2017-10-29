package com.bocsoft.bocop.mdm.mapper;

import java.util.List;

import com.bocsoft.bocop.mdm.entity.CommentNearbyInfo;
import com.bocsoft.bocop.mdm.entity.qo.LocationQo;

/**
 * @author dzg7151
 *
 */
public interface GetCommentsNearbyMapper {
	
	public List<CommentNearbyInfo> getComments(LocationQo location);
}
