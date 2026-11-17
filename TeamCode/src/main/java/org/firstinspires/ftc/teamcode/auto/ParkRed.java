package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Drive;

@Autonomous
public class ParkRed extends OpMode {
    Drive drive;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        drive = new Drive(hardwareMap);
    }

    public void start() {
        timer.reset();
    }

    @Override
    public void loop() {
        while (timer.seconds() <= 1) {
            drive.drive(0, 0, -0.3);
        }
        drive.drive(0, 0, 0);
    }
}
