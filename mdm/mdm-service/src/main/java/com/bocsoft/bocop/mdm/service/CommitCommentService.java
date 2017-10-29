/**
 * @author dzg7151
 * @time 下午1:49:07
 */
package com.bocsoft.bocop.mdm.service;

import com.bocsoft.bocop.mdm.entity.qo.CommentQo;
import com.bocsoft.bocop.mdm.entity.qo.ResponseQo;

public interface CommitCommentService {

	public ResponseQo commit(CommentQo cq);
}
