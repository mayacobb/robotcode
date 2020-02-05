package org.team5419.frc2020.subsystems

import org.team5419.fault.subsystems.Subsystem
import org.team5419.fault.hardware.ctre.BerkeliumSRX
import org.team5419.frc2020.IntakeConstants
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.ControlMode

object Intake : Subsystem("Intake") {
    private var doesIntake = false
    private val intakeMotor = BerkeliumSRX(IntakeConstants.kIntakePort, IntakeConstants.kIntakeModel)
    private val deployMotor = BerkeliumSRX(IntakeConstants.kDeployPort, IntakeConstants.kIntakeModel)

    init{
        deployMotor.brakeMode = true
    }

    public var isIntake: Boolean = false
        set (value: Boolean) {
            if(value && !field){
                setIntake(1.0)
            }
            if(!value && field){
                setIntake(0.0)
            }
            field = value
        }

    public fun setIntake(percent: Double){
        intakeMotor.talonSRX.set(ControlMode.PercentOutput, percent)
    }

    public fun setDeploy(percent: Double){
        deployMotor.talonSRX.set(ControlMode.PercentOutput, percent)
    }

}
