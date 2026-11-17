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
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Windmill;


@Autonomous
public class ShootRed extends OpMode {

    Follower follower;
    PoseFactory poseFactory = PoseFactory.degrees();

    Launcher launcher;
    Windmill windmill;

    Pose startPose = poseFactory.of(56, 9, 90);
    Pose shootPose = poseFactory.of(56, 24, 90);
    Pose controlPose = poseFactory.of(8.26, 54.13, 0);
    Pose parkPose = poseFactory.of(9.22, 98.08, 90);

    ElapsedTime timer = new ElapsedTime();

    private Path startToShoot() {
        return line(startPose, shootPose).linear(startPose, shootPose);
    }

    private Path shootToPark() {
        return curve(shootPose, controlPose, parkPose).linear(shootPose, parkPose);
    }

    public Command shootThree = Command.build() // TODO: test to make sure robot actually shoots three balls
            .setStart(() -> {
                timer.reset();
            })
            .setExecute(() -> {
                launcher.startLauncher();
                if (launcher.isTargetVelocity()) {
                    windmill.startWindmill();
                }
                else {
                    windmill.stopWindmill();
                }
            })
            .setDone(() -> timer.seconds() >= 6)
            .setEnd(endCondition -> {
                launcher.stopLauncher();
                windmill.stopWindmill();
            });

    private Command routine() {
        return sequential(
                follow(follower, startToShoot()),
                shootThree,
                follow(follower, shootToPark())
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
