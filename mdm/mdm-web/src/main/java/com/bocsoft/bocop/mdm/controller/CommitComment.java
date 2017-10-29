package com.bocsoft.bocop.mdm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.bocsoft.bocop.mdm.controller.util.LcResponse;
import com.bocsoft.bocop.mdm.entity.qo.CommentQo;
import com.bocsoft.bocop.mdm.entity.qo.ResponseQo;
import com.bocsoft.bocop.mdm.service.CommitCommentService;
import com.bocsoft.bocop.mdm.utils.CommonConstants;

/**
 * 提交评论的接口
 * @author dzg7151
 *
 */

@Controller
public class CommitComment {
	
	@Autowired
	private CommitCommentService ccService;
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST, value="/commitcomment")
	public LcResponse commit(@RequestBody CommentQo cq) {
		LcResponse resp = new LcResponse();
		
		ResponseQo serviceResponse = ccService.commit(cq);
		if(serviceResponse == null || serviceResponse.getDesc() == null) {
			resp.setStat(CommonConstants.LC_FAILURE);
			resp.setDesc("失败");
		}
		switch(serviceResponse.getDesc()) {
		case CommonConstants.LC_SUCCESS:
			resp.setStat(CommonConstants.LC_SUCCESS);
			resp.setDesc("成功");
			resp.setResult(serviceResponse.getResult());
			break;			
		default:
			resp.setStat(CommonConstants.LC_FAILURE);
			resp.setDesc("失败");
		}
		return resp;
	}
}
