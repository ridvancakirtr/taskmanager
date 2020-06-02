const express = require("express");
const bodyParser = require("body-parser");
const cors = require("cors");
const app = express();

var corsOptions = {
  origin: "http://localhost:8081"
};

app.use(cors(corsOptions));

// json - application/json
app.use(bodyParser.json());

// içerik - application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: true }));

const db = require("./app/models");
db.mongoose
  .connect(db.url, {
    useNewUrlParser: true,
    useUnifiedTopology: true
  })
  .then(() => {
    console.log("Connected to the database!");
  })
  .catch(err => {
    console.log("Cannot connect to the database!", err);
    process.exit();
  });

// Ana Sayfa
app.get("/", (req, res) => {
  res.json({ message: "Welcome to Task Manager Application" });
});

require("./app/routes/task.routes")(app);

// Port Atama İşlemi
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}.`);
});
