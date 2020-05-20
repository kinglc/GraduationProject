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
    const param = req.body;
    const user_id = param.user_id;
    const sqlStr = "select friend from user where user_id = '" + user_id+"'";
    console.log(param);
    console.log(typeof param);
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
                res.json({
                    code: 200,
                    msg: "查询成功",
                    data: result
                });
            }
        });
        pool.releaseConnection(conn) // 释放连接池，等待别的连接使用
    })

});

//删除好友
// params{
//     user_id:""
//         id:""
// }
// return{
//     code:
//     msg:""
// }
router.post("/delete", (req, res) => {
    const param = req.body;
    const user_id = param.user_id;
    const id = param.id;
    console.log(param);
    // const sqlStr = "select friend from user where user_id = '" + user_id+"'";
    const sqlStr = "select * from user";
    conn.query(sqlStr, (err, result) => {
        if (err) {
            conn.connect(handleError);
            conn.on('error', handleError);
            return res.json({
                code: 300,
                msg: "获取失败",
                err: err
            });
        }
        else {
            console.log(result);
            res.json({
                code: 200,
                msg: "查询成功",
                data: result
            });
        }
    });
});

//添加好友


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
