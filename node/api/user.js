const { pool, resJson } = require('../connect')
//全局Express框架
const express = require("express");
const mysql  = require('mysql');
const router = express.Router();
module.exports = router;

//注册
//     params{
//         user_id:""
//         name:""
//     }
//     return{
//         code:
//         msg:""
//

router.post("/register", (req, res) => {
    // 定义SQL语句
    console.log('register');
    var sqlStr = "insert into user (user_id, name, todo_day, plan_time, " +
        "friend, prize_todo, prize_plan)" +" values('"+req.body.user_id+"','"+
        req.body.name+"',0,'0m',\"'"+req.body.user_id+"',\",-1,0)";
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
                    msg: "注册成功",
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 用户名是否存在
//     params{
//         name:""
//     }
//     return{
//         code:
//         msg:""
//
router.post("/isNameExist", (req, res) => {
    console.log("isNameExist");
    var sqlStr = "select 1 from user where name = '" + req.body.name+"' limit 1";
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
            else if(result.length===0){
                return res.json({
                    code: 200,
                    msg: "不存在",
                });
            }else{
                console.log(result);
                console.log('存在');
                return res.json({
                    code: 201,
                    msg: "存在",
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 用户id是否存在
//     params{
//         user_id:""
//     }
//     return{
//         code:
//         msg:""
//
router.post("/isIdExist", (req, res) => {
    console.log("isIdExist");
    var sqlStr = "select 1 from user where user_id = '" + req.body.user_id+"' limit 1";
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