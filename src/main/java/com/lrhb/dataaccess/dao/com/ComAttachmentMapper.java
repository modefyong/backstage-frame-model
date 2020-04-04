package com.lrhb.dataaccess.dao.com;

import com.lrhb.dataaccess.model.com.ComAttachment;

public interface ComAttachmentMapper {	

    int insert(ComAttachment comAttachment);

	int update(ComAttachment comAttachment);

	ComAttachment getByAtStaffCode(String stId);

	int delete(String id);
  
}