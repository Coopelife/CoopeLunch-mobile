package com.coopelife.croissant.domain.usecase

import com.coopelife.croissant.data.entitiy.Plan
import com.coopelife.croissant.data.repository.PlanRepository

actual class FetchMyPlansUseCase actual constructor(
    private val planRepository: PlanRepository
) {
    suspend fun fetchMyPlans(): List<Plan> = planRepository.fetchMyPlans()
}
