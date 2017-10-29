package com.bocsoft.bocop.mdm.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocsoft.bocop.mdm.controller.util.ControllerConstants;
import com.bocsoft.bocop.mdm.controller.util.LcResponse;
import com.bocsoft.bocop.mdm.entity.CommentInfo;
import com.bocsoft.bocop.mdm.entity.qo.CommentQo;
import com.bocsoft.bocop.mdm.mapper.ShowCommentMapper;
import com.bocsoft.bocop.mdm.service.ShowCommentService;
@Controller
@RequestMapping("/showComment")
public class ShowCommentController {
		@Autowired
		ShowCommentService showCommentService;
		ControllerConstants controllerConstants;
		ShowCommentMapper showCommentMapper;
		@RequestMapping(method= RequestMethod.POST)
		@ResponseBody
		public LcResponse showComment(@RequestBody CommentInfo cm){
			LcResponse lcr = new LcResponse();
			List<CommentQo> list_cm = new LinkedList<>();
			list_cm = showCommentService.showComment(cm);
			
			if(list_cm.size() != 0){
				lcr.setStat(controllerConstants.LC_SUCCESS);
				lcr.setDesc("成功");
				lcr.setResult(list_cm);
				
			}
			else{
				lcr.setStat(controllerConstants.LC_FAILURE);
				lcr.setDesc("没有相关评论");
				lcr.setResult(list_cm);
			}
			
			 return lcr;
		}
}
