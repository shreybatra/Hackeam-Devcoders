package com.varshney.hackeamdevcoders_partners;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yash on 11/2/18.
 */

public class RequestPOJO implements Serializable {

    Double Latitude;
    Double Longitude;
    String Username;
    ArrayList<String> Medicines;
    ArrayList<Integer> Quantities;

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public ArrayList<String> getMedicines() {
        return Medicines;
    }

    public void setMedicines(ArrayList<String> medicines) {
        Medicines = medicines;
    }

    public ArrayList<Integer> getQuantities() {
        return Quantities;
    }

    public void setQuantities(ArrayList<Integer> quantities) {
        Quantities = quantities;
    }
}
