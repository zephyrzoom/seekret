package com.bocsoft.bocop.mdm.service.impl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bocsoft.bocop.mdm.entity.qo.CommentQo;
import com.bocsoft.bocop.mdm.entity.qo.ResponseQo;
import com.bocsoft.bocop.mdm.mapper.CommitCommentMapper;
import com.bocsoft.bocop.mdm.service.CommitCommentService;
import com.bocsoft.bocop.mdm.utils.CommonConstants;

/**
 * @author dzg7151
 *
 */
@Service
public class CommitCommentServiceImpl implements CommitCommentService{

	@Autowired
	private CommitCommentMapper ccMapper;
	
	@Override
	public ResponseQo commit(CommentQo cq) {
		ResponseQo resp = new ResponseQo();
		if(cq == null || cq.getContent() == null) {
			resp.setDesc(CommonConstants.LC_FAILURE);
		}
		
		Date date = new Date(System.currentTimeMillis());
		cq.setCommitDate(date.toString());
		
		try{
			ccMapper.commit(cq);			
			resp.setDesc(CommonConstants.LC_SUCCESS);
			resp.setResult(cq);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

}
