package com.carfax

import java.util.Date;
import java.util.List;

class VehicleInfo {

	String vin, make, model, year, lastOdoSource
	String lastOdoDate
	Integer lastOdoMileage = 0, numberOfServiceRecords = 0, numberOfRecallRecords = 0, alertCount = 0
	List<DisplayRecord> displayRecords = new ArrayList<DisplayRecord>()
	Long id
	String aTest = null
	
}
