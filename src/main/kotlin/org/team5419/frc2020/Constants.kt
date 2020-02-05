package org.team5419.frc2020

import org.team5419.fault.math.physics.DCMotorTransmission
import org.team5419.fault.math.physics.DifferentialDrive
import org.team5419.fault.math.geometry.Rotation2d
import org.team5419.fault.math.units.derived.acceleration
import org.team5419.fault.math.units.derived.velocity
import org.team5419.fault.math.units.derived.radians
import org.team5419.fault.math.units.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.kEpsilon
import kotlin.math.PI
import kotlin.math.pow

object RobotConstants {
    val Mass = 120.lbs
    val Length = 32.inches
    val Width = 27.5.inches
    val BumperThickness = 2.inches
}

object DriveConstants {
    // ports

    const val LeftMasterPort = 10
    const val LeftSlavePort = 11

    const val RightMasterPort = 8
    const val RightSlavePort = 6

    const val GyroPort = 7

    // configuration

    const val EncoderPhase = true

    // path following parameters

    const val Beta = 1.0 // m^-2
    const val Zeta = 1.0 // unitless

    val MaxVelocity = 10.0.feet.velocity
    val MaxAcceleration = 4.0.feet.acceleration
    val MaxCentripetalAcceleration = 4.0.feet.acceleration
    val MaxAngularAcceleration = 2.0.radians.acceleration

    // dimensions and constants

    val WheelRadius = 3.inches
    val WheelDiameter = WheelRadius * 2.0
    val WheelCircumference = WheelDiameter * PI

    val TrackWidth = 20.inches
    val EffectiveWheelbaseRadius = TrackWidth / 2.0

    val Moi = 0.0 // kg * m^2
    val AngularDrag = 10.0 // (N * m) / (rad / s)  TUNE ME

    val TicksPerRotation = 4096.nativeUnits
    val PigeonConversion = (3600.0 / 8192.0).nativeUnits

    const val DriveKv = kEpsilon
    const val DriveKa = kEpsilon
    const val DriveKs = kEpsilon

    val LeftDriveGearbox = DCMotorTransmission(
        1 / DriveKv,
        WheelRadius.value.pow(2) * RobotConstants.Mass.value / (2.0 * DriveKa),
        DriveKs
    )

    val RightDriveGearbox = DCMotorTransmission(
        1 / DriveKv,
        WheelRadius.value.pow(2) * RobotConstants.Mass.value / (2.0 * DriveKa),
        DriveKs
    )

    val DriveModel = DifferentialDrive(
        RobotConstants.Mass.value,
        Moi,
        AngularDrag, // tune me
        WheelRadius.value,
        EffectiveWheelbaseRadius.value,
        LeftDriveGearbox,
        RightDriveGearbox
    )

    val NativeGearboxConversion = NativeUnitLengthModel(
        TicksPerRotation,
        WheelRadius
    )
}

object InputConstants {
    public const val XboxCodrivePort = 0
    public const val XboxDrivePort = 1

    public const val SlowTurnMult = 0.4
    public const val SlowMoveMult = 0.4

    public const val DriverStowTimeout = 2.0

    public const val TriggerDeadband = 0.1
    public const val JoystickDeadband = 0.07
}

object ShoogerConstants {
    public const val MasterPort = 3
    public const val SlavePort1 = 15
    public const val SlavePort2 = 16

    public const val FeederPort = 13
    public const val HopperPort = 9

    public val MaxVelocity = 6000.0.radians.velocity
    public val V = SIUnit<Frac<Volt, AngularVelocity>>(12.0/6000.0/2/PI)
    public val TicksPerRotation = (4092.0 / 3.5).nativeUnits
    public val lywheel = NativeUnitRotationModel(TicksPerRotation)
}

object HoodConstants{
    public const val HoodPort = 12

    object PID {
        public const val P = 1.0
        public const val I = 0.0
        public const val D = 0.0
    }

    public val TicksPerRotation = (4092 / 3).nativeUnits
    public val Hood = NativeUnitRotationModel(TicksPerRotation)
}

object IntakeConstants {
    public val DeployTicksPerRotation = (4096 * 81).nativeUnits
    public val DeployModel = NativeUnitRotationModel(DeployTicksPerRotation)
    public val IntakeTicksPerRotation = (4096 * 10).nativeUnits
    public val IntakeModel = NativeUnitRotationModel(IntakeTicksPerRotation)
    public const val IntakePort = 2
    public const val DeployPort = 4
}


object VisionConstants {
    public val CameraHeight = 0.0.meters
    public val TargetHeight = 0.0.meters
    public val CameraAngle = Rotation2d( 0.0.radians )

    public val Tolerance = 10.0
}
