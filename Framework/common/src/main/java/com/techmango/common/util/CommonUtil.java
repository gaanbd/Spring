package com.techmango.common.util;

import java.util.UUID;

public class CommonUtil {
	
	private CommonUtil() {}
	
	public static UUID generateUUID( ) {
		return UUID.randomUUID();		
	}

}
