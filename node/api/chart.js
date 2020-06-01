const { pool, resJson } = require('../connect')
//全局Express框架
const express = require("express");
const mysql  = require('mysql');
const router = express.Router();
module.exports = router;


// 获取信息
//     params{
//         user_id:,
//         date:
//         type:
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/query", (req, res) => {
    console.log("query");
    let sqlStr;
    console.log(req.body);
    if(req.body.type==0) {
        sqlStr = "select date,pass_red,pass_yellow,pass_green,pass_blue from chart where user_id = '" + req.body.user_id +
            "' and YEARWEEK(date_format(date,'%Y-%m-%d'),1) = YEARWEEK(date_format('" + req.body.date + "','%Y-%m-%d'),1)";
    }else if(req.body.type==1) {
        sqlStr = "select date,pass_red,pass_yellow,pass_green,pass_blue from chart where user_id = '" + req.body.user_id +
            "' and DATE_FORMAT(date,'%Y%m') = DATE_FORMAT('" + req.body.date + "','%Y%m')";
    }else if(req.body.type==2){
        sqlStr = "select date,pass_red,pass_yellow,pass_green,pass_blue from chart where user_id = '" + req.body.user_id +
            "' and DATE_FORMAT(date,'%Y') = DATE_FORMAT('" + req.body.date + "','%Y')";
    }
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
    var sqlStr = "select * from chart where user_id = '" + req.body.user_id+"' and date = '"+req.body.date+"' limit 1";
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
//         pass_red:
//         pass_yellow:
//         pass_green:
//         pass_blue:
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/add", (req, res) => {
    console.log("add");
    var sqlStr = "insert into chart (user_id, date, pass_red,pass_yellow,pass_green,pass_blue) values('"+req.body.user_id+"','"+
        req.body.date+"',"+req.body.pass_red+","+req.body.pass_yellow+","+req.body.pass_green+","+req.body.pass_blue+")";
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
//         pass_red:
//         pass_yellow:
//         pass_green:
//         pass_blue:
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/update", (req, res) => {
    console.log("query");
    var sqlStr = "update chart set pass_red = " + req.body.pass_red+
        ", pass_yellow = "+req.body.pass_yellow
        +", pass_green = "+req.body.pass_green
        +", pass_blue = "+req.body.pass_blue+" where chart_id = "+req.body.chart_id;
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
