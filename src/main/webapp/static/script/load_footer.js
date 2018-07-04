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
    loadFooter();
});