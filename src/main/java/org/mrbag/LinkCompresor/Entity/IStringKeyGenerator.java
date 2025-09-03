package org.mrbag.LinkCompresor.Entity;
/**
 * Interface simple uniqe generator key 
 */
public interface IStringKeyGenerator {

	/** 
	 * @return current random ken, after calculate next random key
	 */
	public String next();
	
	/**
	 * @return return current random key
	 */
	public String get();
	
	public boolean isValid();
	
}
