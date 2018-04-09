package fme.ibd;

public class Verbund {

    private int id;
    private String name;
    private String address;
    private int plz;
    private String city;
    private String state;

    private int patients;
    private int devices;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(){
        this.address = address;
    }

    public int getPlz(){
        return plz;
    }

    public void setPlz(){
        this.plz = plz;
    }

    public String getCity(){
        return city;
    }

    public void setCity(){
        this.city = city;
    }

    public String getState(){
        return state;
    }

    public void setState(){
        this.state = state;
    }

}
