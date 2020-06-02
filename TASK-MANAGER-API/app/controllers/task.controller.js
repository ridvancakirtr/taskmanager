const db = require("../models");
const Task = db.tasks;

// Yeni Görev Oluşturur ve Kayıt Eder.
exports.create = (req, res) => {
  // Kayıt İsteği Kontrolü
  if (!req.body.title) {
    res.status(400).send({ status:"OK",message: "Task Title can not be empty!" });
    return;
  }

  // Görev Şeması
  const task = new Task({
    title: req.body.title,
    description: req.body.description,
    type: req.body.type,
  });

  if (req.body.type>2) {
    return res.status(400).send({
      status:"ERROR",message: "Enter valid type range Ex : | 0 ->Day  | 1->Week | 2->Month |"
    });
  }

  // Yeni Görev Ekler
  task
    .save(task)
    .then(data => {
      res.send({data:data});
    })
    .catch(err => {
      res.status(500).send({
        status:"ERROR",message:
          err.message || "Some error occurred while creating the Task."
      });
    });
};

// Kayıtlı Görevleri Listeler
exports.findAll = (req, res) => {
  const title = req.query.title;
  var condition = title ? { title: { $regex: new RegExp(title), $options: "i" } } : {};
  Task.find(condition)
    .then(data => {
      res.send({status:"OK",data});
    })
    .catch(err => {
      res.status(500).send({
        status:"ERROR",message:
          err.message || "Some error occurred while retrieving Task."
      });
    });
};


// ID 'ye Göre Görevi Listeler
exports.findOne = (req, res) => {
  const id = req.params.id;
  Task.findById(id)
    .then(data => {
      if (!data)
        res.status(404).send({ status:"ERROR",message: "Not found Task with id " + id });
      else res.send({status:"OK",data:data});
    })
    .catch(err => {
      res
        .status(500)
        .send({ status:"ERROR",message: "Error retrieving Task with id=" + id });
    });
};


// ID 'ye Göre Görevi Günceller
exports.update = (req, res) => {
  if (!req.body) {
    return res.status(400).send({
      status:"ERROR",message: "Data to update can not be empty!"
    });
  }

  const id = req.params.id;
  Task.findByIdAndUpdate(id, req.body, { useFindAndModify: false })
    .then(data => {
      if (!data) {
        res.status(404).send({
          status:"ERROR",message: `Cannot update Task with id=${id}. Maybe Task was not found!`
        });
      } else res.send({ status:"OK",message: "Task was updated successfully." });
    })
    .catch(err => {
      res.status(500).send({
        status:"ERROR",message: "Error updating Task with id=" + id
      });
    });
};

// ID 'ye Göre Görevi Siler
exports.delete = (req, res) => {
  const id = req.params.id;

  Task.findByIdAndRemove(id, { useFindAndModify: false })
    .then(data => {
      if (!data) {
        res.status(404).send({
          status:"ERROR", message: `Cannot delete Task with id=${id}. Maybe Task was not found!`
        });
      } else {
        res.send({
          status:"OK",message: "Task was deleted successfully!"
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        status:"ERROR",message: "Could not delete Task with id=" + id
      });
    });
};

// Tiplere Göre Görevleri Listeler | 0 ->Gunluk  | 1->Haftalık | 2->Aylık |
exports.findType = (req, res) => {
  const type = req.params.type;
  var condition = type ? { type: { $regex: new RegExp(type), $options: "i" } } : {};
  Task.find(condition)
    .then(data => {
      if (!data || data.length==0)
        res.status(404).send({ status:"ERROR",message: "Not found Task with type " + type });
      else res.send({status:"OK",data:data});
    })
    .catch(err => {
      res
        .status(500)
        .send({ status:"ERROR",message: "Error retrieving Task with type=" + type });
    });
};

// Tüm Görevleri Siler
exports.deleteAll = (req, res) => {
  Task.deleteMany({})
    .then(data => {
      res.send({
        status:"OK",message: `${data.deletedCount} Tasks were deleted successfully!`
      });
    })
    .catch(err => {
      res.status(500).send({
        status:"ERROR",message:
          err.message || "Some error occurred while removing all tasks."
      });
    });
};
