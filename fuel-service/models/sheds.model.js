const mongoose = require("mongoose");

const ShedsSchema = mongoose.Schema(
  {
    name: {
      type: String,
      required: true,
    },

    fuleTypes: [
      {
        fuelType: String,
        availability: Boolean,
        updatedFuelLevel: Number,
      },
    ],

    queue: [
      {
        id: { type: mongoose.Schema.Types.ObjectId, ref: "users" },
        fuelType: String,
        arrivalTime: Date,
        depatureTime: {
          type: Date,
          default: null,
        },
      },
    ],
  },
  { timestamps: true }
);
module.exports = mongoose.model("sheds", ShedsSchema);
