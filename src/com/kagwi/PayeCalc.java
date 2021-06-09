package com.kagwi;

public class PayeCalc {

	public PayeCalc() {
		
	}
	
	public String calculateNetPay(String income) {
		Double netPay = 0.00;
		Double grossIncome = Double.parseDouble(income);
		
		if (grossIncome<=12298) {
			netPay = grossIncome-(0.10*grossIncome);
		}
		
		if (grossIncome>=12299 && grossIncome<=23885) {
			netPay = grossIncome-(0.15*grossIncome);
		}
		
		if (grossIncome>=23886 && grossIncome<=35472) {
			netPay = grossIncome-(0.20*grossIncome);
		}
		
		if (grossIncome>=35473 && grossIncome<=47059) {
			netPay = grossIncome-(0.25*grossIncome);
		}
		
		if (grossIncome>47059) {
			netPay = grossIncome-(0.30*grossIncome);
		}
		return Double.toString(netPay);
	}
}
