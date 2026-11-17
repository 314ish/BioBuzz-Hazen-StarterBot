package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    DcMotorEx geckoWheels;
    CRServo leftIntake;
    CRServo rightIntake;

    private boolean intakePowerStatus;
    private final double MAX_SPEED_GECKO_WHEELS = 0.65;

    public Intake(HardwareMap HW) {
        geckoWheels = HW.get(DcMotorEx.class, "geckoWheels");
        leftIntake = HW.get(CRServo.class, "leftIntake");
        rightIntake = HW.get(CRServo.class, "rightIntake");

        leftIntake.setDirection(DcMotorSimple.Direction.REVERSE);

        geckoWheels.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakePowerStatus = false;
    }

    // only used for specifically controlling the intake subsystem
    public void toggleIntake() {
        intakePowerStatus = !intakePowerStatus;

        if (intakePowerStatus) {
            startIntake();
            startIntakeWheels();
        }
        else {
            stopIntake();
            stopIntakeWheels();
        }
    }

    public void stopIntake() {
        geckoWheels.setPower(0);
    }

    public void startIntake() {
        geckoWheels.setPower(MAX_SPEED_GECKO_WHEELS);
    }

    public void stopIntakeWheels() {
        leftIntake.setPower(0);
        rightIntake.setPower(0);
    }

    public void startIntakeWheels() {
        leftIntake.setPower(1);
        rightIntake.setPower(1);
    }

    public void intakeReverse() {
        geckoWheels.setPower(-MAX_SPEED_GECKO_WHEELS);
    }
}