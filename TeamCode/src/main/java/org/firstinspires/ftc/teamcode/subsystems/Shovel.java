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
            fold();
        } else { // up
            toFloor();
        }
    }

    public void toFloor() {
        shovel.setPosition(0.4);
    }

    public void fold() {
        shovel.setPosition(1);
    }

    public double getPosition() {
        return shovel.getPosition();
    }
}
