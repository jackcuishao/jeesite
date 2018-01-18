package com.thinkgem.jeesite.modules.cms.web.front;

import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.cms.entity.Comment;
import com.thinkgem.jeesite.modules.cms.entity.FrontUser;
import com.thinkgem.jeesite.modules.cms.entity.Site;
import com.thinkgem.jeesite.modules.cms.service.FrontUserService;
import com.thinkgem.jeesite.modules.cms.utils.CmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by shaojie.cui on 2018/1/17.
 */
@Controller
@RequestMapping(value = "${frontPath}/register")
public class FrontLoginController {
    @Autowired
    private FrontUserService frontUserService;
    /**
     * 网站首页
     */
    @RequestMapping
    public String index(Model model) {
        Site site = CmsUtils.getSite(Site.defaultSiteId());
        model.addAttribute("site", site);
        return "modules/cms/front/themes/"+site.getTheme()+"/register";
    }
    @ResponseBody
    @RequestMapping(value = "/checkEmail")
    public String checkEmail(String email, HttpServletResponse response) throws IOException {
        System.out.println(222222);
        if (email !=null && frontUserService.getUserByregisterEmail(email) == null) {
            return "true";
        }
        return "false";
    }
    @ResponseBody
    @RequestMapping(value = "/doregister",method = RequestMethod.POST)
    public String doregister(FrontUser frontUser, String validateCode,HttpServletRequest request) {
        System.out.println(11111);
        if (StringUtils.isNotBlank(validateCode)){
            if (ValidateCodeServlet.validate(request, validateCode)){
                return "{result:1, message:'提交成功。'}";
            }else{
                return "{result:2, message:'验证码不正确。'}";
            }
        }else{
            return "{result:2, message:'验证码不能为空。'}";
        }
    }


}
