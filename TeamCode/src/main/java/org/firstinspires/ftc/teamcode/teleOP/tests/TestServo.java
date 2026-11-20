package org.firstinspires.ftc.teamcode.teleOP.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp // consider disabling later on
public class TestServo extends LinearOpMode {

    Servo shovel;
    Servo kicker;

    final double increment = 0.05;
    double intendedPos = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        shovel = hardwareMap.get(Servo.class, "shovel");
        kicker = hardwareMap.get(Servo.class, "kicker");

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.yWasPressed()) {
                intendedPos += increment;
            }
            if (gamepad1.xWasPressed()) {
                intendedPos -= increment;
            }

            if (gamepad1.aWasPressed()) { // on the floor to collect from flower
                shovel.setPosition(intendedPos);
            }
            else if (gamepad1.bWasPressed()) { // folded up
                shovel.setPosition(1);
            }

            if (gamepad1.dpadUpWasPressed()) {
                kicker.setPosition(intendedPos);
            }
            else if (gamepad1.dpadUpWasPressed()) {
                kicker.setPosition(1);
            }
            telemetry.addData("Increment: ", increment);
            telemetry.addData("Intended Pos: ", intendedPos);
            telemetry.addData("current Pos: ", shovel.getPosition());
            telemetry.addLine("");
            telemetry.addLine("Increment intended pos (y)");
            telemetry.addLine("Decrement intended pos (x)");
            telemetry.addLine("Set position to intended pos");
            telemetry.addLine("Set position to 1");
            telemetry.update();
        }
    }
}
