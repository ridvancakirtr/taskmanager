module.exports = app => {
  const task = require("../controllers/task.controller.js");

  var router = require("express").Router();

  // Yeni Görev Ekler
  router.post("/", task.create);

  // Kayıtlı Görevleri Listeler
  router.get("/", task.findAll);

  // ID 'ye Göre Görevi Listeler
  router.get("/:id", task.findOne);

  // ID 'ye Göre Görevi Günceller
  router.put("/:id", task.update);

  // ID 'ye Göre Görevi Siler
  router.delete("/:id", task.delete);

  // Tiplere Göre Görevleri Listeler | 0 ->Gunluk  | 1->Haftalık | 2->Aylık |
  router.get("/type/:type", task.findType);

  // Tüm Görevleri Siler
  router.delete("/", task.deleteAll);

  app.use("/api/tasks", router);
  
};
