require("dotenv").config()
const express = require("express");
const bodyParser = require("body-parser");
const MongoClient = require('mongodb').MongoClient;
const md5 = require('md5');
const jwt = require('jsonwebtoken');
var ObjectId = require('mongodb').ObjectID

var db;
const router = express.Router();
const app = express();
app.use(bodyParser.json({ limit: '10mb' }));
app.use(bodyParser.urlencoded({ extended: true, limit: '10mb' }));

const DATABASE_URL = "mongodb://uq2br62itgkczdy3ld7o:3ax10rxF8qHi5uciSyuF@bkj8bp4plc6eccf-mongodb.services.clever-cloud.com:27017/bkj8bp4plc6eccf"
const DATABASE_NAME = "bkj8bp4plc6eccf"

router.post("/login", (req, res) => {
    const email = req.body.email.toLowerCase();
    const password = md5(req.body.password)
    db.collection("users").find({ email: email, password: password }).toArray(function(err, docs) {
        if (docs.length != 0) {
            //create user detail object from received information
            const user = {
                    name: docs[0].name,
                    email: docs[0].email
                }
                //create token for loggedin user with JWT library
            const acccessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET);
            res.send(JSON.stringify({
                status: 200,
                message: "You are logged in successfully",
                accessToken: acccessToken
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
    const email = req.body.email.toLowerCase();
    const password = md5(req.body.password);

    db.collection("users").find({ email: email }).toArray(function(err, docs) {
        if (docs.length == 0) {
            db.collection("users").insertOne({
                name: req.body.name,
                email: email,
                phone: req.body.phone,
                password: password
            });
            //create user detail object from received information
            const user = {
                    name: req.body.name,
                    email: email
                }
                //create token for loggedin user with JWT library
            const acccessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET);
            res.send(JSON.stringify({
                status: 200,
                message: "Account created successfully.",
                accessToken: acccessToken
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

router.post("/user", authenticate, (req, res) => {
    db.collection("users")
        .find({ email: req.body.user.email.toLowerCase() })
        .project({ password: 0, _id: 0 })
        .toArray(function(err, docs) {
            if (docs.length != 0) {
                res.send(JSON.stringify(docs[0]))
            }
        });
})

router.put("/updateUser", authenticate, (req, res) => {
    if (req.body.user.email == req.body.email) {
        console.log(req.body)
        var query = { email: req.body.user.email };
        var data = req.body;
        var name = data.name;
        var phone = data.phone;
        var address = data.address;

        if (name == null || address == null || phone == null) {
            return res.sendStatus(403)
        }

        db.collection("users").updateOne(query, { $set: { name: name, phone: phone, address: address } }, function(err, result) {
            if (err) return res.sendStatus(503);
            console.log("1 document updated");
            res.send(JSON.stringify({
                status: 200,
                message: "Profile updated successfully"
            }))
        });
    } else {
        res.sendStatus(403)
    }
})


// authenticate and send address
router.post("/address", authenticate, (req, res) => {
    db.collection("users")
        .find({ email: req.body.user.email.toLowerCase() })
        .project({ address: 1 })
        .toArray(function(err, docs) {
            if (docs.length != 0) {
                if (!docs[0].address) {
                    res.send(JSON.stringify(docs[0].address))
                } else {
                    res.send("")
                }

            }
        });
})

router.get("/categories", (req, res) => {
    db.collection("categories")
        .find()
        .project({ _id: 0 })
        .toArray(function(err, docs) {
            if (docs.length != 0) {
                res.send(JSON.stringify(docs))
            }
        });
})



router.get("/donatedItems", authenticate, (req, res) => {
    db.collection("donatedItem").find().toArray(function(err, docs) {
        res.send(JSON.stringify(docs))
    });
})

//  authenticate user and remove item 
router.delete("/donatedItem", authenticate, (req, res) => {
    db.collection("donatedItem").deleteOne({
        _id: ObjectId(req.body.ID)
    }, function(err, result) {
        if (err) {
            res.send(JSON.stringify({
                status: 403,
                message: "failed to delete item."
            }))
        } else {
            res.send(JSON.stringify({
                status: 200,
                message: "item removed."
            }))
        }
    })
})

router.post("/donorInfo", authenticate, (req, res) => {
    if (req.body.email) {
        db.collection("users")
            .find({ email: req.body.email.toLowerCase() })
            .project({ phone: 1, name: 1, _id: 0 })
            .toArray(function(err, docs) {
                if (docs.length != 0) {
                    res.send(JSON.stringify(docs[0]))
                }
            });
    }
})


router.put("/donatedItem", authenticate, (req, res) => {
    console.log("add donatedItem " + req.body)
    db.collection("donatedItem").insertOne(
        req.body
    );
    res.send({
        status: 200,
        message: "Item added for donation"
    })
})

//  authenticate user and remove post 
router.delete("/postUser", authenticate, (req, res) => {
    db.collection("postUser").deleteOne({
        _id: ObjectId(req.body.ID)
    }, function(err, result) {
        if (err) {
            res.send(JSON.stringify({
                status: 403,
                message: "failed to delete item."
            }))
        } else {
            res.send(JSON.stringify({
                status: 200,
                message: "item removed."
            }))
        }
    })
})

router.put("/postUser", authenticate, (req, res) => {
    console.log("add Post " + req.body)

    db.collection("postUser").insertOne({
            id: req.body.id,
            email: req.body.email,
            title: req.body.title,
            description: req.body.description,
            text: req.body.text,
            picture: req.body.picture
        },
        function(err, result) {
            res.send({
                status: 200,
                message: "Post Success"
            })
        });

})

router.get("/postUser", authenticate, (req, res) => {
    console.log("get posts list")
    db.collection("postUser").find().toArray(function(err, docs) {
        res.send(JSON.stringify(docs))
    });
})

router.get("/needyPersons", (req, res) => {
    db.collection("needyPersons").find().toArray(function(err, docs) {
        res.send(JSON.stringify(docs))
    });
})

router.put("/needyPersons", (req, res) => {
    console.log("add Needy " + req.body)
    db.collection("needyPersons").insertOne(
        req.body
    );
    res.send({
        status: 200,
        message: "Needy person added"
    });
})
app.use("/", router);

function authenticate(req, res, next) {
    const headers = req.headers['authorization']
    const token = headers && headers.split(' ')[1];
    if (token == null) {
        // user not authorised
        return res.sendStatus(401)
    }
    jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, user) => {
        if (err) return res.sendStatus(403);
        req.body.user = user;
        console.log("token = ", token, " user = ", user);
        next();
    });
}

MongoClient.connect(DATABASE_URL, { useNewUrlParser: true, useUnifiedTopology: true }, function(err, client) {
    console.log("Connected successfully to server");
    db = client.db(DATABASE_NAME);
    app.listen(process.env.PORT || 3000, () => {
        console.log(`App Started on PORT ${process.env.PORT || 3000}`);
    });
});