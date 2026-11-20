package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Windmill {

    CRServo windmill;
    Servo kicker;

    boolean isDefault;

    public Windmill(HardwareMap HW) {
        windmill = HW.get(CRServo.class, "windmill");
        kicker = HW.get(Servo.class, "kicker");

        isDefault = true;
    }

    public void startWindmill() {
        windmill.setPower(1);
    }

    public void stopWindmill() {
        windmill.setPower(0);
    }

    public void toggleKicker() {
        isDefault = !isDefault;

        if (isDefault) {
            toDefault();
        }
        else {
            toPush();
        }
    }

    public void toPush() {
        kicker.setPosition(0.8);
        isDefault = false;
    }

    public void toDefault() {
        kicker.setPosition(1);
        isDefault = true;
    }
}
