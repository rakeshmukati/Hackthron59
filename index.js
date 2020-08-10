const express = require("express");
const bodyParser = require("body-parser");
const MongoClient = require('mongodb').MongoClient;
var md5 = require('md5');
const router = express.Router();
const app = express();
app.use(bodyParser)

const DATABASE_URL = "mongodb://u0xvnfqtghlzyzjll6tg:JQ1kWeJGxzjB8ufJauzi@b7mseqkz0wkghgb-mongodb.services.clever-cloud.com:27017/b7mseqkz0wkghgb"
const DATABASE_NAME = "b7mseqkz0wkghgb"

router.post("/login", (req, res) => {

})

router.post("/signup", (req, res) => {

})

MongoClient.connect(DATABASE_URL, { useNewUrlParser: true, useUnifiedTopology: true }, function(err, client) {
    console.log("Connected successfully to server");
    db = client.db(DATABASE_NAME);
    app.listen(process.env.PORT || 3000, () => {
        console.log(`App Started on PORT ${process.env.PORT || 3000}`);
    });
});