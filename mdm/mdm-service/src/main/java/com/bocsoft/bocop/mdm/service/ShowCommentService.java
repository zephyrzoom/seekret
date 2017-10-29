package com.bocsoft.bocop.mdm.service;

import java.util.List;

import com.bocsoft.bocop.mdm.entity.CommentInfo;
import com.bocsoft.bocop.mdm.entity.qo.CommentQo;


public interface ShowCommentService {

	public List<CommentQo> showComment(CommentInfo ci);
}
