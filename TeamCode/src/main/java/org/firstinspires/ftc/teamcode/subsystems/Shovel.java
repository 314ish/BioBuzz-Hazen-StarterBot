package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Shovel {

    Servo shovel;

    boolean isDown;

    public Shovel(HardwareMap HW) {
        shovel = HW.get(Servo.class, "shovel");

        isDown = false;
    }

    public void toggle() {
        isDown = !isDown;

        if (isDown) { // down
            toFloor();
        } else { // up
            fold();
        }
    }

    public void toFloor() {
        shovel.setPosition(0.35);
        isDown = true;
    }

    public void fold() {
        shovel.setPosition(0.9);
        isDown = false;
    }

    public double getPosition() {
        return shovel.getPosition();
    }

    public boolean getStatus() {
        return isDown;
    }
}
