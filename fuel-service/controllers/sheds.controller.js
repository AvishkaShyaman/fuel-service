const User = require("../models/user.model");
const Shed = require("../models/sheds.model");

const addShed = async (req, res) => {
  try {
    if (req.user.role !== "Admin") {
      res.status(403).json("You are not allowed!");
    }
    const newshed = new Shed(req.body);
    const savedshed = await newshed.save();
    res.status(201).json({
      success: true,
      savedshed,
    });
  } catch (err) {
    res.status(500).json({
      success: false,
      message: error.message,
    });
  }
};

const addUserstoShedQue = async (req, res) => {
  if (req.params.userId == req.user.id || req.user.role == "Admin") {
    if (!req.body.fuelType) {
      res.status(400).json("Fuel type required");
      return;
    }
    try {
      const updatedShed = await Shed.updateOne(
        { _id: req.params.shedId },
        {
          $push: {
            queue: {
              id: req.params.userId,
              fuelType: req.body.fuelType,
              arrivalTime: new Date(),
              depatureTime: null,
            },
          },
        },
        { new: true }
      );

      const updatedUser = await User.updateOne(
        { _id: req.params.userId },
        {
          $push: {
            visitedSheds: req.params.shedId,
          },
        },
        { new: true }
      );

      res.status(200).json({
        success: true,
        message: "User added to Quaue succesfully",
      });
    } catch (err) {
      res.status(500).json({
        success: false,
        message: error.message,
      });
    }
  } else {
    res.status(403).json("You are not allowed!");
  }
};

const userExitFromQue = async (req, res) => {
  try {
    const updatedShed = await Shed.updateOne(
      { _id: req.params.shedId, "queue.id": req.params.userId },
      {
        $set: {
          "queue.$.depatureTime": new Date(),
        },
      }
    );

    res.status(200).json({
      success: true,
      updatedShed,
    });
  } catch (err) {
    res.status(500).json({
      success: false,
      message: error.message,
    });
  }
};

const getAllSheds = async (req, res) => {
  try {
    const allSheds = await Shed.find();

    res.status(200).json({
      success: true,
      allSheds,
    });
  } catch (err) {
    res.status(500).json({
      success: false,
      message: error.message,
    });
  }
};

const getSingleShed = async (req, res) => {
  try {
    const singShed = await Shed.findById(req.params.shedId);

    res.status(200).json({
      success: true,
      singShed,
    });
  } catch (err) {
    res.status(500).json({
      success: false,
      message: error.message,
    });
  }
};

const addFuelToShedStart = async (req, res) => {
  try {
    const { fuelType, updatedFuelLevel } = req.body;
    await Shed.updateOne(
      { _id: req.params.shedId, "fuleTypes.fuelType": fuelType },
      {
        $set: {
          "fuleTypes.$.fuelType": fuelType,
          "fuleTypes.$.availability": true,
          "fuleTypes.$.updatedFuelLevel": updatedFuelLevel,
        },
      },
      { new: true }
    );
    res.status(200).json({
      success: true,
      message: "Fuel data updated succesfully",
    });
  } catch (err) {
    res.status(500).json({
      success: false,
      message: error.message,
    });
  }
};

const addFuelToShedEnd = async (req, res) => {
  try {
    const updatedShed = await Shed.updateOne(
      { _id: req.params.shedId, "fuleTypes.fuelType": req.body.fuelType },
      {
        $set: {
          "fuleTypes.$.availability": false,
          "fuleTypes.$.updatedFuelLevel": 0,
        },
      }
    );
    res.status(200).json({
      success: true,
      message: "Fuel data updated succesfully",
    });
  } catch (err) {
    res.status(500).json({
      success: false,
      message: error.message,
    });
  }
};

module.exports = {
  addShed,
  addUserstoShedQue,
  userExitFromQue,
  getAllSheds,
  getSingleShed,
  addFuelToShedStart,
  addFuelToShedEnd,
};
