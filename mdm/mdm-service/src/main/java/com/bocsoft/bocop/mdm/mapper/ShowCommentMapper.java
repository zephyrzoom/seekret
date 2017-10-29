package com.bocsoft.bocop.mdm.mapper;

import java.util.List;

import com.bocsoft.bocop.mdm.entity.CommentInfo;
import com.bocsoft.bocop.mdm.entity.qo.CommentQo;

public interface ShowCommentMapper {

	public  List<CommentQo> showComment(CommentInfo cmf);
}
