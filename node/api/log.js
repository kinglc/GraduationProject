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

//查询数据（all方法支持POST、GET、PUT、PATCH、DELETE传参方式）
router.get("/select", (req, res) => {
    // 定义SQL语句
    const id = req.body.id || req.query.id;
    const sqlStr = "select * from achieve ";

    conn.query(sqlStr, id, (err, data) => {
        if (err) return res.json({code: 404, data: "获取失败"});
        res.json({
            code: 0, data: data
        });
    });
});

