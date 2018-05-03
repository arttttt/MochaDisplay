package com.arttt.mochadisplay.utils

class ColorUtils {
    companion object {
        val instance by lazy {
            ColorUtils()
        }
    }
    fun getRGBFromK(temperature: Int): ColorCompat {
        var x = temperature / 1000.0
        if (x > 40) {
            x = 40.0
        }
        var red: Double
        var green: Double
        var blue: Double

        // R
        red = when {
            temperature < 6527 -> 1.0
            else -> {
                val redPoly = doubleArrayOf(4.93596077e0, -1.29917429e0, 1.64810386e-01, -1.16449912e-02, 4.86540872e-04, -1.19453511e-05, 1.59255189e-07, -8.89357601e-10)
                poly(redPoly, x)
            }
        }
        // G
        green = when {
            temperature < 850 -> 0.0
            temperature <= 6600 -> {
                val greenPoly = doubleArrayOf(-4.95931720e-01, 1.08442658e0, -9.17444217e-01, 4.94501179e-01, -1.48487675e-01, 2.49910386e-02, -2.21528530e-03, 8.06118266e-05)
                poly(greenPoly, x)
            }
            else -> {
                val greenPoly = doubleArrayOf(3.06119745e0, -6.76337896e-01, 8.28276286e-02, -5.72828699e-03, 2.35931130e-04, -5.73391101e-06, 7.58711054e-08, -4.21266737e-10)
                poly(greenPoly, x)
            }
        }
        // B
        blue = when {
            temperature < 1900 -> 0.0
            temperature < 6600 -> {
                val bluePoly = doubleArrayOf(4.93997706e-01, -8.59349314e-01, 5.45514949e-01, -1.81694167e-01, 4.16704799e-02, -6.01602324e-03, 4.80731598e-04, -1.61366693e-05)
                poly(bluePoly, x)
            }
            else -> 1.0
        }

        red = clamp(red, 0.0, 1.0)
        green = clamp(green, 0.0, 1.0)
        blue = clamp(blue, 0.0, 1.0)
        return ColorCompat(red, green, blue)
    }

    private fun poly(coefficients: DoubleArray, x: Double): Double {
        var result = coefficients[0]
        var xn = x
        for (i in 1 until coefficients.size) {
            result += xn * coefficients[i]
            xn *= x

        }
        return result
    }

    private fun clamp(x: Double, min: Double, max: Double): Double {
        if (x < min) {
            return min
        }
        return if (x > max) {
            max
        } else x
    }

    data class ColorCompat(val red: Double, val green: Double, val blue: Double)
}