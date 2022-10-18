module.exports = (app, express) => {
  const router = express.Router();
  const { isAuthorised } = require("../services/auth");
  const {
    addUserstoShedQue,
    addShed,
    userExitFromQue,
    getAllSheds,
    getSingleShed,
    addFuelToShedStart,
    addFuelToShedEnd,
    getShedQueue
  } = require("../controllers/sheds.controller");
  
  //create shed with shed name
  router.post("/create/shed", addShed);

  //add user to a que
  router.post("/join/:shedId/:userId", addUserstoShedQue);

  //user exit the que
  router.put("/exit/:shedId/:userId", userExitFromQue);

  //get all sheds
  router.get("/shed/getAll", getAllSheds);

  //get queue by fule type of a single shed
  router.get("/shed-queue/:shedId", getShedQueue);

  //get single shed
  router.get("/shed/:shedId", getSingleShed);

  //update fuel type at fule-arival
  router.put("/shed/fule-arival/:shedId", addFuelToShedStart);

  //update fuel type at shed/fule-finish/:shedId/
  router.put("/shed/fule-finish/:shedId/", addFuelToShedEnd);

  app.use("/api/v1", isAuthorised, router);
  // app.use("/api/v1", router);
};
