const User = require("../models/user.model");

const getUserVisitesheds = async (req, res) => {
  try {
    if (req.params.userId !== req.user.id && req.user.role !== "Admin") {
      res.status(403).json("You are not allowed!");
      return;
    }
    const shedLook = await User.findById(req.params.userId).populate(
      "visitedSheds"
    ); // key to populate

    console.log("shedLook", shedLook);
    res.status(200).json(shedLook);
  } catch (err) {
    res.status(500).json(err);
  }
};

module.exports = {
  getUserVisitesheds,
};
