app.get('/api/notifications', (req, res) => {
  Notifications.find({}, function(err, notes) {
    if (err) {
      return res.status(409).json({
        message: err
      });
    }
    res.status(200).send(notes);
  });
});

app.post('/api/query', (req, res) => {
  console.log(req.body.artisan_id);
  var query = new Queries({
    artisan_id: req.body.artisan_id,
    query: req.body.query,
    status: 'false',
    timestamp: Date.now()
  });
  query.save().then(result => {
    console.log(result.query);
      var notification = new Notifications({
        title: 'New question has been asked',
        description: result.query,
        typ: 'for_Dashboard',
        status: 'false',
        timestamp: Date.now()
      });
      notification.save().then(result => {
          console.log(result);
          res.status(200).json({
            message: 'Success'
          });
        })
        .catch(e => {
          res.status(409).json({
            messsage: e
          });
        });
    })
    .catch(e => {
      res.status(409).json({
        message: e
      });
    });
});

//Routes Created by Ankit
app.post('/api/artisans', (req, res) => {
  console.log(req.body.username);
  const salt = bcrypt.genSaltSync(10);
  const hash = bcrypt.hashSync(req.body.password, salt);

  var artisan = new Artisan({
    username: req.body.username,
    password: hash
  });

  artisan.save().then(artisan => {
    console.log(artisan);
    res.status(201).json({
        message: 'User Created',
        success: true
      });
  }  ).catch(e => {
    {
      if (e.code === 11000) {
        return res.json({
          message: 'Mail Exists',
          success: false
        });
      }
      return res.json({
        message: 'network error',
        success: false
      });
    }
      });
});

app.post('/api/artisans/login', (req, res, next) => {
  console.log(req.body);
  Artisan.find({ username: req.body.username })
    .exec()
    .then(user => {
      if (user.length < 1) {
        return res.json({
          message: 'Auth Failed',
          success: false
        });
      }
      bcrypt.compare(req.body.password, user[0].password, (err, result) => {
        if (err) {
          return res.json({
            message: 'Auth Failed',
            success: false
          });
        }
        if (result) {
          const token = jwt.sign(
            {
              username: user[0].username,
              userId: user[0]._id
            },
            process.env.JWT_KEY,
            {
              expiresIn: '5h'
            }
          );
          return res.status(200).json({
            message: 'Auth Successful',
            success: true,
            token: token,
            username: user[0].username
          });
        }
        res.json({
          message: 'Auth Failed',
          success: false
        });
      });
    })
    .catch(err => {
      console.log(err);
      res.status(500).json({
        error: err
      });
    });
});

app.post('/api/artisans/fcmtoken', (req, res, next) => {
  console.log(req.body);
  Artisan.findOneAndUpdate(
    { username: req.body.username },
    { fcmtoken: req.body.fcmtoken },
    { new: true },
    function(err, docs) {
      if (err) return res.send(500, { error: err });
      return res.json({
        message: 'Token saved',
        success: true
      });
    }
  );
});

app.post('/api/artisans/notificationfcmtoken',(req,res)=>{
  answer=req.body.answer;
  Artisan.find({},'fcmtoken',function(err,doc){
    if (err){
      return res.send({message:Unsuccessful});
    }
    let fcmtoken=[];
    for(var i=0;i<doc.length;i++)
    {
      fcmtoken.push(doc[i].fcmtoken)
    }
    const url="https://fcm.googleapis.com/fcm/send";

//   'Authorization': 'key=AAAAD40Q9JY:APA91bEhbfyVKiOTdTuzirQeqGpkB165eiKbaEnbFONJzJOjkVki1372DLi4zb1gCsWP8uUDLPLNVpVjI3tPK1M6n-Jop8Ryd5x__ei1eM29q7hK7z_DUBU8w9e76iL0y_f2biax60sj',

axios.defaults.headers.common['Authorization'] = 'key=AAAAD40Q9JY:APA91bEhbfyVKiOTdTuzirQeqGpkB165eiKbaEnbFONJzJOjkVki1372DLi4zb1gCsWP8uUDLPLNVpVjI3tPK1M6n-Jop8Ryd5x__ei1eM29q7hK7z_DUBU8w9e76iL0y_f2biax60sj';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
    axios.post(url,{
    "registration_ids": fcmtoken,
    data : {
    body :  answer,
    title : "Queries",
    click_action: ".Query.View.QueryActivity"
    }
    }).then((response)=>{
      console.log(response);
      res.send({message:'success'})
    }).catch((e)=>{
      console.log(e);
    });

    console.log(fcmtoken);
  });
});

