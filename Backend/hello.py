from flask import Flask,jsonify,request
import time,calendar
from geopy.distance import vincenty

app = Flask(__name__)


#---------DATABSES--------------

medical_reqs = [
{
'id':0,
'username':'admin',
'medicines':['none'],
'quantities':['none'],
'latitude':0,
'longitude':0,
'time_created':0
}
]

logins = [
{
'id':0,
'username':'admin',
'email':'abc@gmail.com'
#'password':'hackeam2018',
}
]

pharmacies = [
{
	'id':0,
	'email':'abc@gmail.com',
	'username':'admin',
	#'password':'admin',
	'latitude':0,
	'longitude':0,
	'last_visited':0
}]

maxDist = 2

#-------------------FUNCTIONSSSS----------------

def dist(x1,y1,x2,y2):
	start = (x1,y1)
	end = (x2,y2)
	return vincenty(start,end).miles




#-----------ENDPOINTS--------X-----X-----X-----X-----X------X------X-----X-----

@app.route('/', methods=['GET'])
def hello():
	return 'Namaste Hackeam 2018. Hakuna Matata.!!!!! Lalalalalalala'
	

#---------------------ADD-----CLIENT------------------


@app.route('/addclient',methods=['POST'])
def addclient():
	username = request.json['username']
	email = request.json['email']

	for person in logins:
		if person['username'] == username or person['email'] == email:
			return jsonify({'msg':'not verified'}),200

	pharmacy = {
		'id':pharmacies[-1]['id']+1,
		'email':email,
		'username':username
		#'password':request.json['password'],
	}

	pharmacies.append(pharmacy)

	return jsonify({'msg':'verified'}),201


#--------------------SEND----REQUEST--------------------


@app.route('/sendreqs',methods=['POST'])
def sendreqs():
	#if not 'username' in request.json:
	#	abort(400)
	req = {
		'id': medical_reqs[-1]['id'] + 1,
		'username': request.json.get('username',""),
		'medicines': request.json['medicines'].split('$'),
		'quantities': request.json['quantities'].split('$'),
		'latitude':request.json.get('latitude','0'),
		'longitude':request.json.get('longitude','0'),
		'time_created': calendar.timegm(time.gmtime())
	}
	medical_reqs.append(req)
	return jsonify({'done':'task'}),201


#--------------------SHOW----REQUESTS--------------------


@app.route('/showreqs', methods=['GET'])
def showreqs():
	return jsonify({'records':medical_reqs}),200


#--------------------DELETE----ALL--------------------


@app.route('/deleteall',methods=['DELETE'])
def deleteall():
	medical_reqs.clear()
	return jsonify({'deleted':'all'}),200



#-------------------UPDATE----PASS--------------------


@app.route('/updatepassword',methods=['POST'])
def updatepassword():

	user = request.json['username']
	oldpass = request.json['oldpassword']
	newpass = request.json['newpassword']

	flag = False

	for person in logins:
		if person['username'] == user and person['password'] == oldpass:
			flag = True
			person['password'] = newpass
			break

	if flag==True:
		return jsonify({'password':'changed'}),200
	else:
		return jsonify({'wrong':'credentials'}),200


#-------------------ADD------PHARMACY-------------


@app.route('/addpharmacy',methods=['POST'])
def addpharmacy():

	username = request.json['username']
	#email = request.json['email']

	for pharma in pharmacies:
		if pharma['username'] == username:
			return jsonify({'msg':'not verified'}),200

	pharmacy = {
		'id':pharmacies[-1]['id']+1,
		'username':username,
		#'password':request.json['password'],
		'latitude':request.json.get('latitude','0'),
		'longitude':request.json.get('longitude','0'),
		'last_visited':calendar.timegm(time.gmtime())
	}

	pharmacies.append(pharmacy)

	return jsonify({'msg':'verified'}),201


#-------------------LOGIN-----PHARMACY---------


@app.route('/pharmacylogin', methods=['POST'])
def pharmacylogin():

	username = request.json['username']
	#password = request.json['password']

	for pharmacy in pharmacies:
		if pharmacy['username']==username:
			return jsonify({'msg','verified'}),200

	return jsonify({'msg','not verified'}),200



#-----------------SHOW-----PHARMACY------------


@app.route('/showpharmacies', methods=['GET'])
def showpharmacies():
	return jsonify({'pharmacies':pharmacies}),200


#------------------RESUEST-----PHARMACY-----------


@app.route('/requestpharmacy', methods=['POST'])
def requestpharmacy():
	username = request.json['username']

	oldtime = 0;
	lat = 0;
	lon = 0;

	newtime = calendar.timegm(time.gmtime())

	for pharmacy in pharmacies:
		if username == pharmacy['username']:
			oldtime = pharmacy['last_visited']
			pharmacy['last_visited'] = newtime
			lat = pharmacy['latitude']
			lon = pharmacy['longitude']
			break

	reqs = []
	for med_req in medical_reqs:
		if med_req['time_created']>=oldtime and dist(med_req['latitude'], med_req['longitude'], lat, lon)<maxDist:
			reqs.append(med_req)

	return jsonify({'requests':reqs}),200



#-------------------THE------END-------------------

if __name__=="__main__":
	app.run()