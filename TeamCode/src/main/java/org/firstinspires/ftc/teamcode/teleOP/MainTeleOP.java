package org.firstinspires.ftc.teamcode.teleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Shovel;
import org.firstinspires.ftc.teamcode.subsystems.Windmill;

@TeleOp
public class MainTeleOP extends LinearOpMode {


    enum SystemState {
        IDLE,
        INTAKE,
        SHOOT,
        REVERSE
    }

    SystemState systemState;

    Drive drive;
    Intake intake;
    Windmill windmill;
    Launcher launcher;
    Shovel shovel;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new Drive(hardwareMap);
        intake = new Intake(hardwareMap);
        windmill = new Windmill(hardwareMap);
        launcher = new Launcher(hardwareMap);
        shovel = new Shovel(hardwareMap);
        systemState = SystemState.IDLE;

        double forward;
        double strafe;
        double rotate;

        waitForStart();
        shovel.fold();

        while (opModeIsActive()) {
            forward = gamepad1.left_stick_y;
            strafe = -gamepad1.left_stick_x;
            rotate = -gamepad1.right_stick_x;
            drive.drive(forward, rotate, strafe);

            if (gamepad1.rightBumperWasPressed()) {
                systemState = SystemState.INTAKE;
            }
            if (gamepad1.leftBumperWasPressed()) {
                systemState = SystemState.SHOOT;
            }
            if (gamepad1.bWasPressed()) {
                systemState = SystemState.IDLE;
            }
            if (gamepad1.xWasPressed()) {
                systemState = SystemState.REVERSE;
            }

            if (gamepad1.rightTriggerWasPressed()) {
                shovel.toggle();
            }

            launcher.idleLauncher();


            updateState();

            telemetry.addData("launcher current velocity: ", launcher.getVelocity());
            telemetry.addData("target Velocity: ", launcher.TARGET_VELOCITY);
            telemetry.addData("is target Velocity: ", launcher.isTargetVelocity());
            telemetry.addData("shovel Pos: ", shovel.getPosition());
            telemetry.update();
        }

    }

    public void updateState() {
        switch (systemState) {
            case IDLE:
                intake.stopIntake();
                intake.stopIntakeWheels();
               // launcher.stopLauncher();
                launcher.idleLauncher();
                windmill.stopWindmill();
                break;
            case INTAKE:
                intake.startIntake();
                intake.startIntakeWheels();
                windmill.stopWindmill();
                //launcher.stopLauncher();
                launcher.idleLauncher();
                break;
            case SHOOT:
                launcher.startLauncher();
                if (launcher.isTargetVelocity()) {
                    windmill.startWindmill();
                }
                else {
                    windmill.stopWindmill();
                }
                break;
            case REVERSE:
                intake.intakeReverse();
                //launcher.stopLauncher();
                launcher.idleLauncher();
                break;
        }
    }
}
