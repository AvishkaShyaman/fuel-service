const bcrypt = require('bcrypt');
const User = require('../models/user.model');
const Vehicle = require('../models/vehicle.model');
const { getToken } = require('../services/auth');

const login = async (req, res) => {
  try {
    const { email, password } = req.body;
    const user = await User.findOne({ email });

    if (!user) {
      return res.status(401).json({ status: false, message: 'Invalid Email' });
    }

    const match = await bcrypt.compare(password, user.password);

    if (!match) {
      return res
        .status(401)
        .json({ status: false, message: 'Invalid Password' });
    }

    const token = await getToken(user._id);

    res.status(200).json({
      success: true,
      data: {
        id: user.id,
        name: user.name,
        email,
        role: user.role,
        token,
      },
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: error.message,
    });
  }
};

const signUp = async (req, res) => {
  try {
    const {
      body: { email, password, name, role, regirsterNumber, type, fuleType },
    } = req;

    if (!email && !password && !name) {
      res.status(400).json({
        success: false,
        data: { message: 'Plese inclue email, password and name' },
      });
      return;
    }

    const isUserExist = await User.findOne({ email });

    if (isUserExist) {
      throw 'Email "' + email + '" is already registered';
    }

    const salt = await bcrypt.genSalt(10);
    const hashPassword = await bcrypt.hash(password, salt);

    const newUser = new User({
      name,
      email,
      password: hashPassword,
      role,
    });

    const user = await newUser.save();

    let vehicle = {};
    if (user && regirsterNumber && type && fuleType) {
      const newVehicle = new Vehicle({
        regirsterNumber,
        type,
        fuleType,
        ownerId: user.id,
      });

      vehicle = await newVehicle.save();
    }

    res.status(200).json({
      id: user.id,
      name: user.name,
      email,
      role: user.role,
      token,
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      data: error,
    });
  }
};

module.exports = {
  login,
  signUp,
};
