package com.example.cafe.infra.reader

import com.example.cafe.domain.product.Option
import com.example.cafe.domain.product.OptionGroup
import com.example.cafe.domain.product.OptionReader
import com.example.cafe.infra.repository.OptionGroupRepository
import com.example.cafe.infra.repository.OptionRepository
import org.springframework.stereotype.Component

@Component
class OptionReaderImpl(
    private val optionRepository: OptionRepository,
    private val optionGroupRepository: OptionGroupRepository
) : OptionReader {

    override fun getOptionByNameAndGroup(name: String, optionGroup: OptionGroup): Option? {
        return optionRepository.findByNameAndOptionGroup(name, optionGroup)
    }

    override fun getOptionGroupByName(name: String): OptionGroup? {
        return optionGroupRepository.findByName(name)
    }

    override fun getOptionGroups(): List<OptionGroup> {
        return optionGroupRepository.findAll()
    }

    override fun getOptionsByOptionGroup(optionGroup: OptionGroup): List<Option> {
        return optionRepository.findByOptionGroup(optionGroup)
    }
}
