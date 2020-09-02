package me.bytebeats.algo.designs

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/9/2 17:16
 * @version 1.0
 * @description TO-DO
 */

class DeterministicFiniteStateMachine {//剑指 offer 20
    fun isNumber(s: String): Boolean {
        val transfer = mutableMapOf<State, Map<CharType, State>>()
        val initialMap = mapOf(
            CharType.CHAR_SPACE to State.STATE_INITIAL,
            CharType.CHAR_NUMBER to State.STATE_INTEGER,
            CharType.CHAR_POINT to State.STATE_POINT_WITHOUT_INT,
            CharType.CHAR_SIGN to State.STATE_INT_SIGN
        )
        transfer[State.STATE_INITIAL] = initialMap
        val intSignMap = mapOf(
            CharType.CHAR_NUMBER to State.STATE_INTEGER,
            CharType.CHAR_POINT to State.STATE_POINT_WITHOUT_INT
        )
        transfer[State.STATE_INT_SIGN] = intSignMap
        val integerMap = mapOf(
            CharType.CHAR_NUMBER to State.STATE_INTEGER,
            CharType.CHAR_EXP to State.STATE_EXP,
            CharType.CHAR_POINT to State.STATE_POINT,
            CharType.CHAR_SPACE to State.STATE_END
        )
        transfer[State.STATE_INTEGER] = integerMap
        val pointMap = mapOf(
            CharType.CHAR_NUMBER to State.STATE_FRACTION,
            CharType.CHAR_EXP to State.STATE_EXP,
            CharType.CHAR_SPACE to State.STATE_END
        )
        transfer[State.STATE_POINT] = pointMap
        val pointWithoutSign = mapOf(
            CharType.CHAR_NUMBER to State.STATE_FRACTION
        )
        transfer[State.STATE_POINT_WITHOUT_INT] = pointWithoutSign
        val fractionMap = mapOf(
            CharType.CHAR_NUMBER to State.STATE_FRACTION,
            CharType.CHAR_EXP to State.STATE_EXP,
            CharType.CHAR_SPACE to State.STATE_END
        )
        transfer[State.STATE_FRACTION] = fractionMap
        val expMap = mapOf(
            CharType.CHAR_NUMBER to State.STATE_EXP_NUMBER,
            CharType.CHAR_SIGN to State.STATE_EXP_SIGN
        )
        transfer[State.STATE_EXP] = expMap
        val expSignMap = mapOf(
            CharType.CHAR_NUMBER to State.STATE_EXP_NUMBER
        )
        transfer[State.STATE_EXP_SIGN] = expSignMap
        val expNumberMap = mapOf(
            CharType.CHAR_NUMBER to State.STATE_EXP_NUMBER,
            CharType.CHAR_SPACE to State.STATE_END
        )
        transfer[State.STATE_EXP_NUMBER] = expNumberMap
        val endMap = mapOf(
            CharType.CHAR_SPACE to State.STATE_END
        )
        transfer[State.STATE_END] = endMap
        var state = State.STATE_INITIAL
        for (c in s) {
            val type = toCharType(c)
            if (!transfer[state]!!.containsKey(type)) {
                return false
            } else {
                state = transfer[state]!![type] ?: error("No char type state!")
            }
        }
        return state == State.STATE_INTEGER || state == State.STATE_POINT || state == State.STATE_FRACTION
                || state == State.STATE_EXP_NUMBER || state == State.STATE_END
    }

    private fun toCharType(char: Char): CharType {
        return when (char) {
            in '0'..'9' -> CharType.CHAR_NUMBER
            'e', 'E' -> CharType.CHAR_EXP
            '.' -> CharType.CHAR_POINT
            '+', '-' -> CharType.CHAR_SIGN
            ' ' -> CharType.CHAR_SPACE
            else -> CharType.CHAR_ILLEGAL
        }
    }

    enum class State {
        STATE_INITIAL,
        STATE_INT_SIGN,
        STATE_INTEGER,
        STATE_POINT,
        STATE_POINT_WITHOUT_INT,
        STATE_FRACTION,
        STATE_EXP,
        STATE_EXP_SIGN,
        STATE_EXP_NUMBER,
        STATE_END;
    }

    enum class CharType {
        CHAR_NUMBER, CHAR_EXP, CHAR_POINT, CHAR_SIGN, CHAR_SPACE, CHAR_ILLEGAL
    }
}