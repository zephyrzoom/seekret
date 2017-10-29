package com.bocsoft.bocop.mdm.service;

import com.bocsoft.bocop.mdm.entity.qo.LocationQo;
import com.bocsoft.bocop.mdm.entity.qo.ResponseQo;

/**
 * @author dzg7151
 *
 */
public interface GetCommentsNearbyService {

	public ResponseQo getComments(LocationQo lacation);
}
