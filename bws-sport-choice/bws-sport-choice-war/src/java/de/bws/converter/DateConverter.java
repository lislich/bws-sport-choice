package de.bws.converter;

import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

/**
 * Diese Klasse dient als Konverter der Eingabedaten zum gewünschten Datumsformat.
 * 
 * @author Lisa
 */
@FacesConverter("DateConverter")
public class DateConverter extends DateTimeConverter{
    
    public DateConverter(){
        setPattern("dd.MM.yyyy");
    }
    
}
