package org.homemotion.devices.spi.impl.ips.impl;


public enum FHTDeviceMode {
	Auto(0), Manual(1), Holiday(2), Party(3);

	int value;

	private FHTDeviceMode(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static FHTDeviceMode valueOf(int mode) {
		switch (mode) {
		case 3:
			return Party;
		case 2:
			return Holiday;
		case 1:
			return Manual;
		case 0:
		default:
			return Auto;
		}
	}

}
