package com.example.core.data.utils

import com.example.core.data.local.CharacterEntity
import com.example.core.data.remote.Characters
import com.example.core.domain.model.CharacterDomain

fun Characters.toDomain() = CharacterDomain(
    id = id,
    name = name,
    status = status,
    species = species,
    gender = gender,
    image = image,
)

fun CharacterEntity.toDomain() = CharacterDomain(
    id = id,
    name = name,
    status = status,
    species = species,
    gender = gender,
    image = image,
)

fun CharacterDomain.toEntity() = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    gender = gender,
    image = image
)
