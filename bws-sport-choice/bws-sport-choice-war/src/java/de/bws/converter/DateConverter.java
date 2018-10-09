/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.converter;

import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Lisa
 */
@FacesConverter("DateConverter")
public class DateConverter extends DateTimeConverter{
    
    public DateConverter(){
        setPattern("dd.MM.yyyy");
    }
    
}
