/**
 * 加载超级管理员菜单
 */
function loadSuperAdministratorMenu() {
    $("aside").load("/static/template/superAdministrator_menu.html");
}

/**
 * 页面准备好便加载
 */
$(function () {
    loadSuperAdministratorMenu();
});