app.post('/api/artisans/getfcmtoken',(req,res)=>{
  var state=req.body.state;
  if (state === 'all'){
    Artisan.find({},'fcmtoken',function(err,docs){
      if(err){
        return res.json({
          message:'Cannot fetch fcm token',
          success:false
        });
      }
      res.json({docs:docs,message:'Successfully Fetched',success:true});
    });
  }

  else{
    Artisan.find({state:state},'fcmtoken',function(err,docs){
      if(err){
        return res.json({
          message:'Cannot fetch fcm token',
          success:false
        });
      }
      res.json({docs:docs,message:'Successfully Fetched',success:true});
    });
  }
});

app.post('/api/artisans/forgot', (req, res) => {
  async.waterfall(
    [
      function(done) {
        Artisan.findOne({ username: req.body.username }, function(
          err,
          artisan
        ) {
          if (!artisan) {
            return res.json({
              message: 'No account with that email address exists.'
            });
          }
          var username = req.body.username;
          var password = generator.generate({
            length: 6,
            numbers: true
          });
          res.json({ password: password });
          done(err, password, username);
        });
      },
      function(password, username, done) {
        var transporter = nodemailer.createTransport({
          service: 'gmail',
          auth: {
            user: 'harshit.jain17071996@gmail.com',
            pass: '17July1996'
          }
        });

        var mailOptions = {
          from: 'harshit.jain17071996@gmail.com',
          to: username,
          subject: 'Sending Email using Node.js',
          text: `New Verificstion Code : ${password}`
        };

        transporter.sendMail(mailOptions, function(error, info) {
          if (error) {
            console.log(error);
          } else {
            console.log('Email sent:');
          }
        });
      }
    ],
    function(err) {
      if (err) return next(err);
    }
  );
});

app.post('/api/artisans/editprofile', (req, res) => {
  console.log(req.body);
  Artisan.findOneAndUpdate(
    { username: req.body.username },
    {
      tribe: req.body.tribe,
      description: req.body.description,
      gender: req.body.gender,
      name: req.body.name,
      address: req.body.address,
      phone: req.body.phone,
      aadharNO: req.body.aadharNO,
      state: req.body.state,
       $push: { skills:req.body.skills } ,
      qualification:req.body.qualification
    },
    { new: true },
    function(err, docs) {
      if (err) {
        return res.status(400).json({ message: 'Cannot load the profile' });
      }
      console.log(docs);
      res.send({ docs, success: true, message: 'Profile saved Successfully' });
    }
  );
});

app.post('/api/artisans/fetchprofile', (req, res, next) => {
  console.log(req.body.username);
  Artisan.findOne(
    { username: req.body.username },
    'name description address state tribe aadharNO phone gender profilephoto ,qualification,skills',
    function(err, docs) {
      if (err) {
        return res.status(400).json({ message: 'Unable to load the profile' });
      }
      console.log(docs);
      res.json({ docs: docs, success: true });
    }
  );
});

app.post(
  '/api/artisans/uploadprofilephoto',
  upload.single('profilephoto'),
  (req, res, next) => {
    const qReplace = str => {
      if (str.charAt(0) === '"' && str.charAt(str.length - 1) === '"') {
        return str.substr(1, str.length - 2);
      }
    };
    var url = req.file.path;
    console.log(url);
    Artisan.findOneAndUpdate(
      { username: qReplace(req.body.username) },
      { profilephoto: url },
      { new: true },
      function(err, docs) {
        if (err) return res.send(500, { error: err });
        return res.json({
          message: 'Profile Photo Uploaded Succesfully',
          success: true
        });
      }
    );
  }
);

// app.post('/api/artisans/fetchindividualprofile',(req,res,next)=>{
//   console.log(req.body.username);
//   Artisan.findOne({username:req.body.username},'name description address state tribe aadharNO phone gender profilephoto',
//   function(err,docs){
//     if (err){
//     return res.status(400).json({message:'Unable to load the profile'});
//   }
//   res.json({docs:docs ,success:true});
//   });
// });
app.post('/api/artisans/fetchimages', (req, res, next) => {
  console.log(req.body.username);
  Artisan.findOne({ username: req.body.username }, 'images', function(
    err,
    docs
  ) {
    if (err) {
      return res.status(400).json({ message: 'Cannot load images' });
    }
    console.log(docs);
    res.send(docs);
  });
});

app.post('/api/artisans/fetchvideos', (req, res, next) => {
  Artisan.findOne({ username: req.body.username }, 'videos', function(
    err,
    docs
  ) {
    if (err) {
      return res.status(400).json({ message: 'Cannot load videos' });
    }
    console.log(docs);
    res.send(docs);
  });
});

