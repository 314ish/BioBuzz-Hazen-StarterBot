package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Windmill;


@Autonomous
public class ShootBlue extends OpMode {

    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();

    Launcher launcher;
    Windmill windmill;
    Intake intake;

    Pose startPose = poseFactory.of(81.5, 132.92, -90);
    Pose shootPose = poseFactory.of(81.5, 120.45,-90);
    Pose flowerPose = poseFactory.of(131.5, 95.16, 180);
    Pose backUpPose = poseFactory.of(118.5, 95.16, 180);
    Pose shoot2ControlPose = poseFactory.of(132.25, 38.27, 0);
    Pose shootPose2 = poseFactory.of(81.5, 20.12, 90);
    Pose parkControlPose = poseFactory.of(123.57, 97.97, 0);
    Pose parkPose = poseFactory.of(131.31, 42.46, 90);

    ElapsedTime timer = new ElapsedTime();

    private Path startToShoot() {
        return line(startPose, shootPose).linear(startPose, shootPose);
    }

    private Path shootToFlower() {
        return line(shootPose, flowerPose).linear(shootPose, flowerPose);
    }

    private Path FlowerToBackUp() {
        return line(flowerPose, backUpPose).linear(flowerPose, backUpPose);
    }

    private Path BackUpToShoot2() {
        return curve(backUpPose, shoot2ControlPose, shootPose2).linear(backUpPose, shootPose2);
    }

    private Path shoot2ToPark() {
        return line(shootPose2, parkPose).linear(shootPose2, parkPose);
    }

    public Command shootThree = Command.build() // TODO: test to make sure robot actually shoots three balls
            .setStart(() -> timer.reset())
            .setExecute(() -> {
                launcher.startLauncher();
                if (launcher.isTargetVelocity()) {
                    windmill.startWindmill();
                }
                else {
                    windmill.stopWindmill();
                }
            })
            .setDone(() -> timer.seconds() >= 5)
            .setEnd(endCondition -> {
                launcher.stopLauncher();
                windmill.stopWindmill();
            });

    public Command intakeFlower = Command.build()
            .setStart(() -> timer.reset())
            .setExecute(() -> {
                intake.startIntake();
                intake.startIntakeWheels();
            })
            .setDone(() -> timer.seconds() >= 6)
            .setEnd(endCondition -> {
                intake.stopIntake();
                intake.stopIntakeWheels();
            });

    private Command routine() {
        return sequential(
                follow(follower, startToShoot()),
                shootThree,
                follow(follower, shootToFlower()),
                intakeFlower
        );
    }


    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);

        windmill = new Windmill(hardwareMap);
        launcher = new Launcher(hardwareMap);
    }

    public void start() {
        schedule(routine());
    }


    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}
