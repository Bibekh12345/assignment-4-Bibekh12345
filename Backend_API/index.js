const express = require('express')
const app = express()
const bodyparser = require('body-parser')
const port = 3000
const multer = require('multer')
const path = require('path')
const {registerUser,getAllUsers,findOneUser} = require('./Controller/UserController')
const {insertItem,getAllItems} = require('./Controller/ItemController')
require('./Database/Database')

app.use((req, res, next) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader(
      "Access-Control-Allow-Headers",
      "Origin, X-Requested-With, Content-Type, Accept"
    );
    res.setHeader(
      "Access-Control-Allow-Methods",
      "GET, POST, PATCH, DELETE, OPTIONS"
    );
    next();
  });


//uses body parser for parsing json data
app.use(bodyparser.json())
app.use(bodyparser.urlencoded({extended:true}))


var publicDir = require('path').join(__dirname,'/assets');
app.use(express.static(publicDir));


var assetStorage = multer.diskStorage({
  destination: './assets/Images/upload',
  filename: (req, file, callback) => {
      let ext = path.extname(file.originalname);
      callback(null, file.fieldname + '-' + Date.now() + ext);
  }
});

var imageFileFilter = (req, file, cb) => {
  if (!file.originalname.match(/\.(jpg|jpeg|png|gif)$/)) {
      return cb(new Error('You can upload only image files!'), false);
  }
  cb(null, true);
}


var upload = multer({
    storage: assetStorage,
    fileFilter: imageFileFilter,
    limits: { fileSize: 10000000 }
})

var imageUpload = upload.single('itemImage')


app.post('/image',imageUpload,(req,res)=>{
  console.log('image uploaded')
  res.statusCode = 200;
  res.setHeader('Content-Type', 'application/json');
  res.json(req.file);
})


app.post('/signup',registerUser,(req,res,next)=>{
    res.sendStatus(200).json({'status':true})
})

app.post('/login',findOneUser)

app.get('/users',getAllUsers,(req,res)=>{
 res.status(200)
})


app.get('/items',getAllItems,(req,res)=>{
  res.status(200)
})

app.post('/items',insertItem,(req,res)=>{
  res.sendStatus(200).json({'status':true})
})


app.listen(port,()=>{
    console.log('success')
})



