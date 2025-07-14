package de.peterpunch.relink.domain.repository

import de.peterpunch.relink.domain.entity.ShortLink
import org.springframework.data.repository.CrudRepository

interface ShortLinkRepo : CrudRepository<ShortLink, Long> {
}
