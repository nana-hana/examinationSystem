/**
 * @Author nana
 * @Date 18-6-22 下午6:34
 * @Description 登陆本地校验及远程校验
 */
$(function () {
    var verify = true;
    $("#submitId").on("click", function () {
        var user = $("#user").val();
        var pwd = $("#pwd").val();
        if (user.length === 0) {
            $("#user_alert").css("visibility", "visible");
            verify = false;
        }
        if (pwd.length === 0) {
            $("#pwd_alert").text("请输入密码").css("visibility", "visible");
            verify = false;
        }
        if (verify) {
            $.ajax({
                type: "POST",
                url: "/login/check",
                data: {"username": user, "password": pwd},
                success: function (data) {
                    data = JSON.parse(data);
                    var a = data.roleList[0].rid;
                    if (data != null) {
                        switch (data.roleList[0].rid) {
                            case 0:
                                setTimeout("window.location.href='administrator'", 2000);
                                break;
                            case 1:
                                setTimeout("window.location.href='teacher'", 2000);
                                break;
                            case 2:
                                setTimeout("window.location.href='student'", 2000);
                                break;
                        }
                    } else {
                        $("#pwd_alert").text("用户名密码不匹配，请重新输入").css("visibility", "visible");
                    }
                }
            });
        }
    });
    $("#user").on("click", function () {
        $("#user_alert").css("visibility", "hidden");
        verify = true;
    });
    $("#pwd").on("click", function () {
        $("#pwd_alert").css("visibility", "hidden");
        verify = true;
    });
});