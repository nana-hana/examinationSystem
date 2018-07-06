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

    updateTeacherInfo();
    updateTeacherPassword();

    addExamination();
    updateExamination();
    deleteExamination();
});

//删除考试申请
function deleteExamination() {
    //注册删除按钮的事件
    $(".delete_examination").on("click", function () {

        var status = $(this).parent().siblings("td").eq(12).children("span").eq(0).text();
        var tr = $(this).parent().parent();
        var taskId = $(this).parent().siblings("input").val();
        Ewin.confirm({message: "确认要删除选择的数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            if (status === "拒绝") {
                return;
            }
            $.ajax({
                type: "DELETE",
                url: "/teacher/deleteExamination/" + taskId,
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

//更新考试申请信息
function updateExamination() {
    $(".update_examination").on("click", function () {

        var _taskId = $(this).parent().siblings("input");
        var taskId = $(this).parent().siblings("input").val();
        var allTd = $(this).parent().siblings("td");

        var singleNumber = allTd.eq(1).text();
        var singleScore = allTd.eq(2).text();
        var multipleNumber = allTd.eq(3).text();
        var multipleScore = allTd.eq(4).text();
        var checkingNumber = allTd.eq(5).text();
        var checkingScore = allTd.eq(6).text();
        var paperLevel = allTd.eq(7).text();
        var paperKind = allTd.eq(8).text();
        var subjectId = allTd.eq(9).text();
        var examinationStudentClass = allTd.eq(10).text().replace("\n", "").trim();//不知道为什么出现回车
        var examinationTime = allTd.eq(11).text().replace("\n", "").trim();
        var status = allTd.eq(12).children("span").eq(0).text();

        Ewin.confirm({message: "确认要更新选择的数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            if (status === "拒绝" || status === "通过") {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/teacher/updateExamination",
                contentType: 'application/json',
                data: JSON.stringify({
                    //根据taskId查询考试事件，更新数据数据包含单选、多选、判断题的总分值和数量，试卷难度，试卷生成类型，科目id，考试持续事件
                    // eiid: 7,
                    taskId: taskId,
                    examination: {
                        singleNumber: singleNumber,
                        singleScore: singleScore,
                        multipleNumber: multipleNumber,
                        multipleScore: multipleScore,
                        checkingNumber: checkingNumber,
                        checkingScore: checkingScore,
                        paperLevel: paperLevel,
                        paperKind: paperKind,
                        subjectId: subjectId,
                        studentClass: examinationStudentClass,
                        examinationTime: examinationTime
                    }
                }),
                success: function (data) {
                    toastr.success("更新成功");
                    data = JSON.parse(data);
                    _taskId.val(data.taskId);
                    allTd[1].text(data.singleNumber);
                    allTd[2].text(data.singleScore);
                    allTd[3].text(data.multipleNumber);
                    allTd[4].text(data.multipleScore);
                    allTd[5].text(data.checkingNumber);
                    allTd[6].text(data.checkingScore);
                    allTd[7].text(data.paperLevel);
                    allTd[8].text(data.paperKind);
                    allTd[9].text(data.subjectId);
                    allTd[10].text(data.studentClass);
                    allTd[11].text(data.examinationTime);
                },
                error: function () {
                    toastr.error("更新失败");
                }
            });
        });
    });
}

//创建考试申请
function addExamination() {

    var verify = true;
    var _table = $("#examinationTable");
    var foreach_count = parseInt($("#examinationTable tr:last").find("td:first").text()) + 1;
    var _moral = $("#addTestModal");

    $("#submitAddExamination").on("click", function () {
        var _singleNumber = $("#editable_singleNumber");
        var _singleScore = $("#editable_singleScore");
        var _multipleNumber = $("#editable_multipleNumber");
        var _multipleScore = $("#editable_multipleScore");
        var _checkingNumber = $("#editable_checkingNumber");
        var _checkingScore = $("#editable_checkingScore");
        var _paperLevel = $("#editable_paperLevel");
        var _paperKind = $("#editable_paperKind");
        var _subjectId = $("#editable_subjectId");
        var _examinationStudentClass = $("#editable_examinationStudentClass");
        var _examinationTime = $("#editable_examinationTime");
        var _comments = $("#editable_comments");

        var _singleNumberParent = _singleNumber.parent();
        var _singleScoreParent = _singleScore.parent();
        var _multipleNumberParent = _multipleNumber.parent();
        var _multipleScoreParent = _multipleScore.parent();
        var _checkingNumberParent = _checkingNumber.parent();
        var _checkingScoreParent = _checkingScore.parent();
        var _paperLevelParent = _paperLevel.parent();
        var _paperKindParent = _paperKind.parent();
        var _subjectIdParent = _subjectId.parent();
        var _examinationStudentClassParent = _examinationStudentClass.parent();
        var _examinationTimeParent = _examinationTime.parent();

        var singleNumber = _singleNumber.text();
        var singleScore = _singleScore.text();
        var multipleNumber = _multipleNumber.text();
        var multipleScore = _multipleScore.text();
        var checkingNumber = _checkingNumber.text();
        var checkingScore = _checkingScore.text();
        var paperLevel = _paperLevel.text();
        var paperKind = _paperKind.text();
        var subjectId = _subjectId.text();
        var examinationStudentClass = _examinationStudentClass.text();
        var examinationTime = _examinationTime.text();
        var comments = _comments.text();

        //此处让包含数据的td变成红框，未完成
        if (singleNumber.length === 0) {
            _singleNumberParent.addClass("has-error");
            verify = false;
        }
        if (singleScore.length === 0) {
            _singleScoreParent.addClass("has-error");
            verify = false;
        }
        if (multipleNumber.length === 0) {
            _multipleNumberParent.addClass("has-error");
            verify = false;
        }
        if (multipleScore.length === 0) {
            _multipleScoreParent.addClass("has-error");
            verify = false;
        }
        if (checkingNumber.length === 0) {
            _checkingNumberParent.addClass("has-error");
            verify = false;
        }
        if (checkingScore.length === 0) {
            _checkingScoreParent.addClass("has-error");
            verify = false;
        }
        if (paperLevel.length === 0) {
            _paperLevelParent.addClass("has-error");
            verify = false;
        }
        if (paperKind.length === 0) {
            _paperKindParent.addClass("has-error");
            verify = false;
        }
        if (subjectId.length === 0) {
            _subjectIdParent.addClass("has-error");
            verify = false;
        }
        if (examinationStudentClass.length === 0) {
            _examinationStudentClassParent.addClass("has-error");
            verify = false;
        }
        if (examinationTime.length === 0) {
            _examinationTimeParent.addClass("has-error");
            verify = false;
        }

        if (verify) {
            $.ajax({
                type: "POST",
                url: "/teacher/createExamination",
                contentType: 'application/json',
                data: JSON.stringify({
                    activityApprovalRequest: {
                        comments: comments
                    },
                    examinationInternal: {
                        //数据包含单选、多选、判断题的总分值和数量，试卷难度，试卷生成类型，科目id，考试持续时间
                        singleNumber: singleNumber,
                        singleScore: singleScore,
                        multipleNumber: multipleNumber,
                        multipleScore: multipleScore,
                        checkingNumber: checkingNumber,
                        checkingScore: checkingScore,
                        paperLevel: paperLevel,
                        paperKind: paperKind,
                        subjectId: subjectId,
                        studentClass: examinationStudentClass,
                        examinationTime: examinationTime
                    }
                }),
                success: function (data) {
                    data = JSON.parse(data);
                    var trHTML = "<tr>"
                        + "<td>" + foreach_count + "</td>"
                        + "<td><a href='javascript:void(0)' class='editable_singleNumber'>" + data.singleNumber + "</a></td>"
                        + "<td><a href='javascript:void(0)' class='editable_singleScore'>" + data.singleScore + "</a></td>"
                        + "<td><a href='javascript:void(0)' class='editable_multipleNumber'>" + data.multipleNumber + "</a></td>"
                        + "<td><a href='javascript:void(0)' class='editable_multipleScore'>" + data.multipleScore + "</a></td>"
                        + "<td><a href='javascript:void(0)' class='editable_checkingNumber'>" + data.checkingNumber + "</a></td>"
                        + "<td><a href='javascript:void(0)' class='editable_checkingScore'>" + data.checkingScore + "</a></td>"
                        + "<td><a href='javascript:void(0)' class='editable_paperLevel'>" + data.paperLevel + "</a></td>"
                        + "<td><a href='javascript:void(0)' class='editable_paperKind'>" + data.paperKind + "</a></td>"
                        + "<td><a href='javascript:void(0)' class='editable_subjectId'>" + data.subjectId + "</a></td>"
                        + "<td><a href='javascript:void(0)' class='editable_examinationStudentClass'>" + data.studentClass + "</a></td>"
                        + "<td><a href='javascript:void(0)' class='editable_examinationTime'>" + data.examinationTime + "</a></td>"
                        + "<td class='editable_status'><span class='label label-warning'>审核中</span></td>"
                        + "<input type='hidden' value=" + data.taskId + ">"
                        + "<td>"
                        + "<button type='button' class='btn btn-warning update_examination'>更新</button>"
                        + " <button type='button' class='btn btn-danger delete_examination'>删除</button>"
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
                url: "/teacher/updateStudent",
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
                        + "<td" + foreach_count + "</td>"
                        + "<td class='editable_username'>>" + data.username + "</td>"
                        + "<td class='editable_name'>>" + data.name + "</td>"
                        + "<td class='editable_studentNumber'>>" + data.studentNumber + "</td>"
                        + "<td class='editable_studentClass'>>" + data.studentClass + "</td>"
                        + "<td class='editable_major'>>" + data.major + "</td>"
                        + "<td class='editable_institute'>>" + data.institute + "</td>"
                        + "<td class='editable_phone'>>" + data.phone + "</td>"
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

//更新教师信息
function updateTeacherInfo() {
    $(".update_teacher_info").on("click", function () {
        var _name = $("#editable_name");
        var _teacherNumber = $("#editable_teacherNumber");
        var _major = $("#editable_major");
        var _institute = $("#editable_institute");
        var _phone = $("#editable_phone");

        var name = _name.text();
        var teacherNumber = _teacherNumber.text();
        var major = _major.text();
        var institute = _institute.text();
        var phone = _phone.text();

        Ewin.confirm({message: "确认要更新选择的数据吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/teacher/updateTeacherInfo",
                contentType: 'application/json',
                data: JSON.stringify({
                    name: name,
                    teacherNumber: teacherNumber,
                    major: major,
                    institute: institute,
                    phone: phone
                }),
                success: function (data) {
                    toastr.success("更新成功");
                    data = JSON.parse(data);
                    _name.text(data.name);
                    _teacherNumber.text(data.teacherNumber);
                    _major.text(data.major);
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

//更新教师密码
function updateTeacherPassword() {
    $(".update_teacher_password").on("click", function () {

        var oldPassword = $("#editable_old_password").text();
        var newPassword = $("#editable_new_password").text();

        Ewin.confirm({message: "确认要修改密码吗？"}).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                type: "PUT",
                url: "/teacher/updateTeacherLoginer",
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


    //教师信息表格
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

    //考试信息表格
    $('.editable_singleNumber').editable({
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_singleScore').editable({
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_multipleNumber').editable({
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_multipleScore').editable({
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_checkingNumber').editable({
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_checkingScore').editable({
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_paperLevel').editable({
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_paperKind').editable({
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_subjectId').editable({
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_examinationStudentClass').editable({
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });
    $('.editable_examinationTime').editable({
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (isNaN(value)) {
                return '必须是数字';
            }
        }
    });

    //考试申请表格
    $('#editable_singleNumber').editable({
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
    $('#editable_singleScore').editable({
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
    $('#editable_multipleNumber').editable({
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
    $('#editable_multipleScore').editable({
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
    $('#editable_checkingNumber').editable({
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
    $('#editable_checkingScore').editable({
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
    $('#editable_paperLevel').editable({
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
    $('#editable_paperKind').editable({
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
    $('#editable_subjectId').editable({
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
    $('#editable_examinationStudentClass').editable({
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
    $('#editable_examinationTime').editable({
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
    $('#editable_comments').editable({
        mode: "inline"
    });
}