package com.example.mytranslator.utils

import com.example.mytranslator.model.data.WordData
import com.example.mytranslator.model.data.dto.DataModelDTO

class InetMapper {

    private fun map(dataModelDTO: DataModelDTO) =
        WordData(
            word = dataModelDTO.text ?: String.EMPTY,
            translation = dataModelDTO.meanings?.first()?.translationDTO?.text ?: String.EMPTY,
            imageUrl = dataModelDTO.meanings?.first()?.imageUrl ?: String.EMPTY
        )

    fun map(dataModels: List<DataModelDTO>) = dataModels.map { model -> map(model) }
}