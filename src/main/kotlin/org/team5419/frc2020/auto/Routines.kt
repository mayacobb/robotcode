package org.team5419.frc2020.auto

import org.team5419.fault.auto.*
import org.team5419.frc2020.auto.actions.RamseteAction
import org.team5419.fault.math.geometry.Pose2d
import org.team5419.fault.math.geometry.Vector2
import org.team5419.fault.math.geometry.Pose2dWithCurvature
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.trajectory.DefaultTrajectoryGenerator
import org.team5419.fault.trajectory.constraints.TimingConstraint
import org.team5419.frc2020.subsystems.Drivetrain
import org.team5419.frc2020.auto.actions.*
import org.team5419.frc2020.subsystems.Hood.HoodPosititions
import org.team5419.frc2020.DriveConstants
import edu.wpi.first.wpilibj.Filesystem
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil
import edu.wpi.first.wpilibj.trajectory.Trajectory
import java.nio.file.Path

fun generateRoutines (initalPose: Pose2d): Array<Routine>{
    return arrayOf<Routine> (
        Routine("Trech", initalPose,
            // // shoog from starting position
            // HoodAction(HoodPosititions.AUTO),
            // TimedShoogAction(5.seconds),

            // // bring hood back down and turn intake on
            // HoodAction(HoodPosititions.RETRACT),
            // IntakeAction(true),

            // navigate to behind trench
            RamseteAction( arrayOf<Pose2d>(
                Pose2d(0.0.meters,   0.0.meters, 0.0.radians),
                Pose2d(2.0.meters, -1.55.meters, 0.0.radians),
                Pose2d(8.0.meters, -1.50.meters, 0.0.radians)
            ) )//,

            // // turn the intake off and lift the hood
            // IntakeAction(false),
            // HoodAction(HoodPosititions.FAR),

            // // align and shoog
            // AutoAlignAction(),
            // TimedShoogAction(6.seconds)
        ),
        Routine("Align", initalPose, AutoAlignAction())
    )
}
