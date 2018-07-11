/**
 * @Author nana
 * @Date 18-7-4 下午9:48
 * @Description 学院权限请求
 */

$(function () {
    lineEditable();
    updateStudentInfo();
    updateStudentPassword();
    initialize();
});

//表格数据id对应名字修改
function initialize() {
    var _class = $('#editable_studentClass');
    var _institute = $('#editable_institute');
    var _major = $('#editable_major');
    $.ajax({
        type: "GET",
        url: "/static/schoolInfo/schoolInfo.xml",
        async: false,
        cache: false,
        success: function (data) {
            _class.text(parseInt(_class.text()) + $(data).find("class").find("name").eq(parseInt(_class.text())).text());
            _institute.text(parseInt(_institute.text()) + $(data).find("institute").find("name").eq(parseInt(_institute.text())).text());
            _major.text(parseInt(_major.text()) + $(data).find("major").find("name").eq(parseInt(_major.text())).text());
        }
    });
}

//更新学生信息
function updateStudentInfo() {
    $(".update_student_info").on("click", function () {
        var _name = $("#editable_name");
        var _studentNumber = $("#editable_studentNumber");
        var _studentClass = $("#editable_studentClass");
        var _major = $("#editable_major");
        var _institute = $("#editable_institute");
        var _phone = $("#editable_phone");

        var name = _name.text();
        var studentNumber = _studentNumber.text();
        var studentClass = parseInt(_studentClass.text());
        var major = parseInt(_major.text());
        var institute = parseInt(_institute.text());
        var phone = _phone.text();

        Ewin.confirm({message: "确认要更新选择的数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/student/updateStudentInfo/",
                contentType: 'application/json',
                data: JSON.stringify({
                    name: name,
                    studentNumber: studentNumber,
                    studentClass: studentClass,
                    major: major,
                    institute: institute,
                    phone: phone
                }),
                success: function () {
                    toastr.success("更新成功");
                },
                error: function () {
                    toastr.error("更新失败");
                }
            });
        });
    });
}

//更新学生密码
function updateStudentPassword() {
    $(".update_student_password").on("click", function () {

        var oldPassword = $("#editable_old_password").text();
        var newPassword = $("#editable_new_password").text();

        Ewin.confirm({message: "确认要修改密码吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/student/updateStudentLoginer",
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
    //学生信息表格
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
    $('#editable_studentNumber').editable({
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
    $('#editable_studentClass').editable({
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
                    $(data).find("class").find("name").each(function (index) {
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
    $('#editable_major').editable({
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
}