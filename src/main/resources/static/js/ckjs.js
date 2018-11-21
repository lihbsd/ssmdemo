$(document).ready(function () {
    /*设置不可修改的input为只读属性，
    thymeleaf标签无法判断实现，通过th:arr标签设置isreadonly，js变通处理*/
    $(function () {
        $("[isreadonly]").attr("readonly", "readonly");
    })


    // 分页：
    // 点击页码时触发
    $("#pagediv").find("a").each(function () {
        $(this).click(function () {
            //无效链接保护，不执行操作
            if (typeof($(this).attr("data-pageNum")) == "undefined") {
                return;
            }
            var pageNum = $(this).attr("data-pageNum");
            var pageSize = $(this).attr("data-pageSize");
            $("input[name='pageNum']").val(pageNum);
            $("input[name='pageSize']").val(pageSize);
            //查询条件改变提交后重新显示第一页，所以表单不带当前页参数，分页提交时临时添加。
            $("#searchform").append($("input[name='pageNum']"));
            $("#searchform").append($("input[name='pageSize']"));
            $("#searchform").submit();

        });
    });
    //输入每页显示条数触发
    $("#pageSizeInput").on("change", function () {
        var pageNum = 1;
        var pageSize = $(this).val();
        if (pageSize <= 0 || pageSize >= 1000) {
            alert("每页显示条数不能少于1，也不能大于999。请重新输入，谢谢！");
            return;
        }
        // alert("change:pageSize="+pageSize);
        $("input[name='pageNum']").val(pageNum);
        $("input[name='pageSize']").val(pageSize);
        $("#searchform").submit();
    });


    //通过外键实现动态选择框，动态加载选项
    /*
    动态选择框要加foreignkey属性，用于触发动态加载选项操作。foreignkey的值为要加载的对象
     */
    $(document).on("click input propertychange", "input[foreignkey]", function () {
        console.log("执行动态选择框");
        //只读状态不加载选项
        if ($(this).attr("readonly") == "readonly") {
            return;
        }
        //点击动态选择框显示选项
        $(this).next().removeClass("hidden");
        //动态加载选项
        //选项来源对象
        var foreignkey = $(this).attr("foreignkey");
        //输入的值
        var keyword = $(this).val();
        //对应id字段
        var forid = $(this).attr("forid");
        var forname = $(this).attr("forname");
        var href = '#';
        var intercept = 'false';
        if (typeof($(this).attr("data-href")) != "undefined") {
            href = $(this).attr("data-href");
        }
        if (typeof($(this).attr("data-intercept")) != "undefined") {
            intercept = $(this).attr("data-intercept");
        }
        console.log("foreignkey=" + foreignkey + ";keyword=" + keyword);
        //访问后台数据，访问名统一定义为findFromAjax.do，目前只支持数据库存放id，显示name的情况
        $.post("/" + foreignkey + "/findFromAjax.do",
            {
                keyword: keyword,//默认传入keyword
                pageSize: "100"
            },
            function (data, status) {
                console.log("n状态：" + status);
                var length = 0;
                var con = "";
                $.each(data, function (index, item) {
                    length++;
                    var href2 = href
                    href2 = href2.replace("##key##", item.key);
                    href2 = href2.replace("##value##", item.value);

                    con += "<li><a href=\"" + href2 + "\" intercept=\"" + intercept + "\" class=\"list-group-item list-group-item-ckjs\" foreignid=\"" + item.key + "\" >" + item.value + "</a></li>";
                });

                /*//剩余唯一选项则默认为选中
                if(length==1){
                    $("#"+forid+"").val(data[0].id);
                    $("#"+forname+"").val(data[0].name);
                }*/
                if (length == 0) {
                    con += "<li><a href=\"#\"  class=\"list-group-item list-group-item-ckjs text-warning\" > 没有记录，请重新输入查询条件！</a></li>";
                }
                if (length >= 100) {
                    con += "<li><a href=\"#\" class=\"list-group-item list-group-item-ckjs text-warning\" > 数据太多，只显示前100条，请输入查询条件筛选数据！</a></li>";
                }

                // console.log(con);    //可以在控制台打印一下看看，这是拼起来的标签和数据

                //清空之前的记录，重新加载，通过forid属性查找
                $("ul[forid='" + forid + "']").find("li").remove();
                $("ul[forid='" + forid + "']").append(con);
            }, "json");
    });

//动态下拉选择框选择选项触发
    $(document).on("click", "a[foreignid]", function () {

        console.log("点击选项");
        //查选择的id值和名称
        var foreignid = $(this).attr("foreignid");
        var foreignname = $(this).text();
        // console.log("foreignid="+foreignid+";foreignname="+foreignname);
        //查要反写的input的id值
        var forid = $(this).parent().parent().attr("forid");
        var forname = $(this).parent().parent().attr("forname");
        // console.log("forid="+forid+";forname="+forname);
        //写入值
        $("#" + forid + "").val(foreignid);
        $("#" + forname + "").val(foreignname);
        // console.log("class="+$(this).parent().parent().attr("class"));
        //选择完毕隐藏选项
        $(this).parent().parent().addClass("hidden");
        // console.log("class2="+$(this).parent().parent().attr("class"));
    });

//离开动态选择框隐藏选项
    $("input[foreignkey]").on("blur", function () {
        console.log("失去焦点");
        /* //查要反写的input的id值
         var forid = $(this).attr("forid");
         var forname = $(this).attr("forname");
         console.log("forid="+forid+";forname="+forname);
         console.log("foridval="+$("#"+forid+"").val());
         if($("#"+forid+"").val()=='' ){
             var foreignid = $("ul[forid='"+forid+"'] li").first().attr("foreignid");
             var foreignname = $("ul[forid='"+forid+"'] li").first().text();
             console.log("22foreignid="+foreignid+";foreignname="+foreignname);
             //写入值
             $("#"+forid+"").val(foreignid);
             $("#"+forname+"").val(foreignname);
         }
         //失去焦点事件执行时间后移，解决于选择选项事件冲突问题
         setTimeout(function(){
             //离开动态选择框隐藏选项
             $(this).next().addClass("hidden");
         },250)*/
    })

    /*
    复选框选中直接调用ajax操作，无其他处理
    用法：checkbox控件中有url属性则调用，url属性填写要访问的url，params属性填写参数key=value&key=value
     */
    $(document).on("change", "input:checkbox[data-url]", function () {

        console.log("复选框调用ajax开始执行");
        //先判断是选中还是取消选中，选中为true，取消为false
        if ($(this).prop('checked')) {
            mark = "true";
        } else {
            mark = "false";
        }
        console.log(":mark=" + mark);

        //获取url，params，并把mark加入params
        var url = $(this).attr("data-url");
        var params = $(this).attr("data-params") + '&mark=' + mark;

        console.log("params=" + params.toString());

        //使用ajax提交
        $.post(url,
            params,
            function (data, status) {
                console.log("n状态：" + status);
                console.log("data：" + data);

            }, "html");


    });

    //错误页面暂时系统错误信息showTrace
    $(document).on("click", "a#showTrace", function () {
        if ($("#stackTrace").hasClass("hidden")) {
            $("#stackTrace").removeClass("hidden");
        } else {
            $("#stackTrace").addClass("hidden");
        }
    });



})
;
