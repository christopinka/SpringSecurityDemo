package com.carfax

import grails.converters.JSON
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import java.text.DateFormat
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.web.json.JSONObject


class VehicleController {

	def springSecurityService
	
	
	static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]

	def create = {
		def vehicle = new Vehicle()
		vehicle.properties = params
		return [vehicle: vehicle]
	}
	
//	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def add = {
		try
		{
		flash.message = flash.message
		RESTClient userrc = new RESTClient(grailsApplication.config.com.carfax.garageApiUrl)
			def userResp = userrc.get(
				path : "account/" + springSecurityService.principal.id.toString(),
				requestContentType : 'application/json',
				headers: ['Accept': 'application/json','Authorization': springSecurityService.authentication.credentials]
			);
	
		Account respAccount = userResp.data as Account;
		
		def vehicle = new Vehicle()
		vehicle.properties = params
		[user: respAccount, vehicle: vehicle, pageName: "Add VIN Plate", atSignUp: ""]
		}
		catch (Exception e)
		{
			log.error e
			// show error page
		}
	}
	
//	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def addAtSignUp = {
		try
		{
			
		flash.message = flash.message
		RESTClient userrc = new RESTClient(grailsApplication.config.com.carfax.garageApiUrl)
			def userResp = userrc.get(
				path : "account/" + springSecurityService.principal.id.toString(),
				requestContentType : 'application/json',
				headers: ['Accept': 'application/json','Authorization': springSecurityService.authentication.credentials]
			);
	
		Account respAccount = userResp.data as Account;
		
		def vehicle = new Vehicle()
		vehicle.properties = params
		[user: respAccount, vehicle: vehicle, pageName: "Add VIN Plate", atSignUp: "AtSignUp"]
		}
		catch (Exception e)
		{
			log.error e
			// show error page
		}
	}
	
//	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def addByVin = {
		try
		{
		RESTClient userrc = new RESTClient(grailsApplication.config.com.carfax.garageApiUrl)
			def userResp = userrc.get(
				path : "account/" + springSecurityService.principal.id.toString(),
				requestContentType : 'application/json',
				headers: ['Accept': 'application/json','Authorization': springSecurityService.authentication.credentials]
			);
	
		Account respAccount = userResp.data as Account;
		
		def vehicle = new Vehicle()
		vehicle.properties = params
		return [user: respAccount, vehicle: vehicle, pageName: "Add vehicle VIN", atSignUp: ""]
		}
		catch (Exception e)
		{
			log.error e
			// show error page
		}
	}
	
//	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def addByVinAtSignUp = {
		try
		{
		RESTClient userrc = new RESTClient(grailsApplication.config.com.carfax.garageApiUrl)
			def userResp = userrc.get(
				path : "account/" + springSecurityService.principal.id.toString(),
				requestContentType : 'application/json',
				headers: ['Accept': 'application/json','Authorization': springSecurityService.authentication.credentials]
			);
	
		Account respAccount = userResp.data as Account;
		
		def vehicle = new Vehicle()
		vehicle.properties = params
		return [user: respAccount, vehicle: vehicle, pageName: "Add vehicle VIN", atSignUp: "AtSignUp"]
		}
		catch (Exception e)
		{
			log.error e
			// show error page
		}
	}

//	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def lookup = {
		withHttp(uri: grailsApplication.config.com.carfax.garageApiUrl) {
			handler.failure = { resp ->
				println resp				
				//need to respond with error page
			}			
			println springSecurityService.authentication.credentials
			def aresp = get(
				path: "vin",
				query: ['plate': params.plate, 'state': params.state],
				requestContentType: 'application/json',
				// need actual auth
				headers: ['Accept': 'application/json','Authorization': springSecurityService.authentication.credentials]				
			)
			render(text:aresp,contentType:'application/json',encoding:'UTF-8')
		}
	}
	
