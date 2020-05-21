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
