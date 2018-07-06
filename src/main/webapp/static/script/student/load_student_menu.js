/**
 * 加载学生菜单
 */
function loadStudentMenu() {
    $("aside").load("/static/template/student_menu.html");
}

/**
 * 页面准备好便加载
 */
$(function () {
    loadStudentMenu();
});