package com.smart.fuelservicemobile.Models;

import androidx.work.Data;

public class DataWrapper {
    private float status;
    private String message;
    private float code;
    Data DataObject;


    // Getter Methods

    public float getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public float getCode() {
        return code;
    }

    public Data getData() {
        return DataObject;
    }

    // Setter Methods

    public void setStatus( float status ) {
        this.status = status;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public void setCode( float code ) {
        this.code = code;
    }

    public void setData( Data dataObject ) {
        this.DataObject = dataObject;
    }
}

