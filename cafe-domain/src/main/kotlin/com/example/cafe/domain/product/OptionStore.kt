package com.example.cafe.domain.product

interface OptionStore {

    fun storeOption(option: Option): Option

    fun storeOptionGroup(optionGroup: OptionGroup): OptionGroup
}
