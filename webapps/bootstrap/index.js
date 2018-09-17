const express = require("express");
const app = express();
const hostname = "127.0.0.1";
const port = 8090;

app.use(express.static('.'));

app.listen(port, hostname);