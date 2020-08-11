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
    const password = md5(req.body.password)
    db.collection("users").find({ email: req.body.email.toLowerCase(), password: password }).toArray(function(err, docs) {
        if (docs.length != 0) {
            res.send(JSON.stringify({
                status: 200,
                message: "You are logged in successfully",
                accessToken: "this is access token for this login"
            }));
        } else {
            res.send(JSON.stringify({
                status: 403,
                message: "wrong email password."
            }));
        }
    });

    console.log(req.body)
})

router.post("/signup", (req, res) => {
    const password = md5(req.body.password)
    db.collection("users").find({ email: req.body.email }).toArray(function(err, docs) {
        if (docs.length == 0) {
            db.collection("users").insertOne({
                name: req.body.name,
                email: req.body.email.toLowerCase(),
                phone: req.body.phone,
                password: password
            });
            res.send(JSON.stringify({
                status: 200,
                message: "account created"
            }));
        } else {
            res.send(JSON.stringify({
                status: 409,
                message: "account already exists."
            }));
        }
    });
    console.log(req.body)
})

router.get("/donatedItem", (req, res) => {
    db.collection("donatedItem").find({ email: req.body.email }).toArray(function(err, docs) {
        res.send(Json.stringify(docs))
    });
})

router.get("/requiredItem", (req, res) => {
    db.collection("requiredItem").find({ email: req.body.email }).toArray(function(err, docs) {
        res.send(Json.stringify(docs))
    });
})

app.use("/", router);

MongoClient.connect(DATABASE_URL, { useNewUrlParser: true, useUnifiedTopology: true }, function(err, client) {
    console.log("Connected successfully to server");
    db = client.db(DATABASE_NAME);
    app.listen(process.env.PORT || 3000, () => {
        console.log(`App Started on PORT ${process.env.PORT || 3000}`);
    });
});