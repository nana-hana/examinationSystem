/**
 * 加载教师菜单
 */
function loadAdministratorMenu() {
    $("aside").load("/static/template/administrator_menu.html");
}

/**
 * 页面准备好便加载
 */
$(function () {
    loadAdministratorMenu();
});