app.post('/api/artisans/fetchpdfs', (req, res, next) => {
  Artisan.findOne({ username: req.body.username }, 'pdfs', function(err, docs) {
    if (err) {
      return res.status(400).json({ message: 'Cannot load pdfs' });
    }
    console.log(docs);
    res.send(docs);
  });
});

app.post(
  '/api/artisans/uploadimages',
  upload.single('images'),
  (req, res, next) => {
    const qReplace = str => {
      if (str.charAt(0) === '"' && str.charAt(str.length - 1) === '"') {
        return str.substr(1, str.length - 2);
      }
    };
    var url = req.file.path;
    var title = qReplace(req.body.title);
    Artisan.findOneAndUpdate(
      { username: qReplace(req.body.username) },
      { $push: { images: { url, title } } },
      function(err, docs) {
        if (err) return res.send(500, { error: err });
        return res.json({
          message: 'Image Uploaded Succesfully',
          success: true
        });
      }
    );
  }
);

app.post(
  '/api/artisans/uploadvideos',
  upload.single('videos'),
  (req, res, next) => {
    // console.log(req.file);
    const qReplace = str => {
      if (str.charAt(0) === '"' && str.charAt(str.length - 1) === '"') {
        return str.substr(1, str.length - 2);
      }
    };
    console.log(req.body.username);
    console.log(qReplace(req.body.username));
    var url = req.file.path;
    var title = qReplace(req.body.title);
    console.log(url);
    console.log(qReplace(title));
    thumbler.extract(
      `./${url}`,
      `./${url}.png`,
      '00:00:02',
      '800x600',
      function() {
        console.log(
          'snapshot saved to snapshot.png (200x125) with a frame at 00:00:02'
        );
      }
    );
    Artisan.findOneAndUpdate(
      { username: qReplace(req.body.username) },
      { $push: { videos: { url, title, thumbnail: `${url}.png` } } },
      function(err, docs) {
        if (err) return res.send(500, { error: err });
        return res.json({
          message: 'Video Uploaded Successfully',
          success: true
        });
      }
    );
  }
);

app.post(
  '/api/artisans/uploadpdfs',
  upload.single('pdfs'),
  (req, res, next) => {
    const qReplace = str => {
      if (str.charAt(0) === '"' && str.charAt(str.length - 1) === '"') {
        return str.substr(1, str.length - 2);
      }
    };
    console.log(req.file);
    var url = req.file.path;
    var title = qReplace(req.body.title);
    Artisan.findOneAndUpdate(
      { username: qReplace(req.body.username) },
      { $push: { pdfs: { url, title } } },
      function(err, docs) {
        if (err) return res.send(500, { error: err });
        return res.json({
          message: 'Pdf File Uploaded Successfully',
          success: true
        });
      }
    );
  }
);

app.post('/api/artisans/deletevideo', (req, res, next) => {
  var username = req.body.username;
  console.log(req.body._id);
  console.log(username);
  Artisan.findOne({ username: username })
    .select()
    .exec(function(err, org) {
      if (err) return res.send({ success: false });
      if (!org) return res.send(err);
      org.videos.id(req.body._id).remove();
      org.save(function(err, org) {
        if (err) return res.send(err);
        return res.send({ org, success: true });
      });
    });
});

app.post('/api/artisans/deleteimage', (req, res, next) => {
  var username = req.body.username;
  console.log(req.body._id);
  console.log(username);
  Artisan.findOne({ username: username })
    .select()
    .exec(function(err, org) {
      if (err) return res.send({ success: false });
      if (!org) return res.send(err);
      org.images.id(req.body._id).remove();
      org.save(function(err, org) {
        if (err) return res.send(err);
        return res.send({ org, success: true });
      });
    });
});

app.post('/api/artisans/deletepdf', (req, res, next) => {
  console.log(req.body.url);
  Artisan.findOneAndUpdate(
    { username: req.body.username },
    { $pull: { pdfs: { url: req.body.url, title: req.body.title } } },
    { upsert: true },
    function(err, docs) {
      if (err) {
        return res.send(500, { error: err });
      }
      console.log(typeof docs);
      res.send(docs);
    }
  );
});

app.get('/api/artisans/files', (req, res, next) => {
  Artisan.find({}, 'images videos username profilephoto name', function(
    err,
    docs
  ) {
    if (err) return res.send(500, { error: err });
    console.log(docs);
    res.send({ docs: docs });
  });
});
