package com.bocsoft.bocop.mdm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocsoft.bocop.mdm.controller.util.LcResponse;
import com.bocsoft.bocop.mdm.entity.qo.LocationQo;
import com.bocsoft.bocop.mdm.entity.qo.ResponseQo;
import com.bocsoft.bocop.mdm.service.GetCommentsNearbyService;
import com.bocsoft.bocop.mdm.utils.CommonConstants;

/**
 * @author dzg7151
 * 显示附近（5公里内的）评论
 */

@Controller
public class CommentsNearby {
	
	@Autowired
	private GetCommentsNearbyService gcnService;
	
	@RequestMapping(method=RequestMethod.POST, value="/getcommentsnearby")
	@ResponseBody
	public LcResponse getComments(@RequestBody LocationQo cnq) {
		
		LcResponse resp = new LcResponse();
		ResponseQo resultResp = gcnService.getComments(cnq);
		if(resultResp == null || resultResp.getResult() == null){
			resp.setStat(CommonConstants.LC_FAILURE);
			resp.setDesc("失败");
		}else{
			resp.setStat(CommonConstants.LC_SUCCESS);
			resp.setResult(resultResp.getResult());
		}
		return resp;
	}
}
