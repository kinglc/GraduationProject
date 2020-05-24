const { pool, resJson } = require('../connect')
//全局Express框架
const express = require("express");
const mysql  = require('mysql');
const router = express.Router();
module.exports = router;


// 获取信息
//     params{
//         user_id:,
//         date
//     }
//     return{
//         code:
//         msg:""
//         data:[]
router.post("/query", (req, res) => {
    console.log("query");
    var sqlStr = "select * from note where user_id = '" + req.body.user_id+
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


// 删除
//     params{
//         note_id:
//     }
//     return{
//         code:
//         msg:""
//     }
router.post("/delete", (req, res) => {
    console.log("delete");
    var sqlStr = "delete from note where note_id = " + req.body.note_id;
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

// 添加
//     params{
//          user_id:
//          title:
//          content:
//          place:
//          date:
//          time:
//     }
//     return{
//         code:
//         msg:""
//     }
router.post("/add", (req, res) => {
    console.log("add");
    var sqlStr = "insert into note (user_id, title, content, place, date, time) values('"+req.body.user_id+"','"+
        req.body.title+"','"+req.body.content+"','"+req.body.place+"','"+req.body.date+"','"+req.body.time+"')";
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
                    data:result.insertId
                });
            }
        });
        pool.releaseConnection(conn); // 释放连接池，等待别的连接使用
    });
});

// 编辑
//     params{
//          note_id:
//          title:
//          content:
//          place:
//          date:
//          time:
//     }
//     return{
//         code:
//         msg:""
//     }
router.post("/update", (req, res) => {
    console.log("update");
    var sqlStr = "update note set title = \"" +req.body.title+ "\" , content = \""+req.body.content+
        "\" , place = \""+req.body.place+ "\" , date = \""+req.body.date+
        "\" , time = \""+req.body.time+"\" where note_id = " + req.body.note_id;
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
                    msg: "修改成功",
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
