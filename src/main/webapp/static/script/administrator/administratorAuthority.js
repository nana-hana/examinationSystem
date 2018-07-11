/**
 * @Author nana
 * @Date 18-7-4 下午9:48
 * @Description 学院权限请求
 */

$(function () {
    lineEditable();
    addTeacher();
    deleteTeacher();
    updateTeacher();
    updateAdministratorInfo();
    updateAdministratorPassword();

    passRequest();
    refuseRequest();

    initialize();
});

//表格数据id对应名字修改
function initialize() {
    var _institute = $('#editable_institute');
    var _instituteClass = $('.editable_institute');
    var _majorClass = $('.editable_major');
    var _examinationStudentClass = $('.editable_examinationStudentClass');
    $.ajax({
        type: "GET",
        url: "/static/schoolInfo/schoolInfo.xml",
        async: false,
        cache: false,
        success: function (data) {
            _institute.text(parseInt(_institute.text())
                + $(data).find("institute").find("name").eq(parseInt(_institute.text())).text());
            var i;
            for (i = 0; i < _instituteClass.length; i++) {
                _instituteClass.eq(i).text(parseInt(_instituteClass.eq(i).text())
                    + $(data).find("institute").find("name").eq(parseInt(_instituteClass.eq(i).text())).text());
            }
            for (i = 0; i < _majorClass.length; i++) {
                _majorClass.eq(i).text(parseInt(_majorClass.eq(i).text())
                    + $(data).find("major").find("name").eq(parseInt(_majorClass.eq(i).text())).text());
            }
            for (i = 0; i < _examinationStudentClass.length; i++) {
                _examinationStudentClass.eq(i).text(parseInt(_examinationStudentClass.eq(i).text())
                    + $(data).find("class").find("name").eq(parseInt(_examinationStudentClass.eq(i).text())).text());
            }
        }
    });
}

//审批请求通过
function passRequest() {
    $(".pass_button").on("click", function () {
        var taskId = $(this).parent().siblings("input").val();
        var _td = $(this).parent();
        var status = 1;
        var _modal = $("#addOtherModal");
        _modal.modal();
        $("#submitApprovalExamination").on("click", function () {
            var examTime = $('#editable_examTime').text() + ":00";
            var examPlace = $('#editable_examPlace').text();
            var teacherNumber = $('#editable_examinationTeacherNumber').text();
            var institute = parseInt($('#editable_examinationInstitute').text());
            Ewin.confirm({message: "确认要进行该操作吗？"}).on(function (e) {
                if (!e) {
                    return;
                }
                $.ajax({
                    type: "POST",
                    url: "/administrator/approvalRequest",
                    contentType: 'application/json',
                    data: JSON.stringify({
                        //审批状态信息，考试开始时间，考试地点，监考老师编号，归属的学院
                        taskId: taskId,
                        status: status,
                        examinationExternal: {
                            //考试事件，地点，监考老师，学院
                            examTime: examTime,
                            examPlace: examPlace,
                            teacherNumber: teacherNumber,
                            institute: institute
                        }
                    }),
                    success: function () {
                        _modal.modal('hide');
                        _td.empty();
                        _td.append("<span class='label label-success'>通过</span>");
                        toastr.success("更新成功");
                    },
                    error: function () {
                        _modal.modal('hide');
                        toastr.error("更新失败");
                    }
                });
            });
        });
    });
}

//审批请求拒绝
function refuseRequest() {
    $(".refuse_button").on("click", function () {
        var taskId = $(this).parent().siblings("input").val();
        var _td = $(this).parent();
        var status = 2;//代表拒绝
        Ewin.confirm({message: "确认要进行该操作吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "POST",
                url: "/administrator/approvalRequest",
                contentType: 'application/json',
                data: JSON.stringify({
                    //审批状态信息，考试开始时间，考试地点，监考老师编号，归属的学院
                    taskId: taskId,
                    status: status,
                    examinationExternal: {}
                }),
                success: function () {
                    toastr.success("操作成功");
                    _td.empty();
                    _td.append("<span class='label label-danger'>拒绝</span>");
                },
                error: function () {
                    toastr.error("操作失败");
                }
            });
        });
    });
}

