const {usertable} = require('../Database/Database')


const registerUser = (req,res,next)=>{
    usertable.create({
        username:req.body.username,
        firstname:req.body.firstname,
        lastname:req.body.lastname,
        password:req.body.password
    })
    .then((result)=>{
        console.log('data inserted')})
        next()
    .catch((error)=>{
        console.log('error')
    })
}

const getAllUsers = (req,res,next) =>{
    usertable
    .findAll()
    .then((result)=>{
        res.send(result)
    })
    .catch(()=>{
        console.log('Counldnot get the data')
    })
}

const findOneUser = (req,res,next) =>{
    usertable
    .findOne({
        limit:1,
        where:{
            username : req.body.username
        }
    })
    .then((result)=>{
        if(req.body.username === result.password){
            console.log('looged In')
            res.status(200).send()
        }
        
    })
    .catch(()=>{
        console.log('Counldnot get the data')
    })

}



module.exports = {
    registerUser,
    getAllUsers,
    findOneUser
}