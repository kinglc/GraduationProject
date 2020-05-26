const { pool, resJson } = require('../connect')
//全局Express框架
const express = require("express");
const mysql  = require('mysql');
const router = express.Router();
module.exports = router;

//登录
router.get("/query", (req, res) => {
    // 定义SQL语句
    const id = req.body.id || req.query.id;
    const sqlStr = "select * from achieve";

    conn.query(sqlStr, id, (err, data) => {
        if (err) return res.json({code: 404, data: "获取失败",msg:err});
        res.json({
            code: 0, data: data
        });
        console.log(data);
    });
});

// 用户是否存在
//     params{
//         id:""
//     }
//     return{
//         code:
//         msg:""
//
router.post("/isExist", (req, res) => {
    console.log("isExist");
    var sqlStr = "select 1 from user where user_id = '" + req.body.id+"' limit 1";
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
                return res.json({
                    code: 201,
                    msg: "不存在",
                });
            }else{
                return res.json({
                    code: 200,
                    msg: "存在",
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});


// 获取用户累计天数
//     params{
//          user_id:
//     }
//     return{
//         code:
//         msg:""
//         date:
//     }
router.post("/getDay", (req, res) => {
    console.log("getday");
    var sqlStr = "select todo_day from user where user_id =\"" +req.body.user_id + "\"";
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
                    data:result
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});


// 设置用户累计天数
//     params{
//          user_id:
//          todo_day:
//     }
//     return{
//         code:
//         msg:""
//     }
router.post("/setDay", (req, res) => {
    console.log("setday");
    var sqlStr = "update user set todo_day =" +req.body.todo_day+ " where user_id = \"" + req.body.user_id+"\"";
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
                    msg: "更新成功"
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});


// 获取用户累计时间
//     params{
//          user_id:
//     }
//     return{
//         code:
//         msg:""
//         date:
//     }
router.post("/getTime", (req, res) => {
    console.log("getTime");
    var sqlStr = "select plan_time from user where user_id =\"" +req.body.user_id + "\"";
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
                    data:result
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});


// 设置用户累计时间
//     params{
//          user_id:
//          plan_day:
//     }
//     return{
//         code:
//         msg:""
//     }
router.post("/setTime", (req, res) => {
    console.log("setTime");
    var sqlStr = "update user set plan_time =\"" +req.body.plan_time+ "\" where user_id = \"" + req.body.user_id+"\"";
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
                    msg: "更新成功"
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});