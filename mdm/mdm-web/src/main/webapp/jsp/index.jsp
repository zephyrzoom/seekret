<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="initial-scale=1.0,
        user-scalable=no,width=device-width,maximum-scale=1.0,minimum-scale=1.0"/>
    <link rel="stylesheet" href="jsp/style.css" type="text/css"/>
    <script src="https://use.fontawesome.com/60c645e1e2.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=U1A35c828hK6MLZYv81trGiL"></script>
    <script type="text/javascript" src="jsp/jquery.js"></script>
    <title>觅秘</title>
</head>
<body>
<i class="fa fa-refresh fa-lg refresh-icon"></i>
<div id="allmap"></div>
<div id="comment">
    <input id="comment-text" maxlength="20"/>
    <input id="comment-button" type="button" value="发布" disabled/>
</div>
<div id="comment-detail">
    <div class="container">
        <div class="comment-title">
            <span class="comment1">很好吃！</span>
            <i class="fa fa-times close-replys"></i>
        </div>

        <hr class="line1">
        <div class="comment-list">
        </div>
        <hr class="line2">
    </div>
    <input id="do-comment-reply" maxlength="20"/>
    <input id="reply-submit-button" type="button" value="评论" disabled/>

</div>
</body>
</html>
<script>
    var mp = new BMap.Map("allmap");
    mp.setMapStyle({style: 'googlelite'});

    function ComplexCustomOverlay(point, text, tylecolor, likecount, id, item, i) {
        this._point = point;
        this._text = text;
        this._tylecolor = tylecolor;
        this._likecount = likecount;
        this._id = id;
        this._item = item;
        this._i = i;
    }

    ComplexCustomOverlay.prototype = new BMap.Overlay();


    ComplexCustomOverlay.prototype.initialize = function (map) {
        this._map = map;

        var div = this._div = document.createElement("div");
        div.className = "ajax-comment";
        div.style.position = "absolute";
        div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
        // div.style.backgroundColor = "#EE5D5B";
        div.style.backgroundColor = "#ffffff";
        div.style.border = "1px solid " + this._tylecolor;
        div.style.color = this._tylecolor;
        div.style.height = "24px";
        div.style.padding = "3px ";
        div.style.lineHeight = "24px";
        div.style.whiteSpace = "nowrap";
        div.style.MozUserSelect = "none";
        div.style.fontSize = "15px";
        div.style.borderRadius = "18px";

        var span = this._span = document.createElement("span");
        span.style.marginLeft = "12px";
        span.style.marginRight = "55px";

        var textSpan = this._span = document.createElement("span");
        textSpan.className = "span-text" + this._id.toString();
        textSpan.appendChild(document.createTextNode(this._text));
        textSpan.style.cursor = "pointer";
        //textSpan.addEventListener("click", openComment.bind(this),false);

        span.appendChild(textSpan);


        var span_zan = this._span = document.createElement("i");
        if (idAndZanFlag[this._id]) {
            span_zan.className = "fa fa-thumbs-up add-zan" + this._id.toString();
            this._likecount = parseInt(this._likecount) + 1;
        } else {
            span_zan.className = "fa fa-thumbs-o-up add-zan" + this._id.toString();
        }

        span_zan.style.position = "absolute";
        span_zan.style.width = "22px";
        span_zan.style.height = "22px";
        span_zan.style.marginLeft = "5px";
        span_zan.style.lineHeight = "22px";
        span_zan.style.zIndex = "9";
        span_zan.style.cursor = "pointer";
        span.appendChild(span_zan);

        var span_zan_count = this._span = document.createElement("span");
        span_zan_count.className = "zan-count" + this._id.toString();
        span_zan_count.style.position = "absolute";
        span_zan_count.style.width = "22px";
        span_zan_count.style.height = "22px";
        span_zan_count.style.paddingLeft = "30px";
        if (idAndZanFlag[this._id]) {
            span_zan_count.style.color = this._tylecolor;
        } else {
            span_zan_count.style.color = "#999999"
        }
        span_zan_count.appendChild(document.createTextNode(this._likecount));
        span.appendChild(span_zan_count);
        div.appendChild(span);

        var arrow = this._arrow = document.createElement("div");
        arrow.style.background =
            "url(http://webmap2.map.bdstatic.com/wolfman/static/common/images/ipLocation/ipLocation_ac75181.png) no-repeat";
        if (this._tylecolor == "#7cbb09") {
            arrow.style.background =
                "url(http://webmap2.map.bdstatic.com/wolfman/static/common/images/ipLocation/ipLocation_ac75181.png) no-repeat";
        }
        arrow.style.backgroundPosition = "-43px 0";
        arrow.style.position = "absolute";
        arrow.style.width = "13px";
        arrow.style.height = "12px";
        arrow.style.top = "30px";
        arrow.style.left = "25px";
        div.appendChild(arrow);
        mp.getPanes().labelPane.appendChild(div);

        span_zan.addEventListener("touchstart", zan.bind(this), false);
        textSpan.addEventListener("touchstart", openComment.bind(this), false);

        return div;
    }
    ComplexCustomOverlay.prototype.draw = function () {
        var map = this._map;
        var pixel = map.pointToOverlayPixel(this._point);
        this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
        this._div.style.top = pixel.y - 30 + "px";
    }
    var class1Color = "#7cbb09";
    var class2Color = "#f69090";

    ////////////////////////////

    var currentPosition = {
        longitude: "109.048895", //经度
        latitude: "34.311102" //纬度
    };


    var recycle;
    var timeouts = [];

    function carousel(item) {
        let myCompOverlay = new ComplexCustomOverlay(new BMap.Point(item[0].longitude, item[0].latitude),
            item[0].content, item[0].praiseNum > 50 ? class2Color : class1Color, item[0].praiseNum, item[0].commentId, item, 0);
        mp.addOverlay(myCompOverlay);

        for (let i = 1; i < item.length; i++) {
            let currentItem = item[i];
            //console.log("222" + item[i]);
            let timeout = setTimeout(function () {
                //console.log(currentItem);
                mp.removeOverlay(myCompOverlay);
                myCompOverlay = new ComplexCustomOverlay(new BMap.Point(currentItem.longitude, currentItem.latitude),
                    currentItem.content, currentItem.praiseNum > 50 ? class2Color : class1Color, currentItem.praiseNum,
                    currentItem.commentId, item, i);
                mp.addOverlay(myCompOverlay);


            }, i * 3 * 1000);
            timeouts[timeouts.length] = timeout;
        }

        let items = item; // 传入setInterval的item
        recycle = setInterval(function () {
            mp.removeOverlay(myCompOverlay);
            myCompOverlay = new ComplexCustomOverlay(new BMap.Point(items[0].longitude, items[0].latitude),
                items[0].content, items[0].praiseNum > 50 ? class2Color : class1Color, items[0].praiseNum, items[0].commentId, items, 0);
            mp.addOverlay(myCompOverlay);
            for (let i = 1; i < items.length; i++) {
                let currentitems = items[i];
                ///console.log("222" + items[i]);
                let timeout = setTimeout(function () {
                    //console.log(currentitems);
                    mp.removeOverlay(myCompOverlay);
                    myCompOverlay = new ComplexCustomOverlay(new BMap.Point(currentitems.longitude, currentitems.latitude),
                        currentitems.content, currentitems.praiseNum > 50 ? class2Color : class1Color, currentitems.praiseNum,
                        currentitems.commentId, items, i);
                    mp.addOverlay(myCompOverlay);

                }, i * 3 * 1000);
                timeouts[timeouts.length] = timeout;
            }
        }, items.length * 3 * 1000);
        allIntervalAndTimeout[recycle]=timeouts;
        allIntervalAndItems[item.mark]=recycle;

    }
    var allIntervalAndTimeout = {};
    var allIntervalAndItems = {};
    function stopCarousel(item) {
        var key = allIntervalAndItems[item.mark];
        console.log('item')
        console.log(item)
        console.log('mark')
        console.log(item.mark)
        console.log('key')
        console.log(key)
        console.log('allIntervalAndItems')
        console.log(allIntervalAndItems)
        console.log('allIntervalAndTimeout')
        console.log(allIntervalAndTimeout)
        for (i of allIntervalAndTimeout[key]) {
            clearTimeout(i);
        }
        clearInterval(key);
    }

    function clearAllEvent() {
        for (let key in allIntervalAndTimeout) {
            for (i of allIntervalAndTimeout[key]) {
                clearTimeout(i);
            }
            clearInterval(key);
        }
        allIntervalAndTimeout = {};
    }

    var globalItem = null;
    var idAndZanFlag = {};
    var isInitial = true;
    var mk;//大头针
    var mk2;//大红点
    function refresh() {

        // 百度地图API功能

        $(".ajax-comment").remove();
        clearAllEvent();

        mp.removeEventListener("dragstart");
        mp.removeEventListener("dragging");
        mp.removeEventListener("dragend");

        mp.centerAndZoom(new BMap.Point(currentPosition.longitude, currentPosition.latitude), 16);
        mp.enableScrollWheelZoom();

        var geolocation = new BMap.Geolocation();

        geolocation.getCurrentPosition(function (r) {
            if (this.getStatus() == BMAP_STATUS_SUCCESS) {
//                r.point = {
//                    lng:109.05004483855,
//                    lat:34.31135199801
//                }
                //每次刷新，删除掉上次留下的标记：大头针和大红点
                mp.removeOverlay(mk);
                mp.removeOverlay(mk2);

                //mk = null;
                var datouzhen = new BMap.Icon("/mdm-web/jsp/datouzhen.png", new BMap.Size(57,120));
                mk = new BMap.Marker(r.point,{icon:datouzhen});
                mk2 = new BMap.Marker(r.point);
                console.log('dahongdian');
                console.log(mk2);

                mp.panTo(r.point);
                mp.addOverlay(mk);
                mp.addOverlay(mk2);
                currentPosition.longitude = r.point.lng;
                currentPosition.latitude = r.point.lat;
                var currentPoint = r.point;
                mk.setPosition(mp.getCenter());
                if (isInitial) {
                    // 中心点固定
                    mp.addEventListener("dragstart", function(){
                        $("#comment-text").blur();
                        $("#do-comment-reply").blur();
                    });

                    mp.addEventListener("dragging", function(){
                        mk.setPosition(mp.getCenter());
                    });
                    mp.addEventListener("dragend", function () {
                        mk.setPosition(mp.getCenter());


                        console.log(mp.getDistance(currentPoint, mp.getCenter()) + '米');
                        if (mp.getDistance(currentPoint, mp.getCenter()) > 5000) {
                            clearAllEvent();
                            $(".ajax-comment").remove();

                            // 大于5000米重置起始点
                            currentPosition.longitude = mp.getCenter().lng;
                            currentPosition.latitude = mp.getCenter().lat;
                            currentPoint.lng = mp.getCenter().lng;
                            currentPoint.lat = mp.getCenter().lat;
                            $.ajax({
                                type: "POST",
                                contentType: "application/json",
                                url: "/mdm-web/getcommentsnearby.action",
                                data: JSON.stringify(currentPosition),
                                dataType: "json",
                                success: data => {
                                    console.log(data);
                                    if (data.stat == "0000") {
                                        for (item of data.result) {
                                            if (item.length > 1) {
                                                for (it of item) {
                                                    idAndZanFlag[it.commentId]=false;
                                                }
                                                item['mark'] = item[0].commentId;
                                                carousel(item);
                                            } else if (item.length === 1) {
                                                let myCompOverlay1 = new ComplexCustomOverlay(new BMap.Point(item[0].longitude, item[0].latitude),
                                                    item[0].content, item[0].praiseNum > 50 ? class2Color : class1Color, item[0].praiseNum, item[0].commentId, item, 0);
                                                mp.addOverlay(myCompOverlay1);
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    });
                }

                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/mdm-web/getcommentsnearby.action",
                    data: JSON.stringify(currentPosition),
                    dataType: "json",
                    success: data => {
                        console.log(data);
                        if (data.stat == "0000") {
                            for (item of data.result) {
                                if (item.length > 1) {
                                    for (it of item) {
                                        idAndZanFlag[it.commentId]=false;
                                    }
                                    item['mark'] = item[0].commentId;
                                    carousel(item);
                                } else if (item.length === 1) {
                                    let myCompOverlay1 = new ComplexCustomOverlay(new BMap.Point(item[0].longitude, item[0].latitude),
                                        item[0].content, item[0].praiseNum > 50 ? class2Color : class1Color, item[0].praiseNum, item[0].commentId, item, 0);
                                    mp.addOverlay(myCompOverlay1);
                                }
                            }
                        }
                    }
                });
            }
            else {
                alert('failed' + this.getStatus());
            }
        }, {enableHighAccuracy: true})

        $(".ajax-comment").click(function () {
            console.log(ajax - comment)
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/mdm-web/showComment.action",
                data: JSON.stringify(comment),
                dataType: "json",
                success: data => {
                console.log(data);
            if (data.stat == "0000") {

            }
        }
        });
        });

    }

    var ref;
    $(document).ready(function () {
        refresh();
        $(".refresh-icon").click(function () {
            isInitial=false;
            refresh();
        });


        $(".close-replys").click(function () {
            $("#comment-detail").fadeOut();
            $("#do-comment-reply").val("");
            if (that._item.length > 1) {
                mp.removeOverlay(that);
                carousel(globalItem);
                globalItem = null;
            }
        });

        $("#comment-text").keyup(function () {
            if ($("#comment-text").val() == "") {
                $("#comment-button").attr("disabled", "disabled");
                $("#comment-button").css("background-color", "#CCC");
            } else {
                $("#comment-button").removeAttr("disabled");
                $("#comment-button").css("background-color", "#6495ED");
            }
        });



        $("#comment-button").click(function () {
            var comment = {
                content: $("#comment-text").val(),
                longitude: currentPosition.longitude,
                latitude: currentPosition.latitude
            };

            $("#comment-text").val("");
            $("#comment-button").attr("disabled", "disabled");
            $("#comment-button").css("background-color", "#CCC");
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/mdm-web/commitcomment.action",
                data: JSON.stringify(comment),
                dataType: "json",
                success: data => {
                    console.log(data);
                    if (data.stat == "0000") {
                        var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(comment.longitude, comment.latitude),
                            comment.content, class1Color, 0, data.result.commentId);
                        mp.addOverlay(myCompOverlay);
                    }
                }
            });

        });

        $("#reply-submit-button").click(function () {
            var commentReply = {
                commentId: commentId4Reply,
                content: $("#do-comment-reply").val()
            }
            $("#do-comment-reply").val("");
            $("#reply-submit-button").attr("disabled", "disabled");
            $("#reply-submit-button").css("background-color", "#CCC");
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/mdm-web/otherComment.action",
                data: JSON.stringify(commentReply),
                dataType: "json",
                success: data => {
                    console.log(data);
                    if (data.stat == "0000") {
                        $(".comment-list").append("<div class='comment_replys'>" + commentReply.content + "</div>");
                    }
                }
            })
            ;
        });

        mp.addEventListener('click', function () {
            $("#comment-text").blur();
            $("#do-comment-reply").blur();
        })

    });
    var commentId4Reply;
    var that;   // 上一次的overlay对象

    function openComment() {

        if (globalItem != null) {
            mp.removeOverlay(that);
            carousel(globalItem);
            globalItem = null;
        }

        if (this._item.length > 1) {
            console.log('this.item');
            console.log(this._item);
            stopCarousel(this._item);
            tmpItem = this._item;
            globalItem = tmpItem.slice(this._i);
            globalItem = globalItem.concat(tmpItem.slice(0, this._i));
            globalItem['mark']=this._item.mark;
            console.log('globalitem');
            console.log(globalItem);
        } else {
            globalItem = null;
        }

        var commentIdOjb = {
            commentId: this._id
        }
        that = this;
        commentId4Reply = commentIdOjb.commentId;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/mdm-web/showComment.action",
            data: JSON.stringify(commentIdOjb),
            dataType: "json",
            success: data => {
            console.log(data);
            if (data.stat == "0000") {
                $(".comment1").text(this._text);
                $(".comment-list").empty();
                for (item of data.result) {
                    $(".comment-list").append("<div class='comment_replys'>" + item.content + "</div>");
                }
                $("#comment-detail").fadeIn();
            }
            if (data.stat == "1111") {
                $(".comment1").text(this._text);
                $(".comment-list").empty();
                $("#comment-detail").fadeIn();
            }
            var bgcolor = this._tylecolor;
            $(".comment1").css("color", bgcolor);
            $(".close-replys").css("color", bgcolor);
            $(".line1").css("border", "1px dashed " + bgcolor);
            $(".line2").css("border", "1px dashed " + bgcolor);
            $("#do-comment-reply").css("border", "1px solid " + bgcolor);
            $("#reply-submit-button").css("background-color", "#CCC");

            $("#do-comment-reply").keyup(function () {
                console.log(bgcolor);
                if ($("#do-comment-reply").val() == "") {
                    $("#reply-submit-button").attr("disabled", "disabled");
                    $("#reply-submit-button").css("background-color", "#CCC");
                } else {
                    $("#reply-submit-button").removeAttr("disabled");
                    $("#reply-submit-button").css("background-color", bgcolor);
                }
            });
        }
        });
    }

    function zan() {
        idAndZanFlag[this._id] = idAndZanFlag[this._id] ? false : true;
        if (idAndZanFlag[this._id]) {
            $(".add-zan" + this._id).removeClass("fa-thumbs-o-up").addClass("fa-thumbs-up");
            $(".zan-count" + this._id).text(parseInt(this._likecount)+1);
            $(".zan-count" + this._id).css("color", this._tylecolor);
        } else {
            $(".add-zan" + this._id).addClass("fa-thumbs-o-up").removeClass("fa-thumbs-up");
            $(".zan-count" + this._id).text(this._likecount);
            $(".zan-count" + this._id).css("color", "#999999");
        }

        var commentIdZan = {
            commentId: this._id
        }
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: idAndZanFlag[this._id] ? "/mdm-web/praise/add.action" : "/mdm-web/praise/sub.action",
            data: JSON.stringify(commentIdZan),
            dataType: "json",
            success: data => {
            console.log(data);
        if (data.stat == "0000") {
        }

    }
    })
        ;
    }
</script>









