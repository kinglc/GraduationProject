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
