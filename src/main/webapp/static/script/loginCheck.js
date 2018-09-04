/**
 * @Author nana
 * @Date 18-6-22 下午6:34
 * @Description 登陆本地校验及远程校验
 */
$(function () {
    var verify = true;
    $("#submitId").on("click", function () {
        var _user = $("#user");
        var _pwd = $("#pwd");
        var user = _user.val();
        var pwd = _pwd.val();
        var userParent = _user.parent();
        var pwdParent = _pwd.parent();
        if (user.length === 0) {
            userParent.addClass("has-error");
            verify = false;
        }
        if (pwd.length === 0) {
            pwdParent.addClass("has-error");
            verify = false;
        }
        if (verify) {
            $.ajax({
                type: "POST",
                url: "/login/check",
                data: {"username": user, "password": pwd},
                success: function (data) {
                    if (data !== "") {
                        toastr.success('登陆成功');
                        data = JSON.parse(data);
                        switch (data.roleList[0].rid) {
                            case 0:
                                setTimeout("window.location.href = '/administrator/administratorTeacherManage'", 2000);
                                break;
                            case 1:
                                setTimeout("window.location.href = '/teacher/teacherStudentManage'", 2000);
                                break;
                            case 2:
                                setTimeout("window.location.href = '/student/studentSelfInfo'", 2000);
                                break;
                            case 3:
                                setTimeout("window.location.href = '/superAdministrator/administratorManage'", 2000);
                                break;
                        }
                    } else {
                        toastr.error("账号密码错误");
                        userParent.addClass("has-error");
                        pwdParent.addClass("has-error");
                    }
                }
            });
        }
    });

    $(".form-group").on("click", function () {
        $(this).removeClass("has-error");
        verify = true;
    });
});