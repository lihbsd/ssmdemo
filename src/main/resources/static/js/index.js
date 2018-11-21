jQuery(function () {

    //首页包含子页时，拦截所有表单提交，使用使用ajax访问并把返回的html内容重写到主页内容中

    $(document).on("submit", "form", function () {

        // 获取form的action值;
        var action = $(this).attr("action");
        // console.log("action=" + action);

        //是否截获访问标志，默认截获
        var mark = "true";

        //获取是否截获的标志值
        if (typeof($(this).attr("intercept")) != "undefined") {
            mark = $(this).attr("intercept");
        }
        //标记了不截获的，不进行截获，继续原来的操作
        if (mark == 'false') {
            console.log("自定义属性不拦截");
            return true;
        } else {
            //$(this)是个query对象，直接用不行，new FormData需要传入一个DOM对象，所以要加下标[0]
            var formdata = new FormData($(this)[0]);
            formdata.set("pageNum", 1);
            // console.log("fd.keyword=" + fd.get("keyword"));

            ajaxsubmit(action, formdata);

            return false;
        }
    });

    //首页包含子页时，拦截所有<a>链接的访问操作，使用ajax访问并把返回的html内容重写到主页内容中
    $(document).on("click", "a", function () {
        //点击时弹框提醒，确认再继续
        if (typeof($(this).attr("confirm")) != "undefined") {
            var confirmtext = $(this).attr("confirm");
            if (!confirm(confirmtext)) {
                return false;
            }
        }
        // 获取<a>标签的href值;
        var href = $(this).attr("href");
        // console.log("href="+ href);
        //提交并新增下一个，则执行这个操作
        if (href == "submitandtoadd") {
            var form = $("form.js-validation-material");
            if (!form.valid()) {
                // alert("表单填写不完整，请填写后重试！");
                return false;
            }

            // 获取form的action值;
            var action = form.attr("action");

            var formdata = new FormData(form[0]);
            formdata.set("target", "toadd.do");
            formdata.set("pageNum", 1);
            ajaxsubmit(action, formdata);
            return false;
        }

        //是否截获访问标志，默认截获
        var mark = "true";

        //获取是否截获的标志值
        if (typeof($(this).attr("intercept")) != "undefined") {
            mark = $(this).attr("intercept");
        }
        //标记了不截获的，或者是javascript:void(0)的，不进行截获，继续原来的操作
        if (href == 'javascript:void(0)') {
            console.log("javascript:void(0)不拦截");
        } else if (mark == 'false') {
            console.log("自定义属性设置为不拦截");
            return true;
        } else {

            //解析href值，把链接和参数分开
            var url = href.split('?')[0];
            var param = href.split('?')[1] || '';
            // console.log("url=" + url);
            // console.log("param=" + param.toString());
            //重组参数，生成FormData
            var formdata = new FormData();
            if (param != "") {
                var paramArr = param.split("&");
                $.each(paramArr, function (index, obj) {
                    var params = obj.split("=");
                    formdata.append(params[0], params[1]);
                });
            }
            // console.log("formdata=" + formdata.toString());

            ajaxsubmit(url, formdata);

            return false;
        }

    });

    //ajax进行提交 action是提交的action,formdata是提交的表单
    function ajaxsubmit(action, formdata) {
        $.ajax({
            url: action,
            type: "POST",
            data: formdata,
            processData: false,  // 不处理数据
            contentType: false,  // 不设置内容类型
            beforeSend: function () {
                // console.log("beforeSend");
            },
            success: function (data) {
                // console.log("success");
                var Obj = $("<code></code>").append($(data));//包装数据
                //获取内容块
                var $html = $("#content", Obj);
                // console.log("$html：" + $html);

                // 加载内容
                $("div#content").html($html);

                // 隐藏菜单栏，如果菜单的<a>触发能够实现隐藏;
                $("aside").removeClass();
                $("aside").addClass("app-layout-drawer");
                //把展开菜单时显示的保护层删除
                $("div.app-ui-mask-visible").removeClass("app-ui-mask-visible");

                /*设置不可修改的input为只读属性，
                thymeleaf标签无法判断实现，通过th:arr标签设置isreadonly，js变通处理*/
                $("[isreadonly]").attr("readonly", "readonly");

                //存在验证表单，则加载验证规则
                if ($('.js-validation-material').length > 0) {
                    //加载验证框架
                    BaseFormValidation.init();

                    //加载验证规则
                    $("[validate]").each(function () {

                        var rulemap = {};
                        var msgmap = {};
                        //验证规则html文件中通过key;value形式配置，validate属性配置规则validatemsg属性配置相应的提示内容
                        var rulestr = $(this).attr("validate");
                        var msgstr = $(this).attr("validatemsg");
                        // console.log("********rules="+rulestr);
                        // console.log("********msgstr="+msgstr);
                        // 解析规则字符串
                        var rulesarr = rulestr.split(",");
                        $.each(rulesarr, function (index, obj) {
                            var arr2 = obj.split(":");
                            var key = arr2[0];
                            var value = arr2[1];
                            // 对特殊规则转义，比如true要成字符串转换成boolean型
                            if (value == "true") {
                                value = true;
                            }
                            if (value == "false") {
                                value = false;
                            }
                            rulemap[key] = value;
                        });
                        //解析消息字符串
                        var msgarr = msgstr.split(",");
                        $.each(msgarr, function (index, obj) {
                            var arr3 = obj.split(":");
                            msgmap[arr3[0]] = arr3[1];
                        });
                        //将提示消息加载到规则里
                        rulemap["messages"] = msgmap;
                        //加载规则
                        $(this).rules("add", rulemap);
                    });

                }
                //如果有延时跳转的，调用跳转方法
                clearTimeout(InterValObj);
                if ($("#action-redirect").length > 0) {
                    console.log("执行延时跳转");
                    delayURL();
                }

            },
            error: function (jqXHR, textStatus, errorThrown) {
                var parsedJson = jQuery.parseJSON(jqXHR.responseText);
                // console.log("parsedJson.message="+parsedJson.message);
                // window.location.href = "/exception/ajax.ex?message=" + parsedJson.message;//需要跳转的地址
                var formdata = new FormData();
                formdata.append("message",parsedJson.message);
                ajaxsubmit("/exception/ajax.ex", formdata);
                // alert("读取数据错误！！请联系系统管理员解决！" + e.message);
            }


        });
    }


    // 滚动到页面最底层后，如果是list页面，加载新数据
    $(window).on("scroll", function () {

        if ($("div#loadmore").length > 0) {
            //无限加载
            if ($(document).height() - $(window).innerHeight() == $(window).scrollTop()) {
                console.log('end of page');
                loaddata();
            }

        }
    });

    function loaddata() {

        if ($("#searchform").length <= 0) {
            return false;
        }
        var href = $("#searchform").attr("action");
        var fd = new FormData($("#searchform")[0]);
        console.log("fd.pageNum=" + fd.get("pageNum"));
        var pageNum = parseInt(fd.get("pageNum")) + 1;
        var pages = fd.get("pages")
        $("input#pageNum").val(pageNum);
        fd.set("pageNum", pageNum);
        // console.log("fd.keyword=" + fd.get("keyword"));

        $.ajax({
            url: href,
            type: "POST",
            data: fd,
            processData: false,  // 不处理数据
            contentType: false,  // 不设置内容类型
            beforeSend: function () {
                // console.log("beforeSend");
            },
            success: function (data) {
                // console.log("data="+data);
                var Obj = $("<tr></tr>").append($(data));//包装数据
                console.log("Obj：" + Obj);
                //获取内容块
                var $html = $(Obj).find("tbody");
                console.log("$html：" + $html);

                // 加载内容
                $("#datatable").append($html);

                if (pageNum >= pages) {
                    $('div#loadmore').text('已加载全部数据');
                    $('div#loadmore').attr('id', 'loadover');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // console.log("jqXHR.responseText="+jqXHR.responseText);
                var parsedJson = jQuery.parseJSON(jqXHR.responseText);
                window.location.href = "/exception/ajax.ex?message=" + parsedJson.message;//需要跳转的地址
            }

        });
    }




});


var InterValObj;
function delayURL() {
    var url = document.getElementById("action-redirect").getAttribute("data-url");
    var delay = document.getElementById("time").innerHTML;
    if(delay > 0) {
        delay--;
        document.getElementById("time").innerHTML = delay;
    } else {
        clearTimeout(InterValObj);
        window.top.location.href = url;
    }
    InterValObj = setTimeout("delayURL()", 1000);
}