package org.firstinspires.ftc.teamcode.teleOP;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp // consider disabling later on
public class TestServo extends LinearOpMode {

    Servo shovel;

    final double increment = 0.05;
    double intendedPos = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        shovel = hardwareMap.get(Servo.class, "shovel");

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
            if (gamepad1.bWasPressed()) { // folded up
                shovel.setPosition(1);
            }

            telemetry.addData("Increment: ", increment);
            telemetry.addData("Intended Pos: ", intendedPos);
            telemetry.addData("current Pos: ", shovel.getPosition());
            telemetry.update();
        }
    }
}
