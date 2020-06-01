const { pool, resJson } = require('../connect')
//全局Express框架
const express = require("express");
const mysql  = require('mysql');
const router = express.Router();
module.exports = router;

//加载配置文件
const conn = mysql.createConnection({
    host     : '140.143.78.135',
    user     : 'jc',
    password : 'jc123',
    port: '3307',
    database: 'timeblock',
});


// 获取信息
//     params{
//         user_id:user1,
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/query", (req, res) => {
    console.log("query");
    var sqlStr = "select prize_plan,prize_todo from user where user_id = '" + req.body.user_id+"'";
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

// 设置用户todo成就
//     params{
//          user_id:
//          prize_todo:
//     }
//     return{
//         code:
//         msg:""
//         date:
//     }
router.post("/todo", (req, res) => {
    console.log("todo");
    var sqlStr = "update user set prize_todo =" +req.body.prize_todo+ " where user_id = \"" + req.body.user_id+"\"";
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
                    msg: "更新成功",
                    data:result
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 设置用户plan成就
//     params{
//          user_id:
//          prize_plan:
//     }
//     return{
//         code:
//         msg:""
//         date:
//     }
router.post("/plan", (req, res) => {
    console.log("plan");
    var sqlStr = "update user set prize_plan =" +req.body.prize_plan+ " where user_id = \"" + req.body.user_id+"\"";
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
