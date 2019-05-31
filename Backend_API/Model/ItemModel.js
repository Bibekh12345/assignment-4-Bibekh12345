module.exports = (sequelize,Type)=>{
    return sequelize.define('Item', {
        id: {
          type: Type.INTEGER,
          primaryKey: true,
          autoIncrement: true
        },
        itemName:{
            type:Type.STRING,
            allowNull:false
        },
        itemPrice : {
            type:Type.STRING
        },
        itemImageName : {
            type:Type.STRING
        },
        itemDescription : {
            type:Type.STRING
        },
        createdAt: Type.DATE,
        updatedAt: Type.DATE,
    },
    {
      freezeTableName: true,
    })
}
