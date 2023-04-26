package com.example.cafe.infra.store

import com.example.cafe.domain.product.Option
import com.example.cafe.domain.product.OptionGroup
import com.example.cafe.domain.product.OptionStore
import com.example.cafe.infra.repository.OptionGroupRepository
import com.example.cafe.infra.repository.OptionRepository
import org.springframework.stereotype.Component

@Component
class OptionStoreImpl(
    private val optionRepository: OptionRepository,
    private val optionGroupRepository: OptionGroupRepository
) : OptionStore {
    override fun storeOption(option: Option): Option {
        return optionRepository.save(option)
    }

    override fun storeOptionGroup(optionGroup: OptionGroup): OptionGroup {
        return optionGroupRepository.save(optionGroup)
    }
}
