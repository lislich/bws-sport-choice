/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.data;

/**
 * Weist einem Schlüssel einen Wert zu.
 *
 * @author joshua
 * @param <K> Typ des Schlüssels (Key)
 * @param <V> Typ des Wertes (Value)
 */
public class Eintrag <K,V>{
    
    // Attribut für den Schlüssel
    private K key;
    
    // Attribut für den Wert
    private V value;
    
    /**
     * Erstellt eine neue Instanz von Eintrag. Der übergebenen Schlüssel und Wert
     * werden den Attributen "key" und "value" zugewiesen;
     * 
     * @param p_key
     * @param p_value 
     */
    public Eintrag(K p_key, V p_value){
        this.key = p_key;
        this.value = p_value;
    }

    /**
     * @return der Schlüssel
     */
    public K getKey() {
        return key;
    }
    
    /**
     * @param key der Schlüssel
     */
    private void setKey(K p_key){
        this.key = p_key;
    }

    /**
     * @return der Wert
     */
    public V getValue() {
        return value;
    }

    /**
     * @param p_value der Wert
     */
    public void setValue(V p_value) {
        this.value = p_value;
    }
}
