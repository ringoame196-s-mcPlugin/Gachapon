package com.github.ringoame196_s_mcPlugin

enum class GachaponItemId(val id: String) {
    NO_CLICK_ITEM("no_click_item"),
    SPIN_ITEM("spain");

    companion object {
        fun fromId(id: String?): GachaponItemId? {
            return id?.let { targetId -> values().find { it.id == targetId } }
        }
    }
}
