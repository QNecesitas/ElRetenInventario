package com.qnecesitas.elreteninventario.auxiliary

class FragmentsInfo {
    companion object{

        enum class EFragments { FR_SHELVES, FR_DRAWERS, FR_SESSION, AC_PRODUCTS }
        var LAST_FRAGMENT_TOUCHED: EFragments = EFragments.FR_SHELVES

        /**Note: this CODE constants are doing references to the code sent to the next fragmente
         * Example: when de LAST_FRAGMENT selected is FR_DRAWER the last code sent him is the SHELVES_CODE
         **/
        var LAST_CODE_SHELVES_SENDED: String = "no"
        var LAST_CODE_DRAWER_SENDED: String = "no"
        var LAST_CODE_SESSION_SENDED: String = "no"

        var LAST_CODE_SHELVES_LS_SENDED: String = "no"
        var LAST_CODE_DRAWER_LS_SENDED: String = "no"
        var LAST_CODE_SESSION_LS_SENDED: String = "no"

    }
}