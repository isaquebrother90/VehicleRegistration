package com.vehicleregistration.vehicle_registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.vehicleregistration.vehicle_registration", "com.vehicleregistration.vehicle_registration.exception"})
public class VehicleRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleRegistrationApplication.class, args);
	}

}
