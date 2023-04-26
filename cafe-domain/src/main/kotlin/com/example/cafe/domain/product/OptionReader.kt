package com.example.cafe.domain.product

interface OptionReader {

    fun getOptionByNameAndGroup(name: String, optionGroup: OptionGroup): Option?

    fun getOptionGroupByName(name: String): OptionGroup?

    fun getOptionGroups(): List<OptionGroup>

    fun getOptionsByOptionGroup(optionGroup: OptionGroup): List<Option>
}
