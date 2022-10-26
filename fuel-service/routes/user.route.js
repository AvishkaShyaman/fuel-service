module.exports = (app, express) => {
  const router = express.Router();
  const { getUserVisitesheds } = require("../controllers/user.controller");

  const { isAuthorised } = require("../services/auth");

  //get all user visited sheds
  router.get("/entered-list/:userId", isAuthorised, getUserVisitesheds);

  app.use("/api/v1", router);
};
