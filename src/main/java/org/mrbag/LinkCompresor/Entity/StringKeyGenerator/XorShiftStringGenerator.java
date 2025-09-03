package org.mrbag.LinkCompresor.Entity.StringKeyGenerator;

import org.mrbag.LinkCompresor.Entity.IStringKeyGenerator;

public class XorShiftStringGenerator implements IStringKeyGenerator {

	int position = 0;
	
	public XorShiftStringGenerator(int position) {
		this.position = position;
	}
	//XORShift32
	private int hash() {
		int buf = position;
		
		buf ^= buf << 31;
		buf ^= buf >> 15;
		buf ^= buf << -16;
		
		return buf;
	}
	
	
	static int hash(int p, int a, int b) {
		int buf = p;
		
		buf ^= buf << a;
		buf ^= buf >> b;
		buf ^= buf << b - a;
		
		return buf;
	}
	
	@Override
	public String next() {
		String s = UtilsConver.IntToString(hash());
		position++;
		return s; 
	}

	@Override
	public String get() {
		return UtilsConver.IntToString(hash());
	}


	@Override
	public boolean isValid() {
		return position+1 < 2000000;
	}

}
