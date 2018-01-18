package com.thinkgem.jeesite.modules.cms.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.cms.dao.ArticleDao;
import com.thinkgem.jeesite.modules.cms.dao.FrontUserDao;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.cms.entity.FrontUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by shaojie.cui on 2018/1/17.
 */
@Service
@Transactional(readOnly = true)
public class FrontUserService extends CrudService<FrontUserDao, FrontUser> {
    @Autowired
    private FrontUserDao frontUserDao;

    public FrontUser getUserByregisterEmail(String registerEmail) {
        FrontUser frontUser = new FrontUser();
        frontUser.setEmail(registerEmail);
        return frontUserDao.get(frontUser);
    }
}
