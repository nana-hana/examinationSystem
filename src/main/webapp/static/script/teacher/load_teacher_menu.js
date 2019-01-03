/**
 * 加载教师菜单
 */
function loadTeacherMenu() {
    $("aside").load("/static/template/teacher_menu.html");
}

/**
 * 页面准备好便加载
 */
$(function () {
    loadTeacherMenu();
});