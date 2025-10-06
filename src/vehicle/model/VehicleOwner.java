package vehicle.model;

import java.util.Objects;

public class VehicleOwner {
    private int id;
    private String vehicleOwnerName;
    private int numOfVehicles;
    
    public VehicleOwner(){
    }
    public VehicleOwner(int id, String vehicleOwnerName){
        this.id = id;
        this.vehicleOwnerName = vehicleOwnerName;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getVehicleOwnerName(){
        return vehicleOwnerName;
    }
    public void setVehicleOwnerName(String vehicleOwnerName){
        this.vehicleOwnerName = vehicleOwnerName;
    }
    public int getNumOfVehicles(){
        return numOfVehicles;
    }
    public void setNumOfVehicles(int vehicleCount){
        this.numOfVehicles = vehicleCount;
    }

    @Override
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return false;
        VehicleOwner vehicleOwner = (VehicleOwner) o;
        return id == vehicleOwner.id && Objects.equals(vehicleOwnerName, vehicleOwner.vehicleOwnerName) && Objects.equals(numOfVehicles, vehicleOwner.numOfVehicles);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, vehicleOwnerName, numOfVehicles);
    }
}
