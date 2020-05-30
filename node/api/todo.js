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
    var sqlStr = "select * from todo where user_id = '" + req.body.user_id+"' and date = '"+req.body.date+"'";
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
//         todo_id:
//     }
//     return{
//         code:
//         msg:""
//     }
router.post("/delete", (req, res) => {
    console.log("delete");
    var sqlStr = "delete from todo where todo_id = " + req.body.todo_id;
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


// 删除某日期全部
//     params{
//         user_id:
//         date:
//     }
//     return{
//         code:
//         msg:""
//     }
router.post("/deleteAll", (req, res) => {
    console.log("deleteAll");
    var sqlStr = "delete from todo where user_id = '" + req.body.user_id+"' and date = '"+req.body.date+"'";
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
//          date:
//     }
//     return{
//         code:
//         msg:""
//     }
router.post("/add", (req, res) => {
    console.log("add");
    var sqlStr = "insert into todo (user_id, title, date, isChecked) values('"+req.body.user_id+"','"+
        req.body.title+"','"+req.body.date+"',0)";
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
//          todo_id:
//          title:
//     }
//     return{
//         code:
//         msg:""
//     }
router.post("/edit", (req, res) => {
    console.log("edit");
    var sqlStr = "update todo set title = \"" +req.body.title+ "\" where todo_id = " + req.body.todo_id;
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

// 选取是否完成
//     params{
//          todo_id:
//          isChecked:
//     }
//     return{
//         code:
//         msg:""
//     }
router.post("/check", (req, res) => {
    console.log("check");
    var sqlStr = "update todo set isChecked =" +req.body.isChecked+ " where todo_id = " + req.body.todo_id;
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

// 当日事项数
//     params{
//          user_id:
//          date:
//     }
//     return{
//         code:
//         msg:""
//         date:
//     }
router.post("/number", (req, res) => {
    console.log("number");
    var sqlStr = "select count(*) from todo where user_id =\"" +req.body.user_id+ "\" and date = \"" + req.body.date+"\"";
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
