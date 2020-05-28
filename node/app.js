const express = require("express");
const app = express();

const port = 8080;
app.listen(port, () => {
    // console.log(`http://127.0.0.1:${port}`);
});//打印一下接口用例地址
app.use(require("body-parser").json());
app.use(require("body-parser").urlencoded({extended: false}));

//设置跨域访问
app.all("*", function (req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "*");
    res.header("Access-Control-Allow-Methods", "*");
    res.header("X-Powered-By", "http://www.shuzhiqiang.com");
    res.header("Content-Type", "application/json;charset=utf-8");
    res.header("Access-Control-Allow-Credentials",true);//携带cookie跨域请求
    req.method.toUpperCase() === "OPTIONS" ? res.sendStatus(200) : next();//防止在预请求阶段就响应接口
});

//待访问接口API名称
app.use("/user", require("./api/user"));
app.use("/todo", require("./api/todo"));
app.use("/plan", require("./api/plan"));
app.use("/note", require("./api/note"));
app.use("/friend", require("./api/friend"));
app.use("/achieve", require("./api/achieve"));
app.use("/log", require("./api/log"));
app.use("/chart", require("./api/chart"));