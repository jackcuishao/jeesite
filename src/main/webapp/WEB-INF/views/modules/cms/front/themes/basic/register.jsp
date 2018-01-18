<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>用户注册</title>
    <meta name="decorator" content="cms_default_${site.theme}"/>
    <meta name="description" content="JeeSite ${site.description}" />
    <meta name="keywords" content="JeeSite ${site.keywords}" />
    <style type="text/css">
        html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
        .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
            -webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
        .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
        .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
        .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
        .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
        .header{height:80px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
        label.error{background:none;width:270px;font-weight:normal;color:red;margin:0;}
    </style>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#registerForm").validate({
                rules: {
                    password:{
                        minlength: 6
                    },
                    confirmNewPassword: {
                        required: true,
                        equalTo: "#password"
                    },
                    email:{
                        email:true,
                        remote: "${ctx}/register/checkEmail"
                    },
                    validateCode: {required: true,remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
                },
                messages: {
                    name: {required:"用户名不能为空"},
                    email:{required:"邮箱不能为空",email:"请输入正确的邮箱格式"},
                    password:{required:"密码不能为空",minlength:"密码不能小于6位"},
                    confirmNewPassword: {required:"确认密码不能为空",equalTo: "输入与上面相同的密码"},
                    validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
                },
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                }
            });
        });
    </script>
    </head>
<body>
<div class=" center">
    <form id="registerForm" class="form-signin" action="${ctx}/register/doregister" method="post">
        <label class="input-label" for="email">用户名</label>
        <input type="text" id="name" name="name" class="input-block-level required">

        <label class="input-label" for="email">邮箱</label>
        <input type="text" id="email" name="email" class="input-block-level required">

        <label class="input-label" for="password">密码</label>
        <input type="password" id="password" name="password" class="input-block-level required">

        <label class="input-label" for="confirmNewPassword">重复密码</label>
        <input type="password" id="confirmNewPassword" name="confirmNewPassword" class="input-block-level required equalTo" data-equalTo="#password">

        <label class="input-label mid" for="validateCode">验证码</label>
        <sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
        <label></label>
        <input class="btn btn-large btn-primary" type="submit" value="注 册"/>&nbsp;&nbsp;&nbsp;&nbsp;
        <input class="btn btn-large" type="button" onclick="history.go(-1)" value="取 消"/>
    </form>
</div>
</body>
</html>