//更新教师
function updateTeacher() {
    $(".update_button").on("click", function () {
        var uid = $(this).parent().siblings("input").val();
        var allTd = $(this).parent().siblings("td");

        var username = allTd.eq(1).text();
        var name = allTd.eq(2).text();
        var teacherNumber = allTd.eq(3).text();
        var major = allTd.eq(4).text();
        var institute = allTd.eq(5).text();
        var phone = allTd.eq(6).text();

        Ewin.confirm({message: "确认要更新选择的数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/administrator/updateTeacher/",
                contentType: 'application/json',
                data: JSON.stringify({
                    loginer: {
                        uid: uid,
                        username: username
                    },
                    teacher: {
                        name: name,
                        teacherNumber: teacherNumber,
                        major: major,
                        institute: institute,
                        phone: phone
                    }
                }),
                success: function (data) {
                    toastr.success("更新成功");
                    data = JSON.parse(data);
                    allTd[1].text(data.username);
                    allTd[2].text(data.name);
                    allTd[3].text(data.teacherNumber);
                    allTd[4].text(data.major);
                    allTd[5].text(data.institute);
                    allTd[6].text(data.phone);
                },
                error: function () {
                    toastr.error("更新失败");
                }
            });
        });
    });
}

//删除教师
function deleteTeacher() {
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
                url: "/administrator/deleteTeacher/" + uid,
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

//创建教师
function addTeacher() {

    var verify = true;
    var _table = $("#teacherTable");
    var foreach_count = parseInt($("#teacherTable tr:last").find("td:first").text()) + 1;
    var _moral = $("#addTeacherModal");

    $("#submitAdd").on("click", function () {
        var _username = $("#username");
        var _password = $("#password");
        var _name = $("#name");
        var _teacherNumber = $("#teacherNumber");
        var _major = $("#major");
        var _institute = $("#institute");

        var usernameParent = _username.parent();
        var passwordParent = _password.parent();
        var nameParent = _name.parent();
        var teacherNumberParent = _teacherNumber.parent();
        var majorParent = _major.parent();
        var instituteParent = _institute.parent();

        var username = _username.text();
        var password = _password.text();
        var name = _name.text();
        var teacherNumber = _teacherNumber.text();
        var major = parseInt(_major.text());
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
        if (teacherNumber.length === 0) {
            teacherNumberParent.addClass("has-error");
            verify = false;
        }
        if (major.length === 0) {
            majorParent.addClass("has-error");
            verify = false;
        }
        if (institute.length === 0) {
            instituteParent.addClass("has-error");
            verify = false;
        }
        if (verify) {
            $.ajax({
                type: "POST",
                url: "/administrator/addTeacher",
                contentType: 'application/json',
                data: JSON.stringify({
                    loginer: {
                        username: username,
                        password: password
                    },
                    teacher: {
                        name: name,
                        teacherNumber: teacherNumber,
                        major: major,
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
                                + "<td>" + data.teacherNumber + "</td>"
                                + "<td>"
                                + parseInt(data.major) + $(data1).find("major").find("name").eq(parseInt(data.major)).text()
                                + "</td>"
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

//更新管理员信息
function updateAdministratorInfo() {
    $(".update_administrator_info").on("click", function () {
        var _name = $("#editable_name");
        var _institute = $("#editable_institute");
        var _phone = $("#editable_phone");

        var name = _name.text();
        var institute = _institute.text();
        var phone = _phone.text();

        Ewin.confirm({message: "确认要更新选择的数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/administrator/updateAdministratorInfo/",
                contentType: 'application/json',
                data: JSON.stringify({
                    name: name,
                    institute: institute,
                    phone: phone
                }),
                success: function (data) {
                    toastr.success("更新成功");
                    data = JSON.parse(data);
                    _name.text(data.name);
                    _institute.text(data.institute);
                    _phone.text(data.phone);
                },
                error: function () {
                    toastr.error("更新失败");
                }
            });
        });
    });
}

//更新管理员密码
function updateAdministratorPassword() {
    $(".update_administrator_password").on("click", function () {

        var oldPassword = $("#editable_old_password").text();
        var newPassword = $("#editable_new_password").text();

        Ewin.confirm({message: "确认要修改密码吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/administrator/updateAdministratorLoginer",
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
        // type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等
        // title: "用户名",              //编辑框的标题
        // disabled: false,             //是否禁用编辑
        // emptytext: "空文本",          //空值的默认文本
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
    $('.editable_teacherNumber').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_major').editable({
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
                    $(data).find("major").find("name").each(function (index) {
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

    //管理员信息表格
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
    $('#editable_teacherNumber').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('#editable_major').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('#editable_institute').editable({
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
    $('#editable_phone').editable({
        mode: "inline",
        validate: function (value) {
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });

    //考试外在信息表格
    $('#editable_examTime').editable({
        mode: "inline",
        combodate: {
            firstItem: 'name',
            minYear: 2018,
            maxYear: 2020
        },
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
        }
    });
    $('#editable_examPlace').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
        }
    });
    $('#editable_examinationTeacherNumber').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('#editable_examinationInstitute').editable({
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

    //教师创建表格
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
    $('#teacherNumber').editable({
        mode: "inline",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('#major').editable({
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
                    $(data).find("major").find("name").each(function (index) {
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