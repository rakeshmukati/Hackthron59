const express = require("express");
const bodyParser = require("body-parser");
const MongoClient = require('mongodb').MongoClient;
var md5 = require('md5');

var db;
const router = express.Router();
const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const DATABASE_URL = "mongodb://uq2br62itgkczdy3ld7o:3ax10rxF8qHi5uciSyuF@bkj8bp4plc6eccf-mongodb.services.clever-cloud.com:27017/bkj8bp4plc6eccf"
const DATABASE_NAME = "bkj8bp4plc6eccf"

router.post("/login", (req, res) => {
    console.log(req.body)
})

router.post("/signup", (req, res) => {
    console.log(req.body)
})

MongoClient.connect(DATABASE_URL, { useNewUrlParser: true, useUnifiedTopology: true }, function(err, client) {
    console.log("Connected successfully to server");
    db = client.db(DATABASE_NAME);
    app.listen(process.env.PORT || 3000, () => {
        console.log(`App Started on PORT ${process.env.PORT || 3000}`);
    });
});