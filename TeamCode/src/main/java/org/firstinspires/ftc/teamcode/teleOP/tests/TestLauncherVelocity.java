package org.firstinspires.ftc.teamcode.teleOP.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Windmill;

@TeleOp
public class TestLauncherVelocity extends LinearOpMode {


    Drive drive;
    Windmill windmill;
    DcMotorEx launcher;

    public int target_velocity = 1250;
    private final int TOLERANCE = 20;

    private final double F = 13.6070;
    private final double P = 4.0963;

    int[] stepSizes = {100, 20, 10, 5, 1};
    int stepIndex = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new Drive(hardwareMap);
        windmill = new Windmill(hardwareMap);
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(P, 0, 0, F));

        waitForStart();
        while (opModeIsActive()) {
            // just for movement convenience
            drive.drive(gamepad1.left_stick_y, -gamepad1.right_stick_x, -gamepad1.left_stick_x);

            if (gamepad1.xWasPressed()) {
                stepIndex = (stepIndex + 1) % stepSizes.length;
            }

            if (gamepad1.dpadUpWasPressed()) {
                target_velocity += stepSizes[stepIndex];
            }
            if (gamepad1.dpadDownWasPressed()) {
                target_velocity -= stepSizes[stepIndex];
            }

            if (gamepad1.aWasPressed()) {
                launcher.setVelocity(target_velocity);
            }
            if (gamepad1.bWasPressed()) {
                launcher.setVelocity(0);
            }

            if (gamepad1.left_trigger > 0) {
                windmill.startWindmill();
            }
            else {
                windmill.stopWindmill();
            }

            telemetry.addLine("(A/B) power launcher");
            telemetry.addData("Current Velocity", launcher.getVelocity());
            telemetry.addLine("");
            telemetry.addData("Target Velocity", "%.4f (D-pad Up/Down)", target_velocity);
            telemetry.addData("Step Size", "%.4f (X Button)", stepSizes[stepIndex]);
            telemetry.update();
        }
    }
}