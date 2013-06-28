package br.com.controlcheque.client.interfaces;

/**
 * @author Robson
 * @version 1.0
 * @created 01-set-2011 21:12:10
 */
 interface InterfaceBase {
	
	public void close();

        public void initialize() throws Exception;
        
        public void clear();
        
        public void showFrame() throws Exception;
        
        public void setEnableBotoes(boolean enable);

}