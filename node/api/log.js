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
    var sqlStr = "select plan_time,todo_day from user where user_id = '" + req.body.ids+"'";
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

// 插入log
//     params{
//          user_id:user1,
//          type:
//          name:
//          date:
//     }
//     return{
//         code:
//         msg:""
router.post("/set", (req, res) => {
    console.log("set");
    var sqlStr = "insert into log (user_id, type, name, date) values('"+req.body.user_id+"',"+req.body.type+",'" +
        req.body.name+"','"+req.body.date+"')";
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


//是否存在
//     params{
//          user_id:user1,
//          type:
//          date:
//     }
//     return{
//         code:
//         msg:""
//         data:
router.post("/isExist", (req, res) => {
    console.log("isExist");
    var sqlStr = "select log_id from log where user_id = '" + req.body.user_id+"' and type = "+req.body.type
        + " and date = '"+req.body.date+"' limit 1";
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
                return res.json({
                    code: 201,
                    msg: "不存在",
                });
            }else{
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


// 更新信息
//     params{
//          log_id:
//          name:
//     }
//     return{
//         code:
//         msg:""
router.post("/update", (req, res) => {
    console.log("update");
    console.log(req);
    var sqlStr = "update log set name = '"+ req.body.name+"' where log_id = "+req.body.log_id;
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
