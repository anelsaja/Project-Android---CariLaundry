package com.example.carilaundry.ui.feature.owner.detail_pesanan

import com.example.carilaundry.domain.model.OwnerOrderDetail

data class DetailOwnerUiState(
    val isLoading: Boolean = false,
    val detail: OwnerOrderDetail? = null,
    val errorMessage: String? = null
)