//	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def save = {
		def vehicle = new Vehicle(params)

		def converter = vehicle as JSON

		def jsonString = converter.toString()
		RESTClient rc = new RESTClient(grailsApplication.config.com.carfax.garageApiUrl)

		def user = springSecurityService.authentication.principal
		
		try {
			withHttp(uri: grailsApplication.config.com.carfax.garageApiUrl) {
				parser.'application/json' = parser.'text/plain'
				handler.failure = { resp, stream ->
					flash.message = "Vehicle not added"
					
					ErrorResponse errorResponse = new ErrorResponse()
					JSON json = new JSON(errorResponse)
					errorResponse = json.parse(stream)
					println errorResponse.errors
					for (JSONObject jsonObject : errorResponse.errors)
					{
						vehicle.errors.rejectValue(jsonObject.'field', "", jsonObject.'message')
					}
					render(view: "addByVin", model: [vehicle: vehicle, pageName: "Add vehicle error", atSignUp: ""])
					return
				}

				handler.success = { resp, stream ->
					
					try
					{
						JSON json = new JSON(Vehicle)
						vehicle = json.parse(stream)
						flash.message = "Vehicle Added"
						redirect(controller:"vehicle", action:"show", params:[id: vehicle.id])
					}
					catch (Exception e)
					{
						println "Exception Showing New Vehicle"
						render(view: "addByVin", model: [vehicle: vehicle, pageName: "Add vehicle error", atSignUp: ""])
					}
					
				}
				
				def aresp = post(
					path: "vehicle",
					body: """{"class":"com.carfax.Vehicle","vin":""" + vehicle.vin + ""","account": {"class":"com.carfax.Account", "id":""" + user.id + """}}""",
					requestContentType: 'application/json',
					headers: ['Accept': 'application/json','Authorization': springSecurityService.authentication.credentials]
					)
										
			}
		}
		catch (Exception e) {
			render(view: "addByVin", model: [vehicle: vehicle, pageName: "Add vehicle error", atSignUp: ""])
			return
		}
	}
	
//	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def saveAtSignUp = {
		def vehicle = new Vehicle(params)

		def converter = vehicle as JSON

		def jsonString = converter.toString()
		RESTClient rc = new RESTClient(grailsApplication.config.com.carfax.garageApiUrl)

		def user = springSecurityService.authentication.principal
		
		try {
			withHttp(uri: grailsApplication.config.com.carfax.garageApiUrl) {
				parser.'application/json' = parser.'text/plain'
				handler.failure = { resp, stream ->
					flash.message = "Vehicle not added"
					
					ErrorResponse errorResponse = new ErrorResponse()
					JSON json = new JSON(errorResponse)
					errorResponse = json.parse(stream)
					println errorResponse.errors
					for (JSONObject jsonObject : errorResponse.errors)
					{
						vehicle.errors.rejectValue(jsonObject.'field', "", jsonObject.'message')
					}
					render(view: "addByVinAtSignUp", model: [vehicle: vehicle, pageName: "Create Account Add vehicle error", atSignUp: "AtSignUp"])
					return
				}

				handler.success = { resp, stream ->
					
					try
					{
						JSON json = new JSON(Vehicle)
						vehicle = json.parse(stream)
						flash.message = "Vehicle Added"
						redirect(controller:"index", action:"signupComplete")
					}
					catch (Exception e)
					{
						render(view: "addByVinAtSignUp", model: [vehicle: vehicle, pageName: "Create Account Add vehicle error", atSignUp: "AtSignUp"])
					}
					
				}
				
				def aresp = post(
					path: "vehicle",
					body: """{"class":"com.carfax.Vehicle","vin":""" + vehicle.vin + ""","account": {"class":"com.carfax.Account", "id":""" + user.id + """}}""",
					requestContentType: 'application/json',
					headers: ['Accept': 'application/json','Authorization': springSecurityService.authentication.credentials]
					)
										
			}
		}
		catch (Exception e) {
			render(view: "addByVinAtSignUp", model: [vehicle: vehicle, pageName: "Create Account Add vehicle error", atSignUp: "AtSignUp"])
			return
		}
	}

