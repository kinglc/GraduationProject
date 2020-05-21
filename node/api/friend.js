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

// 获取好友列表
//     params{
//         user_id:""
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/query", (req, res) => {
    console.log("query");
    const sqlStr = "select friend from user where user_id = '" + req.body.user_id+"'";
    var friends;
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

// 获取好友信息
//     params{
//         friends:""
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/getInfo", (req, res) => {
    console.log("getinfo");
    var sqlStr = "select * from user where user_id in (" + req.body.ids+")";
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


// 好友是否存在
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

//删除或他添加好友
// params{
//     user_id:""
//          ids:""
// }
// return{
//     code:
//     msg:""
// }
router.post("/update", (req, res) => {
    console.log("update");
    var sqlStr = "update user set friend =\"" +req.body.ids+ "\" where user_id = '" + req.body.user_id+"'";
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
                    // data:
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// //添加好友
// // params{
// //     user_id:""
// //          ids:""
// // }
// // return{
// //     code:
// //     msg:""
// // }
// router.post("/add", (req, res) => {
//     console.log("add");
//     var sqlStr = "update user set friend =\"" +req.body.ids+ "\" where user_id = '" + req.body.user_id+"'";
//     console.log(sqlStr);
//     pool.getConnection((err, conn) => {
//         conn.query(sqlStr, (err, result) => {
//             if (err) {
//                 conn.connect(handleError);
//                 conn.on('error', handleError);
//                 return res.json({
//                     code: 300,
//                     msg: "获取失败",
//                     err: err.code
//                 });
//             }
//             else {
//                 console.log(result);
//                 return res.json({
//                     code: 200,
//                     msg: "添加成功"
//                     // data:
//                 });
//             }
//         });
//         pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
//     });
// });

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
