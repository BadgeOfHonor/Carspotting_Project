package parking

class Carspotting(_spotNumber: Int) {
    val spotNumber: Int = _spotNumber
    var spotList: HashMap<Int, Car> = HashMap(spotNumber)


    fun findEmptySpot(): Int {
        val sptList = mutableListOf<Int>()
        for (i in 1..spotNumber) {
            if (spotList[i] == null) sptList.add(i)
        }
        return if (sptList.any()) sptList.first() else 0
    }

    fun Int.isAvailable(_spot: Int = toInt()): Boolean {
        return (_spot != 0)
    }

    fun park(
        _carRegNumber: String,
        _carColor: String
    ) {
        val spot = findEmptySpot()
        if (spot.isAvailable()) {
            spotList[spot] = Car(_carRegNumber, _carColor)
            println("${spotList[spot]!!.carColor} car parked in spot $spot.")
        } else {
            println("Sorry, the parking lot is full.")
        }

    }

    fun Int.isComplete(_spot: Int = toInt()): Boolean {
        return (spotList[_spot] != null)
    }


    fun leave(
        _spot: Int
    ) {
        if (_spot.isComplete()) {
            spotList.remove(_spot)
            println("Spot $_spot is free.")
        } else {
            println("There is no car in spot $_spot.")
        }
    }

    fun status() {
        if (spotList.isEmpty()) {
            println("Parking lot is empty.")
        }else {
            for (i in spotList) {
                println("${i.key} ${i.value.infoCar()}")
            }
        }
    }

    fun reg_by_color(_color: String) {
        val colorList = spotList.filter { it.value.carColor.lowercase() == _color.lowercase() }.values
        val regList = mutableListOf<String>()
        for (i in colorList) {
            regList.add(i.regNumberCar)
        }
        println(if (regList.isEmpty()) "No cars with color $_color were found." else regList.joinToString(", "))
    }
    fun spot_by_color(_color: String) {
        val colorList = spotList.filter { it.value.carColor.lowercase() == _color.lowercase() }.keys
        println(if (colorList.isEmpty()) "No cars with color $_color were found." else colorList.joinToString(", "))
    }
    fun spot_by_reg(_reg: String) {
        val carSpot = (spotList.filter { it.value.regNumberCar == _reg }).keys.firstOrNull() ?: 0
        println(if (carSpot == 0) "No cars with registration number $_reg were found." else carSpot)
    }



    //------CAR-------------------------------------------------------------------------
    class Car {
        constructor () {
            regNumberCar = ""
            carColor = ""
        }

        constructor (_carRegNumber: String, _carColor: String) : this() {
            regNumberCar = _carRegNumber
            carColor = _carColor
        }

        var regNumberCar: String
        var carColor: String


        fun infoCar(): String {
            return "$regNumberCar $carColor"
        }
    }
    //-----CAR----------------------------------------------------------------------------
}



fun main() {
    var carspotting: Carspotting = Carspotting(0)

    do {
        val input = readln().split(" ")
        when (input.first()) {
            "create" -> {
                carspotting = Carspotting(input.last().toInt())
                println("Created a parking lot with ${carspotting.spotNumber} spots.")
            }
            "exit" -> return
            else -> {
                println("Sorry, a parking lot has not been created.")
            }
        }
    } while (carspotting.spotNumber == 0)



    do {
        val input = readln().split(" ")
        when (input.first()) {
            "park" -> carspotting.park(input[1], input[2])
            "leave" -> carspotting.leave(input[1].toInt())
            "status" -> carspotting.status()
            "create" -> {
                carspotting = Carspotting(input.last().toInt())
                println("Created a parking lot with ${carspotting.spotNumber} spots.")
            }
            "reg_by_color" -> carspotting.reg_by_color(input.last())
            "spot_by_color" -> carspotting.spot_by_color(input.last())
            "spot_by_reg" -> carspotting.spot_by_reg(input.last())
        }
    } while (input.first() != "exit")
}
