module.exports = (sequelize, Type) => {
    return sequelize.define('User', {
        id: {
          type: Type.INTEGER,
          primaryKey: true,
          autoIncrement: true
        },
        username:{
            type:Type.STRING,
            allowNull:false
        },
        firstname:{
            type:Type.STRING
        } ,
        lastname:{
            type:Type.STRING
        },
        password:{
            type:Type.STRING
        },
        createdAt: Type.DATE,
        updatedAt: Type.DATE,
    },
    {
      freezeTableName: true,
    })
}