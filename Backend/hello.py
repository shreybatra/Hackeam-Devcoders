from flask import Flask,jsonify,request
import time,calendar
from geopy.distance import vincenty
import pymongo
from pymongo import MongoClient

client = MongoClient()

app = Flask(__name__)

db = client.hackeam

medical_reqs = db.medical_reqs
logins = db.logins
pharmacies = db.pharmacies


#---------DATABSES--------------


# medical_reqs = [
# {
# #'id':0,
# 'username':'admin',
# 'medicines':['none'],
# 'quantities':['none'],
# 'latitude':0,
# 'longitude':0,
# 'time_created':0
# }
# ]

# logins = [
# {
# #'id':0,
# 'username':'admin',
# 'email':'abc@gmail.com'
# #'password':'hackeam2018',
# }
# ]

# pharmacies = [
# {
# 	#'id':0,
# 	#'email':'abc@gmail.com',
# 	'username':'abc@gmail.com',
# 	#'password':'admin',
# 	'latitude':0,
# 	'longitude':0,
# 	'last_visited':0
# }]

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
	user = request.json['username']
	#email = request.json['email']

	person = logins.find(username==user)
	
	if person!=None:
			return jsonify({'msg':'not verified'}),200

	pharmacy = {
		#'id':pharmacies[-1]['id']+1,
		'username':user,
		'username':username
		#'password':request.json['password'],
	}

	pharmacies.insert_one(pharmacy)

	return jsonify({'msg':'verified'}),201


#--------------------SEND----REQUEST--------------------


@app.route('/sendreqs',methods=['POST'])
def sendreqs():
	#if not 'username' in request.json:
	#	abort(400)
	req = {
		#'id': medical_reqs[-1]['id'] + 1,
		'username': request.json.get('username',""),
		'medicines': request.json['medicines'].split('$'),
		'quantities': request.json['quantities'].split('$'),
		'latitude':request.json.get('latitude','0'),
		'longitude':request.json.get('longitude','0'),
		'time_created': calendar.timegm(time.gmtime())
	}
	#medical_reqs.append(req)
	medical_reqs.insert_one(req)
	return jsonify({'done':'task'}),201


#--------------------SHOW----REQUESTS--------------------


@app.route('/showreqs', methods=['GET'])
def showreqs():

	final = []

	for req in medical_reqs.find({}):
		req.pop('_id')
		final.append(req)

	return jsonify({'records':final}),200


#--------------------DELETE----ALL--------------------


@app.route('/deleteall',methods=['DELETE'])
def deleteall():
	db.medical_reqs.drop()
	db.pharmacies.drop()
	db.logins.drop()
	return jsonify({'deleted':'all'}),200


#-------------DELETE---USERS------------


@app.route('/deleteusers',methods=['DELETE'])
def deleteusers():
	db.logins.drop()
	return jsonify({'deleted':'users'}),200



#-------------------ADD------PHARMACY-------------


@app.route('/addpharmacy',methods=['POST'])
def addpharmacy():

	user = request.json['username']
	#email = request.json['email']

	pharma = pharmacies.find_one({'username':user})

	if pharma!=None:
		return jsonify({'msg':'not verified'}),200
			

	pharmacy = {
		#'id':pharmacies[-1]['id']+1,
		'username':user,
		#'password':request.json['password'],
		'latitude':request.json.get('latitude','0'),
		'longitude':request.json.get('longitude','0'),
		'last_visited':calendar.timegm(time.gmtime())
	}

	pharmacies.insert_one(pharmacy)

	return jsonify({'msg':'verified'}),201



#-----------------SHOW-----PHARMACY------------


@app.route('/showpharmacies', methods=['GET'])
def showpharmacies():

	final = []

	for pharma in pharmacies.find():
		pharma.pop('_id')
		final.append(pharma)
	return jsonify({'pharmacies':final}),200


#------------------RESUEST-----PHARMACY-----------


@app.route('/requestpharmacy', methods=['POST'])
def requestpharmacy():
	user = request.json['username']


	pharma = pharmacies.find_one({'username':user})

	newtime = calendar.timegm(time.gmtime())

	oldtime = pharma['last_visited']
	
	
	lat = pharma['latitude']
	lon = pharma['longitude']
	pharmacies.find_one_and_update({'username':user},{'$set':{'last_visited':newtime}})
	#flag = pharma['last_visited']

	

	

	if pharma==None:
		return jsonify({'msg':'not verified'}),200

	reqs = []
	for med_req in medical_reqs.find():
		if med_req['time_created']>=oldtime and dist(med_req['latitude'], med_req['longitude'], lat, lon)<maxDist:
			med_req.pop('_id')
			reqs.append(med_req)
	return jsonify({'requests':reqs}),200



#-------------------THE------END-------------------

if __name__=="__main__":
	app.run(port=5000,use_reloader=True)