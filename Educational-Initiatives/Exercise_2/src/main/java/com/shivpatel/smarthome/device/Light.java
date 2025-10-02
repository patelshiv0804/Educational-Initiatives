package com.shivpatel.smarthome.device ;

public class Light extends AbstractDevice {
    public Light(int id) {
        super(id);
    }

    @Override
    public String getType() {
        return "Light";
    }
}
