/**
 * @Author nana
 * @Date 18-8-30 上午11:22
 * @Description 学院权限请求
 */

$(function () {
    lineEditable();
    addAdministrator();
    deleteAdministrator();
    updateAdministrator();
    updateSuperAdministratorInfo();
    updateSuperAdministratorPassword();

    initialize();
});

//表格数据id对应名字修改
function initialize() {
    var _instituteClass = $('.editable_institute');
    $.ajax({
        type: "GET",
        url: "/static/schoolInfo/schoolInfo.xml",
        async: false,
        cache: false,
        success: function (data) {
            var i;
            for (i = 0; i < _instituteClass.length; i++) {
                _instituteClass.eq(i).text(parseInt(_instituteClass.eq(i).text())
                    + $(data).find("institute").find("name").eq(parseInt(_instituteClass.eq(i).text())).text());
            }
        }
    });
}

//更新管理员
function updateAdministrator() {
    $(".update_button").on("click", function () {
        var uid = $(this).parent().siblings("input").val();
        var allTd = $(this).parent().siblings("td");

        var username = allTd.eq(1).text();
        var name = allTd.eq(2).text();
        var institute = allTd.eq(3).text().substr(0, 1);
        var phone = allTd.eq(4).text();

        Ewin.confirm({message: "确认要更新选择的数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/superAdministrator/updateAdministrator/",
                contentType: 'application/json',
                data: JSON.stringify({
                    loginer: {
                        uid: uid,
                        username: username
                    },
                    administrator: {
                        name: name,
                        institute: institute,
                        phone: phone
                    }
                }),
                success: function (data) {
                    toastr.success("更新成功");
                    data = JSON.parse(data);
                    allTd[1].text(data.username);
                    allTd[2].text(data.name);
                    allTd[3].text(data.institute);
                    allTd[4].text(data.phone);
                },
                error: function () {
                    toastr.error("更新失败");
                }
            });
        });
    });
}

//删除管理员
function deleteAdministrator() {
    //注册删除按钮的事件
    $(".delete_button").on("click", function () {
        var tr = $(this).parent().parent();
        var uid = $(this).parent().siblings("input").val();
        Ewin.confirm({message: "确认要删除选择的数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "DELETE",
                url: "/superAdministrator/deleteAdministrator/" + uid,
                success: function () {
                    toastr.success("删除成功");
                    tr.remove();
                },
                error: function () {
                    toastr.error("删除失败");
                }
            });
        });
    });
}

//创建管理员
function addAdministrator() {

    var verify = true;
    var _table = $("#administratorTable");
    var foreach_count = parseInt($("#administratorTable tr:last").find("td:first").text()) + 1;
    var _moral = $("#addAdministratorModal");

    $("#submitAdd").on("click", function () {
        var _username = $("#username");
        var _password = $("#password");
        var _name = $("#name");
        var _institute = $("#institute");

        var usernameParent = _username.parent();
        var passwordParent = _password.parent();
        var nameParent = _name.parent();
        var instituteParent = _institute.parent();

        var username = _username.text();
        var password = _password.text();
        var name = _name.text();
        var institute = parseInt(_institute.text());
        var phone = $("#phone").text();

        if (username.length === 0) {
            usernameParent.addClass("has-error");
            verify = false;
        }
        if (password.length === 0) {
            passwordParent.addClass("has-error");
            verify = false;
        }
        if (name.length === 0) {
            nameParent.addClass("has-error");
            verify = false;
        }
        if (institute.length === 0) {
            instituteParent.addClass("has-error");
            verify = false;
        }
        if (verify) {
            $.ajax({
                type: "POST",
                url: "/superAdministrator/addAdministrator",
                contentType: 'application/json',
                data: JSON.stringify({
                    loginer: {
                        username: username,
                        password: password
                    },
                    administrator: {
                        name: name,
                        institute: institute,
                        phone: phone
                    }
                }),
                success: function (data) {
                    data = JSON.parse(data);
                    $.ajax({
                        type: "GET",
                        url: "/static/schoolInfo/schoolInfo.xml",
                        async: false,
                        cache: false,
                        success: function (data1) {
                            var trHTML = "<tr>"
                                + "<td>" + foreach_count + "</td>"
                                + "<td>" + data.username + "</td>"
                                + "<td>" + data.name + "</td>"
                                + "<td>"
                                + parseInt(data.institute) + $(data1).find("major").find("name").eq(parseInt(data.institute)).text()
                                + "</td>"
                                + "<td>" + data.phone + "</td>"
                                + "<input type='hidden' value=" + data.uid + ">"
                                + "<td>"
                                + "<button type='button' class='btn btn-warning update_button'>更新</button>"
                                + " <button type='button' class='btn btn-danger delete_button'>删除</button>"
                                + "</td>"
                                + "</tr>";
                            _moral.modal("hide");
                            _table.append(trHTML);
                            toastr.success("创建成功");
                        }
                    });
                },
                error: function () {
                    toastr.error("创建失败");
                }
            });
        }
    });

    $(".form-group").on("click", function () {
        $(this).removeClass("has-error");
        verify = true;
    });
}

