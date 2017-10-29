package com.bocsoft.bocop.mdm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocsoft.bocop.mdm.controller.util.ControllerConstants;
import com.bocsoft.bocop.mdm.controller.util.LcResponse;
import com.bocsoft.bocop.mdm.entity.CommentInfo;
import com.bocsoft.bocop.mdm.service.OtherCommentService;


@Controller
@RequestMapping("/otherComment")
public class OtherCommentController {

	@Autowired
	OtherCommentService otherCommentService;
	ControllerConstants controllerConstants;
	@RequestMapping(method= RequestMethod.POST)
	@ResponseBody
	public LcResponse addPraise(@RequestBody CommentInfo ci){
		LcResponse lcr = new LcResponse();
		boolean bl = otherCommentService.otherComment(ci);
		if(bl == true){
			lcr.setStat(controllerConstants.LC_SUCCESS);
			lcr.setResult("更新成功");
			return lcr;
		}
		else{
			lcr.setStat(controllerConstants.LC_FAILURE);
			lcr.setResult("更新失败");
			return lcr;
		}
	
	}
}
