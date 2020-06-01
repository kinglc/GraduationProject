const { pool, resJson } = require('../connect')
//全局Express框架
const express = require("express");
const mysql  = require('mysql');
const router = express.Router();
module.exports = router;


// 获取信息
//     params{
//         user_id:,
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/query", (req, res) => {
    console.log("query");
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

// 新建
//     params{
//          user_id:,
//          title:'',
//          content:'',
//          urgency:
//     }
//     return{
//         code:
//         msg:""
router.post("/add", (req, res) => {
    console.log("add");
    var sqlStr = "insert into plan (user_id, title, content, urgency, pass, finish) values('"+req.body.user_id+"','"+
        req.body.title+"','"+req.body.content+"',"+req.body.urgency+",'0m',0)";
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
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 删除
//     params{
//         plan_id:
//     }
//     return{
//         code:
//         msg:""
//     }
router.post("/delete", (req, res) => {
    console.log("delete");
    var sqlStr = "delete from plan where plan_id = " + req.body.plan_id;
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
                    msg: "删除成功",
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 修改
//     params{
//          plan_id:,
//          title:
//          content:
//          urgency:
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/update", (req, res) => {
    console.log("update");
    var sqlStr = "update plan set title = \"" +req.body.title+ "\" , content = \""+req.body.content+
        "\" , urgency = "+req.body.urgency+ " where plan_id = " + req.body.plan_id;
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

// 进行
//     params{
//          plan_id:,
//          pass:
//     }
//     return{
//         code:
//         msg:""
router.post("/pass", (req, res) => {
    console.log("pass");
    var sqlStr = "update plan set pass = \"" +req.body.pass+ "\" where plan_id = " + req.body.plan_id;
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
                    msg: "修改成功"
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 完成
//     params{
//          plan_id:,
//     }
//     return{
//         code:
//         msg:""
router.post("/finish", (req, res) => {
    console.log("pass");
    var sqlStr = "update plan set finish = 1 where plan_id = " + req.body.plan_id;
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
                    msg: "修改成功"
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
