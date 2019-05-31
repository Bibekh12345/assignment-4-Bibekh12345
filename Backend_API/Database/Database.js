const Sequelize = require('sequelize')
const UserModel = require('../Model/UserModel')
const ItemModel = require('../Model/ItemModel')
const sequelize = new Sequelize('shopping',
                    'root',
                    'aaditya@123', {
       host : 'localhost',
  dialect: 'mysql'
  });



 const usertable = UserModel(sequelize, Sequelize)
 const itemtable = ItemModel(sequelize, Sequelize)
 sequelize.sync({ force: false })
  .then(() => {
    console.log(`Database & tables created!`)
  })
  .catch(()=>{
      console.log(`Could not create table`)
  })



  module.exports ={
    usertable,
    itemtable
  }