package net.azib.ipscan.fetchers;

import net.azib.ipscan.core.ScanningSubject;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MACVendorFetcher extends AbstractFetcher {
	public static final String ID = "fetcher.mac.vendor";
	private static Map<String, String> vendors = new HashMap<>();
	private MACFetcher macFetcher;

	@Inject public MACVendorFetcher(MACFetcher macFetcher) {
		this.macFetcher = macFetcher;
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public void init() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/mac-vendors.txt")))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty()) continue;
				String prefix = line.substring(0, 2) + ':' + line.substring(2, 4) + ':' + line.substring(4, 6);
				vendors.put(prefix, line.substring(6));
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object scan(ScanningSubject subject) {
		String mac = (String)subject.getParameter(MACFetcher.ID);
		if (mac == null) {
			mac = macFetcher.scan(subject);
		}
		return mac != null ? findMACVendor(mac) : null;
	}

	String findMACVendor(String mac) {
		return vendors.get(mac.substring(0, 8));
	}
}
