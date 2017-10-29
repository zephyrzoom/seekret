package com.bocsoft.bocop.mdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bocsoft.bocop.mdm.entity.CommentInfo;
import com.bocsoft.bocop.mdm.mapper.OtherCommentMapper;
import com.bocsoft.bocop.mdm.service.OtherCommentService;
@Service
public class OtherCommentServiceImpl implements OtherCommentService{

	@Autowired
	OtherCommentMapper otherCommentMapper;
	@Override
	public boolean otherComment(CommentInfo ci) {
		
		boolean bl = otherCommentMapper.otherComment(ci);
		return bl;
	}

}
