package com.tapac1k.training.contract_ui

import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.utils.common.WithBackNavigation

interface TrainingTagNavigation: WithBackNavigation {
    fun createTag()
    fun ediTag(tag: TrainingTag)
}