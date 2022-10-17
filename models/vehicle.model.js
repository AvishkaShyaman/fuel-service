const mongoose = require('mongoose');

const VehiclesSchema = mongoose.Schema(
  {
    regirsterNumber: {
      type: String,
      required: true,
    },
    type: {
      type: String,
      required: true,
    },
    fuleType: {
      type: String,
      required: true,
    },
    ownerId: { type: mongoose.Schema.Types.ObjectId, ref: "users" },
  },
  { timestamps: true }
);
module.exports = mongoose.model('vehicles', VehiclesSchema);