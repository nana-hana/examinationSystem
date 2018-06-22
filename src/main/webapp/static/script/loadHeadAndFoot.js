/**
 * @Author nana
 * @Date 18-6-22 下午6:34
 * @Description 加载页首页尾
 */

/**
 * 加载页面头部页面
 */
function loadHeader() {
    $("header").load("/static/template/header.html");
}

/**
 * 加载页面尾部页面
 */
function loadFooter() {

    $("footer").load("/static/template/footer.html");

}

/**
 * 页面准备好便加载
 */
$(function () {
    loadHeader();
    loadFooter();
});