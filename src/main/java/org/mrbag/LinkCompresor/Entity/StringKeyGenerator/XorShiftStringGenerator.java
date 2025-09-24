package org.mrbag.LinkCompresor.Entity.StringKeyGenerator;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.mrbag.LinkCompresor.Entity.IStringKeyGenerator;

public class XorShiftStringGenerator implements IStringKeyGenerator {

	AtomicLong position = new AtomicLong();
	
	public XorShiftStringGenerator(long position) {
		this.position =new AtomicLong(position);
	}
	
	Lock lock = new ReentrantLock();
	//XORShift32
	private int hash() {
		lock.lock();
		try {
			int buf = (int) position.get();
			
			buf ^= buf << 31;
			buf ^= buf >> 15;
			buf ^= buf << -16;
			
			return buf;
		} finally {
			lock.unlock();
		}
		
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
		lock.lock();
		try {
			String s = UtilsConver.IntToString(hash());
			position.incrementAndGet();
			return s; 
		} finally {
			lock.unlock();
		}
	}

	@Override
	public String get() {
		return UtilsConver.IntToString(hash());
	}


	@Override
	public boolean isValid() {
		return position.get() + 1 < 2000000;
	}

}
