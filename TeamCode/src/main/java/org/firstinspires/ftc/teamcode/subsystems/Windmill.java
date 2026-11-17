package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Windmill {

    CRServo windmill;

    public Windmill(HardwareMap HW) {
        windmill = HW.get(CRServo.class, "windmill");
    }

    public void startWindmill() {
        windmill.setPower(1);
    }

    public void stopWindmill() {
        windmill.setPower(0);
    }
}
