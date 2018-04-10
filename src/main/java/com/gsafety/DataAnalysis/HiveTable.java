package com.gsafety.DataAnalysis;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.gsafety.SensorBean.BridgeSensorTable;

@Component
public class HiveTable  {
	private static Logger LOGGER = LogManager.getLogger(HiveTable.class);

	public  String GetHiveTableNameBYSensorType(String sensorType) {
		if (sensorType != null) {
			if (sensorType.equals(BridgeSensorTable.Jsd)) {
				return "ods.bridge_acce";
			} else if (sensorType.equals(BridgeSensorTable.displacement)) {
				return "ods.bridge_dispmt";
			} else if (sensorType.equals(BridgeSensorTable.strain)) {
				return "ods.bridge_strain";
			} else if (sensorType.equals(BridgeSensorTable.DynamicDeflection)) {
				return "ods.bridge_dyndef";
			} else {
				return "ods.bridge_lowsumary";
			}

		} else {
			LOGGER.error("sensorType  must be not null");
			return null;

		}

	}

}
