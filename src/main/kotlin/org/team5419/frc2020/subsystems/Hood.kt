package org.team5419.frc2020.subsystems

import org.team5419.frc2020.tab
import org.team5419.frc2020.HoodConstants
import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.math.units.native.NativeUnitRotationModel
import org.team5419.fault.math.units.native.*
import org.team5419.fault.math.units.derived.*
import org.team5419.fault.math.units.*
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import kotlin.math.PI
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.networktables.EntryListenerFlags
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.ControlMode

object Hood : Subsystem("Hood") {
    // motor

    private val hoodMotor = TalonSRX(HoodConstants.HoodPort)
        .apply {
            // reset
            configFactoryDefault(100)

            // config PID constants
            config_kP(0, HoodConstants.PID.P, 100)
            config_kI(0, HoodConstants.PID.I, 100)
            config_kD(0, HoodConstants.PID.D, 100)

            // make sure it dosent go to fast
            configClosedLoopPeakOutput(0, HoodConstants.MaxSpeed, 100)

            // limit the current to not brown out
            configPeakCurrentLimit(40, 100)

            // config the sensor and direction
            configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100)
            setSensorPhase(true)
            setInverted(false)

            // config the soft limits
            configForwardSoftLimitThreshold( angleToNativeUnits( HoodConstants.MaxAngle ).toInt(), 100)
            configForwardSoftLimitEnable(true, 100)
            configReverseSoftLimitThreshold( angleToNativeUnits( 0.0 ).toInt(), 100)
            configReverseSoftLimitEnable(true, 100)

            // reset the sensor
            setSelectedSensorPosition(0, 0, 100)
        }

    // hood positions

    public enum class HoodPosititions(public val angle: Double) {
        FAR(15.0),
        CLOSE(5.0),
        RETRACT(0.0)
    }

    // public api

    private val nativeUnitsToAngle = HoodConstants.GearRatio / HoodConstants.TicksPerRotation * (2 * PI)

    fun angleToNativeUnits(angle: Double) = angle / nativeUnitsToAngle

    fun hoodAngle() = hoodMotor.getSelectedSensorPosition(0) * nativeUnitsToAngle

    fun goto(angle: HoodPosititions) = goto( angle.angle )

    fun goto(angle: Double) {
        assert(angle >= 0.0 && angle <= HoodConstants.MaxAngle)

        val ticks = angleToNativeUnits(angle)

        hoodMotor.set(ControlMode.Position, ticks)
    }
}