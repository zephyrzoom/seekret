package com.bocsoft.bocop.mdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bocsoft.bocop.mdm.mapper.PraiseMapper;
import com.bocsoft.bocop.mdm.service.PraiseService;



@Service
public class PraiseServiceImpl implements PraiseService {

	
	@Autowired 
	private PraiseMapper praiseMapper;
	
	@Override
	public boolean addPraise(String id) {
		
		boolean bl = praiseMapper.addPraise(id);
		return bl;
		
	}

	@Override
	public boolean subPraise(String id) {
		
		boolean bl = praiseMapper.subPraise(id);
		return bl;
	}

	

}
