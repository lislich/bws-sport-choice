/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.data;

/**
 *
 * @author joshua
 */
public class Eintrag <K,V>{
    private K key;
    private V value;
    
    public Eintrag(K p_key, V p_value){
        this.key = p_key;
        this.value = p_value;
    }

    /**
     * @return the key
     */
    public K getKey() {
        return key;
    }
    
    /**
     * 
     * @param key 
     */
    private void setKey(K key){
        this.key = key;
    }

    /**
     * @return the value
     */
    public V getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(V value) {
        this.value = value;
    }
}
