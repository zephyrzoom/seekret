package com.bocsoft.bocop.mdm.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bocsoft.bocop.mdm.entity.CommentInfo;
import com.bocsoft.bocop.mdm.entity.qo.CommentQo;
import com.bocsoft.bocop.mdm.mapper.ShowCommentMapper;
import com.bocsoft.bocop.mdm.service.ShowCommentService;

@Service
public class ShowCommentServiceImpl implements ShowCommentService{

	@Autowired
	ShowCommentMapper showCommentMapper;
	@Override
	public List<CommentQo> showComment(CommentInfo ci) {
		
		return showCommentMapper.showComment(ci);
		
	}

}
