package com.thinkgem.jeesite.modules.cms.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.cms.entity.FrontUser;


/**
 * Created by shaojie.cui on 2018/1/17.
 */
@MyBatisDao
public interface FrontUserDao extends CrudDao<FrontUser> {

}
