const {itemtable} = require('../Database/Database')



const insertItem = (req,res,next)=>{
    itemtable.create({
        itemName:req.body.itemName,
        itemPrice:req.body.itemPrice,
        itemImageName:req.body.itemImageName,
        itemDescription:req.body.itemDescription
    })
    .then((result)=>{
        console.log('item data inserted')
        res.status(200).send()
    })
    .catch((error)=>{

    })
}

const getAllItems = (req,res,next)=>{
    itemtable.findAll()
    .then((result)=>{
        res.send(result)
    })
    .catch((error)=>{
        console.log(error)
    })
}




module.exports = {insertItem,getAllItems}