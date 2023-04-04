package com.example.repository.mappers

import com.example.core.messages.WordData
import com.example.repository.retrofit.dto.DataModelDTO
import com.example.utils.EMPTY

class InetMapper {

    private fun map(dataModelDTO: DataModelDTO) =
        WordData(
            word = dataModelDTO.text ?: String.EMPTY,
            translation = dataModelDTO.meanings?.first()?.translationDTO?.text ?: String.EMPTY,
            imageUrl = dataModelDTO.meanings?.first()?.imageUrl ?: String.EMPTY
        )

    fun map(dataModels: List<DataModelDTO>) = dataModels.map { model -> map(model) }
}