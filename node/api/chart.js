const { pool, resJson } = require('../connect')
//全局Express框架
const express = require("express");
const mysql  = require('mysql');
const router = express.Router();
module.exports = router;


// 获取信息
//     params{
//         user_id:,
//         begindate:
//         enddate:
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/queryWeek", (req, res) => {
    console.log("queryWeek");
    var sqlStr = "select * from plan where user_id = '" + req.body.user_id+"' and finish = 0";
    console.log(sqlStr);
    pool.getConnection((err, conn) => {
        conn.query(sqlStr, (err, result) => {
            if (err) {
                conn.connect(handleError);
                conn.on('error', handleError);
                return res.json({
                    code: 300,
                    msg: "获取失败",
                    err: err.code
                });
            }
            else {
                console.log(result);
                return res.json({
                    code: 200,
                    msg: "查询成功",
                    data: result
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 获取信息
//     params{
//         user_id:,
//         date:
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/queryMonth", (req, res) => {
    console.log("queryMonth");
    var sqlStr = "select * from chart where user_id = '" + req.body.user_id+
        "' and DATE_FORMAT(date,'%Y%m') = DATE_FORMAT('"+req.body.date+"','%Y%m')";
    console.log(sqlStr);
    pool.getConnection((err, conn) => {
        conn.query(sqlStr, (err, result) => {
            if (err) {
                conn.connect(handleError);
                conn.on('error', handleError);
                return res.json({
                    code: 300,
                    msg: "获取失败",
                    err: err.code
                });
            }
            else {
                console.log(result);
                return res.json({
                    code: 200,
                    msg: "查询成功",
                    data: result
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 获取信息
//     params{
//         user_id:,
//         date:
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/queryYear", (req, res) => {
    console.log("queryYear");
    var sqlStr = "select * from chart where user_id = '" + req.body.user_id+
        "' and DATE_FORMAT(date,'%Y') = DATE_FORMAT('"+req.body.date+"','%Y')";
    console.log(sqlStr);
    pool.getConnection((err, conn) => {
        conn.query(sqlStr, (err, result) => {
            if (err) {
                conn.connect(handleError);
                conn.on('error', handleError);
                return res.json({
                    code: 300,
                    msg: "获取失败",
                    err: err.code
                });
            }
            else {
                console.log(result);
                return res.json({
                    code: 200,
                    msg: "查询成功",
                    data: result
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 是否存在
//     params{
//         user_id:,
//         date:
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/isExist", (req, res) => {
    console.log("isExist");
    var sqlStr = "select chart_id, pass from chart where user_id = '" + req.body.user_id+"' and date = '"+req.body.date+"' limit 1";
    console.log(sqlStr);
    pool.getConnection((err, conn) => {
        conn.query(sqlStr, (err, result) => {
            if (err) {
                conn.connect(handleError);
                conn.on('error', handleError);
                return res.json({
                    code: 300,
                    msg: "获取失败",
                    err: err.code
                });
            }
            else if(result.length==0){
                console.log(result);
                return res.json({
                    code: 201,
                    msg: "不存在",
                });
            }else{
                console.log(result);
                return res.json({
                    code: 200,
                    msg: "存在",
                    data:result
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 添加
//     params{
//         user_id:,
//         date:
//         pass
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/add", (req, res) => {
    console.log("add");
    var sqlStr = "insert into chart (user_id, date, pass) values('"+req.body.user_id+"','"+
        req.body.date+"',"+req.body.pass+")";
    console.log(sqlStr);
    pool.getConnection((err, conn) => {
        conn.query(sqlStr, (err, result) => {
            if (err) {
                conn.connect(handleError);
                conn.on('error', handleError);
                return res.json({
                    code: 300,
                    msg: "获取失败",
                    err: err.code
                });
            }
            else {
                console.log(result);
                return res.json({
                    code: 200,
                    msg: "添加成功",
                    data: result
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 修改
//     params{
//         chart_id:,
//         pass:
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/update", (req, res) => {
    console.log("query");
    var sqlStr = "update chart set pass = " + req.body.pass+" where chart_id = "+req.body.chart_id;
    console.log(sqlStr);
    pool.getConnection((err, conn) => {
        conn.query(sqlStr, (err, result) => {
            if (err) {
                conn.connect(handleError);
                conn.on('error', handleError);
                return res.json({
                    code: 300,
                    msg: "获取失败",
                    err: err.code
                });
            }
            else {
                console.log(result);
                return res.json({
                    code: 200,
                    msg: "查询成功",
                    data: result
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

function handleError(err) {
    if (err) {
        // 如果是连接异常，自动重新连接
        console.log('err code:' + err.code);
        if (err.code === 'PROTOCOL_CONNECTION_LOST' || err.code === 'PROTOCOL_ENQUEUE_AFTER_FATAL_ERROR' || err.code === 'ETIMEDOUT') {
            connect();
        } else {
            console.error(err.stack || err);
        }
    }
}
