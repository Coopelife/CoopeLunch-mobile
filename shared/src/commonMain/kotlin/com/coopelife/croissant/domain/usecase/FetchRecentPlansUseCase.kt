package com.coopelife.croissant.domain.usecase

import com.coopelife.croissant.data.repository.PlanRepository

expect class FetchRecentPlansUseCase(planRepository: PlanRepository)

