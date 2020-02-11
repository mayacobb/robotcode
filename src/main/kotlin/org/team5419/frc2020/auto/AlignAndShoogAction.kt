package org.team5419.frc2020.auto

import org.team5419.fault.auto.SerialAction
import org.team5419.frc2020.subsystems.Shooger
import org.team5419.frc2020.ShoogerConstants

class AlignAndShoogAction : SerialAction(
    AutoAlignAction(),
    ShoogAction()
) {}
