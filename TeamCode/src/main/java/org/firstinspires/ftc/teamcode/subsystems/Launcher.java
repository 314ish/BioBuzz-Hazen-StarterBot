package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;


public class Launcher {

    DcMotorEx launcher;


    public final int TARGET_VELOCITY = 1250;
    public final int IDLE_VELOCITY = 900;
    private final int TOLERANCE = 20;

    private final double F = 13.6070;
    private final double P = 4.0963;

    public Launcher(HardwareMap HW) {
        launcher = HW.get(DcMotorEx.class, "launcher");
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(P, 0, 0, F));
    }

    public void idleLauncher() {
        launcher.setVelocity(IDLE_VELOCITY);
    }

    public void startLauncher() {
        launcher.setVelocity(TARGET_VELOCITY);
    }

    public void stopLauncher() {
        launcher.setVelocity(0);
    }

    public boolean isTargetVelocity() {
        return launcher.getVelocity() >= TARGET_VELOCITY - TOLERANCE;
    }

    public double getVelocity() {
        return launcher.getVelocity();
    }
}