//更新超级管理员信息
function updateSuperAdministratorInfo() {
    $(".update_superAdministrator_info").on("click", function () {
        var _name = $("#editable_name");
        var _phone = $("#editable_phone");

        var name = _name.text();
        var phone = _phone.text();

        Ewin.confirm({message: "确认要更新选择的数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/superAdministrator/updateSuperAdministratorInfo/",
                contentType: 'application/json',
                data: JSON.stringify({
                    name: name,
                    phone: phone
                }),
                success: function (data) {
                    toastr.success("更新成功");
                    data = JSON.parse(data);
                    _name.text(data.name);
                    _phone.text(data.phone);
                },
                error: function () {
                    toastr.error("更新失败");
                }
            });
        });
    });
}

//更新超级管理员密码
function updateSuperAdministratorPassword() {
    $(".update_administrator_password").on("click", function () {

        var oldPassword = $("#editable_old_password").text();
        var newPassword = $("#editable_new_password").text();

        Ewin.confirm({message: "确认要修改密码吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/superAdministrator/updateSuperAdministratorLoginer",
                contentType: 'application/json',
                data: JSON.stringify({
                    username: oldPassword, //username做老密码校验用
                    password: newPassword  //新密码
                }),
                success: function (data) {
                    data = JSON.parse(data);
                    if (data === 200) {
                        toastr.success("修改成功");
                        $("#editable_old_password").text('empty');
                        $("#editable_new_password").text('empty');
                    } else {
                        toastr.error("修改失败");
                    }
                }
            });
        });
    });
}

//表格编辑
function lineEditable() {
    $('.editable_username').editable({
        mode: "inline",              //编辑框的模式：支持popup和inline两种模式，默认是popup
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_name').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
        }
    });
    $('.editable_institute').editable({
        mode: "inline",
        type: "select",
        source: function () {
            var result = [];
            $.ajax({
                type: "GET",
                url: "/static/schoolInfo/schoolInfo.xml",
                async: false,
                cache: false,
                success: function (data) {
                    $(data).find("institute").find("name").each(function (index) {
                        result.push({value: index, text: index + $(this).text()})
                    });
                }
            });
            return result;
        },
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_phone').editable({
        mode: "inline",
        validate: function (value) {
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });

    //超级管理员信息表格
    $('#editable_old_password').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
        }
    });
    $('#editable_new_password').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
        }
    });
    $('#editable_name').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
        }
    });
    $('#editable_phone').editable({
        mode: "inline",
        validate: function (value) {
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });

    //管理员创建表格
    $('#username').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
        }
    });
    $('#password').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
        }
    });
    $('#name').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
        }
    });
    $('#institute').editable({
        mode: "inline",
        type: "select",
        source: function () {
            var result = [];
            $.ajax({
                type: "GET",
                url: "/static/schoolInfo/schoolInfo.xml",
                async: false,
                cache: false,
                success: function (data) {
                    $(data).find("institute").find("name").each(function (index) {
                        result.push({value: index, text: index + $(this).text()})
                    });
                }
            });
            return result;
        },
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
        }
    });
    $('#phone').editable({
        mode: "inline",
        validate: function (value) {
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
}