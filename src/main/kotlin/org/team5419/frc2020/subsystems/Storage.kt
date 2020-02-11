package org.team5419.frc2020.subsystems

import org.team5419.frc2020.StorageConstants
import org.team5419.frc2020.ShoogerConstants
import org.team5419.fault.subsystems.Subsystem
import edu.wpi.first.wpilibj.shuffleboard.*
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.AnalogInput
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.ControlMode

enum class StorageMode() { LOAD, PASSIVE, OFF }

object Storage : Subsystem("Storage") {


    var mode = StorageMode.OFF
        set(mode: StorageMode) {
            if (mode == field) return

            if (mode == StorageMode.LOAD) {
                hopper.set( ControlMode.PercentOutput, hopperPercent )
                feeder.set( ControlMode.PercentOutput, feederPercent )
            }

            if (mode == StorageMode.PASSIVE) {
                hopper.set( ControlMode.PercentOutput, 0.0 )
                feeder.set( ControlMode.PercentOutput, hopperLazyPercent )
            }

            if (mode == StorageMode.OFF) {
                hopper.set( ControlMode.PercentOutput, 0.0 )
                feeder.set( ControlMode.PercentOutput, 0.0 )
            }

            field = mode
        }

    // motors
    private val feeder = TalonSRX(StorageConstants.FeederPort)
        .apply {
            setInverted(true)
            setNeutralMode(NeutralMode.Brake)
        }

    private val hopper = TalonSRX(StorageConstants.HopperPort)
        .apply {
            setInverted(true)
        }

    // default settings
    private var hopperPercent = StorageConstants.HopperPercent
    private var feederPercent = StorageConstants.FeederPercent

    private var feederLazyPercent = feederPercent
    private var hopperLazyPercent = StorageConstants.HopperLazyPercent

    // distance sensor to find balls
    private var distanceSensor: AnalogInput = AnalogInput(1)
    private val range: Double
        get() = 5.0 * distanceSensor.getVoltage() / 0.004883


    // subsystem functions
    fun reset() {
        mode = StorageMode.OFF
    }

    override fun autoReset() = reset()
    override fun teleopReset() = reset()

    override public fun periodic() {
        if (mode == StorageMode.PASSIVE) {
            feeder.set(
                ControlMode.PercentOutput,
                if (range <= 152.0) feederLazyPercent else 0.0
            )
        }
    }
}
