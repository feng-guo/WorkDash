package com.example.workdash.models

data class CurrentEmployeeModel (
    var jobId: String,
    var checkInState: String,
    var checkOutState: String,
    var checkInTime: String,
    var checkOutTime: String,
    var employeeName: String,
    var PhoneNum: String,
    var uid: String
) {
    constructor() : this("","false","false","","","","","")
}
