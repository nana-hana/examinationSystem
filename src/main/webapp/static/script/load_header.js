/**
 * 加载页面头部页面
 */
function loadHeader() {
    $("header").load("/static/template/header.html");
}

/**
 * 页面准备好便加载
 */
$(function () {
    loadHeader();
});