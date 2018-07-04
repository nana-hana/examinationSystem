/**
 * @Author nana
 * @Date 18-7-4 下午1:16
 * @Description 教师权限请求
 */
$(function () {
    lineEditable();
    addStudent();
    deleteStudent();
    updateStudent();
});

//更新学生
function updateStudent() {
    $(".update_button").on("click", function () {
        var uid = $(this).parent().siblings("input").val();
        var allTd = $(this).parent().siblings("td");

        var username = allTd.eq(1).text();
        var name = allTd.eq(2).text();
        var studentNumber = allTd.eq(3).text();
        var studentClass = allTd.eq(4).text();
        var major = allTd.eq(5).text();
        var institute = allTd.eq(6).text();
        var phone = allTd.eq(7).text();

        Ewin.confirm({message: "确认要更新选择的数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/teacher/updateStudent/",
                contentType: 'application/json',
                data: JSON.stringify({
                    loginer: {
                        uid: uid,
                        username: username
                    },
                    student: {
                        name: name,
                        studentNumber: studentNumber,
                        studentClass: studentClass,
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
                    allTd[3].text(data.studentNumber);
                    allTd[4].text(data.studentClass);
                    allTd[5].text(data.major);
                    allTd[6].text(data.institute);
                    allTd[7].text(data.phone);
                },
                error: function () {
                    toastr.error("更新失败");
                }
            });
        });
    });
}

//删除学生
function deleteStudent() {
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
                url: "/teacher/deleteStudent/" + uid,
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

//创建学生
function addStudent() {

    var verify = true;
    var _table = $("#studentTable");
    var foreach_count = parseInt($("#studentTable tr:last").find("td:first").text()) + 1;
    var _moral = $("#addStudentModal");

    $("#submitAdd").on("click", function () {
        var _username = $("#username");
        var _password = $("#password");
        var _name = $("#name");
        var _studentNumber = $("#studentNumber");
        var _studentClass = $("#studentClass");
        var _major = $("#major");
        var _institute = $("#institute");

        var usernameParent = _username.parent();
        var passwordParent = _password.parent();
        var nameParent = _name.parent();
        var studentNumberParent = _studentNumber.parent();
        var studentClassParent = _studentClass.parent();
        var majorParent = _major.parent();
        var instituteParent = _institute.parent();

        var username = _username.val();
        var password = _password.val();
        var name = _name.val();
        var studentNumber = _studentNumber.val();
        var studentClass = _studentClass.val();
        var major = _major.val();
        var institute = _institute.val();
        var phone = $("#phone").val();
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
        if (studentNumber.length === 0) {
            studentNumberParent.addClass("has-error");
            verify = false;
        }
        if (studentClass.length === 0) {
            studentClassParent.addClass("has-error");
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
                url: "/teacher/addStudent",
                contentType: 'application/json',
                data: JSON.stringify({
                    loginer: {
                        username: username,
                        password: password
                    },
                    student: {
                        name: name,
                        studentNumber: studentNumber,
                        studentClass: studentClass,
                        major: major,
                        institute: institute,
                        phone: phone
                    }
                }),
                success: function (data) {
                    data = JSON.parse(data);
                    var trHTML = "<tr>"
                        + "<td>" + foreach_count + "</td>"
                        + "<td>" + data.username + "</td>"
                        + "<td>" + data.name + "</td>"
                        + "<td>" + data.studentNumber + "</td>"
                        + "<td>" + data.studentClass + "</td>"
                        + "<td>" + data.major + "</td>"
                        + "<td>" + data.institute + "</td>"
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
    $('.editable_studentNumber').editable({
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
    $('.editable_studentClass').editable({
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
}