//	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def show = {
		RESTClient userrc = new RESTClient(grailsApplication.config.com.carfax.garageApiUrl)
		def userResp = userrc.get(
			path : "account/" + springSecurityService.principal.id.toString(),
			requestContentType : 'application/json',
			headers: ['Accept': 'application/json','Authorization': springSecurityService.authentication.credentials]
		);

		Account respAccount = userResp.data as Account;
		
		HTTPBuilder vehiclerc = new HTTPBuilder(grailsApplication.config.com.carfax.garageApiUrl + params.id)
		vehiclerc.parser.'application/json' = vehiclerc.parser.'text/plain'
		def vehicleResp = vehiclerc.get(
			    path: "vehicle/" + params.id,
				requestContentType : 'application/json',
				headers: ['Accept': 'application/json','Authorization': springSecurityService.authentication.credentials]
				)

		    VehicleInfo vehicle = new VehicleInfo()
			JSON json = new JSON(vehicle)
			vehicle = json.parse(vehicleResp.str)
		if (!vehicle) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), params.id])}"
			redirect(controller: "index", action: "index")
		}
		else {
			vehicle.id = new Long(params.id)
			String odometerUpdatedMessage = "LAST REPORTED ODOMETER"
			try
			{
				SimpleDateFormat fromsdf = new SimpleDateFormat("yyyy-MM-dd")
				Date aDate = fromsdf.parse(vehicle.lastOdoDate)
				SimpleDateFormat tosdf = new SimpleDateFormat("MM/dd/yyyy")
 				odometerUpdatedMessage = odometerUpdatedMessage + " ON " + tosdf.format(aDate)
			}
			catch (Exception e)
			{
			}
			[user: respAccount, vehicle: vehicle, odometerUpdatedMessage: odometerUpdatedMessage, pageName: "Service History Report"]
		}
	}

//	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def update = {
		
		RESTClient rc = new RESTClient(grailsApplication.config.com.carfax.garageApiUrl)
		
		def user = springSecurityService.authentication.principal
		try {
			
			Integer odo
			String odoString
			
			try {
				odoString = params.odo
				odoString = odoString.replaceAll("[^0-9]", "")
				odo = Integer.parseInt(odoString)
			}
			catch (Exception e) {
				flash.message = "Odometer not updated."
				chain(controller:"vehicle", action:"show", model: [odo: params.odo], id:params.id)
				return
			}
			
			if (odo < 0)
			{
				flash.message = "Odometer update must be greater than 0."
				chain(controller:"vehicle", action:"show", model: [odo: params.odo], id:params.id)
				return
			}
			
			if (odo >= 500000)
			{
				flash.message = "Odometer update must be less than 500,000."
				chain(controller:"vehicle", action:"show", model: [odo: params.odo], id:params.id)
				return
			}
			
			def aresp = rc.put(
					path: "vehicle/" + params.id,
					body: '{"class": "com.carfax.Vehicle", "lastOdoMileage":"' + odoString + '", account: {"class":"com.carfax.Account", "id":' + springSecurityService.authentication.principal.id + '}}',
					requestContentType: 'application/json',
					headers: ['Accept': 'application/json','Authorization': springSecurityService.authentication.credentials]
					)
		}
		catch (Exception e) {
			println e
			println "Exception in odo Update"
			// show error page
		}
		redirect(controller:"vehicle", action:"show", params:[id: params.id])

	}

//	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
//	def delete = {
//		def vehicleId = params.id
//		if (vehicleId) {
//			try {
//				RESTClient vehiclerc = new RESTClient(grailsApplication.config.com.carfax.garageApiUrl + params.id)
//				def vehicleResp = vehiclerc.delete(
//						path: "vehicle/" + params.id,
//						requestContentType : 'application/json',
//						headers: ['Accept': 'application/json','Authorization': springSecurityService.authentication.credentials]
//						);
//				flash.message = "Vehicle deleted from account"
//				redirect(controller:"vehicle", action: "add")
//			}
//			catch (org.springframework.dao.DataIntegrityViolationException e) {
//				flash.message = "Vehicle not deleted"
//				redirect(action: "show", id: params.id)
//			}
//		}
//		else {
//			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), params.id])}"
//			redirect(controller:"index", action: "index")
//		}
//	}
}
