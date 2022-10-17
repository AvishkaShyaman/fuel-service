const mongoose = require("mongoose");

const UserSchema = mongoose.Schema(
  {
    name: {
      type: String,
      required: true,
    },
    email: {
      type: String,
    },
    password: {
      type: String,
    },
    role: {
      type: String,
    },
    vehicleId: { type: mongoose.Schema.Types.ObjectId, ref: "users" },
    visitedSheds: [
      { type: mongoose.Schema.Types.ObjectId, required: false, ref: "sheds" },
    ],
  },
  { timestamps: true }
);
module.exports = mongoose.model("users", UserSchema);
