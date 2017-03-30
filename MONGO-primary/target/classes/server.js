var express = require("express");
var path = require("path")
var app = express();
var port = process.env.PORT||4000

app.use('/static/images', express.static(path.join( __dirname, '/static/images')));
app.use('/static/css', express.static(path.join( __dirname, '/static/css')));
app.use('/static/js', express.static(path.join( __dirname, '/static/js')));
app.get('*', function(req, res) {
  res.sendFile(path.join(__dirname, 'templates/welcome.html'));
});
app.listen(port,function(err){
	console.log(" App started",port)
});