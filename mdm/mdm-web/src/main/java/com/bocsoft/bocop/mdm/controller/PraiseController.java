package com.bocsoft.bocop.mdm.controller;




//import com.bocsoft.bocop.lc.entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocsoft.bocop.mdm.controller.util.ControllerConstants;
import com.bocsoft.bocop.mdm.controller.util.LcResponse;
import com.bocsoft.bocop.mdm.entity.CommentInfo;
import com.bocsoft.bocop.mdm.service.PraiseService;


@Controller
@RequestMapping("/praise")
public class PraiseController {

	@Autowired
	PraiseService praiseService;
	ControllerConstants controllerConstants;
	@RequestMapping(method= RequestMethod.POST,value="/add")
	@ResponseBody
	public LcResponse addPraise(@RequestBody CommentInfo cm){
		LcResponse lcr = new LcResponse();
		boolean bl = praiseService.addPraise(cm.getCommentId());
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
	@RequestMapping(method= RequestMethod.POST,value="/sub")
	@ResponseBody
	public LcResponse subPraise(@RequestBody CommentInfo cm){
		LcResponse lcr = new LcResponse();
		boolean bl = praiseService.subPraise(cm.getCommentId());
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
